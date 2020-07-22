package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.GrupoM;
import controller.ConexaoController;
import controller.MetodosController;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GrupoC implements Initializable, ConsultaI {

    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();

    private Stage stageCadGrupo;
    @FXML
    private TableView<GrupoM> tvGrupoG, tvGrupoMG, tvGrupoMC;
    @FXML
    private TableColumn tcCodigoG, tcDescricaoG, tcComVendaG, tcDescMaxG;
    @FXML
    private TableColumn tcCodigoMG, tcDescricaoMG, tcClasseMG, tcGrupoMG;
    @FXML
    private TableColumn tcCodigoMC, tcDescricaoMC, tcClasseMC, tcGrupoMC;
    @FXML
    private TextField txtConsultaG, txtConsultaMG, txtConsultaMC;
    @FXML
    private ComboBox cbFilial, cbGrupoOMG, cbGrupoDMG, cbGrupoOMC, cbClasseDMC;      
    @FXML
    private Button btIncluir;
    @FXML
    private Tab tabGrupo, tabMudGrupo, tabMudClasse;

    private ObservableList<GrupoM> data = FXCollections.observableArrayList();
    private ObservableList<GrupoM> dataMG = FXCollections.observableArrayList();
    private ObservableList<GrupoM> dataMC = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();

    }

    @Override
    public void inicioPadrao() {
        conn.Conexao();
        carregaTabela();
        preencheCBFilial();
        preencheCBGrupo();
        preencheCBClasse();

        tvGrupoMG.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        tvGrupoMC.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    @FXML
    void keyPressed(KeyEvent event) {
        if (tabGrupo.isSelected()) {
            switch (event.getCode()) {
                case F2:
                    abrirCadastro();
                    break;
                case F6:
                    txtConsultaG.requestFocus();
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
        } else if (tabMudGrupo.isSelected()) {
            switch (event.getCode()) {
                case F2:
                    cbGrupoOMG.requestFocus();
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
        } else if (tabMudClasse.isSelected()) {
            switch (event.getCode()) {
                case F2:
                    cbGrupoOMC.requestFocus();
                    break;
                case F4:
                    cbClasseDMC.requestFocus();
                    break;
                case F6:
                    txtConsultaMC.requestFocus();
                    break;
                case F10:
                    alteraClasse();
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
        Stage stage = (Stage) btIncluir.getScene().getWindow();
        stage.close();
    }

    @Override
    public void carregaTabela() {
        tcCodigoG.setCellValueFactory(new PropertyValueFactory<>("idGrupo"));
        tcDescricaoG.setCellValueFactory(new PropertyValueFactory<>("descricao")); 
        tcComVendaG.setCellValueFactory(new PropertyValueFactory<>("taxComissao"));
        tcDescMaxG.setCellValueFactory(new PropertyValueFactory<>("descMax"));

        try {
            conn.executaSQL("SELECT idGrupo, descricao, CAST (taxComissao * 100 AS DECIMAL(4,2)), CAST (descMax * 100 AS DECIMAL(4,2)) FROM dbo.Grupo");

            while (conn.rs.next()) {
                data.add(new GrupoM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getDouble(3), conn.rs.getDouble(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        tvGrupoG.setItems(data);
    }

    public void carregaTabelaMG() {
        dataMG.removeAll(dataMG);
        cbGrupoOMG.getSelectionModel().clearSelection();
        cbGrupoDMG.getSelectionModel().clearSelection();
        txtConsultaMG.clear();
        
        tcCodigoMG.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        tcDescricaoMG.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcClasseMG.setCellValueFactory(new PropertyValueFactory<>("classeDescricao"));
        tcGrupoMG.setCellValueFactory(new PropertyValueFactory<>("grupoDescricao"));

        try {
            conn.executaSQL("SELECT PP.idProduto, PP.descricao, C.descricao, G.descricao FROM Produto.Produto AS PP, dbo.Classe AS C, dbo.Grupo AS G WHERE PP.idGrupo = G.idGrupo AND PP.idClasse = C.idClasse");

            while (conn.rs.next()) {
                dataMG.add(new GrupoM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        tvGrupoMG.setItems(dataMG);
    }
    
    public void carregaTabelaMC() {
        dataMC.removeAll(dataMC);
        cbGrupoOMC.getSelectionModel().clearSelection();
        cbClasseDMC.getSelectionModel().clearSelection();
        txtConsultaMC.clear();
        
        tcCodigoMC.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        tcDescricaoMC.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcClasseMC.setCellValueFactory(new PropertyValueFactory<>("classeDescricao"));
        tcGrupoMC.setCellValueFactory(new PropertyValueFactory<>("grupoDescricao"));

        try {
            conn.executaSQL("SELECT PP.idProduto, PP.descricao, C.descricao, G.descricao FROM Produto.Produto AS PP, dbo.Classe AS C, dbo.Grupo AS G WHERE PP.idGrupo = G.idGrupo AND PP.idClasse = C.idClasse");

            while (conn.rs.next()) {
                dataMC.add(new GrupoM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        tvGrupoMC.setItems(dataMC);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadGrupo == null){
            stageCadGrupo = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Grupo.fxml", stageCadGrupo);
            stageCadGrupo.setOnHiding(we -> stageCadGrupo = null);
            stageCadGrupo.showAndWait();
        } else if (stageCadGrupo.isShowing()){
            stageCadGrupo.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }

    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover este Grupo?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            GrupoM grupo = tvGrupoG.getSelectionModel().getSelectedItem();
            int idGrupo = grupo.getIdGrupo();

            try {
                conn.executaSQL("DELETE FROM dbo.Grupo WHERE idGrupo = '" + idGrupo + "'");

                data.removeAll(data);
                met.removeSucess("Grupo");
                carregaTabela();
            } catch (Exception e) {
                System.out.println();
            }
        }
    }

    private void preencheCBFilial() {
        ObservableList<String> listaFilial = FXCollections.observableArrayList();

        try {
            conn.executaSQL("SELECT nome FROM dbo.Filial");

            while (conn.rs.next()) {
                listaFilial.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        cbFilial.setItems(listaFilial);
    }

    private void preencheCBGrupo() {
        ObservableList<String> listaDescricao = FXCollections.observableArrayList();

        try {
            conn.executaSQL("SELECT descricao FROM dbo.Grupo");

            while (conn.rs.next()) {
                listaDescricao.add(conn.rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }

        cbGrupoOMG.setItems(listaDescricao);
        cbGrupoDMG.setItems(listaDescricao);
        cbGrupoOMC.setItems(listaDescricao);
    }
    
    private void preencheCBClasse(){
        ObservableList<String> listaClasse = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT descricao FROM dbo.Classe");
            
            while(conn.rs.next()){
                listaClasse.add(conn.rs.getString(1));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        cbClasseDMC.setItems(listaClasse);
    }

    @Override
    public void filtraTabela() {
        ObservableList filterData = FXCollections.observableArrayList();

        if (txtConsultaG.getText().isEmpty()) {
            tvGrupoG.setItems(data);
        } else {
            String filtro = txtConsultaG.getText();

            try {
                conn.executaSQL("SELECT idGrupo, descricao, taxComissao, descMax FROM dbo.Grupo WHERE (idGrupo LIKE '" + filtro + "%' OR descricao LIKE '" + filtro + "%')");

                while (conn.rs.next()) {
                    filterData.add(new GrupoM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getDouble(3), conn.rs.getDouble(4)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvGrupoG.setItems(filterData);
        }
    }

    public void filtraTabelaMG() {
        ObservableList filterData = FXCollections.observableArrayList();

        if (txtConsultaMG.getText().isEmpty()) {
            tvGrupoMG.setItems(dataMG);
        } else {
            String filtro = txtConsultaMG.getText();

            try {
                conn.executaSQL("SELECT PP.idProduto, PP.descricao, C.descricao, G.descricao FROM Produto.Produto AS PP, dbo.Classe AS C, dbo.Grupo AS G WHERE PP.idGrupo = G.idGrupo AND PP.idClasse = C.idClasse AND (PP.idProduto LIKE '%" + filtro + "%' OR PP.descricao LIKE '%" + filtro + "%')");

                while (conn.rs.next()) {
                    filterData.add(new GrupoM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvGrupoMG.setItems(filterData);
        }
    }
    
    public void filtraTabelaMC(){
        ObservableList filterData = FXCollections.observableArrayList();

        if (txtConsultaMC.getText().isEmpty()) {
            tvGrupoMC.setItems(dataMC);
        } else {
            String filtro = txtConsultaMC.getText();

            try {
                conn.executaSQL("SELECT PP.idProduto, PP.descricao, C.descricao, G.descricao FROM Produto.Produto AS PP, dbo.Classe AS C, dbo.Grupo AS G WHERE PP.idGrupo = G.idGrupo AND PP.idClasse = C.idClasse AND (PP.idProduto LIKE '%" + filtro + "%' OR PP.descricao LIKE '%" + filtro + "%')");

                while (conn.rs.next()) {
                    filterData.add(new GrupoM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvGrupoMG.setItems(filterData);
        }
    }
    
    public void filtraTabelaMGCB() {
        ObservableList filterData = FXCollections.observableArrayList();
        String filtro = cbGrupoOMG.getSelectionModel().getSelectedItem().toString();

        try {
            conn.executaSQL("SELECT PP.idProduto, PP.descricao, C.descricao, G.descricao FROM Produto.Produto AS PP, dbo.Classe AS C, dbo.Grupo AS G WHERE PP.idGrupo = G.idGrupo AND PP.idClasse = C.idClasse AND (G.descricao LIKE '%" + filtro + "%')");

            while (conn.rs.next()) {
                filterData.add(new GrupoM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        tvGrupoMG.setItems(filterData);
    }
    
    public void filtraTabelaMCCB() {
        ObservableList filterData = FXCollections.observableArrayList();

        String filtro = cbGrupoOMC.getSelectionModel().getSelectedItem().toString();

        try {
            conn.executaSQL("SELECT PP.idProduto, PP.descricao, C.descricao, G.descricao FROM Produto.Produto AS PP, dbo.Classe AS C, dbo.Grupo AS G WHERE PP.idGrupo = G.idGrupo AND PP.idClasse = C.idClasse AND (G.descricao LIKE '%" + filtro + "%')");

            while (conn.rs.next()) {
                filterData.add(new GrupoM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        tvGrupoMC.setItems(filterData);
    }
    
    public void alteraGrupo() {
        Alert alteraConf = new Alert(Alert.AlertType.CONFIRMATION);
        alteraConf.initStyle(StageStyle.DECORATED);
        alteraConf.setTitle("Confirmação");
        alteraConf.setHeaderText("Tem certeza que deseja mudar o Grupo deste(s) Produto(s)?");

        Optional<ButtonType> action = alteraConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            int idGrupoOrigem = 0;
            int idGrupoDestino = 0;
            String grupoOrigem = (String) cbGrupoOMG.getSelectionModel().getSelectedItem();
            String grupoDestino = (String) cbGrupoDMG.getSelectionModel().getSelectedItem();

            try {
                conn.executaSQL("SELECT idGrupo FROM dbo.Grupo WHERE descricao IN ('" + grupoOrigem + "', '" + grupoDestino + "')");

                conn.rs.first();
                idGrupoOrigem = conn.rs.getInt(1);
                conn.rs.last();
                idGrupoDestino = conn.rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            ObservableList<GrupoM> data = FXCollections.observableArrayList();
            data = tvGrupoMG.getSelectionModel().getSelectedItems();

            List<Integer> columnData = new ArrayList<>();
            for (GrupoM gp : tvGrupoMG.getSelectionModel().getSelectedItems()) {
                columnData.add((Integer) tcCodigoMG.getCellObservableValue(gp).getValue());
            }

            String listString = columnData.stream().map(Object::toString)
                    .collect(Collectors.joining(","));

            conn.executaSQL("UPDATE Produto.Produto SET idGrupo = '" + idGrupoDestino + "' WHERE idProduto IN (" + listString + ")");
            
            met.updateSucess("Produto(s)");
            carregaTabelaMG();
            cbGrupoOMG.getSelectionModel().clearSelection();
            cbGrupoDMG.getSelectionModel().clearSelection();
            txtConsultaMG.clear();
        }
    }
    
    public void alteraClasse() {
        Alert alteraConf = new Alert(Alert.AlertType.CONFIRMATION);
        alteraConf.initStyle(StageStyle.DECORATED);
        alteraConf.setTitle("Confirmação");
        alteraConf.setHeaderText("Tem certeza que deseja mudar a Classe deste(s) Produto(s)?");

        Optional<ButtonType> action = alteraConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            int idClasseDestino = 0;
            String classeDestino = (String) cbClasseDMC.getSelectionModel().getSelectedItem();

            try {
                conn.executaSQL("SELECT idClasse FROM dbo.Classe WHERE descricao IN ('" + classeDestino + "')");

                conn.rs.first();
                idClasseDestino = conn.rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            ObservableList<GrupoM> data = FXCollections.observableArrayList();
            data = tvGrupoMC.getSelectionModel().getSelectedItems();

            List<Integer> columnData = new ArrayList<>();
            for (GrupoM gp : tvGrupoMC.getSelectionModel().getSelectedItems()) {
                columnData.add((Integer) tcCodigoMC.getCellObservableValue(gp).getValue());
            }

            String listString = columnData.stream().map(Object::toString)
                    .collect(Collectors.joining(","));

            conn.executaSQL("UPDATE Produto.Produto SET idClasse = '" + idClasseDestino + "' WHERE idProduto IN (" + listString + ")");
            
            met.updateSucess("Produto(s)");
            carregaTabelaMC();
            cbGrupoOMC.getSelectionModel().clearSelection();
            cbClasseDMC.getSelectionModel().clearSelection();
            txtConsultaMC.clear();
        }
    }
}