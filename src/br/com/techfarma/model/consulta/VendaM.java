package br.com.techfarma.model.consulta;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.ImageView;

public class VendaM {
    
    private static ObservableList<VendaM> lista = FXCollections.observableArrayList();
    public static String descricaoS;
    
    public static String concluidoS;
    public static boolean finalizaVendaS;
    public static String barCodeVenda;
    public static boolean newElementS;

    public static ObservableList<VendaM> getLista() {
        return lista;
    }

    public static String getDescricaoS() {
        return descricaoS;
    }

    private int idProduto;
    private String descricao;
    private int idFabricante;
    private BigDecimal PMC;
    private BigDecimal valorPromocao;
    private BigDecimal descontoPerc;
    private String promocaoView;
    private BigDecimal promocaoPerc;
    private int qtdEstoque; // Consulta Produto
    
    /* Variáveis exclusivas da finalização de venda */
    private int idFilial;
    private int idVenda;
    private int idVendaTroca;
    private int idVendedor;
//    private int idCliente;
//    private int idConvenio;
    private int idCupom;
    private float idCFOP;
    private String bloqueado;
    private String codigoBarras;
    private String NCM;
    private String CEST;
    private String usoContinuo;
    private int qtd;
    private String unidade;
    private BigDecimal valorDesc;
    private BigDecimal diferencaPromocao;
    private BigDecimal valorUnitario;
    private BigDecimal valorTotal;
    private BigDecimal troco;
    private String aliquota;
    private String tipoVenda;
    private String entrega;
//    private String endereco;
//    private String bairro;
//    private String referencia;
    private Date dataVenda;
    private Time horaVenda;
    private String vendedor;
    private int terminal;
    private String concluido;
    /* FIM das variáveis exclusivas */
    
    public static int idCliente;
    public static int idConvenio;
    
    public static String nomeCliente;
    public static String endereco;
    public static String bairro;
    public static String referencia;
    public static String telefone;
    public static String celular;
    
    public static int idVendaFinal;
    public static double valorTotalFinal;
    public static double trocoFinal;

    public static String tipoCartao;
    public static String idBarCode;
    public static String idPagamento;
    public static int idCartao;
    
    //
    private ImageView bandeira;
    private String descricaoCartao;
    //

    public VendaM(String descricaoCartao, ImageView bandeira) {
        this.descricaoCartao = descricaoCartao;
        this.bandeira = bandeira;
    }
    
    public VendaM(int idFilial, int idVenda, String idBarCode, String idPagamento, int idCartao, int idCliente, int idConvenio, String tipoCartao, double valor, double troco, String status){
        this.idFilial = idFilial;
        this.idVenda = idVenda;
        VendaM.idBarCode = idBarCode;
        VendaM.idPagamento = idPagamento;
        VendaM.idCartao = idCartao;
        VendaM.idCliente = idCliente;
        VendaM.idConvenio = idConvenio;
        VendaM.tipoCartao = tipoCartao;
        VendaM.valorTotalFinal = valor;
        VendaM.trocoFinal = troco;
        this.concluido = status;
    }
    
    public static double getValorTotalFinal() {
        return valorTotalFinal;
    }

    public static String getTipoCartao() {
        return tipoCartao;
    }

    public static String getIdBarCode() {
        return idBarCode;
    }

    public static String getIdPagamento() {
        return idPagamento;
    }

    public static int getIdCartao() {
        return idCartao;
    }
    
    public VendaM(int idVendaFinal, int idProduto, String descricao, BigDecimal valorUnitario, int qtd, BigDecimal valorTotal, String unidade){
        VendaM.idVendaFinal = idVendaFinal;
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.qtd = qtd;
        this.valorTotal = valorTotal;
        this.unidade = unidade;
    }

   
    
    public VendaM(int idProduto, String descricao, int idFabricante, BigDecimal PMC, BigDecimal valorPromocao, BigDecimal descontoPerc) {
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.idFabricante = idFabricante;
        this.PMC = PMC;
        this.valorPromocao = valorPromocao;
        this.descontoPerc = descontoPerc;
    }
   
    public VendaM(int idProduto, String descricao, int qtd, BigDecimal PMC, BigDecimal valorUnitario, BigDecimal valorPromocao, BigDecimal descontoPerc, BigDecimal valorTotal){
        this.idProduto = idProduto;
        this.descricao = descricao;
        this.qtd = qtd;
        this.PMC = PMC;
        this.valorUnitario = valorUnitario;
        this.valorPromocao = valorPromocao;
        this.descontoPerc = descontoPerc;
        this.valorTotal = valorTotal;
    }
    
    public VendaM(int idFilial, int idVenda, String barCodeVenda, int idVendaTroca, int idVendedor, int idCliente, int idConvenio,int idCupom, float idCFOP, String bloqueado, int idProduto, String codigoBarras, String descricao, String NCM, String CEST, String usoContinuo, 
            int qtd, String unidade, BigDecimal PMC, BigDecimal valorDesc, BigDecimal diferencaPromocao, BigDecimal descontoPerc, BigDecimal valorUnitario, BigDecimal valorTotal, BigDecimal troco, String aliquota, String tipoVenda, String entrega, 
            String endereco, String bairro, String referencia, Date dataVenda, Time horaVenda, String vendedor, int terminal, String concluido) {
        
        this.idFilial = idFilial;
        this.idVenda = idVenda;
        this.barCodeVenda = barCodeVenda;
        this.idVendaTroca = idVendaTroca;
        this.idVendedor = idVendedor;
        this.idCliente = idCliente;
        this.idConvenio = idConvenio;
        this.idCupom = idCupom;
        this.idCFOP = idCFOP;
        this.bloqueado = bloqueado;
        this.idProduto = idProduto;
        this.codigoBarras = codigoBarras;
        this.descricao = descricao;
        this.NCM = NCM;
        this.CEST = CEST;
        this.usoContinuo = usoContinuo;
        this.qtd = qtd;
        this.unidade = unidade;
        this.PMC = PMC;
        this.valorDesc = valorDesc;
        this.diferencaPromocao = diferencaPromocao;
        this.descontoPerc = descontoPerc;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
        this.troco = troco;
        this.aliquota = aliquota;
        this.tipoVenda = tipoVenda;
        this.entrega = entrega;
        this.endereco = endereco;
        this.bairro = bairro;
        this.referencia = referencia;
        this.dataVenda = dataVenda;
        this.horaVenda = horaVenda;
        this.vendedor = vendedor;
        this.terminal = terminal;
        this.concluido = concluido;
    }
    @Override
    public String toString() {
        return idFilial + ", " + idVenda + ", " + idVendaTroca + ", " + idVendedor + ", " + idCliente + ", " + idConvenio + ", " + idCupom + ", " + idCFOP + ", "+ bloqueado + ", " + idProduto + ", " + codigoBarras + ", " + descricao + ", " + NCM + ", " +usoContinuo + ", " + qtd + ", " +
                unidade + ", " + PMC + ", " + valorDesc + ", " + diferencaPromocao + ", " + descontoPerc + ", " + valorUnitario + ", " + valorTotal + ", " + troco + ", " + aliquota + ", " + tipoVenda + ", " + entrega + ", " + endereco + ", " + bairro + ", " + referencia + ", " +
                dataVenda + ", " + horaVenda + ", " + vendedor + ", " +terminal + ", " + concluido;
    }

    public static int getIdVendaFinal() {
        return idVendaFinal;
    }
    
    public int getIdProduto() {
        return idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getNCM() {
        return NCM;
    }

    public String getCEST() {
        return CEST;
    }
    
    public String getUnidade() {
        return unidade;
    }

    public int getIdFabricante() {
        return idFabricante;
    }

    public BigDecimal getPMC() {
        return PMC;
    }

    public BigDecimal getValorPromocao() {
        return valorPromocao;
    }

    public BigDecimal getDescontoPerc() {
        return descontoPerc;
    }
    
    public void setDescontoPerc(BigDecimal descontoPerc){
        this.descontoPerc = descontoPerc;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public int getQtd() {
        return qtd;
    }

    public BigDecimal getValorUnitario() {
        return valorUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setQtd(int qtd) {
        this.qtd = qtd;
    }

    public int getIdVenda() {
        return idVenda;
    }
    
    public String getBarCodeVenda(){
        return barCodeVenda;
    }

    public int getIdVendedor() {
        return idVendedor;
    }

    public int getIdFilial() {
        return idFilial;
    }

    public int getIdVendaTroca() {
        return idVendaTroca;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public int getIdConvenio() {
        return idConvenio;
    }

    public int getIdCupom() {
        return idCupom;
    }

    public float getIdCFOP() {
        return idCFOP;
    }
    
    public String getBloqueado() {
        return bloqueado;
    }

    public String getCodigoBarras() {
        return codigoBarras;
    }

    public String getUsoContinuo() {
        return usoContinuo;
    }

    public BigDecimal getValorDesc() {
        return valorDesc;
    }

    public BigDecimal getDiferencaPromocao() {
        return diferencaPromocao;
    }

    public BigDecimal getTroco() {
        return troco;
    }

    public String getAliquota() {
        return aliquota;
    }

    public String getTipoVenda() {
        return tipoVenda;
    }

    public String getEntrega() {
        return entrega;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public String getReferencia() {
        return referencia;
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

    public int getTerminal() {
        return terminal;
    }

    public String getConcluido() {
        return concluido;
    } 

    public ImageView getBandeira() {
        return bandeira;
    }

    public String getDescricaoCartao() {
        return descricaoCartao;
    }

    public String getPromocaoView() {
        return promocaoView;
    }

    public BigDecimal getPromocaoPerc() {
        return promocaoPerc;
    }
    
    
}