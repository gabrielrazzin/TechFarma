package br.com.techfarma.model.consulta;

public class ClienteM {
    private int idFilial;
    private int idCliente;
    private String nome;
    private String convenio;
    private String endereco;
    private String bairro;
    private String telefone;

    public ClienteM(int idFilial, int idCliente, String nome, String convenio, String endereco, String bairro, String telefone) {
        this.idFilial = idFilial;
        this.idCliente = idCliente;
        this.nome = nome;
        this.convenio = convenio;
        this.endereco = endereco;
        this.bairro = bairro;
        this.telefone = telefone;
    }

    public int getIdFilial() {
        return idFilial;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public String getNome() {
        return nome;
    }

    public String getConvenio() {
        return convenio;
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
