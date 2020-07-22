package br.com.techfarma.controller.caixa;

import controller.ConexaoController;
import java.net.URL;
import java.sql.SQLException;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.VerificaVendasCaixaM;

public class VerificaVendasCaixaC implements Initializable{
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TableView<VerificaVendasCaixaM> tvVendas;
    @FXML
    private TableColumn tcIdVenda, tcVendedor, tcData, tcHora, tcQtdItens, tcValor;
    
    
    private ObservableList<VerificaVendasCaixaM> data = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
        verificaVendas();
        
        tcIdVenda.setCellValueFactory(new PropertyValueFactory<>("idVenda"));
        tcVendedor.setCellValueFactory(new PropertyValueFactory<>("vendedor"));
        tcData.setCellValueFactory(new PropertyValueFactory<>("dataVenda"));
        tcHora.setCellValueFactory(new PropertyValueFactory<>("horaVenda"));
        tcQtdItens.setCellValueFactory(new PropertyValueFactory<>("qtdItens"));
        tcValor.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
        
    }
    
    private void verificaVendas(){
        try {
            conn.executaSQL("WITH CTE_Primeira (idVenda, vendedor, dataVenda, horaVenda, concluido) AS\n"
                    + "(\n"
                    + "SELECT DISTINCT idVenda, vendedor, dataVenda, horaVenda, concluido FROM dbo.Vendas),\n"
                    + "CTE_Segunda (idVenda, quantidade, valorTotal) AS\n"
                    + "(SELECT idVenda, SUM(quantidade), SUM(valorTotal) FROM dbo.Vendas GROUP BY idVenda)\n"
                    + "SELECT C1.idVenda, C1.vendedor, C1.dataVenda, C1.horaVenda, C2.quantidade, C2.valorTotal FROM CTE_Primeira AS C1 INNER JOIN CTE_Segunda AS C2 ON C2.idVenda = C1.idVenda WHERE C1.dataVenda = '" + java.time.LocalDate.now() + "' AND C1.concluido = 'N'");
            
            while(conn.rs.next()){
                data.add(new VerificaVendasCaixaM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getDate(3), conn.rs.getTime(4), conn.rs.getInt(5), conn.rs.getDouble(6)));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        tvVendas.setItems(data);
    }
}
