package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import br.com.techfarma.model.cadastro.GrupoCompraM;
import controller.ConexaoController;
import controller.MetodosController;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class GrupoCompraC implements Initializable, CadastroI{
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TextField txtDescricao;
    @FXML
    private Button btCancelar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }

    @Override
    public void inicioPadrao() {
        conn.Conexao();
    }

    @Override
    public void fecharJanela() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void keyPressed(KeyEvent event){
        switch (event.getCode()) {
            case F10:
                inserirBanco();
                break;
            case ESCAPE:
                fecharJanela();
                break;
            default:
                break;
            }
    }

    @Override
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtDescricao);
        
        if(met.verificaCampos(1, aTxt)){
            GrupoCompraM gc = new GrupoCompraM(txtDescricao.getText());
            
            String SQL = "INSERT INTO dbo.GrupoCompra (descricao) VALUES (?)";
            
            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setString(1, gc.getDescricao());
                pst.executeUpdate();
                
                met.insertSucess("Grupo de Compra");
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }
    }
}