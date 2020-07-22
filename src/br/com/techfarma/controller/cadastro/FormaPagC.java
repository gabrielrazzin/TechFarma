package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import br.com.techfarma.model.cadastro.FormaPagM;
import com.jfoenix.controls.JFXToggleButton;
import controller.ConexaoController;
import controller.MetodosController;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class FormaPagC implements Initializable, CadastroI{
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TextField txtDescricao, txtArredonda, txtIndiceR, txtIndiceM, txtLocalECF, txtNumVias, txtJuros;
    @FXML
    private ComboBox cbParcelas;
    @FXML
    private JFXToggleButton tbArredonda, tbJuros, tbDesconto, tbComissao;
    @FXML
    private Button btCancelar;
    
    private String arredonda, juros, desconto;
    private int numVias, parcelas;
    private double indiceR, indiceM, taxaJuros;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }
    
    @Override
    public void inicioPadrao() {
        conn.Conexao();
        preencheCBParcela();
    }

    @Override
    public void fecharJanela() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();      
    }
    
    public void limparCampos(){
        txtDescricao.clear();
        txtArredonda.clear();
        txtIndiceR.clear();
        txtIndiceM.clear();
        txtLocalECF.clear();
        txtNumVias.clear();
        txtJuros.clear();
        tbArredonda.setSelected(false);
        tbJuros.setSelected(false);
        tbDesconto.setSelected(false);
        tbComissao.setSelected(false);
        txtDescricao.requestFocus();
    }
    
    @FXML
    void keyPressed(KeyEvent event){
        switch(event.getCode()){
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
    
    private void preencheCBParcela(){
        ObservableList<Integer> listaParcela = FXCollections.observableArrayList();
        
        for(int i = 1; i <= 10; i++){
            listaParcela.add(i);
        }
        
        cbParcelas.setItems(listaParcela);
    }
    
    private void verificaTB(){
        if(tbArredonda.isSelected()){
            arredonda = "S";
        } else {
            arredonda = "N";
        }
        
        if(tbJuros.isSelected()){
            juros = "S";
        } else {
            juros = "N";
        }
        
        if(tbDesconto.isSelected()){
            desconto = "S";
        } else {
            desconto = "N";
        }
    }

    private void verificaTextField() {
        if (txtIndiceR.getText().isEmpty()) {
            indiceR = 0.00;
        } else {
            indiceR = Double.parseDouble(txtIndiceR.getText());
        }

        if (txtIndiceM.getText().isEmpty()) {
            indiceM = 0.00;
        } else {
            indiceM = Double.parseDouble(txtIndiceM.getText());
        }

        if (txtNumVias.getText().isEmpty()) {
            numVias = 0;
        } else {
            numVias = Integer.parseInt(txtNumVias.getText());
        }

        if (cbParcelas.getSelectionModel().isEmpty()) {
            parcelas = 0;
        } else {
            parcelas = Integer.parseInt(cbParcelas.getSelectionModel().getSelectedItem().toString());
        }

        if (txtJuros.getText().isEmpty()) {
            taxaJuros = 0.00;
        } else {
            taxaJuros = Double.parseDouble(txtJuros.getText());
        }
    }
    
    @Override
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtDescricao);
        
        if(met.verificaCampos(1, aTxt)){
            verificaTB();
            verificaTextField();
            
            FormaPagM pag = new FormaPagM(txtDescricao.getText(), txtArredonda.getText(), (indiceR/100), (indiceM/100), txtLocalECF.getText(), numVias, 
                    arredonda, juros, parcelas, taxaJuros, desconto);
            
            String SQL = "INSERT INTO dbo.FormasPag (descricao, arredondamento, indiceR, indiceM, localECF, numVias, arredonda, cobraJuros, parcelaInicial, taxaJuros, desconto) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            
            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);     
                pst.setString(1, pag.getDesricao());
                pst.setString(2, pag.getArredondamento());
                pst.setDouble(3, pag.getIndiceR());
                pst.setDouble(4, pag.getIndiceM());
                pst.setString(5, pag.getLocalECF());
                pst.setInt(6, pag.getNumVias());
                pst.setString(7, pag.getArredonda());
                pst.setString(8, pag.getCobraJuros());
                pst.setInt(9, pag.getParcelaInicial());
                pst.setDouble(10, pag.getTaxaJuros());
                pst.setString(11, pag.getDesconto());
                pst.executeUpdate();
                
                met.insertSucess("Forma de Pagamento");
                limparCampos();
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }
    }
}
