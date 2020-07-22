package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import controller.ConexaoController;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import br.com.techfarma.model.cadastro.ClasseM;
import com.jfoenix.controls.JFXToggleButton;
import controller.MetodosController;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ClasseC implements Initializable, CadastroI{
    ConexaoController conn = new ConexaoController();

    @FXML
    private TextField txtDescricao;
    @FXML
    private ComboBox cbTipo;
    @FXML
    private JFXToggleButton tbEstqNeg, tbControlLote;
    @FXML
    private Button btCancelar;

    private String estqNegativo, controlLote, tipo;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }

    @FXML
    void keyPressed(KeyEvent event){
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
    public void inicioPadrao() {
        conn.Conexao();
        preencheCB();
    }
    
    @Override
    public void fecharJanela(){
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
    
    public void limparCampos(){
        txtDescricao.clear();
        cbTipo.getSelectionModel().clearSelection();
        tbEstqNeg.setSelected(false);
        tbControlLote.setSelected(false);
        txtDescricao.requestFocus();
    }
    
    private void preencheCB() {
        cbTipo.getItems().addAll(
                "Medicamento",
                "Bonificado",
                "Genérico",
                "Perfumaria",
                "Outro"
        );
    }
    
    public void verificaTB(){
        if(tbEstqNeg.isSelected()){
            estqNegativo = "S";
        } else {
            estqNegativo = "N";
        }
        
        if(tbControlLote.isSelected()){
            controlLote = "S";
        } else {
            controlLote = "N";
        }
    }
    
    private void verificaTipo(){
        switch(cbTipo.getSelectionModel().getSelectedItem().toString()){
            case "Medicamento":
                tipo = "MD";
                break;
            case "Bonificado":
                tipo = "BF";
                break;
            case "Genérico":
                tipo = "GN";
                break;
            case "Perfumaria":
                tipo = "PF";
                break;
            case "Outro":
                tipo = "OU";
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
        ArrayList<ComboBox> aCB = new ArrayList<>();
        aCB.add(cbTipo);

        if (met.verificaCampos(1, aTxt) & met.verificaComboBox(1, aCB)) {
            verificaTipo();
            verificaTB();
            ClasseM cls = new ClasseM(txtDescricao.getText(), tipo, estqNegativo, controlLote);
            
            String SQL = "INSERT INTO Classe (descricao, tipo, estqNegativo, controlLote) VALUES (?,?,?,?)";

            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setString(1, cls.getDescricao());
                pst.setString(2, cls.getTipo());
                pst.setString(3, cls.getEstqNeg());
                pst.setString(4, cls.getControlLote());
                pst.executeUpdate();

                met.insertSucess("Classe");
                limparCampos();
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }
    }
}