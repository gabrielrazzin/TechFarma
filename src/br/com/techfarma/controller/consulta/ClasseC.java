package br.com.techfarma.controller.consulta;

import br.com.techfarma.interfaces.ConsultaI;
import br.com.techfarma.model.consulta.ClasseM;
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
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class ClasseC implements Initializable, ConsultaI{
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    
    private Stage stageCadClasse, stageAlteraClasse;
    
    @FXML
    private TableView<ClasseM> tvClasseC, tvClasseMC;
    @FXML
    private TableColumn tcCodigoC, tcDescricaoC, tcTipoC;
    @FXML
    private TableColumn tcCodigoMC, tcDescricaoMC, tcClasseMC, tcGrupoMC;
    @FXML
    private TextField txtConsultaC, txtConsultaMC;
    @FXML
    private ComboBox cbFilialC, cbClasseOMC, cbClasseDMC;
    @FXML
    private Button btIncluirC;
    @FXML
    private Tab tabClasse, tabMudClasse;
    
    private ObservableList<ClasseM> data = FXCollections.observableArrayList();
    private ObservableList<ClasseM> dataMC = FXCollections.observableArrayList();
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicioPadrao();
    }
    
    @Override
    public void inicioPadrao() {
        conn.Conexao();
        carregaTabela();
        preencheCBFilial();
        preencheCBClasse();
        
        tvClasseMC.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
    
    @FXML
    protected void keyPressed(KeyEvent event) {
        if (tabClasse.isSelected()) {
            switch (event.getCode()) {
                case F2:
                    abrirCadastro();
                    break;
                case F6:
                    txtConsultaC.requestFocus();
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
        } else if (tabMudClasse.isSelected()) {
            switch (event.getCode()) {
                case F2:
                    cbClasseOMC.requestFocus();
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
    
    @FXML
    protected void keyPressedC(KeyEvent event) {
        switch (event.getCode()) {
            case F2:
                abrirCadastro();
                break;
            case F6:
                txtConsultaC.requestFocus();
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
    
    @FXML
    protected void keyPressedMC(KeyEvent event) {
        switch (event.getCode()) {
            case F2:
                cbClasseOMC.requestFocus();
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
    
    @FXML
    protected void keyTableEventC(KeyEvent event){
        switch (event.getCode()) {
            case F2:
               abrirCadastro();
            break;
            case F6:
                txtConsultaC.requestFocus();
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
    
    @FXML
    protected void keyTableEventMC(KeyEvent event){
        switch (event.getCode()) {
            case F2:
                cbClasseOMC.requestFocus();
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

    @Override
    public void fecharJanela() {
        Stage stage = (Stage) btIncluirC.getScene().getWindow();
        stage.close();
    }

    @Override
    public void carregaTabela() {
        tcCodigoC.setCellValueFactory(new PropertyValueFactory<>("idClasse"));
        tcDescricaoC.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcTipoC.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        try {
            conn.executaSQL("SELECT idClasse, descricao, tipo FROM dbo.Classe");
            
            while(conn.rs.next()){
                data.add(new ClasseM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvClasseC.setItems(data);
    }
    
    public void carregaTabelaMC() {
        dataMC.removeAll(dataMC);
        cbClasseOMC.getSelectionModel().clearSelection();
        cbClasseDMC.getSelectionModel().clearSelection();
        txtConsultaMC.clear();
        
        tcCodigoMC.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        tcDescricaoMC.setCellValueFactory(new PropertyValueFactory<>("produtoMC"));
        tcClasseMC.setCellValueFactory(new PropertyValueFactory<>("classeMC"));
        tcGrupoMC.setCellValueFactory(new PropertyValueFactory<>("grupoMC"));
        
        try {
            conn.executaSQL("SELECT PP.idProduto, PP.descricao, C.descricao, G.descricao FROM Produto.Produto AS PP, dbo.Grupo AS G, dbo.Classe AS C WHERE PP.idGrupo = G.idGrupo AND PP.idClasse = C.idClasse");

            while (conn.rs.next()) {
                dataMC.add(new ClasseM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        tvClasseMC.setItems(dataMC);
    }

    @Override
    public void abrirCadastro() {
        if(stageCadClasse == null){
            stageCadClasse = met.abrirTelaEspera("/br/com/techfarma/view/cadastro/Classe.fxml", stageCadClasse);
            stageCadClasse.setOnHiding(we -> stageCadClasse = null);
            stageCadClasse.showAndWait();
        } else if (stageCadClasse.isShowing()){
            stageCadClasse.toFront();
        }
        
        data.removeAll(data);
        carregaTabela();
    }
    
    @Override
    public void removeItem() {
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Confirmação");
        removeConf.setHeaderText("Tem certeza que deseja remover esta Classe?");

        Optional<ButtonType> action = removeConf.showAndWait();

        if (action.get() == ButtonType.OK) {
            ClasseM classe = tvClasseC.getSelectionModel().getSelectedItem();
            int idClasse = classe.getIdClasse();

            try {
                conn.executaSQL("DELETE FROM dbo.Classe WHERE idClasse = '" + idClasse + "'");

                data.removeAll(data);
                met.removeSucess("Classe");
                carregaTabela();
            } catch (Exception e) {
                System.out.println();
            }
        }
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
        
        cbFilialC.setItems(listaFilial);
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
        
        cbClasseOMC.setItems(listaClasse);
        cbClasseDMC.setItems(listaClasse);
    }
    
    @Override
    public void filtraTabela() {
        ObservableList filterData = FXCollections.observableArrayList();
        
        if (txtConsultaC.getText().isEmpty()) {
            tvClasseC.setItems(data);
        } else {
            String filtro = txtConsultaC.getText();

            try {
                conn.executaSQL("SELECT idClasse, descricao, tipo FROM dbo.Classe WHERE (idClasse LIKE '" +filtro+ "%' OR descricao LIKE '" +filtro+ "%')");

                while (conn.rs.next()) {
                    filterData.add(new ClasseM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvClasseC.setItems(filterData);
        }
    }
    
    public void filtraTabelaMC() {
        ObservableList filterData = FXCollections.observableArrayList();

        if (txtConsultaMC.getText().isEmpty()) {
            tvClasseMC.setItems(dataMC);
        } else {
            String filtro = txtConsultaMC.getText();

            try {
                conn.executaSQL("SELECT PP.idProduto, PP.descricao, C.descricao, G.descricao FROM Produto.Produto AS PP, dbo.Classe AS C, dbo.Grupo AS G WHERE PP.idGrupo = G.idGrupo AND PP.idClasse = C.idClasse AND (PP.idProduto LIKE '%" + filtro + "%' OR PP.descricao LIKE '%" + filtro + "%')");

                while (conn.rs.next()) {
                    filterData.add(new ClasseM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }

            tvClasseMC.setItems(filterData);
        }
    }
    
    public void filtraTabelaMCCB() {
        ObservableList filterData = FXCollections.observableArrayList();
        String filtro = cbClasseOMC.getSelectionModel().getSelectedItem().toString();

        try {
            conn.executaSQL("SELECT PP.idProduto, PP.descricao, C.descricao, G.descricao FROM Produto.Produto AS PP, dbo.Classe AS C, dbo.Grupo AS G WHERE PP.idGrupo = G.idGrupo AND PP.idClasse = C.idClasse AND (C.descricao LIKE '%" + filtro + "%')");

            while (conn.rs.next()) {
                filterData.add(new ClasseM(conn.rs.getInt(1), conn.rs.getString(2), conn.rs.getString(3), conn.rs.getString(4)));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        
        tvClasseMC.setItems(filterData);
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

            ObservableList<ClasseM> data = FXCollections.observableArrayList();
            data = tvClasseMC.getSelectionModel().getSelectedItems();

            List<Integer> columnData = new ArrayList<>();
            for (ClasseM gp : tvClasseMC.getSelectionModel().getSelectedItems()) {
                columnData.add((Integer) tcCodigoMC.getCellObservableValue(gp).getValue());
            }

            String listString = columnData.stream().map(Object::toString)
                    .collect(Collectors.joining(","));

            conn.executaSQL("UPDATE Produto.Produto SET idClasse = '" + idClasseDestino + "' WHERE idProduto IN (" + listString + ")");
            
            met.updateSucess("Produto(s)");
            carregaTabelaMC();
            cbClasseOMC.getSelectionModel().clearSelection();
            cbClasseDMC.getSelectionModel().clearSelection();
            txtConsultaMC.clear();
        }
    }
    
    public void altera(){
        br.com.techfarma.model.cadastro.ClasseM.idClasseA = tvClasseC.getSelectionModel().getSelectedItem().getIdClasse();

        if(stageAlteraClasse == null){
            stageAlteraClasse = met.abrirTelaEspera("/br/com/techfarma/view/altera/Classe.fxml", stageAlteraClasse);
            stageAlteraClasse.setOnHiding(we -> stageAlteraClasse = null);
            stageAlteraClasse.showAndWait();
        } else if (stageAlteraClasse.isShowing()){
            stageAlteraClasse.toFront();
        }
       
        data.removeAll(data);
        carregaTabela();
    }
}