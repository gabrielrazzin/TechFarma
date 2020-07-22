package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.ClasseM;
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

public class ProdClasseC implements Initializable{
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TableColumn tcCodigo, tcDescricao;
    @FXML
    private TableView<ClasseM> tvClasse;
    @FXML
    private Button btClose;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }
    
    public void inicioPadrao() {
        conn.Conexao();
        carregaTabela();
    }

    public void fecharJanela() {
        Stage stage = (Stage) btClose.getScene().getWindow();
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
        ObservableList<ClasseM> data = FXCollections.observableArrayList();

        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idClasse"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));

        try {
            conn.executaSQL("SELECT idClasse, descricao FROM dbo.Classe");

            while (conn.rs.next()) {
                data.add(new ClasseM(conn.rs.getInt(1), conn.rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        tvClasse.setItems(data);
    }
    
     public void enviaDados() {
        ClasseM classe = tvClasse.getSelectionModel().getSelectedItem();
        ClasseM.idClasseS = classe.getIdClasse();
        ClasseM.descricaoS = classe.getDescricao();
        fecharJanela();
    }
    
}
