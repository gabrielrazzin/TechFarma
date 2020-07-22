package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.UsuarioM;
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

public class UsuarioC implements Initializable, ConsultaI{
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    
    private Stage stageCadUsuario;
    @FXML
    private TableView<UsuarioM> tvUsuario;
    @FXML
    private TableColumn tcCodigo, tcUsuario, tcLogin, tcPermissao, tcFilial;
    @FXML
    private TextField txtConsulta;
    @FXML
    private Button btClose;
    
    private ObservableList<UsuarioM> data = FXCollections.observableArrayList();
    
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
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idUsuario"));
        tcUsuario.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcLogin.setCellValueFactory(new PropertyValueFactory<>("login"));
        tcPermissao.setCellValueFactory(new PropertyValueFactory<>("permissao"));
        tcFilial.setCellValueFactory(new PropertyValueFactory<>("filial"));
        
        try {
            conn.executaSQL("SELECT idUsuario, nome, login, permissao, filial FROM dbo.Usuario");
            
            while(conn.rs.next()){
                data.add(new UsuarioM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvUsuario.setItems(data);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadUsuario == null){
            stageCadUsuario = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Usuario.fxml", stageCadUsuario);
            stageCadUsuario.setOnHiding(we -> stageCadUsuario = null);
            stageCadUsuario.showAndWait();
        } else if (stageCadUsuario.isShowing()){
            stageCadUsuario.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover este Usuário?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            UsuarioM usuario = tvUsuario.getSelectionModel().getSelectedItem();
            int idUsuario = usuario.getIdUsuario();

            try {
                conn.executaSQL("DELETE FROM dbo.Usuario WHERE idUsuario = '" + idUsuario + "'");

                data.removeAll(data);
                met.removeSucess("Usuário");
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
            tvUsuario.setItems(data);
        } else {
            String filtro = txtConsulta.getText();

            try {
                conn.executaSQL("SELECT idUsuario, nome, login, permissao, filial FROM dbo.Usuario WHERE (idUsuario LIKE '" +filtro+ "%' OR nome LIKE '" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new UsuarioM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvUsuario.setItems(filterData);
        }
    }
    
}
