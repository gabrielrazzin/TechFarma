package br.com.techfarma.model.consulta;

public class FornecedorM {
    private int idFornecedor;
    private String nome;
    private String telefone;
    private String endereco;
    private String bairro;

    public FornecedorM(int idFornecedor, String nome, String telefone, String endereco, String bairro) {
        this.idFornecedor = idFornecedor;
        this.nome = nome;
        this.telefone = telefone;
        this.endereco = endereco;
        this.bairro = bairro;
    }

    public int getIdFornecedor() {
        return idFornecedor;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getBairro() {
        return bairro;
    }
}
