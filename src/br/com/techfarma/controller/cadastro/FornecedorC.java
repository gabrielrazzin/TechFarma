package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import com.jfoenix.controls.JFXToggleButton;
import controller.ConexaoController;
import java.net.URL;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import br.com.techfarma.model.cadastro.FornecedorM;
import controller.MaskedTextField;
import controller.MetodosController;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class FornecedorC implements Initializable, CadastroI{
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TextField txtRazao, txtNome, txtLimCred, txtCodFat, txtEnd, txtNum, txtBairro, txtCompl, txtCidade, txtEmail, txtVendedor, txtSite, txtEndCob, txtBairroCob, txtCidadeCob, txtServidor, txtUser, txtSenha, txtDirEnvio, 
            txtDirRetorno, txtArqEnvio, txtArqRetorno, txtCaminhoProg;      
    @FXML
    private MaskedTextField txtCNPJ, txtInsEst, txtCEP, txtTel, txtCEPCob;
    @FXML
    private ComboBox cbCodPais, cbUF, cbCodMun, cbUFC, cbConta, cbCFOP, cbCodDic;   
    @FXML
    private JFXToggleButton tbFTP, tbICMS, tbIPI, tbFrete, tbSeguro, tbOutrasDesp;
    
    @FXML
    private Button btCancelar;
    
    private String UF, UFCob, configFTP, somaICMS, somaIPI, somaFrete, somaSeguro, somaOutrasDesp, codCFOP;
    private int codPais, codMun, codDic, codFaturamento;
    private double limiteCred, codConta;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }

    @Override
    public void inicioPadrao(){
        conn.Conexao();
        
        preencheCBPais();
        preencheCBMunicipio();
        preencheCBUFs();
        preencheCBCodDic();
        preencheCBCFOP();
        
        tbICMS.setSelected(true);
        tbIPI.setSelected(true);
        tbFrete.setSelected(true);
        tbSeguro.setSelected(true);
        tbOutrasDesp.setSelected(true);
        
        cbCodPais.getSelectionModel().select(33);
        cbUF.getSelectionModel().select(18);
        cbUFC.getSelectionModel().select(18);
        
        txtCNPJ.setMask("##.###.###/####-##");
        txtInsEst.setMask("##.###.####-#");
        txtCEP.setMask("#####-###");
        txtTel.setMask("(##)#####-####");
        txtCEPCob.setMask("#####-###");
    }
    
    @Override
    public void fecharJanela(){
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
    
    public void limparCampos(){
        txtRazao.clear();
        txtNome.clear();
        txtCNPJ.clear();
        txtInsEst.clear();
        txtLimCred.clear();
        txtCodFat.clear();
        txtEnd.clear();
        txtNum.clear();
        txtBairro.clear();
        txtCompl.clear();
        txtCidade.clear();
        txtCEP.clear();
        txtTel.clear();
        txtEmail.clear();
        txtVendedor.clear();
        txtSite.clear();
        txtEndCob.clear();
        txtBairroCob.clear();
        txtCidadeCob.clear();
        txtCEPCob.clear();
        txtServidor.clear();
        txtUser.clear();
        txtSenha.clear();
        txtDirEnvio.clear();
        txtDirRetorno.clear();
        txtArqEnvio.clear();
        txtArqRetorno.clear();
        txtCaminhoProg.clear();
        
        cbCodPais.getSelectionModel().select(33);
        cbUF.getSelectionModel().select(18);
        cbCodMun.getSelectionModel().clearSelection();
        cbConta.getSelectionModel().clearSelection();
        cbCFOP.getSelectionModel().clearSelection();
        cbUFC.getSelectionModel().select(18);
        cbCodDic.getSelectionModel().clearSelection();
        
        tbFTP.setSelected(false);
        tbICMS.setSelected(true);
        tbIPI.setSelected(true);
        tbFrete.setSelected(true);
        tbSeguro.setSelected(true);
        tbOutrasDesp.setSelected(true);
    }   
    
    @FXML
    void keyPressed(KeyEvent event){
        switch (event.getCode()) {
            case F10:
                inserirBanco();
                break;
            case F4:
                limparCampos();
                break;
            case ESCAPE:
                fecharJanela();
                break;
            default:
                break;
            }
    }
    
    private void preencheCBPais() {
        ObservableList listaPais = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT nome FROM Codigos.Pais ORDER BY nome");

            while (conn.rs.next()) {
                listaPais.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher Cód. Pais:" +ex);
        }
        
        cbCodPais.getItems().addAll(listaPais);
    }
    
    private void preencheCBMunicipio() {
        ObservableList listaMun = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT nome FROM Codigos.Municipio");

            while (conn.rs.next()) {
                listaMun.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher Cód. Municipio:" +ex);
        }
        
        cbCodMun.getItems().addAll(listaMun);
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
        cbUFC.setItems(listaUF);
    }
    
    private void preencheCBCFOP() {
        ObservableList listaCFOP = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT idCFOP, descricao FROM dbo.CFOP");

            while (conn.rs.next()) {
                listaCFOP.add(conn.rs.getString(1) + " - " + conn.rs.getString(2));
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao preencher Cód. Pais:" +ex);
        }
        
        cbCFOP.getItems().addAll(listaCFOP);
    }
    
    private void preencheCBCodDic(){
        ObservableList<String> listaCD = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT nome FROM PedidoEletronico");
            
            while(conn.rs.next()){
                listaCD.add(conn.rs.getString(1));
            }
            
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        cbCodDic.setItems(listaCD);
    }
    
    private void verificaTB() {
        if(tbFTP.isSelected()){
            configFTP = "S";
        } else {
            configFTP = "N";
        }
        
        if(tbICMS.isSelected()){
            somaICMS = "S";
        } else {
            somaICMS = "N";
        }
        
        if(tbIPI.isSelected()){
            somaIPI = "S";
        } else {
            somaIPI = "N";
        }
        
        if(tbFrete.isSelected()){
            somaFrete = "S";
        } else {
            somaFrete = "N";
        }
        
        if(tbSeguro.isSelected()){
            somaSeguro = "S";
        } else {
            somaSeguro = "N";
        }
        
        if(tbOutrasDesp.isSelected()){
            somaOutrasDesp = "S";
        } else {
            somaOutrasDesp = "N";
        }
    }
    
    private void capturaCodPais() {
        if (cbCodPais.getSelectionModel().isEmpty()) {
            codPais = 0;
        } else {
            String nomePais = cbCodPais.getSelectionModel().getSelectedItem().toString();

            try {
                conn.executaSQL("SELECT idPais FROM Codigos.Pais WHERE nome = '" + nomePais + "'");
                conn.rs.first();

                codPais = conn.rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    
    private void capturaCodMun() {
        if (cbCodMun.getSelectionModel().isEmpty()) {
            codMun = 0;
        } else {
            String nomeMun = cbCodMun.getSelectionModel().getSelectedItem().toString();

            try {
                conn.executaSQL("SELECT idMunicipio FROM Codigos.Municipio WHERE nome = '" + nomeMun + "'");
                conn.rs.first();

                codMun = conn.rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    
    private void capturaCodDic() {
        if (cbCodDic.getSelectionModel().isEmpty()) {
            codDic = 0;
        } else {
            String nomePed = cbCodDic.getSelectionModel().getSelectedItem().toString();

            try {
                conn.executaSQL("SELECT idPedido FROM dbo.PedidoEletronico WHERE nome = '" + nomePed + "'");
                conn.rs.first();

                codDic = conn.rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    
    private void filtraUF(){
        String descricao = cbUF.getSelectionModel().getSelectedItem().toString();
        String[] splitUF = descricao.split("- ");
        
        UF = splitUF[0];
    }
    
    private void filtraUFCob(){
        String descricao = cbUFC.getSelectionModel().getSelectedItem().toString();
        String[] splitUFCob = descricao.split("- ");
        
        UFCob = splitUFCob[0];
    }
    
    private void verificaTF(){
        if (cbCFOP.getSelectionModel().isEmpty()) {
            codCFOP = null;
        } else {
            String descricao = cbCFOP.getSelectionModel().getSelectedItem().toString();
            String[] splitDesc = descricao.split("-");
            
            codCFOP = splitDesc[0];
        }
        
        if(cbConta.getSelectionModel().isEmpty()){
            codConta = 0.00;
        } else {
            codConta = Double.parseDouble(cbConta.getSelectionModel().getSelectedItem().toString());
        }
        
        if(txtLimCred.getText().isEmpty()){
            limiteCred = 0.00;
        } else {
            limiteCred = Double.parseDouble(txtLimCred.getText());
        }
        
        if(txtCodFat.getText().isEmpty()){
            codFaturamento = 0;
        } else {
            codFaturamento = Integer.parseInt(txtCodFat.getText());
        }
    }
    
    @Override
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtNome);

        if (met.verificaCampos(1, aTxt)) {
            verificaTB();
            verificaTF();
            capturaCodPais();
            capturaCodMun();
            capturaCodDic();
            filtraUF();
            filtraUFCob();

            FornecedorM forn = new FornecedorM(txtRazao.getText(), txtNome.getText(), txtEnd.getText(), txtNum.getText(), txtBairro.getText(), txtCompl.getText(), txtCidade.getText(), txtCEP.getText().replaceAll("[_-]", ""), codPais, UF, codMun, 
                    txtCNPJ.getText().replaceAll("[-./_]", ""), txtInsEst.getText().replaceAll("[-._]", ""), limiteCred, codFaturamento, txtVendedor.getText(), txtTel.getText().replaceAll("[_]", ""),  txtEmail.getText(), txtSite.getText(), codConta, codCFOP, 
                    txtEndCob.getText(), txtBairroCob.getText(), txtCidadeCob.getText(), txtCEPCob.getText().replaceAll("[_-]", ""), UFCob, configFTP, txtServidor.getText(), txtUser.getText(),  txtSenha.getText(), txtDirEnvio.getText(), 
                    txtDirRetorno.getText(), txtArqEnvio.getText(), txtArqRetorno.getText(), txtCaminhoProg.getText(), codDic, somaICMS, somaIPI, somaFrete, somaSeguro, somaOutrasDesp);

            String SQL = "exec sp_insertFornecedor ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?";

            try {
                CallableStatement cst = conn.getConexao().prepareCall(SQL);

                cst.setString(1, forn.getRazaoSocial());
                cst.setString(2, forn.getNome());
                cst.setString(3, forn.getEndereco());
                cst.setString(4, forn.getNumero());
                cst.setString(5, forn.getBairro());
                cst.setString(6, forn.getComplemento());
                cst.setString(7, forn.getCidade());
                cst.setString(8, forn.getCEP());
                cst.setInt(9, forn.getCodPais());
                cst.setString(10, forn.getUF());
                cst.setInt(11, forn.getCodMunicipio());
                cst.setString(12, forn.getCNPJ());
                cst.setString(13, forn.getInsEstadual());
                cst.setDouble(14, forn.getLimiteCred());
                cst.setInt(15, forn.getCodFaturamento());
                cst.setString(16, forn.getVendedor());
                cst.setString(17, forn.getTelefone());
                cst.setString(18, forn.getEmail());
                cst.setString(19, forn.getWebsite());
                cst.setDouble(20, forn.getContaCont());
                cst.setString(21, forn.getCFOP());
                cst.setString(22, forn.getEnderecoCob());
                cst.setString(23, forn.getBairroCob());
                cst.setString(24, forn.getCidadeCob());
                cst.setString(25, forn.getCEPCob());
                cst.setString(26, forn.getUFCob());
                cst.setString(27, forn.getConfig());
                cst.setString(28, forn.getServidor());
                cst.setString(29, forn.getUsuario());
                cst.setString(30, forn.getSenha());
                cst.setString(31, forn.getDirEnvio());
                cst.setString(32, forn.getDirRetorno());
                cst.setString(33, forn.getArqEnvio());
                cst.setString(34, forn.getArqRetorno());
                cst.setString(35, forn.getDiretorioPE());
                cst.setInt(36, forn.getCodDic());
                cst.setString(37, forn.getSomaICMS());
                cst.setString(38, forn.getSomaIPI());
                cst.setString(39, forn.getSomaFrete());
                cst.setString(40, forn.getSomaSeguro());
                cst.setString(41, forn.getSomaOutrasDesp());

                cst.executeUpdate();
                met.insertSucess("Fornecedor");
                limparCampos();
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }

    }
}