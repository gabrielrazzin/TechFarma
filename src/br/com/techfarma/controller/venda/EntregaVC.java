package br.com.techfarma.controller.venda;

import br.com.techfarma.model.consulta.VendaM;
import controller.ConexaoController;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javax.swing.JOptionPane;
import model.VendaEntregaM;

public class EntregaVC implements Initializable{
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TableView<VendaEntregaM> tvClientes;
    @FXML
    private TableColumn tcNome, tcTelefone, tcCelular, tcEndereco, tcComplemento, tcBairro, tcCidade, tcConvenio;
    @FXML
    private TextField txtNome, txtEndereco, txtFidelidade;
    @FXML
    private Button btClose;
    
    private ObservableList<VendaEntregaM> filterData = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       conn.Conexao();
    }
    
    @FXML
    protected void tableViewEvent(KeyEvent event) {
        switch (event.getCode()) {
            case ENTER:
                enviaDados();
                break;
            case ESCAPE:
                fecharJanela();
                break;
            default:
                break;
        }
    }
    
    @FXML
    protected void textFieldEvent(KeyEvent event){
        switch (event.getCode()){
            case ENTER:
                buscaCliente();
                break;
            case ESCAPE:
                fecharJanela();
                break;
            default:
                break;
        }
    }
    
    @FXML
    protected void fecharJanela(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }
    
    private void buscaCliente() {
        tvClientes.getItems().clear();
        
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tcCelular.setCellValueFactory(new PropertyValueFactory<>("celular"));
        tcEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tcComplemento.setCellValueFactory(new PropertyValueFactory<>("complemento"));
        tcBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        tcCidade.setCellValueFactory(new PropertyValueFactory<>("municipio"));
        tcConvenio.setCellValueFactory(new PropertyValueFactory<>("idConvenio"));
        
        if(txtNome.getText().isEmpty() && txtEndereco.getText().isEmpty() && txtFidelidade.getText().isEmpty()){
            JOptionPane.showMessageDialog(null, "Preencha pelo menos um campo.");
        } else if (!txtNome.getText().isEmpty() && txtEndereco.getText().isEmpty() && txtFidelidade.getText().isEmpty()) {
            try {
                conn.executaSQL("SELECT nome, telefone, celular, rua + ' ' + numero AS endereco, complemento, bairro, municipio, idConvenio FROM dbo.Cliente WHERE nome LIKE '%" + txtNome.getText() + "%' OR telefone = '" + txtNome.getText() + "' "
                        + "OR celular = '" + txtNome.getText() + "' OR CPF = '" + txtNome.getText() + "'");

                while (conn.rs.next()) {
                    filterData.add(new VendaEntregaM(conn.rs.getString(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5), conn.rs.getString(6), conn.rs.getString(7), conn.rs.getInt(8)));
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        } else if (!txtEndereco.getText().isEmpty() && txtNome.getText().isEmpty() && txtFidelidade.getText().isEmpty()) {
            try {
                conn.executaSQL("SELECT nome, telefone, celular, rua + ' ' + numero AS endereco, complemento, bairro, municipio, idConvenio FROM dbo.Cliente WHERE rua LIKE '%" + txtEndereco.getText() + "%' OR CEP = '" + txtEndereco.getText() + "'");

                while (conn.rs.next()) {
                    filterData.add(new VendaEntregaM(conn.rs.getString(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5), conn.rs.getString(6), conn.rs.getString(7), conn.rs.getInt(8)));
                }
            } catch (SQLException e) {
                System.out.println(e);
            }
        }
        
        tvClientes.setItems(filterData);
        tvClientes.requestFocus();
        tvClientes.getSelectionModel().select(0);
    }
    
    private void enviaDados() {
        try {
            if (tvClientes.getSelectionModel().getSelectedItem().getTelefone() == null) {
                if (tvClientes.getSelectionModel().getSelectedItem().getCelular() != null) {
                    conn.executaSQL("SELECT idCliente, idConvenio FROM dbo.Cliente WHERE celular = '" + tvClientes.getSelectionModel().getSelectedItem().getCelular() + "'");
                    try {
                        conn.rs.first();
                        VendaM.idCliente = conn.rs.getInt(1);
                        VendaM.idConvenio = conn.rs.getInt(2);
                    } catch (SQLException e) {
                    }
                }
            } else {
                conn.executaSQL("SELECT idCliente, idConvenio FROM dbo.Cliente WHERE telefone = '" + tvClientes.getSelectionModel().getSelectedItem().getTelefone() + "'");
                try {
                    conn.rs.first();
                    VendaM.idCliente = conn.rs.getInt(1);
                    VendaM.idConvenio = conn.rs.getInt(2);
                } catch (SQLException e) {
                }
            }
        } catch (NullPointerException e) {
            System.out.println(e);
        }

        VendaM.nomeCliente = tvClientes.getSelectionModel().getSelectedItem().getNome();
        VendaM.endereco = tvClientes.getSelectionModel().getSelectedItem().getEndereco();
        VendaM.bairro = tvClientes.getSelectionModel().getSelectedItem().getBairro();
        VendaM.referencia = tvClientes.getSelectionModel().getSelectedItem().getComplemento();
        VendaM.telefone = tvClientes.getSelectionModel().getSelectedItem().getTelefone();
        VendaM.celular = tvClientes.getSelectionModel().getSelectedItem().getCelular();
        fecharJanela();

        System.out.println("ID Cliente: " + VendaM.idCliente + "\nID Convenio: " + VendaM.idConvenio + "\nCliente: " + VendaM.nomeCliente + "\nEndereço: " + VendaM.endereco + "\nBairro: " + VendaM.bairro + "\nReferencia: " + VendaM.referencia
                + "\nTelefone: " + VendaM.telefone + "\nCelular = " + VendaM.celular);
    }
}