/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import model.DiretorioDB;

/**
 *
 * @author Gabriel
 */
public class DiretorioDBController implements Initializable{
    ConexaoController conn = new ConexaoController();

    @FXML
    private TextField txtURL;
    
    @FXML
    private TextField txtUser;
    
    @FXML
    private TextField txtSenha;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
    }
    
    @FXML
    public void inserir(){
        inserirDiretorio();
    }

    public boolean inserirDiretorio(){
        DiretorioDB ddb = new DiretorioDB(); 
        
        ddb.setURL(txtURL.getText());
        ddb.setUsuario(txtUser.getText());
        ddb.setSenha(txtSenha.getText());
        
        String SQL = "INSERT INTO bancoDeDados (URL, Usuario, Senha) VALUES (?,?,?)";
        
        try {
            PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
            
            pst.setString(1, ddb.getURL());
            pst.setString(2, ddb.getUsuario());
            pst.setString(3, ddb.getSenha());
            
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null, "Erro ao incluir novo caminho do Banco de Dados: " +ex);
             return false;
        }
    }
}
