import java.util.ArrayList;

/**
 * Previsao por comparacao: compara a media do periodo atual com a do periodo
 * anterior e projeta a mesma variacao para o proximo valor. Herda de
 * EstrategiaPrevisao.
 *
 * Atributo especifico: tamanhoPeriodo (quantas leituras formam cada periodo).
 */
public class PrevisaoComparacao extends EstrategiaPrevisao {

    private final int tamanhoPeriodo;

    public PrevisaoComparacao(int tamanhoPeriodo) {
        // Precisa de dois periodos completos para comparar.
        super("Comparacao de Periodos", tamanhoPeriodo * 2);
        this.tamanhoPeriodo = tamanhoPeriodo;
    }

    private double media(ArrayList<Double> leituras, int inicio, int fim) {
        double soma = 0;
        for (int i = inicio; i < fim; i++) {
            soma += leituras.get(i);
        }
        return soma / (fim - inicio);
    }

    @Override
    public double prever(ArrayList<Double> leituras) {
        if (!dadosSuficientes(leituras)) {
            return -1; // previsao indisponivel
        }
        int n = leituras.size();
        double mediaAtual = media(leituras, n - tamanhoPeriodo, n);
        double mediaAnterior = media(leituras, n - tamanhoPeriodo * 2, n - tamanhoPeriodo);
        double variacao = mediaAtual - mediaAnterior;
        // Projeta a tendencia: valor atual + a variacao observada.
        return Math.round((mediaAtual + variacao) * 100.0) / 100.0;
    }

    @Override
    public String descrever() {
        return super.descrever() + " - periodos de " + tamanhoPeriodo + " leituras (tendencia)";
    }

    public int getTamanhoPeriodo() {
        return tamanhoPeriodo;
    }
}
