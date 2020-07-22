package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.CaixaM;

public class CaixaC implements Initializable{
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TableView<CaixaM> tvCaixa;
    @FXML
    private TableColumn tcCodigo, tcDescricao, tcPreco, tcQtd, tcTotal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
    }
}
