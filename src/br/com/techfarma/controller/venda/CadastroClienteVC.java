package br.com.techfarma.controller.venda;

import br.com.techfarma.interfaces.CadastroI;
import br.com.techfarma.model.cadastro.ClienteM;
import com.jfoenix.controls.JFXDatePicker;
import controller.BuscaCEPC;
import controller.ConexaoController;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CadastroClienteVC implements Initializable, CadastroI{
    ConexaoController conn = new ConexaoController();
    //Edição feita por souza
    
    @FXML
    private TextField txtCPF, txtNome, txtCNPJ, txtInsEst, txtFone, txtCelular, txtEmail, txtCEP, txtRua, txtNum, txtBairro, txtCompl, txtLimCred, txtCartFid;
    @FXML
    private ComboBox  cbUF, cbMunicipio, cbConv;
    @FXML
    private CheckBox chbMasc, chbFem;
    @FXML
    private JFXDatePicker dpDataNasc;
    @FXML
    private Button btCancelar;
    
    private String codUF, codMunicipio, sexo, status;
    private Double limCred;
    private Date dataNascimento, dataCadastro;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }

    @Override
    public void inicioPadrao() {
        conn.Conexao();
        moveCursor();
        preencheCBUF();
        preencheCBMunicipio();
        
        cbUF.getSelectionModel().select(18);
        cbMunicipio.getSelectionModel().select(67);
    }

    @Override
    public void fecharJanela() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void keyPressed(KeyEvent event){
        switch (event.getCode()) {
            case F4:
                limparCampos();
            break;
            case F10:
                inserirBanco();
            break;
            case ESCAPE:
                fecharJanela();
            default:
                break;
            }
    }
    
    public void moveCursor(){
        Control[] focusOrder = new Control[] {
            txtCPF, txtNome, txtCNPJ, txtInsEst, dpDataNasc, txtFone, txtCelular, txtEmail, txtCEP, txtRua, txtNum, txtBairro, txtCompl, cbUF, cbMunicipio, chbMasc, chbFem, txtCartFid, cbConv, 
            txtLimCred
        };
        
        for (int i = 0; i < focusOrder.length-1; i++) {
            Control nextControl = focusOrder[i + 1];
            focusOrder[i].addEventHandler(ActionEvent.ACTION, e -> nextControl.requestFocus());
        }
    }
    
    @FXML
    public void limparCampos(){
        txtCPF.clear(); txtNome.clear(); txtCNPJ.clear(); txtInsEst.clear(); txtFone.clear(); txtCelular.clear(); txtEmail.clear(); txtCEP.clear(); txtRua.clear(); txtNum.clear(); txtBairro.clear(); txtCompl.clear(); 
        cbUF.getSelectionModel().select(18); cbMunicipio.getSelectionModel().select(67); txtLimCred.clear(); txtCartFid.clear();
        codUF = null; codMunicipio = null; sexo = null; status = null; limCred = 0.00;
        chbMasc.setDisable(false); chbFem.setDisable(false);
        chbMasc.setSelected(false); chbFem.setSelected(false);
        dpDataNasc.setValue(null);
    }

    private void preencheCBUF(){
        ObservableList<String> listaUF = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT sigla FROM Codigos.UF");
            
            while(conn.rs.next()){
                listaUF.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        cbUF.setItems(listaUF);
    }
    
    private void preencheCBMunicipio(){
        ObservableList<String> listaMunicipio = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT nome FROM Codigos.Municipio");
            
            while(conn.rs.next()){
                listaMunicipio.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        cbMunicipio.setItems(listaMunicipio);
    }
    
    public void verificaGenero(){
        if (chbMasc.isSelected()){
            sexo = "M";
            chbFem.setDisable(true);
        } else if (chbFem.isSelected()){
            sexo = "F";
            chbMasc.setDisable(true);
        } else {
            sexo = null;
            chbMasc.setDisable(false);
            chbFem.setDisable(false);
        }
    }
    
    private void converteData() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        if (dpDataNasc.getValue() == null) {
            dataNascimento = null;
        } else {
            try {
                java.util.Date date = sdf.parse(dpDataNasc.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                dataNascimento = new Date(date.getTime());
                System.out.println(dataNascimento);
            } catch (ParseException ex) {
                System.out.println("Erro durante a conversão de datas: " + ex);
            }
        }
    }
    
    private void filtraCodUF() {
        try {
            conn.executaSQL("SELECT idUF FROM Codigos.UF WHERE sigla = '" + cbUF.getSelectionModel().getSelectedItem().toString() + "'");
            conn.rs.last();
            codUF = conn.rs.getString(1);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void filtraCodMunicipio(){
        try {
            conn.executaSQL("SELECT idMunicipio FROM Codigos.Municipio WHERE nome = '" + cbMunicipio.getSelectionModel().getSelectedItem().toString() + "'");
            conn.rs.last();
            codMunicipio = conn.rs.getString(1);
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    public void buscaCEP(){
        BuscaCEPC.buscaCEP(txtCEP.getText());
        
        txtRua.setText(controller.BuscaCEPC.logradouro);
        txtBairro.setText(controller.BuscaCEPC.bairro);
        cbMunicipio.getSelectionModel().select(controller.BuscaCEPC.cidade);
        cbUF.getSelectionModel().select(controller.BuscaCEPC.uf);
        
        txtNum.clear();
        txtNum.requestFocus();
    }
    
    private void verificaNulos(){
        if(txtLimCred.getText().isEmpty()){
            limCred = 0.00;
        } else {
            limCred = Double.parseDouble(txtLimCred.getText().replaceAll(",", "."));
        }
    }
    
    @Override
    public void inserirBanco() {
        converteData();
        filtraCodUF();
        filtraCodMunicipio();
        verificaGenero();
        verificaNulos();
        
        status = "A";
        dataCadastro = new Date(new java.util.Date().getTime());
        System.out.println(dataCadastro.toString());
        
        ClienteM cliente = new ClienteM(1, txtCPF.getText(), txtNome.getText(), txtCNPJ.getText(), txtInsEst.getText(), dataNascimento, txtFone.getText(), txtCelular.getText(), txtEmail.getText(), txtCEP.getText(), txtRua.getText(), txtNum.getText(), 
                txtBairro.getText(), txtCompl.getText(), cbUF.getSelectionModel().getSelectedItem().toString(), codUF, cbMunicipio.getSelectionModel().getSelectedItem().toString(), codMunicipio, sexo, status, 0, limCred, txtCartFid.getText(), dataCadastro);
        
        String SQL = "INSERT INTO dbo.Cliente (idFilial, CPF, nome, CNPJ, insEst, dataNasc, telefone, celular, email, CEP, rua, numero, bairro, complemento, UF, codUF, municipio, codMun, sexo, status, idConvenio, limCred, cartaoFid, dataCad) "
                + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
            pst.setInt(1, cliente.getIdFilial());
            pst.setString(2, cliente.getCPF());
            pst.setString(3, cliente.getNome());
            pst.setString(4, cliente.getCNPJ());
            pst.setString(5, cliente.getInsEst());
            pst.setDate(6, cliente.getDataNasc());
            pst.setString(7, cliente.getTelefone());
            pst.setString(8, cliente.getCelular());
            pst.setString(9, cliente.getEmail());
            pst.setString(10, cliente.getCEP());
            pst.setString(11, cliente.getRua());
            pst.setString(12, cliente.getNumero());
            pst.setString(13, cliente.getBairro());
            pst.setString(14, cliente.getCompl());
            pst.setString(15, cliente.getUF());
            pst.setString(16, cliente.getCodUF());
            pst.setString(17, cliente.getMunicipio());
            pst.setString(18, cliente.getCodMun());
            pst.setString(19, cliente.getSexo());
            pst.setString(20, cliente.getStatus());
            pst.setInt(21, cliente.getIdConvenio());
            pst.setDouble(22, cliente.getLimCred());
            pst.setString(23, cliente.getCartaoFid());
            pst.setDate(24, cliente.getDataCad());
            pst.executeUpdate();
            limparCampos();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
}
