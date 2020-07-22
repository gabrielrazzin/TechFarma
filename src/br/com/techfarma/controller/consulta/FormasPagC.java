package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.FormasPagM;
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

public class FormasPagC implements Initializable, ConsultaI{
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();

    private ObservableList<FormasPagM> data = FXCollections.observableArrayList();
    private Stage stageCadFormasPag;
    
    @FXML
    private TableView<FormasPagM> tvFormasPag;
    @FXML
    private TableColumn tcCodigo, tcDescricao, tcArredonda, tcArredondamento;
    @FXML
    private TextField txtConsulta;
    @FXML
    private Button btClose;
    
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
    protected void keyPressed(KeyEvent event){
        switch (event.getCode()) {
            case F2:
                abrirCadastro();
                break;
            case F6:
                txtConsulta.requestFocus();
                break;
            case ESCAPE:
                fecharJanela();
                break;
            default:
                break;
        }
    }
    
    @FXML
    protected void keyTextFieldEvent(KeyEvent event){
        switch (event.getCode()) {
            case F2:
                abrirCadastro();
                break;
            case ESCAPE:
                fecharJanela();
                break;
            default:
                break;
        }
    }
    
    @FXML
    protected void keyTableViewEvent(KeyEvent event){
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
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idFormasPag"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcArredonda.setCellValueFactory(new PropertyValueFactory<>("arredonda"));
        tcArredondamento.setCellValueFactory(new PropertyValueFactory<>("arredondamento"));
        
        try {
            conn.executaSQL("SELECT idFormasPag, descricao, arredonda, arredondamento FROM dbo.FormasPag");
            
            while(conn.rs.next()){
                data.add(new FormasPagM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        tvFormasPag.setItems(data);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadFormasPag == null){
            stageCadFormasPag = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Forma Pagamento.fxml", stageCadFormasPag);
            stageCadFormasPag.setOnHiding(we -> stageCadFormasPag = null);
            stageCadFormasPag.showAndWait();
        } else if (stageCadFormasPag.isShowing()){
            stageCadFormasPag.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover esta Forma de Pagamento?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            FormasPagM formaPag = tvFormasPag.getSelectionModel().getSelectedItem();
            int idFormaPag = formaPag.getIdFormasPag();

            try {
                conn.executaSQL("DELETE FROM dbo.FormasPag WHERE idFormasPag = '" + idFormaPag + "'");

                data.removeAll(data);
                met.removeSucess("Forma de Pagamento");
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
            tvFormasPag.setItems(data);
        } else {
            String filtro = txtConsulta.getText();

            try {
                conn.executaSQL("SELECT idFormasPag, descricao, arredonda, arredondamento FROM dbo.FormasPag WHERE (idFormasPag LIKE '" +filtro+ "%' OR descricao LIKE '" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new FormasPagM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvFormasPag.setItems(filterData);
        }
    }
    
}
