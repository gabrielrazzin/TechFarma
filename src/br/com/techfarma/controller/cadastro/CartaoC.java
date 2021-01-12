package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import br.com.techfarma.model.cadastro.CartoesM;
import com.jfoenix.controls.JFXToggleButton;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CartaoC implements Initializable, CadastroI {
    ConexaoController conn = new ConexaoController();
    
    @FXML
    public TextField txtDescricao, txtTaxADM, txtTaxADMP, txtPrazoPag;
    @FXML
    public JFXToggleButton tbCredito, tbDebito, tbTEF;
    @FXML
    public ComboBox<String> cbBandeira;
    @FXML
    public Button btCancelar;
    @FXML
    public ImageView ivBandeiras;

    private String tipoCartao, usaTEF, bandeira;

  
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }
    
    @Override
    public void inicioPadrao() {
        conn.Conexao();
        preencheCB();
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
    
    private void verificaTB() {
        if (tbCredito.isSelected()) {
            tipoCartao = "C";
        }
        
        if (tbDebito.isSelected()) {
            tipoCartao = "D";
        }
        
        if(tbTEF.isSelected()){
            usaTEF = "S";
        } else {
            usaTEF = "N";
        }
    }
    
    public void preencheCB() {
        cbBandeira.getItems().addAll(
                "Visa",
                "Visa Electron",
                "Credicard",
                "Redeshop",
                "Elo Crédito",
                "Elo Débito",
                "American Express"
        );
     }
     
     public void insereBandeira() {
        switch (cbBandeira.getSelectionModel().getSelectedItem()) {
            case "Visa":
                MetodosController visa = new MetodosController();
                visa.insertBandeira("src/br/com/techfarma/view/Images/Bandeiras/Visa.png");
                ivBandeiras.setImage(visa.getBandeira());
                bandeira = "Visa";
                break;
            case "Visa Electron":
                MetodosController visaElectron = new MetodosController();
                visaElectron.insertBandeira("src/br/com/techfarma/view/Images/Bandeiras/Visa Electron.png");
                ivBandeiras.setImage(visaElectron.getBandeira());
                bandeira = "Visa Electron";
                break;
            case "Credicard":
            case "Redeshop":
                MetodosController masterCard = new MetodosController();
                masterCard.insertBandeira("src/br/com/techfarma/view/Images/Bandeiras/Mastercard.png");
                ivBandeiras.setImage(masterCard.getBandeira());
                bandeira = "Mastercard";
                break;
            case "Elo Crédito":
            case "Elo Débito":
                MetodosController elo = new MetodosController();
                elo.insertBandeira("src/br/com/techfarma/view/Images/Bandeiras/Elo.png");
                ivBandeiras.setImage(elo.getBandeira());
                bandeira = "Elo";
                break;
            case "American Express":
                MetodosController americanExpress = new MetodosController();
                americanExpress.insertBandeira("src/br/com/techfarma/view/Images/Bandeiras/American Express.png");
                ivBandeiras.setImage(americanExpress.getBandeira());
                bandeira = "American Express";
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
        
        if(met.verificaCampos(1, aTxt)){
            verificaTB();
            
            CartoesM carM = new CartoesM(txtDescricao.getText(), tipoCartao, (Double.parseDouble(txtTaxADM.getText())/100), (Double.parseDouble(txtTaxADMP.getText())/100), Integer.parseInt(txtPrazoPag.getText()), usaTEF, bandeira);
            String SQL = "INSERT INTO dbo.Cartoes (descricao, tipoCartao, taxADM, taxADMParc, prazoPag, usaTEF, bandeira) VALUES (?,?,?,?,?,?,?)";
            
            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setString(1, carM.getDescricao());
                pst.setString(2, carM.getTipoCartao());
                pst.setDouble(3, carM.getTaxADM());
                pst.setDouble(4, carM.getTaxADMParc());
                pst.setInt(5, carM.getPrazoPag());
                pst.setString(6, carM.getUsaTEF());
                pst.setString(7, carM.getBandeira());
                pst.executeUpdate();
                
                met.insertSucess("Cartão");
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }
    }
}