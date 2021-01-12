package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.CartaoM;
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

public class CartaoC implements Initializable, ConsultaI {
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    
    private Stage stageCadCartao;
    private ObservableList<CartaoM> data = FXCollections.observableArrayList();
    
    @FXML
    private TableView<CartaoM> tvCartao;
    @FXML
    private TableColumn tcCodigo, tcDescricao, tcTipo, tcBandeira;
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
    protected void keyPressed(KeyEvent event) {
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
    protected void keyTextFieldEvent(KeyEvent event) {
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
    protected void keyTableEvent(KeyEvent event) {
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
        String tipoCartao = null;
        
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idCartao"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcTipo.setCellValueFactory(new PropertyValueFactory<>("tipoCartao"));
        tcBandeira.setCellValueFactory(new PropertyValueFactory<>("bandeira"));
        
        try {
            conn.executaSQL("SELECT idCartao, descricao, tipoCartao, bandeira FROM dbo.Cartoes");
            
            
            while(conn.rs.next()){
                if(conn.rs.getString(3).equals("C")){
                    tipoCartao = "Credito";
                } else if (conn.rs.getString(3).equals("D")){
                    tipoCartao = "Debito";
                }
                
                data.add(new CartaoM(conn.rs.getInt(1), conn.rs.getString(2), tipoCartao, conn.rs.getString(4)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        tvCartao.setItems(data);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadCartao == null){
            stageCadCartao = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Cartao.fxml", stageCadCartao);
            stageCadCartao.setOnHiding(we -> stageCadCartao = null);
            stageCadCartao.showAndWait();
        } else if (stageCadCartao.isShowing()){
            stageCadCartao.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover este Cartão?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            CartaoM cartao = tvCartao.getSelectionModel().getSelectedItem();
            int idCartao = cartao.getIdCartao();

            try {
                conn.executaSQL("DELETE FROM dbo.Cartoes WHERE idCartao = '" + idCartao + "'");

                data.removeAll(data);
                met.removeSucess("Cartão");
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
            tvCartao.setItems(data);
        } else {
            String filtro = txtConsulta.getText();

            try {
                conn.executaSQL("SELECT idCartao, descricao, tipoCartao, bandeira FROM dbo.Cartoes WHERE (idCartao LIKE '" +filtro+ "%' OR descricao LIKE '" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new CartaoM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvCartao.setItems(filterData);
        }
    }
    
}
