package model;

import java.sql.Date;
import java.sql.Time;

public class VerificaVendasCaixaM {
    private int idVenda;
    private int idVendedor;
    private Date dataVenda;
    private Time horaVenda;
    private String vendedor;
    private int qtdItens;
    private double valorTotal;

    public VerificaVendasCaixaM(int idVenda, String vendedor, Date dataVenda, Time horaVenda, int qtdItens, double valorTotal) {
        this.idVenda = idVenda;
        this.vendedor = vendedor;
        this.dataVenda = dataVenda;
        this.horaVenda = horaVenda;
        this.qtdItens = qtdItens;
        this.valorTotal = valorTotal;
    }
    
    @Override
    public String toString(){
        return idVenda + ", " + vendedor + ", " + dataVenda + ", " + horaVenda + ", " + valorTotal;
    }

    public int getIdVenda() {
        return idVenda;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public Date getDataVenda() {
        return dataVenda;
    }

    public Time getHoraVenda() {
        return horaVenda;
    }
    
    public String getVendedor() {
        return vendedor;
    }

    public int getQtdItens() {
        return qtdItens;
    }

    public double getValorTotal() {
        return valorTotal;
    }
}
