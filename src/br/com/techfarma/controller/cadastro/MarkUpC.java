package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import br.com.techfarma.model.cadastro.MarkUpM;
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

public class MarkUpC implements Initializable, CadastroI{
    ConexaoController conn = new ConexaoController();

    @FXML
    private TextField txtDescricao, txtMarkUp;
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
    
    public void limparCampos(){
        txtDescricao.clear();
        txtMarkUp.clear();
        txtDescricao.requestFocus();
    }

    @FXML
    void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case F10:
                inserirBanco();
                break;
            case F4:
                limparCampos();
                break;
            case ESCAPE:
                fecharJanela();
            default:
                break;
        }
    }
    
    @Override
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtDescricao);
        aTxt.add(txtMarkUp);

        if (met.verificaCampos(2, aTxt)) {
            MarkUpM muM = new MarkUpM(txtDescricao.getText(), (Double.parseDouble(txtMarkUp.getText().replace(",", "."))/100));

            String SQL = "INSERT INTO dbo.ListaMarkUp (descricao, markUP) VALUES (?,?)";

            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setString(1, muM.getDescricao());
                pst.setDouble(2, muM.getMarkUp());
                pst.executeUpdate();
                
                met.insertSucess("Mark-UP");
                limparCampos();
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }
    }
}