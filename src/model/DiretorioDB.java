/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Gabriel
 */
public class DiretorioDB {
    
    private int idBanco;
    private String URL;
    private String Usuario;
    private String Senha;

    public DiretorioDB(int idBanco, String URL, String Usuario, String Senha) {
        this.idBanco = idBanco;
        this.URL = URL;
        this.Usuario = Usuario;
        this.Senha = Senha;
    }

    public DiretorioDB() {
    }

    public int getIdBanco() {
        return idBanco;
    }

    public void setIdBanco(int idBanco) {
        this.idBanco = idBanco;
    }
    
    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getUsuario() {
        return Usuario;
    }

    public void setUsuario(String Usuario) {
        this.Usuario = Usuario;
    }

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String Senha) {
        this.Senha = Senha;
    }
}
