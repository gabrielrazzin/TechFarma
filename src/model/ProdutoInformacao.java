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
public class ProdutoInformacao {
    private int idProdInfo;
    private int idProduto;
    private String fabricante;
    private String principioAtv;
    private String classe;
    private String grupo;

    public ProdutoInformacao(int idProdInfo, int idProduto, String fabricante, String principioAtv, String classe, String grupo) {
        this.idProdInfo = idProdInfo;
        this.idProduto = idProduto;
        this.fabricante = fabricante;
        this.principioAtv = principioAtv;
        this.classe = classe;
        this.grupo = grupo;
    }

    public ProdutoInformacao() {
    }

    
    public int getIdProdInfo() {
        return idProdInfo;
    }

    public void setIdProdInfo(int idProdInfo) {
        this.idProdInfo = idProdInfo;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getPrincipioAtv() {
        return principioAtv;
    }

    public void setPrincipioAtv(String principioAtv) {
        this.principioAtv = principioAtv;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }
    
    
}
