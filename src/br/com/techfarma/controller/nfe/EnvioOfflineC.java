package br.com.techfarma.controller.nfe;

import com.acbr.nfe.ACBrNFe;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import controller.ConexaoController;
import controller.MetodosController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class EnvioOfflineC implements Initializable {
    ConexaoController conn = new ConexaoController();
    String XML = null;
    private boolean statusVerificacao;
    private ACBrNFe acbrNFe;
    @FXML
    private TextArea taRetorno;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
        try {
            acbrNFe = new ACBrNFe();
        } catch (Exception ex) {
            Logger.getLogger(EnvioOfflineC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void criaXML() throws IOException{
        conn.executaSQL("SELECT arquivoXML, chNFe FROM dbo.NFCe WHERE status = 'O'");
        try {
            while(conn.rs.next()){
                FileWriter fw = new FileWriter("C:\\NFCe\\Contingencia Offline\\"+ conn.rs.getString(2) + ".xml");
                fw.write(conn.rs.getString(1));
                fw.close();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public boolean consultaStatusSefaz() {
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

                Logger.getLogger(EnvioOfflineC.class.getName()).log(Level.SEVERE, null, e);
            }         
        } catch (IOException ex) {
            Logger.getLogger(EnvioOfflineC.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }
    
    public void listFilesFolder(final File folder){
        for(final File fileEntry: folder.listFiles()){
            if(fileEntry.isDirectory()){
                listFilesFolder(fileEntry);
            } else {
                System.out.println(fileEntry.getName());
            }
        }
    }
    
    public void enviaCotingencia() {
        final File folder = new File("C:\\NFCe\\Contingencia Offline\\");

        try {
            for (final File fileEntry : folder.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFilesFolder(fileEntry);
                } else {
                    acbrNFe.carregarXml(fileEntry.getAbsolutePath());
                }
            }

            if (consultaStatusSefaz()) {
                try {
                    
                    taRetorno.appendText(acbrNFe.enviar(0, false, false, false));
                } catch (Exception e) {
                    Logger.getLogger(EnvioOfflineC.class.getName()).log(Level.SEVERE, null, e);
                }
                
                loadXML();
                
                if(!aRejeicao.isEmpty()){
                    System.out.println("Items rejeitados");
                }
            }
        } catch (Exception e) {
        }
    }

    private File getLastModified() {
        File directory = new File("C:\\Salvar\\");
        
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
    
    private String stringXML, chNFe;
    ArrayList<String> aAutorizado = new ArrayList<>();
    ArrayList<String> aRejeicao = new ArrayList<>();

    public void loadXML() {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(getLastModified().getAbsolutePath()));
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("infProt");
            Element line = null;
            Element line2 = null;
            
            for (int i = 0; i < nList.getLength(); i++) {
                Element e1 = (Element) nList.item(i);
                NodeList chave = e1.getElementsByTagName("chNFe");

                Element e2 = (Element) nList.item(i);
                NodeList motivo = e1.getElementsByTagName("xMotivo");
                for (int j = 0; j < chave.getLength(); j++) {
                    line = (Element) chave.item(j);
                    line2 = (Element) motivo.item(j);

                    if (line2.getFirstChild().getNodeValue().contains("Rejeicao")) {
                        aRejeicao.add(line.getFirstChild().getNodeValue());
                    } else {
                        aAutorizado.add(line.getFirstChild().getNodeValue());
                    }
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(EnvioOfflineC.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void atualizaDatabase() {
        loadXML();
        
        for (int i = 0; i < aAutorizado.size(); i++) {
            conn.executaSQL("UPDATE dbo.NFCe SET status = 'A' WHERE chNFe = '" + aAutorizado.get(i) + "'");
        }
    }
    
    public void consultaXML() throws Exception {
//        acbrNFe.carregarXml("C:\\NFCe\\Contingencia Offline\\33200430424716000187650010000001519328515299-nfe.xml");
//        String ret = acbrNFe.consultar("33200430424716000187650010000001519328515299");
        
        
//        if(ret.contains("Autorizado o uso da NF-e")){
//            JOptionPane.showMessageDialog(null, "Sucesso!");
//        }
        
//        acbrNFe.gravarXml(0);
    }
    
    
}
