/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;
import model.Produto;

/**
 *
 * @author Gabriel
 */
public class ProdutoController implements Initializable{
    ObservableList <Produto> data = FXCollections.observableArrayList();   
    ConexaoController conn = new ConexaoController();
    Produto cadProd = new Produto();
    
    ObservableList<String> listaFabricante = FXCollections.observableArrayList("Medley", "Medicamento"); 
    
    @FXML
    private TableView<Produto> tvProduto;

    @FXML
    private TableColumn <Produto, String> colIdProduto;
    
    @FXML
    private TableColumn<Produto, String> colDescricao;
    
    @FXML
    private TableColumn<Produto, String> colPcoVenda;
    
    @FXML
    private TableColumn<Produto, String> colPcoPromo;
    
    @FXML
    private ComboBox<String> cbFabricante;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) { 
        conn.Conexao();
        carregaTabela("SELECT PP.idProduto, PP.descricao, PF.PMC, PF.valorPromo FROM Produto.Produto AS PP, Produto.Financeiro AS PF");
        //cbFabricante.setItems(listaFabricante);
        preencherFabricante();
    }
    
    public void preencherFabricante(){
        try {
           String SQL = "SELECT descricao FROM Produto.Fabricante";
           PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
           ResultSet rs;
           rs = pst.executeQuery();
           
           ObservableList<String> fabricantes = FXCollections.observableArrayList();
           
           while(rs.next()){
               String descricao = rs.getString("descricao");
               fabricantes.add(descricao);
           }
          
           cbFabricante.setItems(fabricantes);
        } catch (Exception e) {
        }
    }
    
    public void carregaTabela(String sql){
        try {
           //String SQL = "SELECT PP.idProduto, PP.descricao, PF.PMC, PF.valorPromo FROM Produto.Produto AS PP, Produto.Financeiro AS PF";
           
           PreparedStatement pst = conn.getConexao().prepareStatement(sql);
           ResultSet rs;
           rs = pst.executeQuery();
           
           while(rs.next()){
               data.add(new Produto(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getBigDecimal(4)));
           }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar a busca na tabela Produto.Produto" +ex);
        }
           
        colIdProduto.setCellValueFactory(new PropertyValueFactory<>("idProduto")); 
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));    
        colPcoVenda.setCellValueFactory(new PropertyValueFactory<>("PMC")); 
        colPcoPromo.setCellValueFactory(new PropertyValueFactory<>("valorPromo"));
        
        //tvProduto.setItems(null);
        tvProduto.setItems(data);

    }
    
    public void filtroClasse(){
        String SQL = "SELECT PP.idProduto, PP.descricao, PF.PMC, PF.valorPromo, POI.fabricante FROM Produto.Produto AS PP, Produto.Financeiro AS PF, "
                   + "Produto.Informacao AS POI WHERE POI.fabricante = ?";
        
        try {
           
           tvProduto.getItems().clear();
           
           PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
           pst.setString(1, cbFabricante.getSelectionModel().getSelectedItem());
           ResultSet rs;
           rs = pst.executeQuery();
           
           while(rs.next()){
               data.add(new Produto(rs.getInt(1), rs.getString(2), rs.getBigDecimal(3), rs.getBigDecimal(4)));
           }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar a busca na tabela Produto.Produto" +ex);
        }
           
        colIdProduto.setCellValueFactory(new PropertyValueFactory<>("idProduto")); 
        colDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));    
        colPcoVenda.setCellValueFactory(new PropertyValueFactory<>("PMC")); 
        colPcoPromo.setCellValueFactory(new PropertyValueFactory<>("valorPromo"));
        
        //tvProduto.setItems(null);
        tvProduto.setItems(data);
        
        
    }
}
