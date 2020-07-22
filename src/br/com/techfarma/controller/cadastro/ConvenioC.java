package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import br.com.techfarma.model.cadastro.ConvenioM;
import com.jfoenix.controls.JFXToggleButton;
import controller.ConexaoController;
import controller.MetodosController;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ConvenioC implements Initializable, CadastroI {

    ConexaoController conn = new ConexaoController();

    @FXML
    private TextField txtNome, txtRua, txtNum, txtBairro, txtCompl, txtCidade, txtCodMun, txtCEP, txtTel, txtCel, txtCNPJ, txtInsEst, txtRG, txtCPF, txtLimCred, txtEmail, txtDesc, txtEndCob, txtNumCob, txtBairroCob, txtCidadeCob;
    @FXML
    private ComboBox cbUF, cbMunicipio, cbFechamento, cbVencimento, cbUFCob, cbConvenio;
    @FXML
    private JFXToggleButton tbVendaFilial, tbComissao, tbStatus;
    @FXML
    private TableView tvClasse;
    @FXML
    private Button btCancelar;
    
    private String UF, vendaFilial, comissao, status, UFCob;
    private int codMunicipio, idVendedor;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }
    
    @Override
    public void inicioPadrao() {
        conn.Conexao();
        
        preencheCBUFs();
        preencheCBFV();
        preencheCBMunicipio();
        
        tbStatus.setSelected(true);
        
        cbUF.getSelectionModel().select(18);
        cbUFCob.getSelectionModel().select(18);       
    }
    
    @Override
    public void fecharJanela() {
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void keyPressed(KeyEvent event){
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
    
    private void verificaTB(){
        if(tbVendaFilial.isSelected()){
            vendaFilial = "S";
        } else {
            vendaFilial = "N";
        }
        
        if(tbComissao.isSelected()){
            comissao = "S";
        } else {
            comissao = "N";
        }
        
        if(tbStatus.isSelected()){
            status = "S";
        } else {
            status = "N";
        }
    }
    
    private void preencheCBFV(){
        ObservableList<Integer> listaFV = FXCollections.observableArrayList();
        
        for(int i = 1; i <= 31; i++){
            listaFV.add(i);
        }
        
        cbFechamento.setItems(listaFV);
        cbVencimento.setItems(listaFV);
    }
    
    private void preencheCBUFs(){
        ObservableList<String> listaUF = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT sigla, nome FROM Codigos.UF");
            
            while(conn.rs.next()){
                listaUF.add(conn.rs.getString(1) + " - " + conn.rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        cbUF.setItems(listaUF);       
        cbUFCob.setItems(listaUF);
    }
    
    private void preencheCBMunicipio(){
        ObservableList<String> listaMun = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT nome FROM Codigos.Municipio");

            while (conn.rs.next()) {
                listaMun.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        cbMunicipio.setItems(listaMun);
    }
    
    private void filtraUF(){
        String descricao = cbUF.getSelectionModel().getSelectedItem().toString();
        String[] splitUF = descricao.split("- ");
        
        UF = splitUF[0];
    }
    
    private void filtraUFCob(){
        String descricao = cbUFCob.getSelectionModel().getSelectedItem().toString();
        String[] splitUF = descricao.split("- ");
        
        UFCob = splitUF[0];
    }
    
    private void capturaCodMun() {
        if (cbMunicipio.getSelectionModel().isEmpty()) {
            codMunicipio = 0;
        } else {
            String nomeMun = cbMunicipio.getSelectionModel().getSelectedItem().toString();

            try {
                conn.executaSQL("SELECT idMunicipio FROM Codigos.Municipio WHERE nome = '" + nomeMun + "'");
                conn.rs.first();

                codMunicipio = conn.rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    @Override
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtNome);
        
        if(met.verificaCampos(1, aTxt)){
            verificaTB();
            filtraUF();
            filtraUFCob();
            capturaCodMun();
            
            ConvenioM conv = new ConvenioM(Integer.parseInt(txtNum.getText()), codMunicipio, Integer.parseInt(cbFechamento.getSelectionModel().getSelectedItem().toString()), 
                    Integer.parseInt(cbVencimento.getSelectionModel().getSelectedItem().toString()), idVendedor, Integer.parseInt(txtNumCob.getText()), txtNome.getText(), txtRua.getText(), txtBairro.getText(), txtCompl.getText(), 
                    UF, txtCidade.getText(), txtCEP.getText(), txtTel.getText(), txtCel.getText(), txtCNPJ.getText(), txtInsEst.getText(), txtRG.getText(), txtCPF.getText(), txtEmail.getText(), vendaFilial, comissao, 
                    txtEndCob.getText(), txtBairroCob.getText(), UFCob, txtCidadeCob.getText(), status, Double.parseDouble(txtLimCred.getText()), Double.parseDouble(txtDesc.getText()));
            
            String SQL = "INSERT INTO dbo.Convenio (nome, endereco, numero, bairro, complemento, UF, cidade, codMun, CEP, telefone, celular, CNPJ, inscEstadual, RG, CPF, diaFechamento, diaVencimento, limCredito, email, desconto, idVendedor,"
                        + "vendaFilial, pagaComissao, endCobranca, numCobranca, bairroCobranca, UFCobranca, cidadeCobranca, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                
                pst.setString(1, conv.getNome());
                pst.setString(2, conv.getEndereco());
                pst.setInt(3, conv.getNumero());
                pst.setString(4, conv.getBairro());
                pst.setString(5, conv.getComplemento());
                pst.setString(6, conv.getUF());
                pst.setString(7, conv.getCidade());
                pst.setInt(8, conv.getCodMun());
                pst.setString(9, conv.getCEP());
                pst.setString(10, conv.getTelefone());
                pst.setString(11, conv.getCelular());
                pst.setString(12, conv.getCNPJ());
                pst.setString(13, conv.getInscEstadual());
                pst.setString(14, conv.getRG());
                pst.setString(15, conv.getCPF());
                pst.setInt(16, conv.getDiaFechamento());
                pst.setInt(17, conv.getDiaVencimento());
                pst.setDouble(18, conv.getLimCredito());
                pst.setString(19, conv.getEmail());
                pst.setDouble(20, conv.getDesconto());
                pst.setInt(21, conv.getIdVendedor());
                pst.setString(22, conv.getVendaFilial());
                pst.setString(23, conv.getPagaComissao());
                pst.setString(24, conv.getEndCobranca());
                pst.setInt(25, conv.getNumCobranca());
                pst.setString(26, conv.getBairroCobranca());
                pst.setString(27, conv.getUFCobranca());
                pst.setString(28, conv.getCidadeCobranca());
                pst.setString(29, conv.getStatus());
                pst.executeUpdate();
                
                met.insertSucess("Convênio");
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }
    }
}
