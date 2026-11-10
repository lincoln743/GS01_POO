import java.util.ArrayList;

/**
 * Previsao por media movel: estima o proximo valor como a media das ultimas
 * N leituras. Herda de EstrategiaPrevisao.
 *
 * Atributo especifico: janela (quantas leituras entram na media).
 */
public class PrevisaoMediaMovel extends EstrategiaPrevisao {

    private final int janela; // N leituras

    public PrevisaoMediaMovel(int janela) {
        // super() chama o construtor da mae; exige ao menos 'janela' leituras.
        super("Media Movel", janela);
        this.janela = janela;
    }

    @Override
    public double prever(ArrayList<Double> leituras) {
        if (!dadosSuficientes(leituras)) {
            return -1; // sinaliza previsao indisponivel
        }
        double soma = 0;
        int inicio = leituras.size() - janela;
        for (int i = inicio; i < leituras.size(); i++) {
            soma += leituras.get(i);
        }
        return Math.round((soma / janela) * 100.0) / 100.0;
    }

    @Override
    public String descrever() {
        // Reaproveita a descricao da mae com super e acrescenta o detalhe proprio.
        return super.descrever() + " - media das ultimas " + janela + " leituras";
    }

    public int getJanela() {
        return janela;
    }
}
