/**
 * Classe abstrata base de todo componente instalado no territorio.
 *
 * Nao pode ser instanciada: nao existe um "componente generico" no mundo real.
 * Define atributos e comportamentos comuns (ativar/desativar) e obriga cada
 * subclasse a implementar verificarStatus().
 */
public abstract class ComponenteTerritorial {

    private final int id;
    private final String nome;
    private final String regiao;
    private boolean ativo;

    public ComponenteTerritorial(int id, String nome, String regiao) {
        this.id = id;
        this.nome = nome;
        this.regiao = regiao;
        this.ativo = true; // todo componente nasce ativo
    }

    // ---- Metodos concretos herdados por todas as subclasses ----

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;
    }

    // ---- Metodo ABSTRATO: cada componente diz seu status a seu modo ----

    public abstract String verificarStatus();

    // ---- Getters ----

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getRegiao() {
        return regiao;
    }

    public boolean isAtivo() {
        return ativo;
    }
}
