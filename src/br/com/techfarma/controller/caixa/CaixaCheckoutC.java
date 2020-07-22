package br.com.techfarma.controller.caixa;

import br.com.techfarma.model.consulta.VendaM;
import controller.ConexaoController;
import controller.MetodosController;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
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
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CaixaCheckoutC implements Initializable{
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    String barCodeVenda;
    
    public static boolean vendaConcluida = false;
    
    private Stage stageFinalizaVenda, stageCancelamentoNFCe;
    @FXML
    private TableView<VendaM> tvCaixa;
    @FXML
    private TableColumn tcCodigo, tcDescricao, tcPreco, tcQuantidade, tcTotal, tcUM;
    @FXML
    private Label lbVendedor, lbTotal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();

        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcPreco.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        tcQuantidade.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        tcTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        tcUM.setCellValueFactory(new PropertyValueFactory<>("unidade"));
    }
    
    @FXML
    void keyPressed(KeyEvent event) throws Exception{
        switch(event.getCode()){
            case F1:
                verificaVenda();
                break;
            case F2:
                try {
                    if (stageFinalizaVenda == null) {
                        stageFinalizaVenda = met.abrirTelaEspera("/br/com/techfarma/view/caixa/Caixa - Finaliza Venda.fxml", stageFinalizaVenda);
                        stageFinalizaVenda.setOnHiding(we -> stageFinalizaVenda = null);
                        stageFinalizaVenda.showAndWait();
                    } else if (stageFinalizaVenda.isShowing()) {
                        stageFinalizaVenda.toFront();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                
                if(vendaConcluida == true){
                    tvCaixa.getItems().clear();
                    lbTotal.setText("0.00");
                    lbVendedor.setText("");
                }
                break;
            case F6:
                try {
                    if (stageCancelamentoNFCe == null) {
                        stageCancelamentoNFCe = met.abrirTelaEspera("/br/com/techfarma/view/view/Cancelamento NFCe.fxml", stageCancelamentoNFCe);
                        stageCancelamentoNFCe.setOnHiding(we -> stageCancelamentoNFCe = null);
                        stageCancelamentoNFCe.show();
                    } else if (stageCancelamentoNFCe.isShowing()) {
                        stageCancelamentoNFCe.toFront();
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
                
                break;
            default:
                break;
        }
    }
    
    private void verificaVenda() {
        barCodeVenda = JOptionPane.showInputDialog("Informe o código de barras da venda:");
        ObservableList<VendaM> data = FXCollections.observableArrayList();
        VendaM.idBarCode = barCodeVenda;
        
        try {
            conn.executaSQL("SELECT idVenda, idProduto, descricao, valorUnitario, quantidade, valorTotal, unidade FROM Vendas.Vendas WHERE idBarCode = '" + barCodeVenda + "' AND concluido = 'N'");
            
            while(conn.rs.next()){
                data.add(new VendaM(conn.rs.getInt(1), conn.rs.getInt(2), conn.rs.getString(3), conn.rs.getBigDecimal(4), conn.rs.getInt(5), conn.rs.getBigDecimal(6), conn.rs.getString(7)));
            }
        } catch (SQLException e) {
        }

        tvCaixa.setItems(data);
        calculaTotal();
    }
    
    private void calculaTotal(){
        try {
            conn.executaSQL("SELECT SUM(valorTotal), vendedor FROM Vendas.Vendas WHERE idBarCode = '" + barCodeVenda + "' AND concluido = 'N' GROUP BY vendedor");
            conn.rs.next();

            lbVendedor.setText(conn.rs.getString(2));
            lbTotal.setText(conn.rs.getBigDecimal(1).setScale(2).toPlainString());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void fecharVendaF(){
        tvCaixa.getItems().clear();
        lbTotal.setText("0.00");
        lbVendedor.setText("");
    }
    
     private void criarXML(){
        String XML = null;
        String nomeArquivo = null;
        
        conn.executaSQL("SELECT arquivoXML, chNFe FROM dbo.NFCe");
        
        try {
            while(conn.rs.next()){
                XML = conn.rs.getString(1);
                nomeArquivo = conn.rs.getString(2);
                
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document doc = builder.parse(new InputSource(new StringReader(XML)));

                // Write the parsed document to an xml file
                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);

                StreamResult result = new StreamResult(new File(nomeArquivo + ".xml"));
                transformer.transform(source, result);
            }
        } catch (IOException | SQLException | ParserConfigurationException | TransformerException | SAXException e) {
            System.out.println(e);
        }
    }
}