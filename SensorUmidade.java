/**
 * Sensor de umidade do solo/ar: mede a umidade em porcentagem.
 * Solo saturado reduz a absorcao da chuva e aumenta o risco de alagamento.
 * Implementa o contrato Sensor.
 */
public class SensorUmidade implements Sensor {

    private final String codigo;
    private final double limiteAlerta; // % a partir do qual o solo esta saturado

    public SensorUmidade(String codigo, double limiteAlerta) {
        this.codigo = codigo;
        this.limiteAlerta = limiteAlerta;
    }

    @Override
    public double lerValor() {
        // Leitura simulada entre 40% e 100%.
        return Math.round((40.0 + Math.random() * 60.0) * 10.0) / 10.0;
    }

    @Override
    public boolean verificarFuncionamento() {
        return Math.random() > 0.05;
    }

    @Override
    public String getTipo() {
        return "Umidade";
    }

    @Override
    public String getUnidade() {
        return "%";
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
