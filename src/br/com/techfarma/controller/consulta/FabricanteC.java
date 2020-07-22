package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import controller.ConexaoController;
import br.com.techfarma.model.consulta.FabricanteM;
import controller.MetodosController;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FabricanteC implements Initializable, ConsultaI{
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();

    private Stage stageCadFabricante;
    @FXML
    private TableView<FabricanteM> tvFabricanteF, tvFabricanteMF, tvFabricanteMG;
    @FXML
    private TableColumn tcCodigoF, tcDescricaoF, tcTelefoneF, tcDescMaxF;
    @FXML
    private TableColumn tcCodigoMF, tcDescricaoMF, tcFabricanteMF;
    @FXML
    private TableColumn tcCodigoMG, tcDescricaoMG, tcGrupoMG, tcFabricanteMG;
    @FXML
    private TextField txtConsultaF, txtConsultaMF, txtConsultaMG;
    @FXML
    private ComboBox cbFilialF, cbFabOMF, cbFabDMF, cbFabMG, cbGrupoDMG;
    @FXML
    private Button btClose;
    @FXML
    private Tab tabFabricante, tabMudFabricante, tabMudGrupo;
    
    ObservableList<FabricanteM> data = FXCollections.observableArrayList();
    ObservableList<FabricanteM> dataMF = FXCollections.observableArrayList();
    ObservableList<FabricanteM> dataMG = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {   
        inicioPadrao();
    }

    @Override
    public void inicioPadrao() {
        conn.Conexao();
        carregaTabela();
        preencheCBFilial();
        preencheCBFabricante();
        preencheCBGrupo();
        
        tvFabricanteMF.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tvFabricanteMG.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    @FXML
    protected void keyPressed(KeyEvent event) {
        if (tabFabricante.isSelected()) {
            switch (event.getCode()) {
                case F2:
                    abrirCadastro();
                    break;
                case F6:
                    txtConsultaF.requestFocus();
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
        } else if (tabMudFabricante.isSelected()) {
            switch (event.getCode()) {
                case F2:
                    cbFabOMF.requestFocus();
                    break;
                case F4:
                    cbFabDMF.requestFocus();
                    break;
                case F6:
                    txtConsultaMF.requestFocus();
                    break;
                case F10:
                    alteraFabricante();
                    break;
                case ESCAPE:
                    fecharJanela();
                    break;
                default:
                    break;
            }
        } else if(tabMudGrupo.isSelected()){
            switch (event.getCode()) {
                case F2:
                    cbFabMG.requestFocus();
                    break;
                case F4:
                    cbGrupoDMG.requestFocus();
                    break;
                case F6:
                    txtConsultaMG.requestFocus();
                    break;
                case F10:
                    alteraGrupo();
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
        tcCodigoF.setCellValueFactory(new PropertyValueFactory<>("idFabricante"));
        tcDescricaoF.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcTelefoneF.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        tcDescMaxF.setCellValueFactory(new PropertyValueFactory<>("desconto"));
        
        try {
            conn.executaSQL("SELECT idFabricante, descricao, telefone, CAST(desconto * 100 AS DECIMAL(5,2)) FROM dbo.Fabricante");
            
            while(conn.rs.next()){
                data.add(new FabricanteM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getDouble(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvFabricanteF.setItems(data);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadFabricante == null){
            stageCadFabricante = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Fabricante.fxml", stageCadFabricante);
            stageCadFabricante.setOnHiding(we -> stageCadFabricante = null);
            stageCadFabricante.showAndWait();
        } else if (stageCadFabricante.isShowing()){
            stageCadFabricante.toFront();
        }

        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover este Fabricante?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            FabricanteM fabricante = tvFabricanteF.getSelectionModel().getSelectedItem();
            int idFabricante = fabricante.getIdFabricante();

            try {
                conn.executaSQL("DELETE FROM dbo.Fabricante WHERE idFabricante = '" + idFabricante + "'");

                data.removeAll(data);
                met.removeSucess("Fabricante");
                carregaTabela();
            } catch (Exception e) {
                System.out.println();
            }
        }
    }

    @Override
    public void filtraTabela() {
        ObservableList filterData = FXCollections.observableArrayList();
        
        if (txtConsultaF.getText().isEmpty()) {
            tvFabricanteF.setItems(data);
        } else {
            String filtro = txtConsultaF.getText();

            try {
                conn.executaSQL("SELECT idFabricante, descricao, telefone, CAST(desconto * 100 AS DECIMAL(5,2)) FROM dbo.Fabricante WHERE (idFabricante LIKE '" +filtro+ "%' OR descricao LIKE '%" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new FabricanteM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getDouble(4)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvFabricanteF.setItems(filterData);
        }
    }
    
    public void filtraTabelaMF() {
        ObservableList filterData = FXCollections.observableArrayList();
        
        if (txtConsultaMF.getText().isEmpty()) {
            tvFabricanteMF.setItems(dataMF);
        } else {
            String filtro = txtConsultaMF.getText();

            try {
                conn.executaSQL("SELECT PP.idProduto, PP.descricao, F.descricao FROM Produto.Produto AS PP, dbo.Fabricante AS F WHERE PP.idFabricante = F.idFabricante AND (PP.idProduto LIKE '%" +filtro+ "%' OR PP.descricao LIKE '%" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new FabricanteM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvFabricanteMF.setItems(filterData);
        }
    }
    
    public void filtraTabelaMG() {
        ObservableList filterData = FXCollections.observableArrayList();
        
        if (txtConsultaMG.getText().isEmpty()) {
            tvFabricanteMG.setItems(dataMG);
        } else {
            String filtro = txtConsultaMG.getText();

            try {
                conn.executaSQL("SELECT PP.idProduto, PP.descricao, G.descricao, F.descricao FROM Produto.Produto AS PP, dbo.Grupo AS G, dbo.Fabricante AS F WHERE PP.idGrupo = G.idGrupo AND PP.idFabricante = F.idFabricante AND (PP.idProduto LIKE '%" +filtro+ "%' OR PP.descricao LIKE '%" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new FabricanteM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvFabricanteMG.setItems(filterData);
        }
    }
    
    public void carregaTabelaMF() {     
        dataMF.removeAll(dataMF);
        cbFabOMF.getSelectionModel().clearSelection();
        cbFabDMF.getSelectionModel().clearSelection();
        
        tcCodigoMF.setCellValueFactory(new PropertyValueFactory<>("idProdutoMF"));
        tcDescricaoMF.setCellValueFactory(new PropertyValueFactory<>("descricaoMF"));
        tcFabricanteMF.setCellValueFactory(new PropertyValueFactory<>("fabricanteMF"));

        try {
            conn.executaSQL("SELECT PP.idProduto, PP.descricao, F.descricao FROM Produto.Produto AS PP , dbo.Fabricante AS F WHERE PP.idFabricante = F.idFabricante");
            
            while(conn.rs.next()){
                dataMF.add(new FabricanteM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvFabricanteMF.setItems(dataMF);
    }
    
    public void carregaTabelaMG() {     
        dataMG.removeAll(dataMG);
        cbFabMG.getSelectionModel().clearSelection();
        cbGrupoDMG.getSelectionModel().clearSelection();
        
        tcCodigoMG.setCellValueFactory(new PropertyValueFactory<>("idProdutoMG"));
        tcDescricaoMG.setCellValueFactory(new PropertyValueFactory<>("descricaoMG"));
        tcGrupoMG.setCellValueFactory(new PropertyValueFactory<>("grupoMG"));
        tcFabricanteMG.setCellValueFactory(new PropertyValueFactory<>("fabricanteMG"));
        
        try {
            conn.executaSQL("SELECT PP.idProduto, PP.descricao, G.descricao, F.descricao FROM Produto.Produto AS PP , dbo.Grupo AS G, dbo.Fabricante AS F WHERE PP.idGrupo = G.idGrupo AND PP.idFabricante = F.idFabricante");
            
            while(conn.rs.next()){
                dataMG.add(new FabricanteM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvFabricanteMG.setItems(dataMG);
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
        
        cbFilialF.setItems(listaFilial);
    }
    
    private void preencheCBFabricante(){
        ObservableList<String> listaFabricante = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT descricao FROM dbo.Fabricante");
            
            while(conn.rs.next()){
                listaFabricante.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        cbFabDMF.setItems(listaFabricante);
        cbFabOMF.setItems(listaFabricante);
        cbFabMG.setItems(listaFabricante);
    }
    
    private void preencheCBGrupo(){
        ObservableList<String> listaGrupo = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT descricao FROM dbo.Grupo");
            
            while(conn.rs.next()){
                listaGrupo.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        cbGrupoDMG.setItems(listaGrupo);
    }
    
    public void alteraFabricante() {
        Alert alteraConf = new Alert(Alert.AlertType.CONFIRMATION);
        alteraConf.initStyle(StageStyle.DECORATED);
        alteraConf.setTitle("Confirmação");
        alteraConf.setHeaderText("Tem certeza que deseja mudar o Fabricante deste(s) Produto(s)?");

        Optional<ButtonType> action = alteraConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            int idFabricanteDestino = 0;
            String fabricanteDestino = (String) cbFabDMF.getSelectionModel().getSelectedItem();

            try {
                conn.executaSQL("SELECT idFabricante FROM dbo.Fabricante WHERE descricao IN ('" + fabricanteDestino + "')");

                conn.rs.first();
                idFabricanteDestino = conn.rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            ObservableList<FabricanteM> data = FXCollections.observableArrayList();
            data = tvFabricanteMF.getSelectionModel().getSelectedItems();

            List<Integer> columnData = new ArrayList<>();
            for (FabricanteM gp : tvFabricanteMF.getSelectionModel().getSelectedItems()) {
                columnData.add((Integer) tcCodigoMF.getCellObservableValue(gp).getValue());
            }

            String listString = columnData.stream().map(Object::toString)
                    .collect(Collectors.joining(","));

            conn.executaSQL("UPDATE Produto.Produto SET idFabricante = '" + idFabricanteDestino + "' WHERE idProduto IN (" + listString + ")");
            
            met.updateSucess("Produto(s)");
            carregaTabelaMF();
            cbFabOMF.getSelectionModel().clearSelection();
            cbFabDMF.getSelectionModel().clearSelection();
            txtConsultaMF.clear();
        }
    }
    
    public void alteraGrupo() {
        Alert alteraConf = new Alert(Alert.AlertType.CONFIRMATION);
        alteraConf.initStyle(StageStyle.DECORATED);
        alteraConf.setTitle("Confirmação");
        alteraConf.setHeaderText("Tem certeza que deseja mudar o Grupo deste(s) Produto(s)?");

        Optional<ButtonType> action = alteraConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            int idGrupoDestino = 0;
            String grupoDestino = (String) cbGrupoDMG.getSelectionModel().getSelectedItem();

            try {
                conn.executaSQL("SELECT idGrupo FROM dbo.Grupo WHERE descricao IN ('" + grupoDestino + "')");

                conn.rs.first();
                idGrupoDestino = conn.rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            ObservableList<FabricanteM> data = FXCollections.observableArrayList();
            data = tvFabricanteMG.getSelectionModel().getSelectedItems();

            List<Integer> columnData = new ArrayList<>();
            for (FabricanteM gp : tvFabricanteMG.getSelectionModel().getSelectedItems()) {
                columnData.add((Integer) tcCodigoMG.getCellObservableValue(gp).getValue());
            }

            String listString = columnData.stream().map(Object::toString)
                    .collect(Collectors.joining(","));

            conn.executaSQL("UPDATE Produto.Produto SET idGrupo = '" + idGrupoDestino + "' WHERE idProduto IN (" + listString + ")");
            
            met.updateSucess("Produto(s)");
            carregaTabelaMG();
            cbFabMG.getSelectionModel().clearSelection();
            cbGrupoDMG.getSelectionModel().clearSelection();
            txtConsultaMG.clear();
        }
    }
}