package br.com.techfarma.model.cadastro;

public class FormaPagM {
    private int idFormasPag;
    private String desricao;
    private String arredondamento;
    private double indiceR;
    private double indiceM;
    private String localECF;
    private int numVias;
    private String arredonda;
    private String cobraJuros;
    private int parcelaInicial;
    private double taxaJuros;
    private String desconto;

    public FormaPagM(String desricao, String arredondamento, double indiceR, double indiceM, String localECF, int numVias, String arredonda, String cobraJuros, int parcelaInicial, double taxaJuros, String desconto) {
        this.desricao = desricao;
        this.arredondamento = arredondamento;
        this.indiceR = indiceR;
        this.indiceM = indiceM;
        this.localECF = localECF;
        this.numVias = numVias;
        this.arredonda = arredonda;
        this.cobraJuros = cobraJuros;
        this.parcelaInicial = parcelaInicial;
        this.taxaJuros = taxaJuros;
        this.desconto = desconto;
    }

    public int getIdFormasPag() {
        return idFormasPag;
    }

    public String getDesricao() {
        return desricao;
    }

    public String getArredondamento() {
        return arredondamento;
    }

    public double getIndiceR() {
        return indiceR;
    }

    public double getIndiceM() {
        return indiceM;
    }

    public String getLocalECF() {
        return localECF;
    }

    public int getNumVias() {
        return numVias;
    }

    public String getArredonda() {
        return arredonda;
    }

    public String getCobraJuros() {
        return cobraJuros;
    }

    public int getParcelaInicial() {
        return parcelaInicial;
    }

    public double getTaxaJuros() {
        return taxaJuros;
    }

    public String getDesconto() {
        return desconto;
    }
    
    
}