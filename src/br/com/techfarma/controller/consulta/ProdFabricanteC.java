package br.com.techfarma.controller.consulta;

import br.com.techfarma.model.consulta.FabricanteM;
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

public class ProdFabricanteC implements Initializable{
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TableColumn tcCodigo, tcDescricao;
    @FXML
    private TableView<FabricanteM> tvFabricante;
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
        ObservableList<FabricanteM> data = FXCollections.observableArrayList();
        
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idFabricante"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        try {
            conn.executaSQL("SELECT idFabricante, descricao FROM dbo.Fabricante");

            while (conn.rs.next()) {
                data.add(new FabricanteM(conn.rs.getInt(1), conn.rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvFabricante.setItems(data);
    }
    
    public void enviaDados() {
        FabricanteM fab = tvFabricante.getSelectionModel().getSelectedItem();
        FabricanteM.idFabricanteS = fab.getIdFabricante();
        FabricanteM.nomeS = fab.getDescricao();
        fecharJanela();
    }
}
