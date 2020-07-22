package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.CFOPM;
import controller.ConexaoController;
import controller.MetodosController;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class CFOPC implements Initializable, ConsultaI{
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    
    private Stage stageCadCFOP;
    
    @FXML
    private TableView<CFOPM> tvCFOP;
    @FXML
    private TableColumn tcCodigo, tcDescricao, tcEquivalente;
    @FXML
    private TextField txtConsulta;
    @FXML
    private Button btClose;
    
    private ObservableList<CFOPM> data = FXCollections.observableArrayList();
    
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
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idCFOP"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcEquivalente.setCellValueFactory(new PropertyValueFactory<>("CFOPEquiv"));
        
        try {
            conn.executaSQL("SELECT idCFOP, descricao, CFOPEquiv FROM dbo.CFOP");
            
            while(conn.rs.next()){
                data.add(new CFOPM(conn.rs.getString(1), conn.rs.getString(2), conn.rs.getString(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvCFOP.setItems(data);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadCFOP == null){
            stageCadCFOP = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/CFOP.fxml", stageCadCFOP);
            stageCadCFOP.setOnHiding(we -> stageCadCFOP = null);
            stageCadCFOP.showAndWait();
        } else if (stageCadCFOP.isShowing()){
            stageCadCFOP.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void filtraTabela() {
        ObservableList filterData = FXCollections.observableArrayList();
        
        if (txtConsulta.getText().isEmpty()) {
            tvCFOP.setItems(data);
        } else {
            String filtro = txtConsulta.getText();

            try {
                conn.executaSQL("SELECT idCFOP, descricao, CFOPEquiv FROM dbo.CFOP WHERE (idCFOP LIKE '" +filtro+ "%' OR descricao LIKE '" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new CFOPM(conn.rs.getString(1), conn.rs.getString(2), conn.rs.getString(3)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvCFOP.setItems(filterData);
        }
    }
}
