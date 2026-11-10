/**
 * Painel informativo instalado no territorio para comunicar alertas a populacao.
 *
 * E UM ComponenteTerritorial (heranca). Exibe a ultima mensagem publicada.
 */
public class PainelInformativo extends ComponenteTerritorial {

    private String ultimaMensagem;

    public PainelInformativo(int id, String nome, String regiao) {
        super(id, nome, regiao);
        this.ultimaMensagem = "Sem avisos no momento.";
    }

    public void exibirMensagem(String mensagem) {
        this.ultimaMensagem = mensagem;
        if (isAtivo()) {
            System.out.println("   [PAINEL " + getNome() + "] " + mensagem);
        }
    }

    public String getUltimaMensagem() {
        return ultimaMensagem;
    }

    @Override
    public String verificarStatus() {
        if (!isAtivo()) {
            return "Painel " + getNome() + " DESLIGADO.";
        }
        return "Painel " + getNome() + " LIGADO em " + getRegiao()
                + " | Mensagem atual: \"" + ultimaMensagem + "\"";
    }
}
