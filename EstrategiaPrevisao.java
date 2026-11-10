import java.util.ArrayList;

/**
 * Classe abstrata base das estrategias de previsao do nivel do rio.
 *
 * Define o que e comum a toda previsao (um nome e a checagem de dados minimos)
 * e obriga cada estrategia concreta a implementar prever() a seu modo.
 */
public abstract class EstrategiaPrevisao {

    protected final String nome;
    protected final int minimoLeituras; // quantas leituras a estrategia exige

    public EstrategiaPrevisao(String nome, int minimoLeituras) {
        this.nome = nome;
        this.minimoLeituras = minimoLeituras;
    }

    /** Cada estrategia preve o proximo valor do seu jeito (polimorfismo). */
    public abstract double prever(ArrayList<Double> leituras);

    /** Regra comum: so prevê com dados suficientes (o sistema deve se calar). */
    protected boolean dadosSuficientes(ArrayList<Double> leituras) {
        return leituras != null && leituras.size() >= minimoLeituras;
    }

    public String descrever() {
        return nome + " (minimo de " + minimoLeituras + " leituras)";
    }

    public String getNome() {
        return nome;
    }

    public int getMinimoLeituras() {
        return minimoLeituras;
    }
}
