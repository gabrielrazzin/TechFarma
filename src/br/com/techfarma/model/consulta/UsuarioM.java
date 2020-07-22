package br.com.techfarma.model.consulta;

public class UsuarioM {
    private int idUsuario;
    private String nome;
    private String login;
    private String permissao;
    private String filial;

    public UsuarioM(int idUsuario, String nome, String login, String permissao, String filial) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.login = login;
        this.permissao = permissao;
        this.filial = filial;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public String getLogin() {
        return login;
    }

    public String getPermissao() {
        return permissao;
    }

    public String getFilial() {
        return filial;
    }
    
    
    
}
