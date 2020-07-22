package br.com.techfarma.model.consulta;

public class PrincipioAtivoM {
    private int idPrincipio;
    private String descricao;
    
    public static int idPrincipioS;
    public static String descricaoS;

    public PrincipioAtivoM(int idPrincipio, String descricao) {
        this.idPrincipio = idPrincipio;
        this.descricao = descricao;
    }

    public int getIdPrincipio() {
        return idPrincipio;
    }

    public String getDescricao() {
        return descricao;
    }
}
