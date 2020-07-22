package br.com.techfarma.model.cadastro;

import java.util.Arrays;
import java.util.List;

public class ProdutoM {
    private int idProduto;
    private String descricao;
    private int idFabricante;
    private int idGrupo;
    private int idClasse;
    private int idPrincipio;
    private int idGrupoCompra;
    private int idLista;
    private String controlado;
    private String usoContinuo;
    private String pisCOFINS;
    private String pedEletronico;
    private int qtdEstoque;
    private double mediaF;
    private double estoqueSeg;
    private double estoqueMax;
    private int qtdCompra;
    private String tipoPreco;
    private String undVenda;
    private int qtdVenda;
    private String IPPT;
    private String IAT;
    private double comissao;
    private double descontoMax;
    private double custoUnit;
    private double markUP;
    private double PMC;
    private double promocao;
    private double pcoPromocao;
    private double PMCAnterior;
    private String usuario;
    private String status;
    
    private String idCFOP;
    private String NCM;
    private String CEST;
    private String origem;
    private double IPI;
    private String situTrib;
    private String CST;
    private double aliquotaICMS;
    private String situTribPIS;
    private String CSTEntrada;
    private String CSTSaida;
    private double aliquotaPIS;
    private double aliquotaCOFINS;
    private String naturezaReceita;
    private String segmento;

    private String codBarras01;
    public static String codBarras02S;
    public static String codBarras03S;
    public static String codBarras04S;
    public static String codBarras05S;
    public static String codBarras06S;
    public static String codBarras07S;
    public static String codBarras08S;
    public static String codBarras09S;
    public static String codBarras10S;
    public static String codBarras11S;
    public static String codBarras12S;
    public static String codBarras13S;
    public static String codBarras14S;
    public static String codBarras15S;
    
    public static List<String> listaCodBarras = Arrays.asList(codBarras02S, codBarras03S, codBarras04S, codBarras05S, codBarras06S, codBarras07S, codBarras08S, codBarras09S, codBarras10S, codBarras11S, codBarras12S, codBarras13S, codBarras14S, codBarras15S);
    public static int posAtualLC = 0;
    
    public ProdutoM(String descricao, int idFabricante, int idGrupo, int idClasse, int idPrincipio, int idGrupoCompra, int idLista, String controlado, String usoContinuo, String pisCOFINS, String pedEletronico, int qtdCompra, String tipoPreco, String undVenda, int qtdVenda, String IPPT, String IAT, double comissao, double descontoMax, double custoUnit, double markUP, double PMC, double promocao, double pcoPromocao, String status) {
        this.descricao = descricao;
        this.idFabricante = idFabricante;
        this.idGrupo = idGrupo;
        this.idClasse = idClasse;  
        this.idPrincipio = idPrincipio;
        this.idGrupoCompra = idGrupoCompra;
        this.idLista = idLista;
        this.controlado = controlado;
        this.usoContinuo = usoContinuo;
        this.pisCOFINS = pisCOFINS;
        this.pedEletronico = pedEletronico;
        this.qtdCompra = qtdCompra;
        this.tipoPreco = tipoPreco;
        this.undVenda = undVenda;
        this.qtdVenda = qtdVenda;
        this.IPPT = IPPT;
        this.IAT = IAT;
        this.comissao = comissao;
        this.descontoMax = descontoMax;
        this.custoUnit = custoUnit;
        this.markUP = markUP;
        this.PMC = PMC;
        this.promocao = promocao;
        this.pcoPromocao = pcoPromocao;
        this.status = status;
    }

    public ProdutoM(int idProduto, String idCFOP, String NCM, String CEST, String origem, double IPI, String situTrib, String CST, double aliquotaICMS, String situTribPIS, String CSTEntrada, String CSTSaida, double aliquotaPIS, double aliquotaCOFINS, String naturezaReceita, String segmento) {
        this.idProduto = idProduto;
        this.idCFOP = idCFOP;
        this.NCM = NCM;
        this.CEST = CEST;
        this.origem = origem;
        this.IPI = IPI;
        this.situTrib = situTrib;
        this.CST = CST;
        this.aliquotaICMS = aliquotaICMS;
        this.situTribPIS = situTribPIS;
        this.CSTEntrada = CSTEntrada;
        this.CSTSaida = CSTSaida;
        this.aliquotaPIS = aliquotaPIS;
        this.aliquotaCOFINS = aliquotaCOFINS;
        this.naturezaReceita = naturezaReceita;
        this.segmento = segmento;
    }
    
    
    
    public ProdutoM(String codBarras02, String codBarras03){
        codBarras02S = codBarras02;
        codBarras03S = codBarras03;
    }

    public ProdutoM(int idProduto, String codBarras01) {
        this.idProduto = idProduto;
        this.codBarras01 = codBarras01;
    }

    public int getIdProduto() {
        return idProduto;
    }
    
    public String getDescricao() {
        return descricao;
    }

    public int getIdFabricante() {
        return idFabricante;
    }
    
    public int getIdGrupo() {
        return idGrupo;
    }

    public int getIdClasse() {
        return idClasse;
    }

    public int getIdPrincipio() {
        return idPrincipio;
    }

    public int getIdGrupoCompra() {
        return idGrupoCompra;
    }
    
    public int getIdLista() {
        return idLista;
    }

    public String getControlado() {
        return controlado;
    }

    public String getUsoContinuo() {
        return usoContinuo;
    }

    public String getPisCOFINS() {
        return pisCOFINS;
    }

    public String getPedEletronico() {
        return pedEletronico;
    }

    public int getQtdEstoque() {
        return qtdEstoque;
    }

    public double getMediaF() {
        return mediaF;
    }

    public double getEstoqueSeg() {
        return estoqueSeg;
    }

    public double getEstoqueMax() {
        return estoqueMax;
    }

    public int getQtdCompra() {
        return qtdCompra;
    }

    public String getTipoPreco() {
        return tipoPreco;
    }

    public String getUndVenda() {
        return undVenda;
    }

    public int getQtdVenda() {
        return qtdVenda;
    }

    public String getIPPT() {
        return IPPT;
    }

    public String getIAT() {
        return IAT;
    }

    public double getComissao() {
        return comissao;
    }

    public double getDescontoMax() {
        return descontoMax;
    }

    public double getCustoUnit() {
        return custoUnit;
    }

    public double getMarkUP() {
        return markUP;
    }

    public double getPMC() {
        return PMC;
    }

    public double getPromocao() {
        return promocao;
    }

    public double getPcoPromocao() {
        return pcoPromocao;
    }

    public double getPMCAnterior() {
        return PMCAnterior;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public String getStatus() {
        return status;
    }

    public String getIdCFOP() {
        return idCFOP;
    }

    public String getNCM() {
        return NCM;
    }

    public String getCEST() {
        return CEST;
    }

    public String getOrigem() {
        return origem;
    }

    public double getIPI() {
        return IPI;
    }

    public String getSituTrib() {
        return situTrib;
    }

    public String getCST() {
        return CST;
    }

    public double getAliquotaICMS() {
        return aliquotaICMS;
    }

    public String getSituTribPIS() {
        return situTribPIS;
    }

    public String getCSTEntrada() {
        return CSTEntrada;
    }

    public String getCSTSaida() {
        return CSTSaida;
    }

    public double getAliquotaPIS() {
        return aliquotaPIS;
    }

    public double getAliquotaCOFINS() {
        return aliquotaCOFINS;
    }

    public String getNaturezaReceita() {
        return naturezaReceita;
    }

    public String getSegmento() {
        return segmento;
    }

    public String getCodBarras01() {
        return codBarras01;
    }
}
