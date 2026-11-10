/**
 * Contrato de comportamento de todo sensor do territorio (Interface).
 *
 * Diz O QUE um sensor deve saber fazer (ler valor, verificar funcionamento,
 * informar tipo e unidade, conhecer e detectar seu limite de alerta), mas nao
 * diz COMO. Cada tipo de sensor implementa a seu modo.
 */
public interface Sensor {

    /** Le o valor atual medido pelo sensor (leitura simulada). */
    double lerValor();

    /** Indica se o sensor esta funcionando corretamente. */
    boolean verificarFuncionamento();

    /** Retorna o tipo do sensor (ex.: "Chuva"). */
    String getTipo();

    /** Retorna a unidade de medida (ex.: "mm/h"). */
    String getUnidade();

    /** Limite a partir do qual a leitura do sensor e considerada de risco. */
    double getLimiteAlerta();

    /** Detecta se um valor lido ultrapassou o limite de alerta do sensor. */
    boolean acimaDoLimite(double valor);
}
