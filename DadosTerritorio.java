/**
 * Dados do territorio monitorado, com encapsulamento forte.
 *
 * Todos os atributos sao privados. Os limiares de alerta (que disparam acoes de
 * emergencia) sao sensiveis e so podem ser alterados com um codigo de acesso.
 * Toda alteracao e validada: valores invalidos sao rejeitados com mensagem clara
 * e o ultimo valor valido e mantido.
 */
public class DadosTerritorio {

    private String regiao;
    private int populacao;

    // Limiares do nivel do rio (em metros) que definem a severidade do alerta.
    private double limiarAtencao;
    private double limiarAlerta;
    private double limiarCritico;

    // Codigo de acesso necessario para alterar os limiares.
    private final int codigoAcesso;

    public DadosTerritorio(String regiao, int populacao, double limiarAtencao,
                           double limiarAlerta, double limiarCritico, int codigoAcesso) {
        this.regiao = regiao;
        // Usa o proprio setter validado para nascer consistente.
        this.populacao = 0;
        setPopulacao(populacao);
        this.limiarAtencao = limiarAtencao;
        this.limiarAlerta = limiarAlerta;
        this.limiarCritico = limiarCritico;
        this.codigoAcesso = codigoAcesso;
        if (!limiaresValidos(limiarAtencao, limiarAlerta, limiarCritico)) {
            // Valores padrao seguros caso a configuracao inicial seja incoerente.
            this.limiarAtencao = 4.0;
            this.limiarAlerta = 5.5;
            this.limiarCritico = 7.0;
            System.out.println("Aviso: limiares iniciais incoerentes. Aplicados valores padrao.");
        }
    }

    private boolean limiaresValidos(double atencao, double alerta, double critico) {
        return atencao > 0 && atencao < alerta && alerta < critico;
    }

    /** Altera a populacao com validacao (nao aceita valor negativo). */
    public void setPopulacao(int novaPopulacao) {
        if (novaPopulacao < 0) {
            System.out.println("Erro: populacao nao pode ser negativa. Valor mantido: " + this.populacao + ".");
            return;
        }
        this.populacao = novaPopulacao;
    }

    /**
     * Altera os limiares de alerta. Exige o codigo de acesso correto e que os
     * valores sejam coerentes (0 < atencao < alerta < critico).
     */
    public void alterarLimiares(int codigo, double atencao, double alerta, double critico) {
        if (codigo != codigoAcesso) {
            System.out.println("Acesso negado: codigo incorreto. Limiares inalterados.");
            return;
        }
        if (!limiaresValidos(atencao, alerta, critico)) {
            System.out.println("Erro: limiares fora de faixa (exige 0 < atencao < alerta < critico). "
                    + "Valores anteriores mantidos.");
            return;
        }
        this.limiarAtencao = atencao;
        this.limiarAlerta = alerta;
        this.limiarCritico = critico;
        System.out.println("Limiares atualizados com sucesso.");
    }

    // ---- Getters (leitura liberada; escrita controlada) ----

    public String getRegiao() {
        return regiao;
    }

    public int getPopulacao() {
        return populacao;
    }

    public double getLimiarAtencao() {
        return limiarAtencao;
    }

    public double getLimiarAlerta() {
        return limiarAlerta;
    }

    public double getLimiarCritico() {
        return limiarCritico;
    }
}
