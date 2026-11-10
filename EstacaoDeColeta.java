import java.util.ArrayList;

/**
 * Estacao de coleta instalada no territorio.
 *
 * E UM ComponenteTerritorial (heranca) e TEM sensores a bordo (associacao):
 * guarda uma lista de objetos Sensor e coleta as leituras deles.
 */
public class EstacaoDeColeta extends ComponenteTerritorial {

    // Associacao: a estacao TEM sensores.
    private final ArrayList<Sensor> sensores;

    public EstacaoDeColeta(int id, String nome, String regiao) {
        super(id, nome, regiao); // repassa a identificacao para a classe mae
        this.sensores = new ArrayList<>();
    }

    public void instalarSensor(Sensor sensor) {
        this.sensores.add(sensor);
    }

    public ArrayList<Sensor> getSensores() {
        return sensores;
    }

    /** Quantos sensores da estacao estao funcionando agora. */
    public int sensoresOperacionais() {
        int ok = 0;
        for (Sensor s : sensores) {
            if (s.verificarFuncionamento()) {
                ok++;
            }
        }
        return ok;
    }

    @Override
    public String verificarStatus() {
        if (!isAtivo()) {
            return "Estacao " + getNome() + " DESATIVADA.";
        }
        return "Estacao " + getNome() + " ATIVA em " + getRegiao()
                + " | " + sensoresOperacionais() + "/" + sensores.size()
                + " sensores operacionais.";
    }
}
