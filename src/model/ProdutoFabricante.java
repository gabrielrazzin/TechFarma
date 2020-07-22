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
public class ProdutoFabricante {
    private int idFabricante;
    private String descricao;
    private String contato;
    private String razao;
    private String CNPJ;
    private int qtProdutos;
    private int qtEstoque;
    private BigDecimal totalCusto;
    private BigDecimal totalVenda;

    public ProdutoFabricante() {
    }

    public ProdutoFabricante(int idFabricante, String descricao, String contato, String razao, String CNPJ, int qtProdutos, int qtEstoque, BigDecimal totalCusto, BigDecimal totalVenda) {
        this.idFabricante = idFabricante;
        this.descricao = descricao;
        this.contato = contato;
        this.razao = razao;
        this.CNPJ = CNPJ;
        this.qtProdutos = qtProdutos;
        this.qtEstoque = qtEstoque;
        this.totalCusto = totalCusto;
        this.totalVenda = totalVenda;
    }

    public int getIdFabricante() {
        return idFabricante;
    }

    public void setIdFabricante(int idFabricante) {
        this.idFabricante = idFabricante;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getContato() {
        return contato;
    }

    public void setContato(String contato) {
        this.contato = contato;
    }

    public String getRazao() {
        return razao;
    }

    public void setRazao(String razao) {
        this.razao = razao;
    }

    public String getCNPJ() {
        return CNPJ;
    }

    public void setCNPJ(String CNPJ) {
        this.CNPJ = CNPJ;
    }

    public int getQtProdutos() {
        return qtProdutos;
    }

    public void setQtProdutos(int qtProdutos) {
        this.qtProdutos = qtProdutos;
    }

    public int getQtEstoque() {
        return qtEstoque;
    }

    public void setQtEstoque(int qtEstoque) {
        this.qtEstoque = qtEstoque;
    }

    public BigDecimal getTotalCusto() {
        return totalCusto;
    }

    public void setTotalCusto(BigDecimal totalCusto) {
        this.totalCusto = totalCusto;
    }

    public BigDecimal getTotalVenda() {
        return totalVenda;
    }

    public void setTotalVenda(BigDecimal totalVenda) {
        this.totalVenda = totalVenda;
    }
    
    
    
}
