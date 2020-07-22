package br.com.techfarma.controller.nfe;

import com.acbr.nfe.ACBrNFe;
import controller.ConexaoController;
import controller.MetodosController;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class CancelamentoC implements Initializable {
    ConexaoController conn = new ConexaoController();

    private ACBrNFe acbrNFe;
    
    private String CNPJ, chNFCe;
    private int lote;
    private boolean verificaCancelamento = false;
    
    @FXML
    private TextField txtIdVenda, txtJust;
    @FXML
    private Button btClose;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Font.loadFont(getClass().getResourceAsStream("/resources/Kayak Sans Regular.otf"), 15);
        try {
            acbrNFe = new ACBrNFe();
        } catch (Exception e) {
        }
        
        conn.getConexao();
        
        txtJust.setDisable(true);
    }
    
    private void fecharJanela(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }
    
    private void limparTela(){
        txtIdVenda.setText("");
        txtJust.setText("");
        txtIdVenda.setDisable(false);
        txtJust.setDisable(true);
        txtIdVenda.requestFocus();
    }
    
    @FXML
    void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                if (txtIdVenda.isFocused()) {
                    verificaVenda();
                } else if (txtJust.isFocused()) {
                    try {
                        String cancelamento = cancelarNFCe();
                        if (cancelamento.contains("TimeOut de Requisição")) {
                            MetodosController met = new MetodosController();
                            met.infoDialog("Atençao", "Servidor do SEFAZ inativo", "Nao foi possivel realizar o cancelamento, servidor do SEFAZ fora do ar, aguarde alguns minutos e tente novamente.");
                            limparTela();
                        } else if (cancelamento.contains("CStat=573")) {
                            MetodosController met = new MetodosController();
                            met.insertError("Cancelamento", "Erro ao efetuar o cancelamento", "Cancelamento já efetuado");
                            limparTela();
                        } else if (cancelamento.contains("CStat=501")) {
                            MetodosController met = new MetodosController();
                            met.insertError("Cancelamento", "Erro ao efetuar o cancelamento", "Prazo de Cancelamento Superior ao Previsto na Legislacao");
                            limparTela();
                        } else {
                            MetodosController met = new MetodosController();
                            met.infoDialog("Cancelamento", "Cancelamento realizado com sucesso", "");
                            verificaCancelamento = true;
                            atualizaDatabase();
                            fecharJanela();
                        }
                        break;
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            default:
                break;
        }
    }
    
    private void verificaVenda(){
        if(obterDadosNFCe()){
            txtIdVenda.setDisable(true);
            txtJust.setDisable(false);
            txtJust.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "Venda não localizada, verifique o número digitado.");
        }
    }
    
    private boolean obterDadosNFCe(){
        conn.executaSQL("SELECT CNPJ, idLote, chNFe FROM Vendas.NFCe WHERE idVenda = "+ txtIdVenda.getText() + "");
        
        try {
            conn.rs.first();
            
            CNPJ = conn.rs.getString(1);
            lote = conn.rs.getInt(2);
            chNFCe = conn.rs.getString(3);
            
            return true;
        } catch (SQLException e) {
            System.out.println(e);
            return false;
        }
    }
    
    private String cancelarNFCe() {
        obterDadosNFCe();
        String ret = null;
        
        try {
            ret = acbrNFe.cancelar(chNFCe, txtJust.getText(), CNPJ, lote);
        } catch (Exception e) {
            Logger.getLogger(CancelamentoC.class.getName()).log(Level.SEVERE, null, e);
        }
        
        return ret;
    }
    
    private void atualizaDatabase(){
        if (verificaCancelamento){
            conn.executaSQL("UPDATE dbo.NFCe SET status = 'C' WHERE idVenda = " + txtIdVenda.getText());
        }
    }
}
