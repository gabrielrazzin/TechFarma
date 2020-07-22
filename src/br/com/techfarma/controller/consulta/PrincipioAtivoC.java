package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.PrincipioAtivoM;
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

public class PrincipioAtivoC implements Initializable, ConsultaI {
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    
    private Stage stageCadPrincipioA;
    @FXML
    private TableView<PrincipioAtivoM> tvPrincipio;
    @FXML
    private TableColumn tcCodigo, tcDescricao;
    @FXML
    private TextField txtConsulta;
    @FXML
    private Button btClose;
    
    private ObservableList<PrincipioAtivoM> data = FXCollections.observableArrayList();
    
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
    public void fecharJanela() {
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }

    @Override
    public void carregaTabela() {
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idPrincipio"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        try {
            conn.executaSQL("SELECT idPrincipio, descricao FROM dbo.PrincipioAtivo");
            
            while(conn.rs.next()){
                data.add(new PrincipioAtivoM(conn.rs.getInt(1), conn.rs.getString(2)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvPrincipio.setItems(data);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadPrincipioA == null){
            stageCadPrincipioA = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Principio Ativo.fxml", stageCadPrincipioA);
            stageCadPrincipioA.setOnHiding(we -> stageCadPrincipioA = null);
            stageCadPrincipioA.showAndWait();
        } else if (stageCadPrincipioA.isShowing()){
            stageCadPrincipioA.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover este Principio Ativo?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            PrincipioAtivoM principioA = tvPrincipio.getSelectionModel().getSelectedItem();
            int idPrincipio = principioA.getIdPrincipio();

            try {
                conn.executaSQL("DELETE FROM dbo.PrincipioAtivo WHERE idPrincipio = '" + idPrincipio + "'");

                data.removeAll(data);
                met.removeSucess("Principio Ativo");
                carregaTabela();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    @Override
    public void filtraTabela() {
        ObservableList filterData = FXCollections.observableArrayList();
        
        if (txtConsulta.getText().isEmpty()) {
            tvPrincipio.setItems(data);
        } else {
            String filtro = txtConsulta.getText();

            try {
                conn.executaSQL("SELECT idPrincipio, descricao FROM dbo.PrincipioAtivo WHERE (idPrincipio LIKE '" +filtro+ "%' OR descricao LIKE '" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new PrincipioAtivoM(conn.rs.getInt(1), conn.rs.getString(2)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvPrincipio.setItems(filterData);
        }
    }
}
