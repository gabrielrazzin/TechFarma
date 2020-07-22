package br.com.techfarma.model.consulta;

public class ConvenioM {
    private int idConvenio;
    private String nome;
    private String endereco;
    private String bairro;
    private String telefone;

    public ConvenioM(int idConvenio, String nome, String endereco, String bairro, String telefone) {
        this.idConvenio = idConvenio;
        this.nome = nome;
        this.endereco = endereco;
        this.bairro = bairro;
        this.telefone = telefone;
    }

    public int getIdConvenio() {
        return idConvenio;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public String getTelefone() {
        return telefone;
    }
}
