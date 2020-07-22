package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.ClienteM;
import controller.ConexaoController;
import controller.MetodosController;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClienteC implements Initializable, ConsultaI {
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    
    private Stage stageCadCliente;
    
    @FXML
    private Button btClose;
    @FXML
    private TableColumn tcFilial, tcCodigo, tcNome, tcConvenio, tcEndereco, tcBairro, tcTelefone;
    @FXML
    private TableView<ClienteM> tvCliente;
    @FXML
    private TextField txtConsulta;
    
    private ObservableList<ClienteM> data = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }

    @Override
    public void inicioPadrao() {
        conn.Conexao();
        carregaTabela();
    }

    
    @FXML
    protected void keyPressed(KeyEvent event) {
        switch (event.getCode()) {
            case F2:
                abrirCadastro();
                break;
            case F6:
                txtConsulta.requestFocus();
                break;
            case DELETE:
                removeItem();
                break;
            case ESCAPE:
                fecharJanela();
                break;
            default:
                break;
        }
    }
    
    @FXML
    protected void keyTableViewEvent(KeyEvent event) {
        switch (event.getCode()) {
            case F2:
                abrirCadastro();
                break;
            case F6:
                txtConsulta.requestFocus();
                break;
            case DELETE:
                removeItem();
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
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }

    @Override
    public void carregaTabela() {
        tcFilial.setCellValueFactory(new PropertyValueFactory<>("idFilial"));
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idCliente"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcConvenio.setCellValueFactory(new PropertyValueFactory<>("convenio"));
        tcEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tcBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        tcTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        
        try {
            conn.executaSQL("SELECT idFilial, idCliente, nome, idConvenio, rua + ', ' + numero AS endereco, bairro, telefone FROM dbo.Cliente");
            
            while(conn.rs.next()){
                data.add(new ClienteM(conn.rs.getInt(1), conn.rs.getInt(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5), conn.rs.getString(6), conn.rs.getString(7)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        tvCliente.setItems(data);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadCliente == null){
            stageCadCliente = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Cliente.fxml", stageCadCliente);
            stageCadCliente.setOnHiding(we -> stageCadCliente = null);
            stageCadCliente.showAndWait();
        } else if (stageCadCliente.isShowing()){
            stageCadCliente.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover este Cliente?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            ClienteM cliente = tvCliente.getSelectionModel().getSelectedItem();
            int idCliente = cliente.getIdCliente();

            try {
                conn.executaSQL("DELETE FROM dbo.Cliente WHERE idCliente = '" + idCliente + "'");

                data.removeAll(data);
                met.removeSucess("Cliente");
                carregaTabela();
            } catch (Exception e) {
                System.out.println();
            }
        }
    }

    @Override
    public void filtraTabela() {
        ObservableList filterData = FXCollections.observableArrayList();
        
        if (txtConsulta.getText().isEmpty()) {
            tvCliente.setItems(data);
        } else {
            String filtro = txtConsulta.getText();

            try {
                conn.executaSQL("SELECT idFilial, idCliente, nome, idConvenio, rua + ', ' + numero AS endereco, bairro, telefone FROM dbo.Cliente WHERE (idCliente LIKE '" +filtro+ "%' OR nome LIKE '" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new ClienteM(conn.rs.getInt(1), conn.rs.getInt(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5), conn.rs.getString(6), conn.rs.getString(7)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvCliente.setItems(filterData);
        }
    }
}
