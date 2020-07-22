package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.PrincipioAtivoM;
import controller.ConexaoController;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ProdPrincipioC implements Initializable {
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TableColumn tcCodigo, tcDescricao;
    @FXML
    private TableView<PrincipioAtivoM> tvPrincipio;
    @FXML
    private Button btCancelar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }

    public void inicioPadrao() {
        conn.Conexao();
        carregaTabela();
    }

    public void fecharJanela() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void keyPressed(KeyEvent event){
        switch(event.getCode()){
            case ENTER:
                enviaDados();
                break;
            case ESCAPE:
                fecharJanela();
                break;
            default:
                break;
        }
    }

    public void carregaTabela() {
        ObservableList<PrincipioAtivoM> data = FXCollections.observableArrayList();
        
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idPrincipio"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        try {
            conn.executaSQL("SELECT idPrincipio, descricao FROM dbo.PrincipioAtivo");

            while (conn.rs.next()) {
                data.add(new PrincipioAtivoM(conn.rs.getInt(1), conn.rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvPrincipio.setItems(data);
    }
    
    public void enviaDados() {
        PrincipioAtivoM pa = tvPrincipio.getSelectionModel().getSelectedItem();
        PrincipioAtivoM.idPrincipioS = pa.getIdPrincipio();
        PrincipioAtivoM.descricaoS = pa.getDescricao();
        fecharJanela();
    }
    
}
