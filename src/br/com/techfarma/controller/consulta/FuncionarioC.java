package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.FuncionarioM;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FuncionarioC implements Initializable, ConsultaI{
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    
    private Stage stageCadFuncionario;
    @FXML
    private TableView<FuncionarioM> tvFuncionario;
    @FXML
    private TableColumn tcCodigo, tcNome, tcTel, tcFilial, tcFuncao;
    @FXML
    private TextField txtConsulta;
    @FXML
    private ComboBox cbFilial;
    @FXML
    private Button btClose;
    @FXML
    private Tab tabFuncionario, tabComissao;
    
    ObservableList<FuncionarioM> data = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }

    @Override
    public void inicioPadrao() {
        conn.Conexao();
        carregaTabela();
        preencheCBFilial();
    }

    @FXML
    protected void keyPressed(KeyEvent event) {
        if (tabFuncionario.isSelected()) {
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
    }
    
    @Override
    public void fecharJanela() {
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }

    @Override
    public void carregaTabela() {
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idFuncionario"));
        tcNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tcTel.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tcFilial.setCellValueFactory(new PropertyValueFactory<>("filial"));
        tcFuncao.setCellValueFactory(new PropertyValueFactory<>("funcao"));
        
        try {
            conn.executaSQL("SELECT idFuncionario, nome, telefone, filial, funcao FROM dbo.Funcionario");
            
            while(conn.rs.next()){
                data.add(new FuncionarioM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5)));
            }
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvFuncionario.setItems(data);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadFuncionario == null){
            stageCadFuncionario = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Funcionario.fxml", stageCadFuncionario);
            stageCadFuncionario.setOnHiding(we -> stageCadFuncionario = null);
            stageCadFuncionario.showAndWait();
        } else if (stageCadFuncionario.isShowing()){
            stageCadFuncionario.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover este Funcionário?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            FuncionarioM funcionario = tvFuncionario.getSelectionModel().getSelectedItem();
            int idFuncionario = funcionario.getIdFuncionario();

            try {
                conn.executaSQL("DELETE FROM dbo.Funcionario WHERE idFuncionario = '" + idFuncionario + "'");

                data.removeAll(data);
                met.removeSucess("Funcionário");
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
            tvFuncionario.setItems(data);
        } else {
            String filtro = txtConsulta.getText();

            try {
                conn.executaSQL("SELECT idFuncionario, nome, telefone, filial, funcao FROM dbo.Funcionario WHERE (idFuncionario LIKE '" +filtro+ "%' OR nome LIKE '" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new FuncionarioM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4), conn.rs.getString(5)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvFuncionario.setItems(filterData);
        }
    }
    
    private void preencheCBFilial(){
        ObservableList<String> listaFilial = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT nome FROM dbo.Filial");
            
            while(conn.rs.next()){
                listaFilial.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        cbFilial.setItems(listaFilial);
    }
}
