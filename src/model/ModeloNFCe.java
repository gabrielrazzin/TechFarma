package model;

import java.math.BigDecimal;

public class ModeloNFCe {
    
    /*/ IDENTIFICAÇÃO /*/
    private String natOp; //Natureza da Operação
    private String indPag; //Indicador de Pagamento: 0 = À Vista / 1 = À Prazo / 2 = Outros
    private String mod; //Código do Modelo do Documento Fiscal: 55 = NFe / 65 = NFCe
    private String serie; //Série do Documento Fiscal
    private String nNF; //Número do Documento Fiscal
    private String dhEmi; //Data e Hora da emissão
    private String tpNF; //Tipo de Operação: 0 = Entrada // 1 = Saída
    private String finalidade; //Finalidade de emissão da NFe: 1 = Normal / 2 = Complementar / 3 = Ajuste / 4 = Devolução
    private String idDest; //Destino da Operação: 1 = OP Interna / 2 = OP Interestadual / 3 = OP Exterior
    private String indFinal; //Indica Operação com Consumidor Final: 0 = Normal / 1 = Consumidor Final
    private String indPres; //Indica Presença do Comprador: 0 = Não se aplica / 1 = Presencial / 2 = Nao presencial (Internet) / 3 = Não presencial (Teleatendimento) / 4 = Entrega à domicílio / 9 = Não presencial (Outros)
    private String tpImp; //Formato de impressão: 4 = DANFE NFC-e (PADRÃO)
    private String tpAmb; //Identificação do Ambiente: 1 = Produção / 2 = Homologação
    
    /*/ EMITENTE /*/
    private String CRT; //Código de Regime Tributário: 1 = Simples Nacional / 2 = Simples Nacional, excesso sublimite de receita bruta / 3 = Regime Normal (v2.0)
    private String CNPJEmi; //CNPJ do orgão emitente
    private String IEEmi; //Inscrição Estadual
    private String razaoSocialEmi; //Razão Social
    private String nomeFanta; //Nome Fantasia
    private String foneEmi; //Telefone
    private String CEPEmi;
    private String logradouroEmi; //Rua
    private String numeroEmi;
    private String complementoEmi;
    private String bairroEmi;
    private int codCidadeEmi; //Codigo da Cidade
    private String cidadeEmi;
    private String UFEmi;
    
    /*/ DESTINATÁRIO /*/
    private String indIEDest; //Indicador da Inscrição Estadual do Destinatário: 9 = Não contribuinte (PADRÃO)
    private String CNPJDest; //CNPJ do Destinatário
    private String IEDest; //Inscrição Estadual do Destinatário
    private String razaoSocialDest; //Razão Social do Destinatário
    private String foneDest; //Telefone
    private String CEPDest;
    private String logradouroDest; //Rua
    private String numeroDest;
    private String complementoDest;
    private String bairroDest;
    private String codCidadeDest; //Codigo da Cidade
    private String cidadeDest;
    private String UFDest;
    
    public ModeloNFCe(String indIEDest, String CNPJDest, String IEDest, String razaoSocialDest, String foneDest, String CEPDest, String logradouroDest, String complementoDest, String bairroDest, String codCidadeDest, String cidadeDest, String UFDest){
        this.indIEDest = indIEDest;
        this.CNPJDest = CNPJDest;
        this.IEDest = IEDest;
        this.razaoSocialDest = razaoSocialDest;
        this.foneDest = foneDest;
        this.CEPDest = CEPDest;
        this.logradouroDest = logradouroDest;
        this.complementoDest = complementoDest;
        this.bairroDest = bairroDest;
        this.codCidadeDest = codCidadeDest;
        this.cidadeDest = cidadeDest;
        this.UFDest = UFDest;
    }

    public ModeloNFCe(String indIEDest) {
        this.indIEDest = indIEDest;
    }

    public String getIndIEDest() {
        return indIEDest;
    }

    public String getCNPJDest() {
        return CNPJDest;
    }

    public String getIEDest() {
        return IEDest;
    }

    public String getRazaoSocialDest() {
        return razaoSocialDest;
    }

    public String getFoneDest() {
        return foneDest;
    }

    public String getCEPDest() {
        return CEPDest;
    }

    public String getLogradouroDest() {
        return logradouroDest;
    }

    public String getNumeroDest() {
        return numeroDest;
    }

    public String getComplementoDest() {
        return complementoDest;
    }

    public String getBairroDest() {
        return bairroDest;
    }

    public String getCodCidadeDest() {
        return codCidadeDest;
    }

    public String getCidadeDest() {
        return cidadeDest;
    }

    public String getUFDest() {
        return UFDest;
    }
    
    /*/ PRODUTO /*/
    private int CFOP;
    private String codigo;
    private String cEAN;
    private String descricao;
    private String NCM;
    private String CEST;
    private String unidade;
    private String quantidade;
    private String valorUnitario;
    private String valorTotal;
    private String valorDesconto;
    private String vFrete;
    private String vSeg;
    private String vOutro;
//    private String indEscala;
//    private String CNPJFab;
    private String uTrib;
    private String cEANTrib;

    public ModeloNFCe(int CFOP, String codigo, String cEAN, String descricao, String NCM, String CEST, String unidade, String quantidade, String valorUnitario, String valorTotal, String valorDesconto, String vFrete, String vSeg, String vOutro, String uTrib, String cEANTrib) {
        this.CFOP = CFOP;
        this.codigo = codigo;
        this.cEAN = cEAN;
        this.descricao = descricao;
        this.NCM = NCM;
        this.CEST = CEST;
        this.unidade = unidade;
        this.quantidade = quantidade;
        this.valorUnitario = valorUnitario;
        this.valorTotal = valorTotal;
        this.valorDesconto = valorDesconto;
        this.vFrete = vFrete;
        this.vSeg = vSeg;
        this.vOutro = vOutro;
        this.uTrib = uTrib;
        this.cEANTrib = cEANTrib;
    }
    
    @Override
    public String toString(){
        return CFOP + " " + codigo + " " + cEAN + " " + descricao + " " + NCM + " " + unidade + " " + quantidade + " " + valorUnitario + " " + valorTotal + " " + valorDesconto + " " + vFrete + " " + vSeg + " " + vOutro + " " + uTrib + " " + cEANTrib;
    }

    public int getCFOP() {
        return CFOP;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getcEAN() {
        return cEAN;
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

    public String getQuantidade() {
        return quantidade;
    }

    public String getValorUnitario() {
        return valorUnitario;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public String getValorDesconto() {
        return valorDesconto;
    }

    public String getvFrete() {
        return vFrete;
    }

    public String getvSeg() {
        return vSeg;
    }

    public String getvOutro() {
        return vOutro;
    }

    public String getuTrib() {
        return uTrib;
    }

    public String getcEANTrib() {
        return cEANTrib;
    }
    
    /*/ PRODUTO - ICMS /*/
    private String CSTICMS;
    private String CSOSN;
    private String origem;
    private BigDecimal valorBaseICMS;
    private BigDecimal aliquotaICMS;
    private BigDecimal valorICMS;
    private String pCredSN;
    private String vCredICMSSN;
    private String modalidade;
    private String valorBaseST;
    private String aliquotaST;
    private String valorST;
    private BigDecimal percentualRed;
    private String vBCFCP;
    private String pFCP;
    private String vFCP;
    
    public ModeloNFCe(String CSOSN, String origem){
        this.CSOSN = CSOSN;
        this.origem = origem;
    }

    public String getCSOSN() {
        return CSOSN;
    }

    public String getOrigem() {
        return origem;
    }

    /*/ PRODUTO - PIS /*/
    private String CSTPIS;
    private String valorBasePIS;
    private BigDecimal aliquotaPIS;
    private String valorPIS;
    
    public ModeloNFCe(String CSTPIS, BigDecimal aliquotaPIS){
        this.CSTPIS = CSTPIS;
        this.aliquotaPIS = aliquotaPIS;
    }

    public String getCSTPIS() {
        return CSTPIS;
    }

    public BigDecimal getAliquotaPIS() {
        return aliquotaPIS;
    }
    
    /*/ PRODUTO - COFINS /*/
    private String CSTCOFINS;
    private String valorBaseCOFINS;
    private BigDecimal aliquotaCOFINS;
    private String valorCOFINS;

    public String getCSTCOFINS() {
        return CSTCOFINS;
    }

    public BigDecimal getAliquotaCOFINS() {
        return aliquotaCOFINS;
    }
    
    /*/ PRODUTO - IPI /*/
    private String CSTIPI;
    private String valorBaseIPI;
    private String alqiuotaIPI;
    private String valorIPI;
    
    /*/ TOTAL /*/
    private String baseICMS;
    private String valorICMST;
    private String vICMSDeson;
    private String baseICMSSub;
    private String valorICMSSub;
    private String valorProduto;
    private String valorFrete;
    private String valorSeguro;
    private String valorDescontoT;
    private String valorIPIT;
    private String valorPIST;
    private String valorCOFINST;
    private String valorOutrasDesp;
    private String valorNota;
    private String vFCPT;
    

    public ModeloNFCe(String CRT, String CNPJEmi, String IEEmi, String razaoSocialEmi, String nomeFanta, String foneEmi, String CEPEmi, String logradouroEmi, String numeroEmi, String complementoEmi, String bairroEmi, int codCidadeEmi, String cidadeEmi, String UFEmi) {
        this.CRT = CRT;
        this.CNPJEmi = CNPJEmi;
        this.IEEmi = IEEmi;
        this.razaoSocialEmi = razaoSocialEmi;
        this.nomeFanta = nomeFanta;
        this.foneEmi = foneEmi;
        this.CEPEmi = CEPEmi;
        this.logradouroEmi = logradouroEmi;
        this.numeroEmi = numeroEmi;
        this.complementoEmi = complementoEmi;
        this.bairroEmi = bairroEmi;
        this.codCidadeEmi = codCidadeEmi;
        this.cidadeEmi = cidadeEmi;
        this.UFEmi = UFEmi;
    }

    
    public String getNatOp() {
        return natOp;
    }

    public String getIndPag() {
        return indPag;
    }

    public String getMod() {
        return mod;
    }

    public String getSerie() {
        return serie;
    }

    public String getnNF() {
        return nNF;
    }

    public String getDhEmi() {
        return dhEmi;
    }

    public String getTpNF() {
        return tpNF;
    }

    public String getFinalidade() {
        return finalidade;
    }

    public String getIdDest() {
        return idDest;
    }

    public String getIndFinal() {
        return indFinal;
    }

    public String getIndPres() {
        return indPres;
    }

    public String getTpImp() {
        return tpImp;
    }

    public String getTpAmb() {
        return tpAmb;
    }

    public String getCRT() {
        return CRT;
    }

    public String getCNPJEmi() {
        return CNPJEmi;
    }

    public String getIEEmi() {
        return IEEmi;
    }

    public String getRazaoSocialEmi() {
        return razaoSocialEmi;
    }

    public String getNomeFanta() {
        return nomeFanta;
    }

    public String getFoneEmi() {
        return foneEmi;
    }

    public String getCEPEmi() {
        return CEPEmi;
    }

    public String getLogradouroEmi() {
        return logradouroEmi;
    }

    public String getNumeroEmi() {
        return numeroEmi;
    }

    public String getComplementoEmi() {
        return complementoEmi;
    }

    public String getBairroEmi() {
        return bairroEmi;
    }

    public int getCodCidadeEmi() {
        return codCidadeEmi;
    }

    public String getCidadeEmi() {
        return cidadeEmi;
    }

    public String getUFEmi() {
        return UFEmi;
    }
}
