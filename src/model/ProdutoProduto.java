/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;

/**
 *
 * @author Gabriel
 */
public class ProdutoProduto {
    private int idProduto;
    private int codBarras;
    private int codBarras2;
    private String descricao;
    private String estado;
    private Date dataCriacao;
    private Date dataAlteracao;
    
    public ProdutoProduto(int idProduto, int codBarras, int codBarras2, String descricao, String estado, Date dataCriacao, Date dataAlteracao) {
        this.idProduto = idProduto;
        this.codBarras = codBarras;
        this.codBarras2 = codBarras2;
        this.descricao = descricao;
        this.estado = estado;
        this.dataCriacao = dataCriacao;
        this.dataAlteracao = dataAlteracao;
    }

    public ProdutoProduto() {
    }
    
    public ProdutoProduto(int idProduto, String descricao){
        this.idProduto = idProduto;
        this.descricao = descricao;
    }
    
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getCodBarras() {
        return codBarras;
    }

    public void setCodBarras(int codBarras) {
        this.codBarras = codBarras;
    }

    public int getCodBarras2() {
        return codBarras2;
    }

    public void setCodBarras2(int codBarras2) {
        this.codBarras2 = codBarras2;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }
}
