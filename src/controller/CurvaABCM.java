package controller;

public class CurvaABCM {
    private int idProduto;
    private double totalPMC;
    private double porcentagem;
    private String curva;

    public CurvaABCM(int idProduto, double totalPMC) {
        this.idProduto = idProduto;
        this.totalPMC = totalPMC;
    }

    public CurvaABCM(int idProduto, double totalPMC, double porcentagem) {
        this.idProduto = idProduto;
        this.totalPMC = totalPMC;
        this.porcentagem = porcentagem;
    }

    public int getIdProduto() {
        return idProduto;
    }

    public double getTotalPMC() {
        return totalPMC;
    }

    public double getPorcentagem() {
        return porcentagem;
    }

    public String getCurva() {
        return curva;
    }
    
    @Override
    public String toString(){
        return idProduto + " - " + totalPMC + " - " + porcentagem;
    }
}
