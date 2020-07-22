package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import br.com.techfarma.model.cadastro.FuncionarioM;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXToggleButton;
import controller.ConexaoController;
import controller.MaskedTextField;
import controller.MetodosController;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.controlsfx.control.CheckComboBox;


public class FuncionarioC implements Initializable, CadastroI {
    ConexaoController conn = new ConexaoController();
    @FXML
    public TextField txtCod, txtNome, txtFuncao, txtSalario, txtEnd, txtBairro, txtCidade, txtEmail, txtNat, txtConju, txtNumDep, txtNomePai, txtNomeMae, txtUsuarioFP, txtSenhaFP;
    @FXML
    private MaskedTextField txtIdentidade, txtCPF, txtDataNasc, txtTel, txtCEP, txtAdm;
    @FXML
    public ComboBox<String> cbFilial, cbUF, cbCargo;
    @FXML
    public JFXToggleButton tbStatus;
    @FXML
    public JFXCheckBox chbMasc, chbFem, chbCasado, chbSolteiro, chbDivorciado, chbViuvo, chbOutros;
    @FXML
    public CheckComboBox<String> cbClasseC;
    @FXML
    public Button btCancelar;
    
    private String sexo, estadoCivil, status, UF;
    private Date dataNascimento, dataAdm;
    private Double salario;
    private Integer numDep;
    
    
       
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }
    
    @FXML
    void keyPressed(KeyEvent event){
        switch(event.getCode()){
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
    
    @Override
    public void fecharJanela() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
    
    private void preencheCBFilial(){
        ObservableList<String> listaFilial = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT nome FROM dbo.Filial");
            
            while(conn.rs.next()){
                listaFilial.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        cbFilial.setItems(listaFilial);
    }
    
    private void preencheCBClasse(){
        ObservableList<String> listaClasse = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT descricao FROM dbo.Classe");
            
            while(conn.rs.next()){
                listaClasse.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        cbClasseC.getItems().addAll(listaClasse);
    }
    
    private void preencheCBUF(){
        ObservableList<String> listaUF = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT sigla, nome FROM Codigos.UF");
            
            while(conn.rs.next()){
                listaUF.add(conn.rs.getString(1) + " - " + conn.rs.getString(2));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        cbUF.setItems(listaUF);
    }
    
    private void preencheCBCargo(){
        ObservableList<String> listaCargo = FXCollections.observableArrayList();

        listaCargo.add("Comprador");
        listaCargo.add("Entregador");
        listaCargo.add("Gerente");
        listaCargo.add("Motociclista");
        listaCargo.add("Operador de Caixa");
        listaCargo.add("Perfumista");
        listaCargo.add("Vendedor");
        
        cbCargo.setItems(listaCargo);
    }
  
   public void verificaGenero(){
        if (chbMasc.isSelected()){
            sexo = "M";
            chbFem.setDisable(true);
        } else if (chbFem.isSelected()){
            sexo = "F";
            chbMasc.setDisable(true);
        } else {
            chbMasc.setDisable(false);
            chbFem.setDisable(false);
            sexo = null;
        }
    }
   
   public void verificaStatus(){
        if (tbStatus.isSelected()){
            status = "A";
        } else {
            status = "I";
        }
   }
   
    public void verificaEstadoCivil() {
        if (chbCasado.isSelected()) {
            estadoCivil = "C";
            chbSolteiro.setDisable(true);
            chbDivorciado.setDisable(true);
            chbViuvo.setDisable(true);
            chbOutros.setDisable(true);
        } else if (chbSolteiro.isSelected()) {
            estadoCivil = "S";
            chbCasado.setDisable(true);
            chbDivorciado.setDisable(true);
            chbViuvo.setDisable(true);
            chbOutros.setDisable(true);
        } else if (chbDivorciado.isSelected()) {
            estadoCivil = "D";
            chbCasado.setDisable(true);
            chbSolteiro.setDisable(true);
            chbViuvo.setDisable(true);
            chbOutros.setDisable(true);
        } else if (chbViuvo.isSelected()) {
            estadoCivil = "V";
            chbCasado.setDisable(true);
            chbSolteiro.setDisable(true);
            chbDivorciado.setDisable(true);
            chbOutros.setDisable(true);
        } else if (chbOutros.isSelected()) {
            estadoCivil = "O";
            chbCasado.setDisable(true);
            chbSolteiro.setDisable(true);
            chbDivorciado.setDisable(true);
            chbViuvo.setDisable(true);
        } else {
            estadoCivil = null;
            chbCasado.setDisable(false);
            chbSolteiro.setDisable(false);
            chbDivorciado.setDisable(false);
            chbViuvo.setDisable(false);
            chbOutros.setDisable(false);
        }
    }
    
    private void converteData() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        if(!txtDataNasc.getText().isEmpty()){
            try {
            java.util.Date date = sdf.parse(txtDataNasc.getText());
            dataNascimento = new Date(date.getTime());
            System.out.println(dataNascimento);
            } catch (ParseException ex) {
            System.out.println("Erro durante a conversão de datas: " + ex);
            }
        } else {
            dataNascimento = null;
        }
        
        if(!txtAdm.getText().isEmpty()){
            try {
            java.util.Date date = sdf.parse(txtAdm.getText());
            dataAdm = new Date(date.getTime());
            System.out.println(dataAdm);
            } catch (ParseException ex) {
            System.out.println("Erro durante a conversão de datas: " + ex);
            }
        } else {
            dataAdm = null;
        }
    }
    
    public void filtraUF(){
        String descricao = cbUF.getSelectionModel().getSelectedItem();
        String[] splitUF = descricao.split("- ");
        
        UF = splitUF[0];
    }
    
    @Override
    public void inicioPadrao() {
        conn.Conexao();
        tbStatus.setSelected(true);
        preencheCBUF();
        cbUF.getSelectionModel().select(18);
        preencheCBFilial();
        preencheCBClasse();
        preencheCBCargo();
        
        txtIdentidade.setMask("##.###.###-#");
        txtCPF.setMask("###.###.###-##");
        txtDataNasc.setMask("##/##/####");
        txtCEP.setMask("#####-###");
        txtTel.setMask("(##)####-####");
        txtAdm.setMask("##/##/####");
    }

    private void verificaTF(){
        if(txtSalario.getText().isEmpty()){
            salario = 0.00;
        } else {
            salario = Double.parseDouble(txtSalario.getText());
        }
        
        if(txtNumDep.getText().isEmpty()){
            numDep = 0;
        } else {
            numDep = Integer.parseInt(txtNumDep.getText());
        }
    }
    
    @Override
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtNome);

        if (met.verificaCampos(1, aTxt)) {
            converteData();
            verificaStatus();
            verificaTF();
            filtraUF();
            
            FuncionarioM func = new FuncionarioM(cbFilial.getSelectionModel().getSelectedItem(), txtNome.getText(), txtIdentidade.getText(), txtCPF.getText(), dataNascimento, cbCargo.getSelectionModel().getSelectedItem(), txtFuncao.getText(), dataAdm,
                    salario, txtEnd.getText(), txtBairro.getText(), txtCidade.getText(), UF, txtCEP.getText(), txtTel.getText(), txtEmail.getText(), txtNat.getText(), txtConju.getText(), numDep, txtNomePai.getText(), 
                    txtNomeMae.getText(), sexo, estadoCivil, status, txtUsuarioFP.getText(), txtSenhaFP.getText());

            String SQL = "INSERT INTO dbo.Funcionario (filial, nome, identidade, CPF, dataNasc, cargo, funcao, admissao, salario, endereco, bairro, cidade, UF, CEP, telefone, email, naturalidade, conjugue, numDependentes, nomePai, nomeMae, sexo, "
                    + "estadoCivil, status, loginFP, senhaFP) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setString(1, func.getFilial());
                pst.setString(2, func.getNome());
                pst.setString(3, func.getIdentidade());
                pst.setString(4, func.getCPF());
                pst.setDate(5, func.getDataNasc());
                pst.setString(6, func.getCargo());
                pst.setString(7, func.getFuncao());
                pst.setDate(8, func.getAdmissao());
                pst.setDouble(9, func.getSalario());
                pst.setString(10, func.getEndereco());
                pst.setString(11, func.getBairro());
                pst.setString(12, func.getCidade());
                pst.setString(13, func.getUF());
                pst.setString(14, func.getCEP());
                pst.setString(15, func.getTelefone());
                pst.setString(16, func.getEmail());
                pst.setString(17, func.getNaturalidade());
                pst.setString(18, func.getConjugue());
                pst.setInt(19, func.getNumDependentes());
                pst.setString(20, func.getNomePai());
                pst.setString(21, func.getNomeMae());
                pst.setString(22, func.getSexo());
                pst.setString(23, func.getEstadoCivil());
                pst.setString(24, func.getStatus());
                pst.setString(25, func.getLoginFP());
                pst.setString(26, func.getSenhaFP());
                pst.executeUpdate();

                met.insertSucess("Funcionário");
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }
    }
}