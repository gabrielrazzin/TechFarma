package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import com.jfoenix.controls.JFXToggleButton;
import controller.ConexaoController;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import br.com.techfarma.model.cadastro.GrupoM;
import controller.MetodosController;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class GrupoC implements Initializable, CadastroI{
    ConexaoController conn = new ConexaoController();

    @FXML
    private TextField txtDescricao, txtTaxComi, txtDescAuto, txtDescMax, txtTaxMarc, txtMeta;
    @FXML
    public JFXToggleButton tbDesconto, tbExgReceita, tbEstqNeg, tbCRM;
    @FXML
    public Button btCancelar;
    @FXML
    public ImageView ivDescricao;
    
    private String desconto, exgReceita, estqNeg, CRM, meta;
    
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
        txtTaxComi.setText("0.00");
        txtDescAuto.setText("0.00");
        txtDescMax.setText("0.00");
        txtTaxMarc.setText("0.00");
        txtMeta.clear();
        tbDesconto.setSelected(false);
        tbExgReceita.setSelected(false);
        tbEstqNeg.setSelected(false);
        tbCRM.setSelected(false);
        txtDescricao.requestFocus();
    }
    
    private void verificaTB(){
        if(tbDesconto.isSelected()){
            desconto = "S";
        } else {
            desconto = "N";
        }
        
        if(tbExgReceita.isSelected()){
            exgReceita = "S";
        } else {
            exgReceita = "N";
        }
        
        if(tbEstqNeg.isSelected()){
            estqNeg = "S";
        } else {
            estqNeg = "N";
        }
        
        if(tbCRM.isSelected()){
            CRM = "S";
        } else {
            CRM = "N";
        }
    } 
    
    private void verificaCampos() {
        if (txtMeta.getText().isEmpty()) {
            meta = "0.00";
        } else {
            meta = txtMeta.getText().replace(",", ".");
        }
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
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtDescricao);

        if (met.verificaCampos(1, aTxt)) {
            verificaTB();
            verificaCampos();

            GrupoM grupoM = new GrupoM(txtDescricao.getText(), (Double.parseDouble(txtTaxComi.getText().replace(",", ".")) / 100), (Double.parseDouble(txtDescAuto.getText().replace(",", ".")) / 100), (Double.parseDouble(txtDescMax.getText().replace(",", ".")) / 100),
                    (Double.parseDouble(txtTaxMarc.getText().replace(",", ".")) / 100), (Double.parseDouble(meta)/100), desconto, exgReceita, estqNeg, CRM);

            String SQL = "INSERT INTO dbo.Grupo (descricao, taxComissao, descAuto, descMax, taxMarcacao, meta, desconto, exgReceita, estqNegativo, coletaCRM) VALUES (?,?,?,?,?,?,?,?,?,?)";

            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setString(1, grupoM.getDescricao());
                pst.setDouble(2, grupoM.getTaxComissao());
                pst.setDouble(3, grupoM.getDescAuto());
                pst.setDouble(4, grupoM.getDescMax());
                pst.setDouble(5, grupoM.getTaxMarcacao());
                pst.setDouble(6, grupoM.getMeta());
                pst.setString(7, grupoM.getDesconto());
                pst.setString(8, grupoM.getExgReceita());
                pst.setString(9, grupoM.getEstqNegativo());
                pst.setString(10, grupoM.getColetaCRM());
                pst.executeUpdate();

                met.insertSucess("Grupo");
                limparCampos();
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }
    }
}
