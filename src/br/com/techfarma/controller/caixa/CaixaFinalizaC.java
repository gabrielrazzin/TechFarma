package br.com.techfarma.controller.caixa;

import br.com.techfarma.controller.venda.VendaC;
import br.com.techfarma.model.consulta.VendaM;
import com.acbr.ACBrSessao;
import com.acbr.nfe.ACBrNFe;
import controller.ConexaoController;
import br.com.techfarma.controller.nfe.ModeloNFCeC;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CaixaFinalizaC implements Initializable {
    private ACBrNFe acbrNFe;
    ConexaoController conn = new ConexaoController();
    
    private String statusNFCe, stringXML;
    private boolean statusVerificacao = true;
    @FXML
    private Label lbTotal, lbTroco;
    @FXML
    private TextField txtDinheiro, txtCartao, txtConvenio;
    @FXML
    private Button btClose;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
        
        preencheCampos();
        try {
            acbrNFe = new ACBrNFe();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    @FXML
    void keyPressed(KeyEvent event) throws Exception{
        switch (event.getCode()){
            case F10:
                emitirNFCe();
                break;
            
            default:
                break;
        }
    }
    
    public void fecharJanela(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }
    
    private void preencheCampos() {
        conn.executaSQL("SELECT idPagamento, valor, troco FROM Vendas.FP WHERE idBarCode = '" + VendaM.idBarCode + "' AND status = 'P'");

        try {
            conn.rs.first();
            String idPagamento = conn.rs.getString(1);
            BigDecimal valor = conn.rs.getBigDecimal(2);
            BigDecimal troco = conn.rs.getBigDecimal(3);
            
            lbTotal.setText(String.format("%.2f", valor));
            lbTroco.setText(String.format("%.2f", troco));

            if (idPagamento.equals("01")) {
                VendaM.idPagamento = idPagamento;
                VendaM.valorTotalFinal = Double.parseDouble(valor.toString());
                
                if(!troco.equals(0.00)){
                    txtDinheiro.setText(String.format("%.2f", valor.add(troco)));
                } else {
                    txtDinheiro.setText(String.format("%.2f", valor));
                }
            } else if (idPagamento.equals("02") || idPagamento.equals("03")) {
                VendaM.idPagamento = idPagamento;
                VendaM.valorTotalFinal = Double.parseDouble(valor.toString());
                txtCartao.setText(String.format("%.2f", valor));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private boolean consultaStatusSefaz() {
        String ret = null;
        try (FileWriter file = new FileWriter(new File("C:\\Users\\Gabriel\\Documents\\NetBeansProjects\\ProjetoFarmaciaMundial\\Logs\\Log - Status.txt"))) {
            try {
                ret = acbrNFe.statusServico();
                file.append(ret);
                statusVerificacao = true;
                return true;
            } catch (Exception e) {
                if (e.getMessage().contains("TimeOut de Requisição") || e.getMessage().contains("Inativo ou Inoperante tente novamente.") || e.getMessage().contains("Erro Interno: 12002")) {
                    file.append(e.getMessage());
                    statusVerificacao = false;
                    return false;
                }

                Logger.getLogger(VendaC.class.getName()).log(Level.SEVERE, null, e);
            }         
        } catch (IOException ex) {
            Logger.getLogger(VendaC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    private void removerArquivos() {
        File folder = new File("C:\\Salvar");
        if (folder.isDirectory()) {
            File[] sun = folder.listFiles();
            for (File toDelete : sun) {
                toDelete.delete();
            }
        }
    }
    
    private void atualizaDatabase() {
        int idNFCe = 0;

        conn.executaSQL("UPDATE Vendas.Vendas SET concluido = 'S' WHERE idVenda = " + VendaM.idVendaFinal + "");
        conn.executaSQL("UPDATE Vendas.FP SET status = 'C' WHERE idVenda = " + VendaM.idVendaFinal + "");

        try {
            conn.executaSQL("SELECT idNFCe FROM Vendas.numNFCe");
            conn.rs.first();

            idNFCe = conn.rs.getInt(1);
            idNFCe = idNFCe + 1;
            
            String SQL = "UPDATE Vendas.numNFCe SET idNFCe = ?";
            PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
            pst.setInt(1, idNFCe);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro atualizaDatabase: " + e);
        }
    }
    
    private void contingenciaOffline(String xJust) {
        try {
            if (!acbrNFe.configLerValor(ACBrSessao.NFe, "FormaEmissao").equals("8")) {
                acbrNFe.configGravarValor(ACBrSessao.NFe, "FormaEmissao", "8");
                acbrNFe.configGravarValor(ACBrSessao.NFe, "PathNFe", "C:\\NFCe\\Contingencia Offline\\");
            }

            statusVerificacao = false;
            acbrNFe.limparLista();
            
            ModeloNFCeC NFCe = new ModeloNFCeC();
            NFCe.carregarIni(true, xJust);
            acbrNFe.assinar();
            acbrNFe.gravarXml(0);
            acbrNFe.imprimir();
            statusNFCe = "O";
        } catch (Exception e) {
            Logger.getLogger(VendaC.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void emissaoNormal() {
        try {
            if (!acbrNFe.configLerValor(ACBrSessao.NFe, "FormaEmissao").equals("0")) {
                acbrNFe.configGravarValor(ACBrSessao.NFe, "FormaEmissao", "0");
                acbrNFe.configGravarValor(ACBrSessao.NFe, "PathNFe", "C:\\NFCe\\Autorizadas\\");
            }
            acbrNFe.limparLista();
            statusVerificacao = true;
            acbrNFe.gravarXml(0);
            acbrNFe.imprimir();
            statusNFCe = "N";

        } catch (Exception e) {
            Logger.getLogger(VendaC.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private void emitirNFCe() {
        try {
            ModeloNFCeC NFCe = new ModeloNFCeC();
            NFCe.carregarIni(false, "");

            if (acbrNFe.validarRegrasdeNegocios().contains("Rejeição")) {
                int resposta = JOptionPane.showConfirmDialog(null, "Divergência de dados no cadastro de produtos, deseja emitir em OFFLINE?");

                switch (resposta) {
                    case 0:
                        contingenciaOffline("Divergencia de dados de produtos.");
                        removerArquivos();
                        atualizaDatabase();
                        inserirVendaNFCe();
                        inserirNFCe();
                        CaixaCheckoutC.vendaConcluida = true;
                        fecharJanela();
                        break;
                    case 1:
                        fecharJanela();
                        break;
                    default:
                        break;
                }
            } else {
                if (consultaStatusSefaz()) {
                        emissaoNormal();
                        removerArquivos();
                        atualizaDatabase();
                        inserirVendaNFCe();
                        inserirNFCe();
                        CaixaCheckoutC.vendaConcluida = true;
                        fecharJanela();
                    
                } else {
                    int resposta = JOptionPane.showConfirmDialog(null, "Servidor do SEFAZ inativo ou inoperante, deseja emitir em OFFLINE?");

                    switch (resposta) {
                        case 0:
                            contingenciaOffline("Sem conexão com a internet.");
                            removerArquivos();
                            atualizaDatabase();
                            inserirVendaNFCe();
                            inserirNFCe();
                            CaixaCheckoutC.vendaConcluida = true;
                            fecharJanela();
                            break;
                        case 1:
                            fecharJanela();
                            break;
                        default:
                            break;
                    }
                }
            }
        } catch (Exception e) {
            Logger.getLogger(VendaC.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    private String cNF = null;
    private String nNF = null;
    private String chNFe = null;
    private String CNPJ = null;
    private String serie = null;

    private File getLastModified() {
        File directory;
        
        if(statusVerificacao){
            directory = new File("C:\\NFCe\\Autorizadas\\");
        } else {
            directory = new File("C:\\NFCe\\Contingencia Offline\\");
        }
        
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;

        if (files != null) {
            for (File file : files) {
                if (file.lastModified() > lastModifiedTime) {
                    chosenFile = file;
                    lastModifiedTime = file.lastModified();
                }
            }
        }

        return chosenFile;
    }
    
     private void obterXML() {
        try {
            Reader fileReader = new FileReader(new File(getLastModified().getAbsolutePath()));
            BufferedReader bf = new BufferedReader(fileReader);
            StringBuilder sb = new StringBuilder();
            String line = bf.readLine();
            
            while(line != null){
                sb.append(line).append("\n");
                line = bf.readLine();
            }
            
            stringXML = sb.toString();
        } catch (IOException ex) {
            Logger.getLogger(CaixaCheckoutC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void loadXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(getLastModified().getAbsolutePath()));
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("ide");
            NodeList nList2 = document.getElementsByTagName("infProt");
            NodeList nList3 = document.getElementsByTagName("emit");
            NodeList nListOff = document.getElementsByTagName("infNFe");
            
            Node node = nList.item(0);
                
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e1 = (Element) node;
                cNF = e1.getElementsByTagName("cNF").item(0).getTextContent();
                serie = e1.getElementsByTagName("serie").item(0).getTextContent();
                nNF = e1.getElementsByTagName("nNF").item(0).getTextContent();
            }
           
            if(statusVerificacao){
                Node node2 = nList2.item(0);
                
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element e2 = (Element) node2;
                    chNFe = e2.getElementsByTagName("chNFe").item(0).getTextContent();
                }
            } else {
                Node nodeOff = nListOff.item(0);
                
                if (nodeOff.getNodeType() == Node.ELEMENT_NODE) {
                    Element eOff = (Element) nodeOff;
                    chNFe = eOff.getAttribute("Id").replace("NFe", "");
                }
            }
            
            Node node3 = nList3.item(0);
                
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element e3 = (Element) node3;
                CNPJ = e3.getElementsByTagName("CNPJ").item(0).getTextContent();
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(CaixaFinalizaC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    private void inserirVendaNFCe() { 
        loadXML();
        
        try {
            String SQL = "INSERT INTO Vendas.NFCe (idVenda, CNPJ, cNF, nNF, chNFe) VALUES (?,?,?,?,?)";
            PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
            
            pst.setInt(1, VendaM.idVendaFinal);
            pst.setString(2, CNPJ);
            pst.setString(3, cNF);
            pst.setString(4, nNF);
            pst.setString(5, chNFe);
            
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void inserirNFCe() throws Exception {
        obterXML();
        loadXML();
        
        try {
            String SQL = "INSERT INTO dbo.NFCe (idVenda, nNF, cNF, serie, chNFe, status, arquivoXML) VALUES (?,?,?,?,?,?,?)";
            PreparedStatement pst = conn.getConexao().prepareStatement(SQL);

            pst.setInt(1, VendaM.idVendaFinal);
            pst.setString(2, nNF);
            pst.setString(3, cNF);
            pst.setString(4, serie);
            pst.setString(5, chNFe);
            pst.setString(6, statusNFCe);
            pst.setString(7, stringXML);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
