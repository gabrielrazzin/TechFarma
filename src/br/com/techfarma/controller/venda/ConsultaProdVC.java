/*/ CONTROLLER DA VIEW Venda - Consulta Produto /*/
package br.com.techfarma.controller.venda;

import br.com.techfarma.model.consulta.VendaM;
import controller.ConexaoController;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ConsultaProdVC implements Initializable {
    ConexaoController conn = new ConexaoController();

    @FXML
    private TableView<VendaM> tvProduto;
    @FXML
    private TextField txtDescricao;
    @FXML
    private TableColumn tcCodigo, tcDescricao, tcFabricante, tcPMC, tcPromo, tcDesconto, tcEstoque;   
    @FXML
    private Button btClose;
    
    private final ObservableList<VendaM> data = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
        
        txtDescricao.setText(VendaM.descricaoS);
        carregaTabela();
        alteraConfigTabela();
        focusTable();
        btClose.setFocusTraversable(false); // Desabilita a opção de focar no objeto.
    }
    
    private void alteraConfigTabela(){
        tvProduto.setRowFactory(tv -> new TableRow<VendaM>() {
            @Override
            public void updateItem(VendaM item, boolean empty) {
                getStylesheets().add(getClass().getResource("/br/com/techfarma/view/css/Visual.css").toExternalForm());
                
                super.updateItem(item, empty);
                if (item == null) {
                    setId("tableRow2");
                } else if (!String.valueOf(item.getValorPromocao()).equals("0.0000")) {
                    setId("tableRow");
                } else {
                   setId("tableRow2");
                }
            }
        });
        
        tcPromo.setCellFactory(param -> {
            return new TableCell<VendaM, BigDecimal>(){
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText("");
                    } else {
                        setText(item.setScale(2, RoundingMode.UP).toString());
                    }
                }
            };
        });
        
        tcPMC.setCellFactory(param -> {
            return new TableCell<VendaM, BigDecimal>(){
                @Override
                protected void updateItem(BigDecimal item, boolean empty) {
                    super.updateItem(item, empty);
                    if(empty || item == null){
                        setText("");
                    } else {
                        setText(item.setScale(2, RoundingMode.UP).toString());
                    }
                }
            };
        });
    }
    
    private void focusTable() {
        if (!txtDescricao.getText().isEmpty() && !tvProduto.getItems().isEmpty()) {
            Platform.runLater(() -> {
                tvProduto.requestFocus();
                tvProduto.getSelectionModel().select(0);
            });
        }
    }
    
    @FXML
    protected void keyTableView(KeyEvent event){
        switch(event.getCode()){
            case F6:
                txtDescricao.requestFocus();
                break;
            case ENTER:
                enviaVenda();
                VendaM.newElementS = true;
                break;
            case ESCAPE:
                closeWindow();
                VendaM.newElementS = false;
                break;
            default:
                break;
        }
    }
    
    @FXML
    protected void keyPesquisa(KeyEvent event){
        switch(event.getCode()){
            case F6:
                txtDescricao.requestFocus();
                break;
            case ENTER:
                filtraTabela();
                break;
            case ESCAPE:
                closeWindow();
                VendaM.newElementS = false;
                break;
            default:
                break;
        }
    }
    
    @FXML
    protected void closeWindow() {
        Stage stage = (Stage) btClose.getScene().getWindow();
        stage.close();
    }

    private void carregaTabela() {
        tcCodigo.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcFabricante.setCellValueFactory(new PropertyValueFactory<>("idFabricante"));
        tcPMC.setCellValueFactory(new PropertyValueFactory<>("PMC"));
        tcPromo.setCellValueFactory(new PropertyValueFactory<>("valorPromocao"));
        tcDesconto.setCellValueFactory(new PropertyValueFactory<>("descontoPerc"));

        try {
            conn.executaSQL("SELECT idProduto, descricao, idFabricante, PMC, pcoPromocao, CAST(promocao * 100 AS DECIMAL(4,2)) FROM Produto.Produto WHERE descricao LIKE '%" + VendaM.getDescricaoS() + "%'");

            while (conn.rs.next()) {
                data.add(new VendaM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getInt(3), conn.rs.getBigDecimal(4), conn.rs.getBigDecimal(5), conn.rs.getBigDecimal(6)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        tvProduto.setItems(data);
    }
    
    private void filtraTabela() {
        ObservableList filterData = FXCollections.observableArrayList();
        
        if (txtDescricao.getText().isEmpty()) {
            tvProduto.setItems(data);
        } else {
            try {
                conn.executaSQL("SELECT idProduto, descricao, idFabricante, PMC, pcoPromocao, CAST(promocao * 100 AS DECIMAL(4,2)) FROM Produto.Produto WHERE descricao LIKE '%"+  txtDescricao.getText() +"%'");

                while (conn.rs.next()) {
                    filterData.add(new VendaM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getInt(3), conn.rs.getBigDecimal(4), conn.rs.getBigDecimal(5), conn.rs.getBigDecimal(6)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvProduto.setItems(filterData);
            tvProduto.requestFocus();
            tvProduto.getSelectionModel().select(0);
        }
    }
    
    private void enviaVenda() {
        int idProduto = tvProduto.getSelectionModel().getSelectedItem().getIdProduto();
        String descricao = tvProduto.getSelectionModel().getSelectedItem().getDescricao();
        int qtdProduto = 1;
        
        BigDecimal descontoPerc;
        if (tvProduto.getSelectionModel().getSelectedItem().getDescontoPerc() == null) {
            descontoPerc = null;
        } else {
            descontoPerc = tvProduto.getSelectionModel().getSelectedItem().getDescontoPerc();
        }

        BigDecimal pcoPromocao = tvProduto.getSelectionModel().getSelectedItem().getValorPromocao();
        BigDecimal PMC = tvProduto.getSelectionModel().getSelectedItem().getPMC();
        BigDecimal pcoUnit;

        if (descontoPerc.compareTo(BigDecimal.ZERO) == 0) {
            pcoUnit = PMC;
        } else {
            BigDecimal valorDesconto = PMC.multiply(descontoPerc.divide(BigDecimal.valueOf(100)));
            pcoUnit = PMC.subtract(valorDesconto);
        }

        BigDecimal valorTotal = pcoUnit;

            VendaM.getLista().add(new VendaM(idProduto, descricao, qtdProduto, PMC.setScale(2, RoundingMode.UP).stripTrailingZeros(), pcoUnit.setScale(2, RoundingMode.UP).stripTrailingZeros(), 
                pcoPromocao.multiply(new BigDecimal(100)).setScale(2), descontoPerc, valorTotal.setScale(2, RoundingMode.UP).stripTrailingZeros()));

        closeWindow();
    }
}
