package br.com.techfarma.controller.nfe;

import br.com.techfarma.model.consulta.VendaM;
import com.acbr.ACBrSessao;
import com.acbr.nfe.ACBrNFe;
import controller.ConexaoController;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import model.ModeloNFCe;

public class ModeloNFCeC implements Initializable {
    ConexaoController conn = new ConexaoController();
    private ObservableList<ModeloNFCe> dataProduto = FXCollections.observableArrayList();
    private ArrayList<String> aProduto = new ArrayList<>();
    private ArrayList<String> aICMS = new ArrayList<>();
    private ArrayList<String> aPIS = new ArrayList<>();
    private ArrayList<String> aCOFINS = new ArrayList<>();
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
    }
    
    private int idVenda;
    
    protected int buscaUltimaVenda(){
        idVenda = 0;
        
        try {
            conn.executaSQL("SELECT idVenda FROM Vendas.Vendas WHERE idBarcode = '"+ VendaM.idBarCode + "' AND concluido = 'N'");
            conn.rs.last();
            idVenda = conn.rs.getInt(1);
        } catch (SQLException e) {
        }
        
        return idVenda;
    }
    
    protected int buscaUltimaNFCe(){
        int idNFCe = 0;
        
        try {
            conn.executaSQL("SELECT idNFCe FROM Vendas.numNFCe");
            conn.rs.last();
            idNFCe = conn.rs.getInt(1);
        } catch (SQLException e) {
        }
        
        return idNFCe;
    }
    
    protected String preencheModeloIdentificacao() throws Exception{
        ACBrNFe acbrNFe = new ACBrNFe();
        String modeloIdentificacao = null;
        String modelo = null;
        String ambiente = null;
        
        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        if (acbrNFe.configLerValor(ACBrSessao.NFe, "ModeloDF").equals("0")) {
            modelo = "55";
        } else if (acbrNFe.configLerValor(ACBrSessao.NFe, "ModeloDF").equals("1")) {
            modelo = "65";
        }
        
        modeloIdentificacao
                = "[Identificacao]\n"
                + "natOp=VENDA\n"
                + "indPag=0\n"
                + "mod="+ modelo +"\n"
                + "serie=1\n"
                + "nNF="+ buscaUltimaNFCe()+"\n"
                + "dEmi="+ dataHora.format(formatter) +"\n"
                + "tpNF=1\n"
                + "Finalidade=0\n"
                + "idDest=1\n"
                + "indFinal=1\n"
                + "indPres=1\n"
                + "tpimp=4\n"
//                + "tpAmb="+ acbrNFe.configLerValor(ACBrSessao.NFe, "Ambiente") +"\n";
                + "tpAmb=2\n"; //APENAS PARA TESTES
        
        return modeloIdentificacao;
    }
    
    protected String preencheModeloEmitente(){
        String modeloNFCeEmitente = null;
        
        try {
            conn.executaSQL("SELECT CRT, CNPJ, insEstadual, razaoSocial, nomeFantasia, telefone, CEP, logradouro, numero, complemento, bairro, codMunicipio, cidade, UF FROM dbo.Parametros WHERE idFilial = 1");
            conn.rs.first();
            ModeloNFCe mod = new ModeloNFCe(conn.rs.getString(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5), conn.rs.getString(6), conn.rs.getString(7), conn.rs.getString(8), conn.rs.getString(9), 
                    conn.rs.getString(10), conn.rs.getString(11), conn.rs.getInt(12), conn.rs.getString(13), conn.rs.getString(14));
            
            modeloNFCeEmitente
                =  "\n[Emitente]\n"
                + "CRT="+mod.getCRT()+"\n"
                + "CNPJ="+mod.getCNPJEmi()+"\n"
                + "IE="+mod.getIEEmi()+"\n"
                + "Razao="+mod.getRazaoSocialEmi()+"\n"
                + "Fantasia="+mod.getNomeFanta()+"\n"
                + "Fone="+mod.getFoneEmi()+"\n"
                + "CEP="+mod.getCEPEmi()+"\n"
                + "Logradouro="+mod.getLogradouroEmi()+"\n"
                + "Numero="+mod.getNumeroEmi()+"\n"
                + "Complemento="+mod.getComplementoEmi()+"\n"
                + "Bairro="+mod.getBairroEmi()+"\n"
                + "CidadeCod="+mod.getCodCidadeEmi()+"\n"
                + "Cidade="+mod.getCidadeEmi()+"\n"
                + "UF="+mod.getUFEmi()+"\n";
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return modeloNFCeEmitente;
    }
   
    protected String preencheModeloDestinatario(){
        String modeloNFCeDestinatario = null;
        
        try {
            ModeloNFCe mod = new ModeloNFCe("9");
            
            modeloNFCeDestinatario
                    = "\n[Destinatario]\n"
                    + "indIEDest=9\n";
        } catch (Exception e) {
            System.out.println(e);
        }
        
        return modeloNFCeDestinatario;
    }
    protected String preencheModeloProduto(){
        int indiceProduto = 1;
        int indice = 0;
        String modeloNFCeProduto;
        String cabecalho = null;
        try {
            conn.executaSQL("SELECT idCFOP, idProduto, codigoBarras, descricao, NCM, CEST, unidade, quantidade, PMC, valorTotal, valorDesc FROM Vendas.Vendas WHERE idVenda = "+buscaUltimaVenda());
            
            while (conn.rs.next()) {
                dataProduto.add(new ModeloNFCe(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5), conn.rs.getString(6), conn.rs.getString(7), conn.rs.getString(8), 
                        conn.rs.getString(9), conn.rs.getString(10), conn.rs.getString(11), "0.00", "0.00", "0.00", "0.00", conn.rs.getString(3)));
                
                if(indiceProduto <= 9) {
                    cabecalho = "\n[Produto00"+ indiceProduto + "]\n";
                } else if (indiceProduto >= 10 && indiceProduto <= 99) {
                    cabecalho = "\n[Produto0"+ indiceProduto + "]\n";
                } else if ( indiceProduto >= 99) {
                    cabecalho = "\n[Produto"+ indiceProduto + "]\n";
                }
                
                modeloNFCeProduto
                        = cabecalho
                        + "CFOP="+dataProduto.get(indice).getCFOP()+"\n"
                        + "Codigo="+dataProduto.get(indice).getCodigo()+"\n"
                        + "cEAN="+dataProduto.get(indice).getcEAN()+"\n"
                        + "Descricao="+dataProduto.get(indice).getDescricao()+"\n"
                        + "NCM="+dataProduto.get(indice).getNCM()+"\n"
                        + "CEST="+dataProduto.get(indice).getCEST()+"\n"
                        + "Unidade="+dataProduto.get(indice).getUnidade()+"\n"
                        + "Quantidade="+dataProduto.get(indice).getQuantidade()+"\n"
                        + "ValorUnitario="+dataProduto.get(indice).getValorUnitario()+"\n"
                        + "ValorTotal="+dataProduto.get(indice).getValorTotal()+"\n"
                        + "ValorDesconto="+dataProduto.get(indice).getValorDesconto()+"\n"
                        + "vFrete="+dataProduto.get(indice).getvFrete()+"\n"
                        + "vSeg="+dataProduto.get(indice).getvSeg()+"\n"
                        + "vOutro="+dataProduto.get(indice).getvOutro()+"\n"
                        + "uTrib="+dataProduto.get(indice).getuTrib()+"\n"
                        + "cEANTrib="+dataProduto.get(indice).getcEANTrib()+"\n";
                
                aProduto.add(modeloNFCeProduto);
                indiceProduto++;
                indice++;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < aProduto.size(); i++){
            sb.append(aProduto.get(i));
        }
        
        return sb.toString();
    }
    
    protected String preencheModeloICMS(){
        int indiceProduto = 1;
        int indiceICMS = 0;
        String modeloNFCeICMS = null;
        String cabecalho = null;
        ObservableList<ModeloNFCe> dataICMS = FXCollections.observableArrayList();
        
        try {
            for(int i = 0; i < aProduto.size(); i++){
                conn.executaSQL("SELECT CST_ICMS, origem FROM Produto.Fisco AS PF, Produto.Produto AS PP WHERE PF.idProduto = PP.idProduto AND PP.idProduto = "+dataProduto.get(indiceICMS).getCodigo()+"");
                conn.rs.first();
                dataICMS.add(new ModeloNFCe(conn.rs.getString(1), conn.rs.getString(2)));
                
                if(indiceProduto <= 9) {
                    cabecalho = "\n[ICMS00" + indiceProduto + "]\n";
                } else if (indiceProduto >= 10 && indiceProduto <= 99) {
                    cabecalho = "\n[ICMS0" + indiceProduto + "]\n";
                } else if ( indiceProduto >= 99) {
                    cabecalho = "\n[ICMS" + indiceProduto + "]\n";
                }
                 
                modeloNFCeICMS
                        = cabecalho
                        + "CSOSN=" + dataICMS.get(i).getCSOSN()+ "\n"
                        + "Origem=" + dataICMS.get(i).getOrigem() + "\n";
                
                aICMS.add(modeloNFCeICMS);
                indiceProduto++;
                indiceICMS++;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < aICMS.size(); i++){
            sb.append(aICMS.get(i));
        }
        
        return sb.toString();
    }
    
    protected String preencheModeloPIS(){
        int indiceProduto = 1;
        int indicePIS = 0;
        String modeloNFCePIS = null;
        String cabecalho = null;
        ObservableList<ModeloNFCe> dataIPI = FXCollections.observableArrayList();
        
        try {
            for (int i = 0; i < aProduto.size(); i++) {
                conn.executaSQL("SELECT CST_PIS_COFINS_Saida, aliquotaPIS FROM Produto.Fisco AS PF, Produto.Produto AS PP WHERE PF.idProduto = PP.idProduto AND PP.idProduto = " + dataProduto.get(indicePIS).getCodigo() + "");
                conn.rs.first();
                dataIPI.add(new ModeloNFCe(conn.rs.getString(1), conn.rs.getBigDecimal(2)));
                
                if(indiceProduto <= 9) {
                    cabecalho = "\n[PIS00" + indiceProduto + "]\n";
                } else if (indiceProduto >= 10 && indiceProduto <= 99) {
                    cabecalho = "\n[PIS0" + indiceProduto + "]\n";
                } else if ( indiceProduto >= 99) {
                    cabecalho = "\n[PIS" + indiceProduto + "]\n";
                }
                
                modeloNFCePIS
                        = "\n[PIS00" + indiceProduto + "]\n"
                        + "CST=" + dataIPI.get(i).getCSTPIS() + "\n"
                        + "ValorBase=0.00\n"
                        + "Aliquota=" + dataIPI.get(i).getAliquotaPIS() + "\n"
                        + "Valor=0.00\n";
                        
                aPIS.add(modeloNFCePIS);
                indiceProduto++;
                indicePIS++;
            }
        } catch (SQLException e) {
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < aPIS.size(); i++){
            sb.append(aPIS.get(i));
        }
        
        return sb.toString();
    }
    
    protected String preencheModeloCOFINS() {
        int indiceProduto = 1;
        int indiceCOFINS = 0;
        String modeloNFCeCOFINS = null;
        String cabecalho = null;
        ObservableList<ModeloNFCe> dataCOFINS = FXCollections.observableArrayList();

        try {
            for (int i = 0; i < aProduto.size(); i++) {
                conn.executaSQL("SELECT CST_PIS_COFINS_Saida, aliquotaCOFINS FROM Produto.Fisco AS PF, Produto.Produto AS PP WHERE PF.idProduto = PP.idProduto AND PP.idProduto = " + dataProduto.get(indiceCOFINS).getCodigo() + "");
                conn.rs.first();

                dataCOFINS.add(new ModeloNFCe(conn.rs.getString(1), conn.rs.getBigDecimal(2)));
                
                if(indiceProduto <= 9) {
                    cabecalho = "\n[COFINS00" + indiceProduto + "]\n";
                } else if (indiceProduto >= 10 && indiceProduto <= 99) {
                    cabecalho = "\n[COFINS0" + indiceProduto + "]\n";
                } else if ( indiceProduto >= 99) {
                    cabecalho = "\n[COFINS" + indiceProduto + "]\n";
                }
                
                modeloNFCeCOFINS
                        = cabecalho
                        + "CST=" + conn.rs.getString(1) + "\n"
                        + "ValorBase=0.00\n"
                        + "Aliquota=" + conn.rs.getBigDecimal(2) + "\n"
                        + "Valor=0.00\n";
                
                aCOFINS.add(modeloNFCeCOFINS);
                indiceProduto++;
                indiceCOFINS++;
            }

        } catch (SQLException e) {
        }

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < aCOFINS.size(); i++){
            sb.append(aCOFINS.get(i));
        }
        
        return sb.toString();
    }
    
    protected String preencheModeloTotal(){
        String modeloNFCeTotal = null;
        //SELECT SUM(PMC) AS PMC, SUM(valorDesc) AS Desconto, SUM(valorTotal) AS Total
        try {
            conn.executaSQL("SELECT SUM(valorDesc) AS Desconto, SUM(valorTotal) AS Total FROM Vendas.Vendas WHERE idVenda = '"+buscaUltimaVenda()+"'");
            conn.rs.first();
            modeloNFCeTotal
                    = "BaseICMS=0.00\n"
                    + "ValorICMS=0.00\n"
                    + "vICMSDeson=0.00\n"
                    + "BaseICMSSubstituicao=0.00\n"
                    + "ValorICMSSubstituicao=0.00\n"
                    + "ValorProduto="+conn.rs.getBigDecimal(2)+"\n"
                    + "ValorFrete=0.00\n"
                    + "ValorSeguro=0.00\n"
                    + "ValorDesconto="+conn.rs.getBigDecimal(1)+"\n"
                    + "ValorIPI=0.00\n"
                    + "ValorPIS=0.00\n"
                    + "ValorCOFINS=0.00\n"
                    + "ValorOutrasDespesas=0.00\n"
                    + "ValorNota="+conn.rs.getBigDecimal(2)+"\n"
                    + "vFCP=0\n";
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        return modeloNFCeTotal;
    }
    
    protected String preencheModeloPagamento(){
        String modeloNFCePagamento = null;
        int indicePagamento = 1;
        if (VendaM.idPagamento.equals("03") || VendaM.idPagamento.equals("04")) {
            try {
                modeloNFCePagamento
                        = "\n[pag00" + indicePagamento + "]\n"
                        + "tPag=" + VendaM.idPagamento + "\n"
                        + "vPag=" + VendaM.valorTotalFinal + "\n"
                        + "tpIntegra=2";
            } catch (Exception e) {
            }
        } else {
            try {
                modeloNFCePagamento
                        = "\n[pag00" + indicePagamento + "]\n"
                        + "tPag=" + VendaM.idPagamento + "\n"
                        + "vPag=" + VendaM.valorTotalFinal + "\n";
            } catch (Exception e) {
            }
        }  
        return modeloNFCePagamento;
    }
    
    protected String preencheModeloContingencia(String xJust) throws Exception {
        String modeloNFCeContingencia = null;
        ACBrNFe acbrNFe = new ACBrNFe();
        String modelo = null;
        String ambiente = null;

        LocalDateTime dataHora = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        if (acbrNFe.configLerValor(ACBrSessao.NFe, "ModeloDF").equals("0")) {
            modelo = "55";
        } else if (acbrNFe.configLerValor(ACBrSessao.NFe, "ModeloDF").equals("1")) {
            modelo = "65";
        }
        
        try {
            modeloNFCeContingencia
                    = "[Identificacao]\n"
                    + "natOp=VENDA\n"
                    + "indPag=0\n"
                    + "mod=" + modelo + "\n"
                    + "serie=1\n"
                    + "nNF=" + buscaUltimaNFCe()+ "\n"
                    + "dEmi=" + dataHora.format(formatter) + "\n"
                    + "tpNF=1\n"
                    + "Finalidade=0\n"
                    + "idDest=1\n"
                    + "indFinal=1\n"
                    + "indPres=1\n"
                    + "tpimp=4\n"
                    + "tpAmb=2\n" //APENAS PARA TESTES
                    + "dhCont=" + dataHora.format(formatter) + "\n"
                    + "xJust=" + xJust + "\n";
        } catch (Exception e) {
        }

        return modeloNFCeContingencia;
    }
    
    public void carregarIni(boolean contingencia, String xJust) throws Exception {
        ACBrNFe acbrNFe = new ACBrNFe();
        String texto = null;
        if(contingencia){
            texto = "[infNFe]\n"
                + "versao=4.0\n"
                + "\n"
                + this.preencheModeloContingencia(xJust)
                + this.preencheModeloDestinatario()
                + this.preencheModeloEmitente()
                + this.preencheModeloProduto()
                + this.preencheModeloICMS()
                + this.preencheModeloPIS()
                + this.preencheModeloCOFINS()
                + "\n"
                + "[Total]\n"
                + this.preencheModeloTotal()
                + "\n"
                + "[DadosAdicionais]\n"
                + "Complemento= PROCON - Av.Rio Branco,25 - 4o a 7o RJ - (21)151;ALERJ - R 1o de Marco s/n - RJ - (21)25881418; Numero da Venda: " + idVenda + ""
                + "\n"
                + "\n"
                + "[Transportador]\n"
                + "modFrete=9"
                + "\n"
                + this.preencheModeloPagamento();
        } else {
            texto = "[infNFe]\n"
                + "versao=4.0\n"
                + "\n"
                + this.preencheModeloIdentificacao()
                + this.preencheModeloDestinatario()
                + this.preencheModeloEmitente()
                + this.preencheModeloProduto()
                + this.preencheModeloICMS()
                + this.preencheModeloPIS()
                + this.preencheModeloCOFINS()
                + "\n"
                + "[Total]\n"
                + this.preencheModeloTotal()
                + "\n"
                + "[DadosAdicionais]\n"
                + "Complemento= PROCON - Av.Rio Branco,25 - 4o a 7o RJ - (21)151;ALERJ - R 1o de Marco s/n - RJ - (21)25881418; Numero da Venda: " + idVenda + ""
                + "\n"
                + "\n"
                + "[Transportador]\n"
                + "modFrete=9"
                + "\n"
                + this.preencheModeloPagamento();
        }

        FileWriter arq = new FileWriter("C:\\Salvar\\NFCe.ini");
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.append(texto);
        arq.close();
        
        acbrNFe.carregarIni("C:\\Salvar\\NFCe.ini");
    }
}