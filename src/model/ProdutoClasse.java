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
public class ProdutoClasse {
    
    private int idClasse;
    private String tipo;
    private String estqNegativo;
    private String controlLote;

    public ProdutoClasse() {
    }

    public ProdutoClasse(int idClasse, String tipo, String estqNegativo, String controlLote) {
        this.idClasse = idClasse;
        this.tipo = tipo;
        this.estqNegativo = estqNegativo;
        this.controlLote = controlLote;
    } 
    
    public int getIdClasse() {
        return idClasse;
    }

    public void setIdClasse(int idClasse) {
        this.idClasse = idClasse;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getEstqNegativo() {
        return estqNegativo;
    }

    public void setEstqNegativo(String estqNegativo) {
        this.estqNegativo = estqNegativo;
    }

    public String getControlLote() {
        return controlLote;
    }

    public void setControlLote(String controlLote) {
        this.controlLote = controlLote;
    }
}
