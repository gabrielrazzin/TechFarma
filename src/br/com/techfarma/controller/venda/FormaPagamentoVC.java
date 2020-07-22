package br.com.techfarma.controller.venda;

import br.com.techfarma.model.consulta.VendaM;
import com.acbr.nfe.ACBrNFe;
import controller.ConexaoController;
import controller.MetodosController;
import java.net.URL;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;

public class FormaPagamentoVC implements Initializable{
    ConexaoController conn = new ConexaoController();
    private ACBrNFe acbrNFe;
    
    private Stage stageCartao, stageSelecionaC;
    @FXML
    private Label lbTotal, lbTroco;
    @FXML
    private TextField txtDinheiro, txtCartao, txtConvenio;
    @FXML
    private Button btClose;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DecimalFormat formato = new DecimalFormat("##.##");
        lbTotal.setText(String.format("%.2f", VendaM.valorTotalFinal));

        filtroTextField();
        moveCursor(); 
        
        try {
            acbrNFe = new ACBrNFe();
        } catch (Exception e) {
        }
    }
    
    public void fecharJanela(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }
    
    private void filtroTextField(){
        txtDinheiro.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.,]\\d{0,4})?")) {
                    txtDinheiro.setText(oldValue);
                }
            }
        });
        
        txtCartao.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.,]\\d{0,4})?")) {
                    txtCartao.setText(oldValue);
                }
            }
        });
        
        txtConvenio.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                    String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.,]\\d{0,4})?")) {
                    txtConvenio.setText(oldValue);
                }
            }
        });
    }
    
    public void keyPressed(KeyEvent event){
        switch(event.getCode()){
            case F4:
                limparCampos();
                break;
            case F10:
                if (verificaCampos()) {
                    if (lerCodigoBarras()) {
                        VendaM.trocoFinal = Double.parseDouble(lbTroco.getText().replaceAll(",", "."));
                        formaPagamento();
                        VendaM.finalizaVendaS = true;
                        btClose.fire();
                    }
                }
                break;
            case ESCAPE:
                btClose.fire();
                break;
            default:
                break;
        }
    }
    
    private void limparCampos(){
        txtDinheiro.clear();
        txtCartao.clear();
        txtConvenio.clear();
        lbTroco.setText("0,00");
        txtDinheiro.requestFocus();
    }
    
    public void moveCursor(){
        Control[] focusOrder = new Control[] {
            txtDinheiro, txtCartao, txtConvenio
        };
        
        for (int i = 0; i < focusOrder.length-1; i++) {
            Control nextControl = focusOrder[i + 1];
            focusOrder[i].addEventHandler(ActionEvent.ACTION, e -> nextControl.requestFocus());
        }
    }
    
    public void setTrocoDinheiro() {
        if (txtDinheiro.getText().isEmpty()) {
            if (txtCartao.getText().isEmpty()) {
                txtDinheiro.setText(String.format("%.2f", VendaM.valorTotalFinal));
                txtCartao.requestFocus();
                calculaTroco();
                VendaM.idPagamento = "01";
//                System.out.println("CONDICAO 1 - DINHEIRO");
            } else {
                txtDinheiro.setText(String.format("%.2f", VendaM.valorTotalFinal - Double.parseDouble(txtCartao.getText().replaceAll(",", "."))));
                txtCartao.setText(String.format("%.2f", Double.parseDouble(txtCartao.getText().replaceAll(",", "."))));
                VendaM.idPagamento = "01";
                calculaTroco();
//                System.out.println("CONDICAO 2 - DINHEIRO");
            }
        } else {
            if (Double.parseDouble(txtDinheiro.getText().replaceAll(",", ".")) <= VendaM.valorTotalFinal) {
                txtDinheiro.setText(String.format("%.2f", Double.parseDouble(txtDinheiro.getText().replaceAll(",", "."))));
                txtCartao.requestFocus();
                VendaM.idPagamento = "01";
//                System.out.println("CONDICAO 3 - DINHEIRO");
            } else if (Double.parseDouble(txtDinheiro.getText().replaceAll(",", ".")) > VendaM.valorTotalFinal && txtCartao.getText().isEmpty()) {
                txtDinheiro.setText(String.format("%.2f", Double.parseDouble(txtDinheiro.getText().replaceAll(",", "."))));
                calculaTroco();
                VendaM.idPagamento = "01";
//                System.out.println("CONDICAO 4  - DINHEIRO");
            } else if (Double.parseDouble(txtDinheiro.getText().replaceAll(",", ".")) + Double.parseDouble(txtCartao.getText().replaceAll(",", ".")) > VendaM.valorTotalFinal) {
                txtDinheiro.setText(String.format("%.2f", Double.parseDouble(txtDinheiro.getText().replaceAll(",", "."))));
                txtCartao.setText(String.format("%.2f", Double.parseDouble(txtCartao.getText().replaceAll(",", "."))));
                calculaTroco();
                VendaM.idPagamento = "01";
//                System.out.println("CONDICAO 5 - DINHEIRO");
            }
        }
    }
    
    public void setTrocoCartao() {
        if (txtCartao.getText().isEmpty()) {
            if (txtDinheiro.getText().isEmpty()) {
                txtCartao.setText(String.format("%.2f", VendaM.valorTotalFinal));
                txtConvenio.requestFocus();
                calculaTroco();
                System.out.println("CONDICAO 1 - CARTAO");
            } else {
                txtCartao.setText(String.format("%.2f", VendaM.valorTotalFinal - Double.parseDouble(txtDinheiro.getText().replaceAll(",", "."))));
                calculaTroco();
                System.out.println("CONDICAO 2 - CARTAO");
            }
        } else {
            if (Double.parseDouble(txtCartao.getText().replaceAll(",", ".")) <= VendaM.valorTotalFinal) {
                txtCartao.setText(String.format("%.2f", Double.parseDouble(txtCartao.getText().replaceAll(",", "."))));
                txtDinheiro.requestFocus();
                System.out.println("CONDICAO 3 - CARTAO");
            } else if (Double.parseDouble(txtCartao.getText().replaceAll(",", ".")) > VendaM.valorTotalFinal && txtDinheiro.getText().isEmpty()) {
                txtCartao.setText(String.format("%.2f", Double.parseDouble(txtCartao.getText().replaceAll(",", "."))));
                calculaTroco();
                System.out.println("CONDICAO 4 - CARTAO");
            }
        }
    }
    
    private void calculaTroco() {
        String valorTroco;
        lbTroco.setText("0,00");

        if (!txtDinheiro.getText().isEmpty() && !txtCartao.getText().isEmpty()) {
            valorTroco = String.format("%.2f", (Double.parseDouble(txtDinheiro.getText().replaceAll(",", ".")) + Double.parseDouble(txtCartao.getText().replaceAll(",", "."))) - VendaM.valorTotalFinal);
            lbTroco.setText(valorTroco);
        } else if (!txtDinheiro.getText().isEmpty()) {
            if (txtCartao.getText().isEmpty() && txtConvenio.getText().isEmpty()) {
                valorTroco = String.format("%.2f", Double.parseDouble(txtDinheiro.getText().replaceAll(",", ".")) - VendaM.valorTotalFinal);
                lbTroco.setText(valorTroco);
            }
        } else if (!txtCartao.getText().isEmpty()) {
            valorTroco = String.format("%.2f", Double.parseDouble(txtCartao.getText().replaceAll(",", ".")) - VendaM.valorTotalFinal);
            lbTroco.setText(valorTroco);
        }
    }
    
    private boolean verificaCampos(){
        if(txtDinheiro.getText().isEmpty() && txtCartao.getText().isEmpty() && txtConvenio.getText().isEmpty()){
            Stage stage = (Stage) btClose.getScene().getWindow();
//            Platform.setImplicitExit(false);
//            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//                @Override
//                public void handle(WindowEvent event) {
//                    event.consume();
//                }
//            });
            
            JOptionPane.showMessageDialog(null, "Um dos campos deve ser preenhcidos");
           // stage.setAlwaysOnTop(true);
            txtDinheiro.requestFocus();
            
            return false;
        } else {
            return true;
        }
    }
    
    private String verificaCartao() {
        MetodosController met = new MetodosController();
        
        if(stageSelecionaC == null){
            stageSelecionaC = met.abrirTelaEspera("/br/com/techfarma/view/venda/Venda - Seleciona Cartao.fxml", stageSelecionaC);
            stageSelecionaC.setOnHiding(we -> stageSelecionaC = null);
            stageSelecionaC.showAndWait();
        } else if (stageSelecionaC.isShowing()){
            stageSelecionaC.toFront();
        }
        
        
        
        if (VendaM.tipoCartao.equals("C")) {
            VendaM.idPagamento = "03";
        } else if (VendaM.tipoCartao.equals("D")) {
            VendaM.idPagamento = "04";
        }
        
        return VendaM.idPagamento;
    }
    
    private void formaPagamento() {
        double dinheiro;
        if (txtDinheiro.getText().isEmpty()) {
            dinheiro = 0.00;
        } else {
            dinheiro = Double.parseDouble(txtDinheiro.getText().replaceAll(",", "."));
        }

        double cartao;
        if (txtCartao.getText().isEmpty()) {
            cartao = 0.00;
        } else {
            cartao = Double.parseDouble(txtCartao.getText().replaceAll(",", "."));
        }

        double convenio;
        if (txtConvenio.getText().isEmpty()) {
            convenio = 0.00;
        } else {
            convenio = Double.parseDouble(txtConvenio.getText().replaceAll(",", "."));
        }

        if (dinheiro > 0.00 && cartao == 0.00 && convenio == 0.00) {
            VendaM.valorTotalFinal = Double.parseDouble(lbTotal.getText().replaceAll(",", "."));
            VendaM.idPagamento = "01";
        } else if (dinheiro == 0.00 && cartao > 0.00 && convenio == 0.00) {
            VendaM.valorTotalFinal = Double.parseDouble(lbTotal.getText().replaceAll(",", "."));
            verificaCartao();
        } else if (dinheiro == 0.00 && cartao == 0.00 && convenio > 0.00) {
            System.out.println("APENAS CONVENIO");
        } else if (dinheiro > 0.00 && cartao > 0.00 && convenio == 0.00) {
            verificaCartao();
        }
    }
    
    private boolean lerCodigoBarras() {
        String barCode = JOptionPane.showInputDialog("Informe o código de barras do cartão de venda:");

        try {
            conn.executaSQL("exec verificaBarCode @barCode = '"+barCode+"'");
            conn.rs.next();
            
            if(conn.rs.getString(1).equals("1")){
                VendaM.barCodeVenda = barCode;
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Código inválido ou já está sendo utilizado!");
                
                Stage stage = (Stage) btClose.getScene().getWindow();
//                stage.setAlwaysOnTop(true);
                stage.toFront();
                stage.requestFocus();
                txtDinheiro.requestFocus();
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }        
        return false;
    }
}
