package br.com.techfarma.controller.venda;

import br.com.techfarma.model.consulta.VendaM;
import controller.ConexaoController;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class SelecionaCartaoVC implements Initializable{
    ConexaoController conn = new ConexaoController();
    
//    @FXML
//    private ListView lvCartoes;
    @FXML
    private TableView<VendaM> tvCartoes;
    @FXML
    private TableColumn tcDescricao;
    @FXML
    private TableColumn<VendaM, String> tcBandeira;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
//        carregaCartoes();
        carregaTabela();
    }
    
//    @FXML
//    public void fecharJanela(){
//        Stage stage = (Stage) lvCartoes.getScene().getWindow();
//        stage.close();
//    }
    
//    public void carregaCartoes(){
//        ObservableList<String> dataCartao = FXCollections.observableArrayList();
//        
//        try {
//            conn.executaSQL("SELECT descricao FROM dbo.Cartoes");
//            
//            while(conn.rs.next()){
//                dataCartao.add(conn.rs.getString(1));
//            }
//        } catch (SQLException e) {
//        }
//        
//        lvCartoes.setItems(dataCartao);
//    }
    private ObservableList<VendaM> data = FXCollections.observableArrayList();
    
    private void carregaTabela() {
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricaoCartao"));
        tcBandeira.setCellValueFactory(new PropertyValueFactory<>("bandeira"));

        

        try {
            conn.executaSQL("SELECT descricao, bandeira FROM dbo.Cartoes");
            
            while (conn.rs.next()) {
                data.add(new VendaM(conn.rs.getString(1), new ImageView(new Image("/br/com/techfarma/view/Images/Bandeiras/" + conn.rs.getString(2) + ""))));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        tvCartoes.setItems(data);
    }
    
//    public void selecionaCartao(KeyEvent event) {
//        switch (event.getCode()) {
//            case ENTER:
//                try {
//                    conn.executaSQL("SELECT tipoCartao, idCartao FROM dbo.Cartoes WHERE descricao = '" +lvCartoes.getSelectionModel().getSelectedItem() +"'");
//                    conn.rs.first();
//                    VendaM.tipoCartao = conn.rs.getString(1);
//                    VendaM.idCartao = conn.rs.getInt(2);
//                    if(VendaM.tipoCartao.equals("C")){
//                        VendaM.idPagamento = "03"; //vPag = Crédito
//                    } else if (VendaM.tipoCartao.equals("D")){
//                        VendaM.idPagamento = "04"; //vPag = Débito
//                    }
//                    
//                    fecharJanela();
//                } catch (SQLException ex) {
//                    System.out.println(ex);
//                }
//                
//                break;
//            default:
//                break;
//        }
//    }
}
