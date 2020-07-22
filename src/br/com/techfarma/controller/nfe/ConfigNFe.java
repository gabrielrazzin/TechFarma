package br.com.techfarma.controller.nfe;


import com.acbr.ACBrSessao;
import com.acbr.nfe.ACBrNFe;
import controller.ConexaoController;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javax.security.auth.login.LoginException;
import javax.swing.JFileChooser;

public class ConfigNFe implements Initializable{
    private ACBrNFe acbrNFe;
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TextField txtIdCSC, txtCSC, txtSchemaPath, txtCertPath, txtCertPassword, txtCertNumero, txtTimeOut;
    @FXML
    private ComboBox cbModeloDF, cbUFDestino, cbSSLType, cbCrypt, cbXmlSign, cbHttp;
    @FXML
    private CheckBox chbHomologacao, chbProducao;
    
    private String ambiente;
    
    ModeloNFCeC mod = new ModeloNFCeC();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
        try {
            acbrNFe = new ACBrNFe();
        } catch (Exception ex) {
            Logger.getLogger(ConfigNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
        preencheComboBox();
        loadConfig();
    }
    
    private void preencheComboBox(){
        cbModeloDF.getItems().addAll("moNFe", "moNFCe");
        cbUFDestino.getItems().addAll("AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MG", "MS", "MT", "PA", "PB", "PE", "PI", "PR", "RJ", "RN", "RO", "RR", "RS", "SC", "SE", "SP", "TO");
        cbSSLType.getItems().addAll("LT_all", "LT_SSLv2", "LT_SSLv3", "LT_TLSv1", "LT_TLSv1_1", "LT_TLSv1_2", "LT_SSHv2");
        cbCrypt.getItems().addAll("cryNone", "cryOpenSSL", "cryCapicom", "cryWinCrypt");
        cbHttp.getItems().addAll("httpNone", "httpWinINet", "httpWinHttp", "httpOpenSSL", "httpIndy");
        cbXmlSign.getItems().addAll("xsNone", "xsXmlSec", "xsMsXml", "xsMsXmlCapicom", "xsLibXml2");
    }
    
    public void selectSchema() {
        try {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Selecione o diretorio");
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(chooser) != JFileChooser.APPROVE_OPTION) {
                return;
            }
            txtSchemaPath.setText(chooser.getCurrentDirectory().getPath());
        } catch (Exception ex) {
            Logger.getLogger(ConfigNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void selectCertificado() {
        try {
            FileChooser chooser = new FileChooser();
            chooser.setInitialDirectory(new File ("."));
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("pfx", "*.pfx"));
            File selectedFile = chooser.showOpenDialog(null);
            
            if (selectedFile != null) {
                txtCertPath.setText(selectedFile.getAbsolutePath());
            } else {
                System.out.println("Arquivo inválido");
            }
        } catch (Exception ex) {
            Logger.getLogger(ConfigNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void verificaAmbiente(){
        if(chbHomologacao.isSelected()){
            ambiente = "1";
            chbProducao.setDisable(true);
            chbHomologacao.setDisable(false);
        } else if(chbProducao.isSelected()){
            ambiente = "0";
            chbHomologacao.setDisable(true);
            chbProducao.setDisable(false);
        } else {
            ambiente = null;
            chbHomologacao.setDisable(false);
            chbProducao.setDisable(false);
        }
    }
    public void loadConfig(){
        try {
            acbrNFe.configLer();
            cbModeloDF.getSelectionModel().select(Integer.parseInt(acbrNFe.configLerValor(ACBrSessao.NFe, "ModeloDF")));
            txtIdCSC.setText(acbrNFe.configLerValor(ACBrSessao.NFe, "IdCSC"));
            txtCSC.setText(acbrNFe.configLerValor(ACBrSessao.NFe, "CSC"));
            cbCrypt.getSelectionModel().select(Integer.parseInt(acbrNFe.configLerValor(ACBrSessao.DFe, "SSLCryptLib")));
            cbHttp.getSelectionModel().select(Integer.parseInt(acbrNFe.configLerValor(ACBrSessao.DFe, "SSLHttpLib")));
            cbXmlSign.getSelectionModel().select(Integer.parseInt(acbrNFe.configLerValor(ACBrSessao.DFe, "SSLXmlSignLib")));
            txtCertPath.setText(acbrNFe.configLerValor(ACBrSessao.DFe, "ArquivoPFX"));
            txtCertPassword.setText(acbrNFe.configLerValor(ACBrSessao.DFe, "Senha"));
            txtCertNumero.setText(acbrNFe.configLerValor(ACBrSessao.DFe, "NumeroSerie"));
            txtSchemaPath.setText(acbrNFe.configLerValor(ACBrSessao.NFe, "PathSchemas"));
            cbUFDestino.getSelectionModel().select(acbrNFe.configLerValor(ACBrSessao.DFe, "UF"));
            
            ambiente = acbrNFe.configLerValor(ACBrSessao.NFe, "Ambiente");
            chbHomologacao.setSelected("1".equals(ambiente));
            chbProducao.setSelected("2".equals(ambiente));
            
            cbSSLType.getSelectionModel().select(Integer.parseInt(acbrNFe.configLerValor(ACBrSessao.NFe, "SSLType")));
            txtTimeOut.setText(acbrNFe.configLerValor(ACBrSessao.NFe, "Timeout"));
        } catch (Exception ex) {
            Logger.getLogger(ConfigNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void saveConfig(){
        try {
            acbrNFe.configGravarValor(ACBrSessao.NFe, "ModeloDF", cbModeloDF.getSelectionModel().getSelectedIndex());
            acbrNFe.configGravarValor(ACBrSessao.NFe, "IdCSC", txtIdCSC.getText());
            acbrNFe.configGravarValor(ACBrSessao.NFe, "CSC", txtCSC.getText());
            
            acbrNFe.configGravarValor(ACBrSessao.DFe, "SSLCryptLib", cbCrypt.getSelectionModel().getSelectedIndex());
            
            acbrNFe.configGravarValor(ACBrSessao.DFe, "SSLHttpLib", cbHttp.getSelectionModel().getSelectedIndex());           
            
            acbrNFe.configGravarValor(ACBrSessao.DFe, "SSLXmlSignLib", cbXmlSign.getSelectionModel().getSelectedIndex());            
            
            acbrNFe.configGravarValor(ACBrSessao.DFe, "ArquivoPFX", txtCertPath.getText());            
            
            acbrNFe.configGravarValor(ACBrSessao.DFe, "Senha", txtCertPassword.getText());            
            
            acbrNFe.configGravarValor(ACBrSessao.DFe, "NumeroSerie", txtCertNumero.getText());            
            
            acbrNFe.configGravarValor(ACBrSessao.NFe, "PathSchemas", txtSchemaPath.getText());            
            
            acbrNFe.configGravarValor(ACBrSessao.DFe, "UF", cbUFDestino.getSelectionModel().getSelectedItem());           
            
            acbrNFe.configGravarValor(ACBrSessao.NFe, "Ambiente", ambiente);        
            acbrNFe.configGravarValor(ACBrSessao.NFe, "SSLType", cbSSLType.getSelectionModel().getSelectedIndex()); 
            acbrNFe.configGravarValor(ACBrSessao.NFe, "Timeout", txtTimeOut.getText());
            
            acbrNFe.configGravar();
        } catch (Exception ex) {
            Logger.getLogger(ConfigNFe.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void carregarIni() throws Exception {
        acbrNFe = new ACBrNFe();
        
        String texto = "[infNFe]\n"
                + "versao=4.0\n"
                + "\n"
                + mod.preencheModeloIdentificacao()
                + mod.preencheModeloDestinatario()
                + mod.preencheModeloEmitente()
                + mod.preencheModeloProduto()
                + mod.preencheModeloICMS()
                + mod.preencheModeloPIS()
                + mod.preencheModeloCOFINS()
                + "\n"
                + "[Total]\n"
                + mod.preencheModeloTotal()
                + "\n"
                + "[DadosAdicionais]\n"
                + "Complemento= PROCON - Av.Rio Branco,25 - 4o a 7o RJ - (21)151;ALERJ - R 1o de Marco s/n - RJ - (21)25881418"
                + "\n"
                + "\n"
                + "[Transportador]\n"
                + "modFrete=9"
                + "\n"
                + mod.preencheModeloPagamento();

        FileWriter arq = new FileWriter("C:\\Salvar\\NFCE01.ini");
        PrintWriter gravarArq = new PrintWriter(arq);
        gravarArq.append(texto);
        arq.close();
        
        acbrNFe.carregarIni("C:\\Salvar\\NFCE01.ini");
    }
    
}