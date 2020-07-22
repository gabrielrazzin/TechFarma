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
public class ProdutoFinanceiro {
    private int idProdFinanceiro;
    private int idProduto;
    private BigDecimal comissao;
    private BigDecimal descontoMax;
    private BigDecimal custoUnit;
    private BigDecimal markUp;
    private BigDecimal PMC;
    private BigDecimal valorPromo;
    private BigDecimal percPromo;

    public ProdutoFinanceiro(int idProdFinanceiro, int idProduto, BigDecimal comissao, BigDecimal descontoMax, BigDecimal custoUnit, BigDecimal markUp, BigDecimal PMC, BigDecimal valorPromo, BigDecimal percPromo) {
        this.idProdFinanceiro = idProdFinanceiro;
        this.idProduto = idProduto;
        this.comissao = comissao;
        this.descontoMax = descontoMax;
        this.custoUnit = custoUnit;
        this.markUp = markUp;
        this.PMC = PMC;
        this.valorPromo = valorPromo;
        this.percPromo = percPromo;
    }

    public ProdutoFinanceiro(){
        
    }
    
    public int getIdProdFinanceiro() {
        return idProdFinanceiro;
    }

    public void setIdProdFinanceiro(int idProdFinanceiro) {
        this.idProdFinanceiro = idProdFinanceiro;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public BigDecimal getComissao() {
        return comissao;
    }

    public void setComissao(BigDecimal comissao) {
        this.comissao = comissao;
    }

    public BigDecimal getDescontoMax() {
        return descontoMax;
    }

    public void setDescontoMax(BigDecimal descontoMax) {
        this.descontoMax = descontoMax;
    }

    public BigDecimal getCustoUnit() {
        return custoUnit;
    }

    public void setCustoUnit(BigDecimal custoUnit) {
        this.custoUnit = custoUnit;
    }

    public BigDecimal getMarkUp() {
        return markUp;
    }

    public void setMarkUp(BigDecimal markUp) {
        this.markUp = markUp;
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

    public BigDecimal getPercPromo() {
        return percPromo;
    }

    public void setPercPromo(BigDecimal percPromo) {
        this.percPromo = percPromo;
    }

    
}
