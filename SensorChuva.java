/**
 * Sensor de chuva: mede a intensidade da precipitacao em mm/h.
 * Implementa o contrato Sensor.
 */
public class SensorChuva implements Sensor {

    private final String codigo;
    private final double limiteAlerta; // mm/h a partir do qual a chuva e intensa

    public SensorChuva(String codigo, double limiteAlerta) {
        this.codigo = codigo;
        this.limiteAlerta = limiteAlerta;
    }

    @Override
    public double lerValor() {
        // Leitura simulada entre 0 e 100 mm/h.
        return Math.round(Math.random() * 100 * 10.0) / 10.0;
    }

    @Override
    public boolean verificarFuncionamento() {
        // 90% de chance de estar operacional.
        return Math.random() > 0.10;
    }

    @Override
    public String getTipo() {
        return "Chuva";
    }

    @Override
    public String getUnidade() {
        return "mm/h";
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
