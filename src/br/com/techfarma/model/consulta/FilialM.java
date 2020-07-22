package br.com.techfarma.model.consulta;

public class FilialM {
    private int idFilial;
    private String nome;
    private String razaoSocial;

    public FilialM(int idFilial, String nome, String razaoSocial) {
        this.idFilial = idFilial;
        this.nome = nome;
        this.razaoSocial = razaoSocial;
    }

    public int getIdFilial() {
        return idFilial;
    }

    public String getNome() {
        return nome;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }
}
