package br.com.techfarma.model.cadastro;

public class UsuarioM {
    private int idUsuario;
    private String filial, nome, login, senha, confSenha, vendedor, operaCaixa;
    private int permissao;
    private double maxDesc;
    
    
    public UsuarioM(String filial, String nome, String login, String senha, String confSenha, int permissao, String vendedor, String operaCaixa, double maxDesc) {
        this.filial = filial;
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.confSenha = confSenha;
        this.permissao = permissao;
        this.vendedor = vendedor;
        this.operaCaixa = operaCaixa;
        this.maxDesc = maxDesc;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getConfSenha() {
        return confSenha;
    }

    public void setConfSenha(String confSenha) {
        this.confSenha = confSenha;
    }

    public int getPermissao() {
        return permissao;
    }

    public void setPermissao(int permissao) {
        this.permissao = permissao;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getOperaCaixa() {
        return operaCaixa;
    }

    public void setOperaCaixa(String operaCaixa) {
        this.operaCaixa = operaCaixa;
    }

    public double getMaxDesc() {
        return maxDesc;
    }

    public void setMaxDesc(double maxDesc) {
        this.maxDesc = maxDesc;
    }
}
