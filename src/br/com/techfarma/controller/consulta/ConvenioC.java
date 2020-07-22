package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.ConvenioM;
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

public class ConvenioC implements Initializable, ConsultaI{
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    
    private Stage stageCadConvenio;
    @FXML
    private TableView<ConvenioM> tvConvenio; 
    @FXML
    private TableColumn tcCodigo, tcNome, tcEndereco, tcBairro, tcTelefone;
    @FXML
    private TextField txtConsulta;
    @FXML
    private Button btClose;
    
    ObservableList<ConvenioM> data = FXCollections.observableArrayList();
    
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
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idConvenio"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tcBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        tcTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        
        try {
            conn.executaSQL("SELECT idConvenio, nome, endereco, bairro, telefone FROM dbo.Convenio");
            
            while(conn.rs.next()){
                data.add(new ConvenioM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvConvenio.setItems(data);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadConvenio == null){
            stageCadConvenio = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Convenio.fxml", stageCadConvenio);
            stageCadConvenio.setOnHiding(we -> stageCadConvenio = null);
            stageCadConvenio.showAndWait();
        } else if (stageCadConvenio.isShowing()){
            stageCadConvenio.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover este Convênio?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            ConvenioM convenio = tvConvenio.getSelectionModel().getSelectedItem();
            int idConvenio = convenio.getIdConvenio();

            try {
                conn.executaSQL("DELETE FROM dbo.Convenio WHERE idConvenio = '" + idConvenio + "'");

                data.removeAll(data);
                met.removeSucess("Convênio");
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
            tvConvenio.setItems(data);
        } else {
            String filtro = txtConsulta.getText();

            try {
                conn.executaSQL("SELECT idConvenio, nome, endereco, bairro, telefone FROM dbo.Convenio WHERE (idConvenio LIKE '" +filtro+ "%' OR nome LIKE '" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new ConvenioM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvConvenio.setItems(filterData);
        }
    }
}
