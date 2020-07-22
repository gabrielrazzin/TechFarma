package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.GrupoFilialM;
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

public class GrupoFilialC implements Initializable, ConsultaI{
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    
    private Stage stageCadGrupoFilial;
    @FXML
    private TableView<GrupoFilialM> tvGrupoF;
    @FXML
    private TableColumn tcCodigo, tcDescricao;
    @FXML
    private TextField txtConsulta;
    @FXML
    private Button btIncluir;
    
    private ObservableList<GrupoFilialM> data = FXCollections.observableArrayList();
    
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
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idGrupoFilial"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        
        try {
            conn.executaSQL("SELECT idGrupoFilial, descricao FROM dbo.GrupoFilial");
            
            while(conn.rs.next()){
                data.add(new GrupoFilialM(conn.rs.getInt(1), conn.rs.getString(2)));
            }
        } catch (SQLException ex) {
        }
        
        tvGrupoF.setItems(data);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadGrupoFilial == null){
            stageCadGrupoFilial = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Grupo Filial.fxml", stageCadGrupoFilial);
            stageCadGrupoFilial.setOnHiding(we -> stageCadGrupoFilial = null);
            stageCadGrupoFilial.showAndWait();
        } else if (stageCadGrupoFilial.isShowing()){
            stageCadGrupoFilial.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover este Grupo de Filial?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            GrupoFilialM grupoFilial = tvGrupoF.getSelectionModel().getSelectedItem();
            int idGrupoF = grupoFilial.getIdGrupoFilial();

            try {
                conn.executaSQL("DELETE FROM dbo.GrupoFilial WHERE idGrupoFilial = '" + idGrupoF + "'");

                data.removeAll(data);
                met.removeSucess("Grupo de Filial");
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
            tvGrupoF.setItems(data);
        } else {
            String filtro = txtConsulta.getText();

            try {
                conn.executaSQL("SELECT idGrupoFilial, descricao FROM dbo.GrupoFilial WHERE (idGrupoFilial LIKE '" + filtro + "%' OR descricao LIKE '%" + filtro + "%')");

                while (conn.rs.next()) {
                    filterData.add(new GrupoFilialM(conn.rs.getInt(1), conn.rs.getString(2)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvGrupoF.setItems(filterData);
        }
    }
}
