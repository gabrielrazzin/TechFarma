package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.FornecedorM;
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

public class FornecedorC implements Initializable, ConsultaI{
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    
    private Stage stageCadFornencedor;
    @FXML
    private TableView<FornecedorM> tvFornecedor;
    @FXML
    private TableColumn tcCodigo, tcNome, tcTel, tcEndereco, tcBairro;
    @FXML
    private TextField txtConsulta;
    @FXML
    private Button btClose;
    
    private ObservableList<FornecedorM> data  = FXCollections.observableArrayList();

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
    
    @Override
    public void fecharJanela(){
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void carregaTabela() {
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idFornecedor"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcTel.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tcEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        tcBairro.setCellValueFactory(new PropertyValueFactory<>("bairro"));
        try {
            conn.executaSQL("SELECT idFornecedor, nome, telefone, endereco, bairro FROM dbo.Fornecedor");

            while (conn.rs.next()) {
                data.add(new FornecedorM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvFornecedor.setItems(data);
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover este Fornecedor?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            FornecedorM fornecedor = tvFornecedor.getSelectionModel().getSelectedItem();
            int idFornecedor = fornecedor.getIdFornecedor();

            try {
                conn.executaSQL("DELETE FROM dbo.Fornecedor WHERE idFornecedor = '" + idFornecedor + "'");

                data.removeAll(data);
                met.removeSucess("Fornecedor");
                carregaTabela();
            } catch (Exception e) {
                System.out.println();
            }
        }
    }

    @Override
    public void abrirCadastro() {
        if(stageCadFornencedor == null){
            stageCadFornencedor = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Fornecedor.fxml", stageCadFornencedor);
            stageCadFornencedor.setOnHiding(we -> stageCadFornencedor = null);
            stageCadFornencedor.showAndWait();
        } else if (stageCadFornencedor.isShowing()){
            stageCadFornencedor.toFront();
        }

        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void filtraTabela() {
        ObservableList filterData = FXCollections.observableArrayList();
        
        if (txtConsulta.getText().isEmpty()) {
            tvFornecedor.setItems(data);
        } else {
            String filtro = txtConsulta.getText();

            try {
                conn.executaSQL("SELECT idFornecedor, nome, telefone, endereco, bairro FROM dbo.Fornecedor WHERE (idFornecedor LIKE '" +filtro+ "%' OR nome LIKE '" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new FornecedorM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvFornecedor.setItems(filterData);
        }
    }
}