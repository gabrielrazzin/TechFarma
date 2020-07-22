package br.com.techfarma.controller.nfe;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser implements Initializable {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    public ArrayList readXML(String file) throws IOException, SAXException{
        String cNF = null;
        String nNF = null;
        String chNFe = null;
        
        ArrayList<String> lista = new ArrayList<>();
        
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File(file));
            document.getDocumentElement().normalize();

            NodeList nList = document.getElementsByTagName("ide");
            NodeList nList2 = document.getElementsByTagName("infProt");
            
            Node node = nList.item(0);
                
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element e1 = (Element) node;
                cNF = e1.getElementsByTagName("cNF").item(0).getTextContent();
                nNF = e1.getElementsByTagName("nNF").item(0).getTextContent();
            }
            
            Node node2 = nList2.item(0);
                
            if(node.getNodeType() == Node.ELEMENT_NODE){
                Element e2 = (Element) node;
                chNFe = e2.getElementsByTagName("chNFe").item(0).getTextContent();
            }
//                
//            for (int temp = 0; temp < nList.getLength(); temp++){
//                Node node = nList.item(temp);
//                
//                if(node.getNodeType() == Node.ELEMENT_NODE){
//                    Element e1 = (Element) node;
//                    String cNF = e1.getElementsByTagName("cNF").item(0).getTextContent();
//                    System.out.println(cNF);
//                }
//            }
//            
//            for (int temp = 0; temp < nList2.getLength(); temp++){
//                Node node = nList2.item(temp);
//                
//                if(node.getNodeType() == Node.ELEMENT_NODE){
//                    Element e2 = (Element) node;
//                    String CNPJ = e2.getElementsByTagName("CNPJ").item(0).getTextContent();
//                    System.out.println(CNPJ);
//                }
//            }
//             
//            for (int temp = 0; temp < nList3.getLength(); temp++){
//                Node node = nList3.item(temp);
//                
//                if(node.getNodeType() == Node.ELEMENT_NODE){
//                    Element e3 = (Element) node;
//                    String chNFe = e3.getElementsByTagName("chNFe").item(0).getTextContent();
//                    System.out.println(chNFe);
//                }
//            }
            
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(XMLParser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        lista.add(0, cNF);
        lista.add(1, nNF);
        lista.add(2, chNFe);
        
        return lista;
    }
}
