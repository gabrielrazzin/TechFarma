package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import br.com.techfarma.model.cadastro.CFOPM;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CFOPC implements Initializable, CadastroI {
    ConexaoController conn = new ConexaoController();

    @FXML
    private TextField txtCod, txtDescricao, txtCFOPEquiv;
    @FXML
    private TextArea taObs;
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
        txtCod.clear();
        txtDescricao.clear();
        txtCFOPEquiv.clear();
        taObs.clear();
        txtCod.requestFocus();
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
                break;
            default:
                break;
        }
    }

    @Override
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtCod);
        aTxt.add(txtDescricao);
  
        if (met.verificaCampos(2, aTxt)) {
            CFOPM cfop = new CFOPM(Integer.parseInt(txtCod.getText()), txtDescricao.getText(), taObs.getText().replaceAll("\n", System.getProperty("line.separator")), Integer.parseInt(txtCFOPEquiv.getText()));
            String SQL = "INSERT INTO dbo.CFOP (idCFOP, descricao, observacao, CFOPEquiv) VALUES (?,?,?,?)";

            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setInt(1, cfop.getIdCFOP());
                pst.setString(2, cfop.getDescricao());
                pst.setString(3, cfop.getObservacao());
                pst.setInt(4, cfop.getCFOPEquiv());
                pst.executeUpdate();

                met.insertSucess("CFOP");
                limparCampos();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
}