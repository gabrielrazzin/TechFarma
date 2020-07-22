/*/ CONTROLLER DA VIEW Venda - Consulta Produto /*/
package br.com.techfarma.controller.venda;

import br.com.techfarma.model.consulta.VendaM;
import controller.ConexaoController;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
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
        alteraConfigTabela();
        carregaTabela();
        txtDescricao.setText(VendaM.descricaoS);
    }
    
    private void alteraConfigTabela(){
        tvProduto.setRowFactory(tv -> new TableRow<VendaM>() {
            @Override
            public void updateItem(VendaM item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setStyle("");
                } else if (!String.valueOf(item.getPcoPromocao()).equals("0.0000")) {
                    getStylesheets().add(getClass().getResource("/br/com/techfarma/view/css/Visual.css").toExternalForm());
                    setId("tableRow");
                } else {
                    setStyle("");
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
    
    @FXML
    protected void keyTableView(KeyEvent event){
        switch(event.getCode()){
            case ENTER:
                enviaVenda();
                break;
            case ESCAPE:
                closeWindow();
                break;
            default:
                break;
        }
    }
    
    @FXML
    protected void keyPesquisa(KeyEvent event){
        switch(event.getCode()){
            case ENTER:
                filtraTabela();
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
        tcPromo.setCellValueFactory(new PropertyValueFactory<>("pcoPromocao"));
        tcDesconto.setCellValueFactory(new PropertyValueFactory<>("desconto"));

        try {
            conn.executaSQL("SELECT idProduto, descricao, idFabricante, PMC, pcoPromocao, desconto FROM Produto.Produto WHERE descricao LIKE '%" + VendaM.getDescricaoS() + "%'");

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
                conn.executaSQL("SELECT idProduto, descricao, idFabricante, PMC, pcoPromocao, desconto FROM Produto.Produto WHERE descricao LIKE '%"+  txtDescricao.getText() +"%'");

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
        
        BigDecimal desconto;
        if (tvProduto.getSelectionModel().getSelectedItem().getTxDesconto() == null) {
            desconto = new BigDecimal(0);
        } else {
            desconto = tvProduto.getSelectionModel().getSelectedItem().getTxDesconto();
        }

        BigDecimal pcoPromocao = tvProduto.getSelectionModel().getSelectedItem().getPcoPromocao();
        BigDecimal PMC = tvProduto.getSelectionModel().getSelectedItem().getPMC();
        BigDecimal pcoUnit;

        if (desconto.compareTo(BigDecimal.ZERO) == 0) {
            pcoUnit = PMC;
        } else {
            BigDecimal valorDesconto = PMC.multiply(desconto);
            pcoUnit = PMC.subtract(valorDesconto);
        }

        BigDecimal valorTotal = pcoUnit;

        VendaM.getLista().add(new VendaM(idProduto, descricao, qtdProduto, desconto.multiply(new BigDecimal(100)).setScale(2), pcoPromocao.multiply(new BigDecimal(100)).setScale(2),
                PMC.setScale(2, RoundingMode.UP).stripTrailingZeros(), pcoUnit.setScale(2, RoundingMode.UP).stripTrailingZeros(), valorTotal.setScale(2, RoundingMode.UP).stripTrailingZeros()));

        closeWindow();
    }
}
