/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Date;
import java.sql.Time;



/**
 *
 * @author Gabriel
 */
public class ControleCaixaM {
    private int idFechamento;
    private int idFilial;
    private int idOperador;
    private int idUsuario;
    private int idTerminal;
    private Date dataAbertura;
    private Time horaAbertura;
    private Date dataFechamento;
    private Time horaFechamento;
    private double totalCaixa;
    private double totalDinheiro;
    private double totalCartao;
    private double totalCheque;
    private double totalConvenio;
    private String status;

    public int getIdFechamento() {
        return idFechamento;
    }

    public void setIdFechamento(int idFechamento) {
        this.idFechamento = idFechamento;
    }

    public int getIdFilial() {
        return idFilial;
    }

    public void setIdFilial(int idFilial) {
        this.idFilial = idFilial;
    }

    public int getIdOperador() {
        return idOperador;
    }

    public void setIdOperador(int idOperador) {
        this.idOperador = idOperador;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdTerminal() {
        return idTerminal;
    }

    public void setIdTerminal(int idTerminal) {
        this.idTerminal = idTerminal;
    }

    public Date getDataAbertura() {
        return dataAbertura;
    }

    public void setDataAbertura(Date dataAbertura) {
        this.dataAbertura = dataAbertura;
    }

    public Time getHoraAbertura() {
        return horaAbertura;
    }

    public void setHoraAbertura(Time horaAbertura) {
        this.horaAbertura = horaAbertura;
    }

    public Date getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(Date dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Time getHoraFechamento() {
        return horaFechamento;
    }

    public void setHoraFechamento(Time horaFechamento) {
        this.horaFechamento = horaFechamento;
    }

    public double getTotalCaixa() {
        return totalCaixa;
    }

    public void setTotalCaixa(double totalCaixa) {
        this.totalCaixa = totalCaixa;
    }

    public double getTotalDinheiro() {
        return totalDinheiro;
    }

    public void setTotalDinheiro(double totalDinheiro) {
        this.totalDinheiro = totalDinheiro;
    }

    public double getTotalCartao() {
        return totalCartao;
    }

    public void setTotalCartao(double totalCartao) {
        this.totalCartao = totalCartao;
    }

    public double getTotalCheque() {
        return totalCheque;
    }

    public void setTotalCheque(double totalCheque) {
        this.totalCheque = totalCheque;
    }

    public double getTotalConvenio() {
        return totalConvenio;
    }

    public void setTotalConvenio(double totalConvenio) {
        this.totalConvenio = totalConvenio;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
    
}
