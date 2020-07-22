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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import br.com.techfarma.model.cadastro.UsuarioM;
import controller.MetodosController;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class UsuarioC implements Initializable, CadastroI {
    ConexaoController conn = new ConexaoController();

    @FXML
    private TextField txtNome, txtLogin, txtSenha, txtConfSenha, txtMaxDesc;
    @FXML
    private ComboBox cbFilial, cbPermissao;
    @FXML
    private JFXToggleButton tbVendedor, tbOpCaixa;
    @FXML
    private Button btCancelar;
    
    private String vendedor, opCaixa;
    private int valorPermissao;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }
    
    @Override
    public void inicioPadrao() {
        conn.Conexao();
        preencheCBFilial();
    }

    @Override
    public void fecharJanela() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void keyPressed(KeyEvent event) {
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
    
    private void preencheCBFilial(){
        ObservableList<String> listaFilial = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT nome FROM dbo.Filial");
            
            while(conn.rs.next()){
                listaFilial.add(conn.rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        cbFilial.setItems(listaFilial);
    }

    public void verificaTB(){
        if(tbVendedor.isSelected()){
            vendedor = "S";
        } else {
            vendedor = "N";
        }
        
        if(tbOpCaixa.isSelected()){
            opCaixa = "S";
        } else {
            opCaixa = "N";
        }
    }

    @Override
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtNome);
        aTxt.add(txtLogin);
        aTxt.add(txtSenha);
        aTxt.add(txtConfSenha);

        if (met.verificaCampos(4, aTxt)) {
            UsuarioM user = new UsuarioM(cbFilial.getSelectionModel().getSelectedItem().toString(), txtNome.getText(), txtLogin.getText(), txtSenha.getText(), txtConfSenha.getText(), valorPermissao, vendedor, opCaixa, (Double.parseDouble(txtMaxDesc.getText()) / 100));
            String SQL = "INSERT INTO dbo.Usuario (filial, nome, login, senha, permissao, vendedor, operaCaixa, maxDesc) VALUES (?,?,?,?,?,?,?,?)";

            if (user.getSenha().equals(user.getConfSenha())) {
                try {
                    PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                    pst.setString(1, "Farmacia Valporto LTDA");
                    pst.setString(2, user.getNome());
                    pst.setString(3, user.getLogin());
                    pst.setString(4, user.getSenha());
                    pst.setInt(5, 1);
                    pst.setString(6, user.getVendedor());
                    pst.setString(7, user.getOperaCaixa());
                    pst.setDouble(8, user.getMaxDesc());
                    pst.executeUpdate();
                    
                    met.insertSucess("Usuário");
                } catch (SQLException ex) {
                    met.insertException(ex);
                }
            } else {
                met.insertError("Validador de Senhas", "As senhas não conferem", "");
            }
        }
    }
}
