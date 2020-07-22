package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.swing.JOptionPane;

public class LoginController implements Initializable {
    
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TextField txtUsuario;
    @FXML
    private PasswordField pfSenha;
    @FXML
    private Button btClose, btVerde, btVermelho;
    @FXML
    private AnchorPane parent, anchorBDC, anchorBDD;
    @FXML
    private Label lbData;
    
    double x = 0, y = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        conn.Conexao();
        makeDragable();
        verificaConexao();
        dataSistema();
    }
    
    private void makeDragable() {
        parent.setOnMousePressed(((event) -> {
            x = event.getSceneX();
            y = event.getSceneY();
        }));

        parent.setOnMouseDragged(((event) -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setX(event.getScreenX() - x);
            stage.setY(event.getScreenY() - y);
            stage.setOpacity(0.8f);
        }));

        parent.setOnDragDone(((event) -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setOpacity(1.0f);
        }));

        parent.setOnMouseReleased(((event) -> {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setOpacity(1.0f);

        }));
    }
    
    public void verificaLogin() {
        String SQL = "SELECT login, senha FROM dbo.Usuario WHERE login = ? and senha = ?";
        
        try {
            PreparedStatement pst = conn.getConexao().prepareStatement(SQL);

            pst.setString(1, txtUsuario.getText());
            pst.setString(2, pfSenha.getText());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                try {    
                    Parent root = FXMLLoader.load(getClass().getResource("/br/com/techfarma/view/view/Menu.fxml"));
                    Scene scene = new Scene(root);
                    Stage stage = new Stage();

                    stage.setScene(scene);
                    stage.setResizable(false);       
                    scene.setFill(Color.TRANSPARENT);
                    stage.initStyle(StageStyle.TRANSPARENT);
                    //stage.setMaximized(true);
                    stage.show();
                    fecharJanela();
                } catch (IOException e) {
                    System.out.println(e);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario ou senha incorreto");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex);
        }
    }
    
    public void fecharJanela(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }
    
    private void verificaConexao(){
        if(conn.verificaConexao()){
            anchorBDC.setVisible(true);
            btVerde.setVisible(true);
            anchorBDD.setVisible(false);
            btVermelho.setVisible(false);
        } else {
            anchorBDC.setVisible(false);
            btVerde.setVisible(false);
            anchorBDD.setVisible(true);
            btVermelho.setVisible(true);
        }
    }
    
    private void dataSistema(){
        Date dataSistema = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        lbData.setText(formato.format(dataSistema));
    }
}

