/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.controlsfx.control.ListSelectionView;

/**
 *
 * @author Gabriel
 */
public class Compras implements Initializable {
    ConexaoController conn = new ConexaoController();
    
    @FXML
    private TabPane tpCompras;
    @FXML
    private Tab tabPedCompras;
    @FXML
    private ListSelectionView<ObservableList> lsvGrupos;
    @FXML
    private ListSelectionView lsvFabricantes;
    
    //private ObservableList<CadProdGrupoView> data;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
        tpCompras.getSelectionModel().select(tabPedCompras);
        
        //data = FXCollections.observableArrayList();
        //preencheLSVGrupos();
    }
    
    /*/public void preencheLSVGrupos(){
        try {
            conn.executaSQL("SELECT descricao FROM Grupo.Grupo");
            
            while(conn.rs.next()){
                data.add(new CadProdGrupoView(conn.rs.getString("descricao")));
            }
            lsvGrupos.getSourceItems().add(data);
        } catch (SQLException e) {
            
        }
        
    }/*/
}
