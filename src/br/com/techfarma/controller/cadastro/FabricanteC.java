package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import controller.ConexaoController;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import br.com.techfarma.model.cadastro.FabricanteM;
import controller.MaskedTextField;
import controller.MetodosController;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class FabricanteC implements Initializable, CadastroI{
    ConexaoController conn = new ConexaoController();
    MaskedTextField mask = new MaskedTextField();
    
    @FXML
    private TextField txtDescricao, txtRazao, txtEnd, txtCidade, txtEmail, txtDesc, txtAnot;
    @FXML
    private MaskedTextField txtCNPJ, txtTel, txtCel;
    @FXML  
    private Button btCancelar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       inicioPadrao();
    }
   
    @Override
    public void inicioPadrao() {
        conn.Conexao();
        
        txtCNPJ.setMask("##.###.###/####-##");
        txtTel.setMask("(##)####-####");
        txtCel.setMask("(##)#####-####");
    }

    @Override
    public void fecharJanela(){
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
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
    
    public void limparCampos(){
        txtDescricao.clear();
        txtRazao.clear();
        txtCNPJ.clear();
        txtEnd.clear();
        txtCidade.clear();
        txtTel.clear();
        txtCel.clear();
        txtEmail.clear();
        txtDesc.clear();
        txtAnot.clear();
        txtDescricao.requestFocus();
    }
    
    @Override
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtDescricao);

        if (met.verificaCampos(1, aTxt)) {
            String desconto;
            
            if (txtDesc.getText().isEmpty()) {
                desconto = "0.00";
            } else {
                desconto = txtDesc.getText().replace(",", ".");
            }
            
            FabricanteM fab = new FabricanteM(txtDescricao.getText(), txtRazao.getText(), txtCNPJ.getText().replaceAll("[_./-]", ""), txtEnd.getText(), txtCidade.getText(), txtTel.getText().replaceAll("[_]", ""), txtCel.getText().replaceAll("[_]", ""), 
                    txtEmail.getText(), (Double.parseDouble(desconto)/100), txtAnot.getText());

            String SQL = "INSERT INTO dbo.Fabricante (descricao, razaoSocial, CNPJ, endereco, cidade, telefone, celular, email, desconto, anotacoes) VALUES (?,?,?,?,?,?,?,?,?,?)";

            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setString(1, fab.getdescricao());
                pst.setString(2, fab.getRazaoSocial());
                pst.setString(3, fab.getCNPJ());
                pst.setString(4, fab.getEndereco());
                pst.setString(5, fab.getCidade());
                pst.setString(6, fab.getTelefone());
                pst.setString(7, fab.getCelular());
                pst.setString(8, fab.getEmail());
                pst.setDouble(9, fab.getDesconto());
                pst.setString(10, fab.getAnotacoes());

                pst.executeUpdate();
                met.insertSucess("Fabricante");
                limparCampos();
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }
    }
}
