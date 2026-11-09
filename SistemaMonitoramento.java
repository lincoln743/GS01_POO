import java.util.ArrayList;
import java.util.Scanner;

/**
 * SENTINELA DO TERRITORIO - Monitoramento de risco de alagamento (CitizenTech).
 *
 * Classe principal com menu interativo. Le os sensores, guarda o historico do
 * nivel do rio, roda duas estrategias de previsao e emite alertas classificados
 * por severidade (ATENCAO, ALERTA, CRITICO), explicando o porque de cada um.
 */
public class SistemaMonitoramento {

    private final DadosTerritorio dados;
    private final EstacaoDeColeta estacao;
    private final PainelInformativo painel;
    private final SensorNivelRio sensorRio;
    private final ArrayList<Double> historicoNivelRio;
    private final EstrategiaPrevisao mediaMovel;
    private final EstrategiaPrevisao comparacao;

    public SistemaMonitoramento() {
        // Dados do territorio: regiao, populacao, limiares (m) e codigo de acesso.
        this.dados = new DadosTerritorio("Bairro Ribeira", 12000, 4.0, 5.5, 7.0, 2024);

        // Sensores (implementam a interface Sensor).
        this.sensorRio = new SensorNivelRio("RIO-01", 5.5);
        SensorChuva sensorChuva = new SensorChuva("CHU-01", 50.0);
        SensorUmidade sensorUmidade = new SensorUmidade("UMI-01", 85.0);

        // Estacao de coleta (componente do territorio) com os sensores a bordo.
        this.estacao = new EstacaoDeColeta(1, "Estacao Ponte Velha", "Bairro Ribeira");
        estacao.instalarSensor(sensorRio);
        estacao.instalarSensor(sensorChuva);
        estacao.instalarSensor(sensorUmidade);

        // Painel informativo para comunicar a populacao.
        this.painel = new PainelInformativo(2, "Painel Praca Central", "Bairro Ribeira");

        // Estrategias de previsao.
        this.mediaMovel = new PrevisaoMediaMovel(3);
        this.comparacao = new PrevisaoComparacao(3);

        // Historico inicial do nivel do rio (metros).
        this.historicoNivelRio = new ArrayList<>();
        double[] inicial = { 2.0, 2.3, 2.6, 2.9, 3.2 };
        for (double v : inicial) {
            historicoNivelRio.add(v);
        }
    }

    // ===================== MENU PRINCIPAL =====================

    public void iniciar() {
        Scanner sc = new Scanner(System.in);
        int opcao = -1;
        System.out.println("============================================================");
        System.out.println("  SENTINELA DO TERRITORIO - Risco de Alagamento (CitizenTech)");
        System.out.println("============================================================");

        while (opcao != 0) {
            System.out.println("\n--------------------- MENU ---------------------");
            System.out.println("1 - Verificar sensores");
            System.out.println("2 - Rodar previsoes e alertas");
            System.out.println("3 - Gerenciar dados do territorio");
            System.out.println("4 - Simular proximas leituras");
            System.out.println("5 - Exibir status completo");
            System.out.println("6 - Simular 24 horas (demonstracao)");
            System.out.println("0 - Sair");
            opcao = lerInteiro(sc, "Escolha uma opcao: ");

            switch (opcao) {
                case 1: verificarSensores(); break;
                case 2: rodarPrevisoes(); break;
                case 3: gerenciarDados(sc); break;
                case 4: simularProximasLeituras(sc); break;
                case 5: exibirStatusCompleto(); break;
                case 6: simular24Horas(); break;
                case 0: System.out.println("Encerrando o Sentinela. Ate logo!"); break;
                default: System.out.println("Opcao invalida.");
            }
        }
        sc.close();
    }

    // ===================== OPCAO 1 - SENSORES =====================

    private void verificarSensores() {
        System.out.println("\n== Leitura dos sensores ==");
        for (Sensor s : estacao.getSensores()) {
            boolean ok = s.verificarFuncionamento();
            if (!ok) {
                System.out.println("- " + s.getTipo() + " (" + s.getUnidade() + "): FALHA no sensor.");
                continue;
            }
            double valor = s.lerValor();
            String aviso = s.acimaDoLimite(valor)
                    ? "  >> ACIMA do limite de " + s.getLimiteAlerta() + " " + s.getUnidade()
                    : "";
            System.out.println("- " + s.getTipo() + ": " + valor + " " + s.getUnidade() + aviso);
        }
    }

    // ===================== OPCAO 2 - PREVISOES + ALERTAS =====================

    private void rodarPrevisoes() {
        System.out.println("\n== Previsao do nivel do rio ==");
        System.out.println("Leituras no historico: " + historicoNivelRio.size());

        double p1 = mediaMovel.prever(historicoNivelRio);
        double p2 = comparacao.prever(historicoNivelRio);

        avaliarPrevisao(mediaMovel, p1);
        avaliarPrevisao(comparacao, p2);
    }

    /** Mostra a previsao de uma estrategia e dispara o alerta correspondente. */
    private void avaliarPrevisao(EstrategiaPrevisao estrategia, double previsto) {
        if (previsto < 0) {
            System.out.println("- " + estrategia.getNome()
                    + ": dados insuficientes (minimo " + estrategia.getMinimoLeituras()
                    + " leituras). O sistema prefere se calar a arriscar um palpite.");
            return;
        }
        System.out.println("- " + estrategia.getNome() + ": nivel previsto = " + previsto + " m");
        emitirAlerta(estrategia.getNome(), previsto);
    }

    /**
     * Sistema de alertas: compara a previsao com os limiares do territorio e
     * classifica a severidade, explicando o porque.
     */
    private void emitirAlerta(String origem, double previsto) {
        String nivel;
        double limiteEstourado;

        if (previsto >= dados.getLimiarCritico()) {
            nivel = "CRITICO";
            limiteEstourado = dados.getLimiarCritico();
        } else if (previsto >= dados.getLimiarAlerta()) {
            nivel = "ALERTA";
            limiteEstourado = dados.getLimiarAlerta();
        } else if (previsto >= dados.getLimiarAtencao()) {
            nivel = "ATENCAO";
            limiteEstourado = dados.getLimiarAtencao();
        } else {
            System.out.println("   Situacao NORMAL: " + previsto + " m abaixo do limiar de atencao ("
                    + dados.getLimiarAtencao() + " m).");
            return;
        }

        String msg = "[" + nivel + "] Nivel do rio previsto (" + previsto
                + " m) ultrapassa o limiar de " + nivel + " (" + limiteEstourado
                + " m). Fonte: previsao por " + origem + ".";
        System.out.println("   " + msg);
        // Comunica a populacao pelo painel informativo.
        painel.exibirMensagem(nivel + ": risco de alagamento no " + dados.getRegiao()
                + ". Nivel previsto " + previsto + " m.");
    }

    // ===================== OPCAO 3 - DADOS DO TERRITORIO =====================

    private void gerenciarDados(Scanner sc) {
        System.out.println("\n== Dados do territorio ==");
        System.out.println("Regiao: " + dados.getRegiao());
        System.out.println("Populacao: " + dados.getPopulacao());
        System.out.println("Limiares (m) -> Atencao: " + dados.getLimiarAtencao()
                + " | Alerta: " + dados.getLimiarAlerta() + " | Critico: " + dados.getLimiarCritico());

        System.out.println("\n1 - Alterar populacao");
        System.out.println("2 - Alterar limiares (requer codigo de acesso)");
        System.out.println("0 - Voltar");
        int op = lerInteiro(sc, "Opcao: ");

        if (op == 1) {
            int nova = lerInteiro(sc, "Nova populacao: ");
            dados.setPopulacao(nova);
        } else if (op == 2) {
            int codigo = lerInteiro(sc, "Codigo de acesso: ");
            double at = lerDouble(sc, "Limiar de ATENCAO (m): ");
            double al = lerDouble(sc, "Limiar de ALERTA (m): ");
            double cr = lerDouble(sc, "Limiar CRITICO (m): ");
            dados.alterarLimiares(codigo, at, al, cr);
        }
    }

    // ===================== OPCAO 4 - SIMULAR LEITURAS =====================

    private void simularProximasLeituras(Scanner sc) {
        int n = lerInteiro(sc, "Quantas leituras simular? ");
        if (n <= 0) {
            System.out.println("Quantidade invalida.");
            return;
        }
        for (int i = 0; i < n; i++) {
            double nova = gerarProximoNivel(0.4); // leve tendencia de subida
            historicoNivelRio.add(nova);
            System.out.println("Nova leitura do rio: " + nova + " m");
        }
    }

    /** Gera a proxima leitura do nivel do rio a partir da ultima, com tendencia. */
    private double gerarProximoNivel(double tendencia) {
        double ultimo = historicoNivelRio.get(historicoNivelRio.size() - 1);
        double novo = ultimo + tendencia + (Math.random() - 0.4) * 0.3;
        novo = Math.max(0.5, Math.min(9.0, novo));
        return Math.round(novo * 100.0) / 100.0;
    }

    // ===================== OPCAO 5 - STATUS COMPLETO =====================

    private void exibirStatusCompleto() {
        System.out.println("\n== Status completo do territorio ==");
        System.out.println(estacao.verificarStatus());
        System.out.println(painel.verificarStatus());
        System.out.println("Populacao exposta: " + dados.getPopulacao() + " habitantes em " + dados.getRegiao());
        double ultimo = historicoNivelRio.get(historicoNivelRio.size() - 1);
        System.out.println("Ultima leitura do rio: " + ultimo + " m");
        double previsto = mediaMovel.prever(historicoNivelRio);
        if (previsto >= 0) {
            System.out.println("Previsao (media movel): " + previsto + " m");
            emitirAlerta(mediaMovel.getNome(), previsto);
        }
    }

    // ===================== OPCAO 6 - SIMULACAO 24H (BONUS) =====================

    private void simular24Horas() {
        System.out.println("\n== Simulacao de 24 horas ==");
        System.out.println("Hora | Nivel (m) | Previsao media movel | Alerta");
        for (int hora = 1; hora <= 24; hora++) {
            double nivel = gerarProximoNivel(0.18); // subida gradual ao longo do dia
            historicoNivelRio.add(nivel);
            double previsto = mediaMovel.prever(historicoNivelRio);
            String nivelAlerta = classificar(previsto);
            System.out.printf("%4dh | %8.2f | %18.2f | %s%n", hora, nivel, previsto, nivelAlerta);
        }
        System.out.println("Fim da simulacao de 24h.");
    }

    /** Apenas classifica a severidade (sem imprimir a explicacao completa). */
    private String classificar(double previsto) {
        if (previsto < 0) return "sem dados";
        if (previsto >= dados.getLimiarCritico()) return "CRITICO";
        if (previsto >= dados.getLimiarAlerta()) return "ALERTA";
        if (previsto >= dados.getLimiarAtencao()) return "ATENCAO";
        return "normal";
    }

    // ===================== LEITURA DE ENTRADA =====================

    private int lerInteiro(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            String linha = sc.hasNextLine() ? sc.nextLine().trim() : "0";
            try {
                return Integer.parseInt(linha);
            } catch (NumberFormatException e) {
                System.out.print("Digite um numero inteiro: ");
            }
        }
    }

    private double lerDouble(Scanner sc, String prompt) {
        System.out.print(prompt);
        while (true) {
            String linha = sc.hasNextLine() ? sc.nextLine().trim().replace(",", ".") : "0";
            try {
                return Double.parseDouble(linha);
            } catch (NumberFormatException e) {
                System.out.print("Digite um numero: ");
            }
        }
    }

    // ===================== MAIN =====================

    public static void main(String[] args) {
        new SistemaMonitoramento().iniciar();
    }
}
