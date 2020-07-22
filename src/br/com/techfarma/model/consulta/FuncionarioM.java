package br.com.techfarma.model.consulta;

public class FuncionarioM {
    private int idFuncionario;
    private String nome;
    private String telefone;
    private String filial;
    private String funcao;

    public FuncionarioM(int idFuncionario, String nome, String telefone, String filial, String funcao) {
        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.telefone = telefone;
        this.filial = filial;
        this.funcao = funcao;
    }

    public int getIdFuncionario() {
        return idFuncionario;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getFilial() {
        return filial;
    }

    public String getFuncao() {
        return funcao;
    }
}
