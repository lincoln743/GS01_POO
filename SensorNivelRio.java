/**
 * Sensor de nivel do rio: mede a altura da agua em metros.
 * E a variavel central para o risco de alagamento.
 * Implementa o contrato Sensor.
 */
public class SensorNivelRio implements Sensor {

    private final String codigo;
    private final double limiteAlerta; // metros a partir do qual ha risco

    public SensorNivelRio(String codigo, double limiteAlerta) {
        this.codigo = codigo;
        this.limiteAlerta = limiteAlerta;
    }

    @Override
    public double lerValor() {
        // Leitura simulada entre 1,0 e 8,0 metros.
        return Math.round((1.0 + Math.random() * 7.0) * 100.0) / 100.0;
    }

    @Override
    public boolean verificarFuncionamento() {
        return Math.random() > 0.08;
    }

    @Override
    public String getTipo() {
        return "Nivel do Rio";
    }

    @Override
    public String getUnidade() {
        return "m";
    }

    @Override
    public double getLimiteAlerta() {
        return limiteAlerta;
    }

    @Override
    public boolean acimaDoLimite(double valor) {
        return valor > limiteAlerta;
    }

    public String getCodigo() {
        return codigo;
    }
}
