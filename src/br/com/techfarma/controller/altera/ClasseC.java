package br.com.techfarma.controller.altera;

import br.com.techfarma.model.cadastro.ClasseM;
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
import javafx.stage.Stage;

public class ClasseC implements Initializable {
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TextField txtDescricao;
    @FXML
    private ComboBox cbTipo;
    @FXML
    private JFXToggleButton tbEstqNeg, tbControlLote;
    @FXML
    private Button btClose;
    
    private String estqNegativo, controlLote, tipo;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //conn.Conexao();
        //carregaDados();
        
        //System.out.println(ClasseM.idClasseA);
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
    
    public void fecharJanela(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }
    
    private void carregaDados(){
//        try {
//            conn.executaSQL("SELECT descricao, tipo, estqNegativo, controlLote FROM dbo.Classe WHERE idClasse = 5");
//            
//            conn.rs.first();
//            
//            txtDescricao.setText(conn.rs.getString(1));
//        } catch (SQLException ex) {
//            System.out.println(ex);
//        }
    }
    
    public void atualizaItem(){
//        ClasseM cls = new ClasseM(txtDescricao.getText(), tipo, estqNegativo, controlLote);
//        
//        String SQL = "UPDATE dbo.Classe SET descricao = ?, tipo = ?, estqNegativo = ?, controlLote = ? WHERE idClasse = " +ClasseM.idClasseA+ "";
//        
//        try {
//                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
//                pst.setString(1, cls.getDescricao());
//                pst.setString(2, cls.getTipo());
//                pst.setString(3, cls.getEstqNeg());
//                pst.setString(4, cls.getControlLote());
//                pst.executeUpdate();
//
//                System.out.println("Atualizado");
//            } catch (SQLException ex) {
//                System.out.println(ex);
//            }
    }
}
