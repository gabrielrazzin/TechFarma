package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import controller.ConexaoController;
import controller.MaskedTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
import javax.swing.JOptionPane;
import br.com.techfarma.model.cadastro.FilialM;
import controller.MetodosController;
import java.util.ArrayList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class FilialC implements Initializable, CadastroI{
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private MaskedTextField txtCNPJ, txtTel, txtCel, txtCEP;
    @FXML
    private TextField txtRazao, txtInscEst, txtNome, txtSite, txtRua, txtNum, txtCompl, txtBairro, txtCidade, txtEmail;
    @FXML
    private ComboBox<String> cbUF, cbGrupoF, cbMunicipio;
    @FXML
    private Button btCancelar;
    
    private String UF, idMunicipio;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }
    
    @Override
    public void inicioPadrao() {
        conn.Conexao();
        preencheCBM();
        preencheCBUF();
        preencheCBGF();
        cbUF.getSelectionModel().select(18);
        
        txtCNPJ.setMask("##.###.###/####-##");
        txtTel.setMask((("(##)####-####")));
        txtCel.setMask("(##)#####-####");
        txtCEP.setMask("#####-###");
        
//        txtNum.lengthProperty().addListener(new ChangeListener<Number>() {
//            @Override
//            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
//                if (newValue.intValue() > oldValue.intValue()) {
//                    if (txtNum.getText().length() >= 20) {
//                        txtNum.setText(txtNum.getText().substring(0, 20));
//                    }
//                }
//            }
//        });
    }
        
    @Override
    public void fecharJanela(){
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
    
    public void limparCampos(){
        txtNome.clear();
        txtRazao.clear();
        txtCNPJ.clear();
        txtInscEst.clear();
        txtSite.clear();
        txtRua.clear();
        txtNum.clear();
        txtCompl.clear();
        txtBairro.clear();
        txtCidade.clear();
        txtCEP.clear();
        txtTel.clear();
        txtCel.clear();
        txtEmail.clear();
        cbGrupoF.getSelectionModel().clearSelection();
        cbMunicipio.getSelectionModel().clearSelection();
        cbUF.getSelectionModel().select(18);
        txtNome.requestFocus();
    }
    
    @FXML
    void keyPressed(KeyEvent event){
        switch (event.getCode()) {
            case F10:
                inserirBanco();
                break;
            case F4:
                limparCampos();
                System.out.println("Limpar Campos");
                break;
            case ESCAPE:
                fecharJanela();
                break;
            default:
                break;
            }
    }
    
    public void preencheCBM(){
        ObservableList<String> codMunCB = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT nome FROM Codigos.Municipio ORDER BY nome");
            
            while(conn.rs.next()){
                codMunCB.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar a lista de Códigos de Municípios." +ex);
        }
        
        cbMunicipio.setItems(codMunCB);
    }
    
    public void preencheCBUF(){
       ObservableList<String> UFCB = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT sigla, nome FROM Codigos.UF ORDER BY nome");
            
            while(conn.rs.next()){
                UFCB.add(conn.rs.getString(1) + " - " + conn.rs.getString(2));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar a lista de Códigos de UF." +ex);
        }
        
        cbUF.setItems(UFCB);
    }
    
    public void preencheCBGF(){
        ObservableList<String> GrupoFilialCB = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT descricao FROM dbo.GrupoFilial");
            
            while(conn.rs.next()){
                GrupoFilialCB.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Não foi possível carregar a lista de Grupos de Filial." +ex);
        }
        cbGrupoF.setItems(GrupoFilialCB);
    }

    public void capturaCodMun() {
        if (cbMunicipio.getSelectionModel().isEmpty()) {
            idMunicipio = null;
        } else {
            String nomeMun = cbMunicipio.getSelectionModel().getSelectedItem();

            try {
                conn.executaSQL("SELECT idMunicipio FROM Codigos.Municipio WHERE nome = '" + nomeMun + "'");
                conn.rs.first();

                idMunicipio = conn.rs.getString(1);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    
    private void filtraUF(){
        String descricao = cbUF.getSelectionModel().getSelectedItem();
        String[] splitUF = descricao.split(" - ");
        
        UF = splitUF[0];
    }
    
    @Override
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtNome);
        aTxt.add(txtRazao);
        ArrayList<ComboBox> aCB = new ArrayList<>();
        aCB.add(cbGrupoF);
        
        if (met.verificaCampos(2, aTxt) & met.verificaComboBox(1, aCB)) {
            capturaCodMun();
            filtraUF();
        
            FilialM filial = new FilialM(cbGrupoF.getSelectionModel().getSelectedItem().toString(), txtNome.getText(), txtRazao.getText(), txtCNPJ.getPlainText(), txtInscEst.getText().replace(",", "."), txtSite.getText(), txtRua.getText(), txtNum.getText(),
                txtCompl.getText(), txtBairro.getText(), txtCidade.getText(), UF, txtCEP.getPlainText(), idMunicipio, txtTel.getPlainText(), txtCel.getPlainText(), txtEmail.getText(), "A");
            
            String SQL = "INSERT INTO dbo.Filial (grupoFilial, nome, razaoSocial, CNPJ, inscricao, site, rua, numero, complemento, bairro, cidade, UF, CEP, idMunicipio,"
                    + "telefone, celular, email, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setString(1, filial.getGrupoFilial());
                pst.setString(2, filial.getNome());
                pst.setString(3, filial.getRazaoSocial());
                pst.setString(4, filial.getCNPJ());
                pst.setString(5, filial.getInscricao());
                pst.setString(6, filial.getSite());
                pst.setString(7, filial.getRua());
                pst.setString(8, filial.getNumero());
                pst.setString(9, filial.getComplemento());
                pst.setString(10, filial.getBairro());
                pst.setString(11, filial.getCidade());
                pst.setString(12, filial.getUF());
                pst.setString(13, filial.getCEP());
                pst.setString(14, filial.getIdMunicipio());
                pst.setString(15, filial.getTelefone());
                pst.setString(16, filial.getCelular());
                pst.setString(17, filial.getEmail());
                pst.setString(18, filial.getStatus());
                pst.executeUpdate();
                
                met.insertSucess("Filial");
                limparCampos();
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }
    }
}
