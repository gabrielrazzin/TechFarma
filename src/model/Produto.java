/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.math.BigDecimal;

/**
 *
 * @author Gabriel
 */
public class Produto {
    private int idProduto;
    private String descricao;
    private BigDecimal PMC;
    private BigDecimal valorPromo;

    public Produto() {
    
    }

    public Produto(int idProduto, String descricao, BigDecimal PMC, BigDecimal valorPromo) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.PMC = PMC;
        this.valorPromo = valorPromo;
    }

    
    public Produto(BigDecimal PMC, BigDecimal valorPromo) {
        this.PMC = PMC;
        this.valorPromo = valorPromo;
    }
    
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getPMC() {
        return PMC;
    }

    public void setPMC(BigDecimal PMC) {
        this.PMC = PMC;
    }

    public BigDecimal getValorPromo() {
        return valorPromo;
    }

    public void setValorPromo(BigDecimal valorPromo) {
        this.valorPromo = valorPromo;
    }
    
    
}
