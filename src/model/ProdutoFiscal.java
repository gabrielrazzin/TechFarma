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
public class ProdutoFiscal {
    private int idProdFiscal;
    private int idProduto;
    private String lista;
    private int tipoPreco;
    private BigDecimal ICMS;
    private int NCM;

    public ProdutoFiscal(int idProdFiscal, int idProduto, String lista, int tipoPreco, BigDecimal ICMS, int NCM) {
        this.idProdFiscal = idProdFiscal;
        this.idProduto = idProduto;
        this.lista = lista;
        this.tipoPreco = tipoPreco;
        this.ICMS = ICMS;
        this.NCM = NCM;
    }

    public ProdutoFiscal() {
    }

    
    public int getIdProdFiscal() {
        return idProdFiscal;
    }

    public void setIdProdFiscal(int idProdFiscal) {
        this.idProdFiscal = idProdFiscal;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }

    public int getTipoPreco() {
        return tipoPreco;
    }

    public void setTipoPreco(int tipoPreco) {
        this.tipoPreco = tipoPreco;
    }

    public BigDecimal getICMS() {
        return ICMS;
    }

    public void setICMS(BigDecimal ICMS) {
        this.ICMS = ICMS;
    }

    public int getNCM() {
        return NCM;
    }

    public void setNCM(int NCM) {
        this.NCM = NCM;
    }
    
    
}
