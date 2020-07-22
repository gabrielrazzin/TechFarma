package br.com.techfarma.controller.consulta;

import br.com.techfarma.model.cadastro.ProdutoM;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ProdCodBarraC implements Initializable {
    @FXML
    private TextField txtCodBarras;
    @FXML
    private ListView listCod;
    @FXML
    private Button btClose;
    
    ArrayList<String> aCod = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        listCod.getItems().setAll(ProdutoM.listaCodBarras);
    }
    
    public void fecharJanela(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case DELETE:
                int indice = listCod.getSelectionModel().getSelectedIndex();
                aCod.remove(indice);
                listCod.getItems().clear();

                for (String teste : aCod) {
                    listCod.getItems().add(teste);
                }
            default:
                break;
        }
    }
    
    public void addCodExtra() {
        if (ProdutoM.listaCodBarras.get(ProdutoM.posAtualLC) == null) {
            listCod.getItems().add(txtCodBarras.getText());
            ProdutoM.listaCodBarras.set(ProdutoM.posAtualLC, txtCodBarras.getText());
            ProdutoM.posAtualLC++;
        }
        
        listCod.getItems().clear();
        listCod.getItems().setAll(ProdutoM.listaCodBarras);
    }
}
