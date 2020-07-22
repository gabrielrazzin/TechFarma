package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.FilialM;
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

public class FilialC implements Initializable, ConsultaI {

    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();

    private Stage stageCadFilial;
    
    @FXML
    private TableView<FilialM> tvFilial;
    @FXML
    private TableColumn tcCodigo, tcNome, tcRazao;
    @FXML
    private TextField txtConsulta;
    @FXML
    private Button btIncluir;

    private ObservableList<FilialM> data = FXCollections.observableArrayList();

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
        Stage stage = (Stage) btIncluir.getScene().getWindow();
        stage.close();
    }

    @Override
    public void carregaTabela() {
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idFilial"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcRazao.setCellValueFactory(new PropertyValueFactory<>("razaoSocial"));

        try {
            conn.executaSQL("SELECT idFilial, nome, razaoSocial FROM dbo.Filial");

            while (conn.rs.next()) {
                data.add(new FilialM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        tvFilial.setItems(data);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadFilial == null){
            stageCadFilial = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Filial.fxml", stageCadFilial);
            stageCadFilial.setOnHiding(we -> stageCadFilial = null);
            stageCadFilial.showAndWait();
        } else if (stageCadFilial.isShowing()){
            stageCadFilial.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover esta Filial?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            FilialM filial = tvFilial.getSelectionModel().getSelectedItem();
            int idFilial = filial.getIdFilial();

            try {
                conn.executaSQL("DELETE FROM dbo.Filial WHERE idFilial = '" + idFilial + "'");

                data.removeAll(data);
                met.removeSucess("Filial");
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
            tvFilial.setItems(data);
        } else {
            String filtro = txtConsulta.getText();

            try {
                conn.executaSQL("SELECT idFilial, nome, razaoSocial FROM dbo.Filial WHERE (idFilial LIKE '" + filtro + "%' OR nome LIKE '%" + filtro + "%')");

                while (conn.rs.next()) {
                    filterData.add(new FilialM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvFilial.setItems(filterData);
        }
    }
}
