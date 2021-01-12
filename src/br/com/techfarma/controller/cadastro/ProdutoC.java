package br.com.techfarma.controller.cadastro;

import br.com.techfarma.interfaces.CadastroI;
import br.com.techfarma.model.cadastro.ProdutoM;
import br.com.techfarma.model.consulta.ClasseM;
import br.com.techfarma.model.consulta.FabricanteM;
import br.com.techfarma.model.consulta.GrupoM;
import br.com.techfarma.model.consulta.PrincipioAtivoM;
import com.jfoenix.controls.JFXToggleButton;
import controller.ConexaoController;
import controller.MetodosController;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class ProdutoC implements Initializable, CadastroI{   
    ConexaoController conn = new ConexaoController(); 
    
    private Stage stageCodBarras, stageFabricante, stageGrupo, stageClasse, stagePrincipio;
    @FXML
    public TextField txtFabricante;
    @FXML
    private TextField txtCodBarras, txtDescricao, txtClasse, txtGrupo, txtPrincipio, txtQtdCompra, txtQtdVenda, txtCustoUnit, txtMarkUp, txtPMC;
    @FXML
    private TextField txtPromo, txtPcoPromo, txtDescMax, txtComissao;
    @FXML
    private TextField txtCFOP, txtNCM, txtCEST, txtIPI, txtCST, txtAliquota, txtCSTEntrada, txtCSTSaida, txtAliqPIS, txtAliqCOFINS, txtNatReceita;
    @FXML
    private Button btCancelar;
    @FXML
    private ComboBox<String> cbEstado,cbUnidVenda, cbTipoPreco, cbGrupoCompra, cbLista, cbIAT, cbIPPT;
    @FXML
    private ComboBox<String> cbOrigem, cbSitTribICMS, cbSitTribPIS;
    @FXML
    private JFXToggleButton tbControlado, tbContinuo, tbPIS, tbPedElet;
    
    ObservableList<String> listaEstado = FXCollections.observableArrayList("Ativo", "Inativo");
    ObservableList<String> listaTipoPreco = FXCollections.observableArrayList("Monitorado", "Liberado");
    ObservableList<String> listaUniVenda = FXCollections.observableArrayList("Unidade - UN", "Pacote - PC", "Display - DP", "Fardo - FD", "Caixa - CX");
    ObservableList<String> listaIPPT = FXCollections.observableArrayList("Produção Própria", "Produzido por Terceiros");
    ObservableList<String> listaIAT = FXCollections.observableArrayList("Truncamento", "Arrendondamento");
    ObservableList<String> listaOrigem = FXCollections.observableArrayList("0 - Nacional, exceto as indicadas nos códigos 3 a 5", "1 - Estrangeira - Importação direta, exceto a indicada no código 6",
            "2 - Estrangeira - Adquirida no mercado interno, exceto a indicada no código 7", 
            "3 - Nacional, mercadoria ou bem com Conteúdo de Importação superior a 40%",
            "4 - Nacional, cuja produção tenha sido feita em conformidade com os processos\n"
            + "produtivos básicos", "5 - Nacional, mercadoria ou bem com Conteúdo de Importação inferior ou igual a 40%", "6 - Estrangeira - Importação direta, sem similar nacional, constante em lista de Resolução\n"
            + "CAMEX", "7 - Estrangeira - Adquirida no mercado interno, sem similar nacional, constante em lista\n"
            + "de Resolução CAMEX");
    ObservableList<String> listaSitTribICMS = FXCollections.observableArrayList("Tributado", "Substituição Tributária", "Isento", "Não Tributado", "Isento ou Não Tributado", "Diferimento", "Suspensão", "Outros");
    ObservableList<String> listaSitTribPIS = FXCollections.observableArrayList("Tributado", "Monofásico", "Substituição Tributária", "Alíquota Zero", "Isento", "Sem Incidência", "Suspensão", "Outros");
    
    private String status, undVenda, tipoPreco, grupoCompra, IAT, IPPT, controlado, usoContinuo, pisCOFINS, pedEletronico;
    private int idProduto, idGrupoCompra, idFabricante, idGrupo, idClasse, idPrincipio, idLista;
    private String origem, situTribICMS, situTribPIS, segmento;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       inicioPadrao();
    }
    
    @Override
    public void inicioPadrao() {
        conn.Conexao();

        cbEstado.setItems(listaEstado);
        cbTipoPreco.setItems(listaTipoPreco);
        cbUnidVenda.setItems(listaUniVenda);
        cbIPPT.setItems(listaIPPT);
        cbIAT.setItems(listaIAT);
        cbOrigem.setItems(listaOrigem);
        cbSitTribICMS.setItems(listaSitTribICMS);
        cbSitTribPIS.setItems(listaSitTribPIS);
        
        preencheCBMarkUp();
        preencheCBGrupoC();

        cbEstado.getSelectionModel().selectFirst();
        cbUnidVenda.getSelectionModel().selectFirst();
        cbTipoPreco.getSelectionModel().selectFirst();
        cbIAT.getSelectionModel().select(1);
        cbIPPT.getSelectionModel().select(1);
        cbOrigem.getSelectionModel().selectFirst();
        cbSitTribICMS.getSelectionModel().select(1);
        cbSitTribPIS.getSelectionModel().select(2);
        
        txtQtdCompra.setText("1");
        txtQtdVenda.setText("1");
        txtCustoUnit.setText("0.00");
        txtMarkUp.setText("0.00");
        txtPMC.setText("0.00");
        txtPromo.setText("0.00");
        txtPcoPromo.setText("0.00");
        txtDescMax.setText("0.00");
        txtComissao.setText("0.00");
        
        
        txtCFOP.setText("5405");
        txtNCM.setText("30049099");
        txtCEST.setText("1300400");  
        txtCST.setText("500");
        txtAliquota.setText("0");  
        txtCSTEntrada.setText("0");
        txtCSTSaida.setText("0");
        txtAliqPIS.setText("0");
        txtAliqCOFINS.setText("0");
        txtNatReceita.setText("0");
        
        tbPedElet.setSelected(true);
        tbPIS.setSelected(true);
        
        moveCursor();
    }
    
    @Override
    public void fecharJanela(){
        Stage stage = (Stage) btCancelar.getScene().getWindow();
        stage.close();
    }
    
    @FXML
    void keyPressed(KeyEvent event){
        switch (event.getCode()) {
            case F4:
                limparCampos();
            break;
            case F10:
                inserirBanco();
            break;
            case ESCAPE:
                fecharJanela();
            default:
                break;
            }
    }

    public void abreCodBarras() throws IOException {
        MetodosController met = new MetodosController();
        met.abrirTelaEspera("/br/com/techfarma/view/cadastro/CadProduto - Cod Barra.fxml", stageCodBarras);
    }
    
    public void abreFabricante() throws IOException {
        MetodosController met = new MetodosController();
        met.abrirTelaEspera("/br/com/techfarma/view/cadastro/CadProduto - Fabricante.fxml", stageFabricante);
        atualizaFabricante();
    }

    public void abreGrupo() throws IOException {
        MetodosController met = new MetodosController();
        met.abrirTelaEspera("/br/com/techfarma/view/cadastro/CadProduto - Grupo.fxml", stageGrupo);
        atualizaGrupo();
    }
    
    public void abreClasse() throws IOException {
        MetodosController met = new MetodosController();
        met.abrirTelaEspera("/br/com/techfarma/view/cadastro/CadProduto - Classe.fxml", stageClasse);
        atualizaClasse();
    }
    
    public void abrePrincipio() throws IOException {
        MetodosController met = new MetodosController();
        met.abrirTelaEspera("/br/com/techfarma/view/cadastro/CadProduto - Principio.fxml", stagePrincipio);
        atualizaPrincipio();
    }
    
    private void preencheCBMarkUp(){
        ObservableList<String> listaDescricao = FXCollections.observableArrayList();
        ObservableList<Double> listaMarkUp = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT descricao, markUP FROM dbo.ListaMarkUp");
            
            while(conn.rs.next()){
                listaDescricao.add(conn.rs.getString(1) + " - " + conn.rs.getDouble(2)*100);
                listaMarkUp.add(conn.rs.getDouble(2));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        cbLista.setItems(listaDescricao);
    }
    
    private void preencheCBGrupoC(){
        ObservableList<String> listaDescricao = FXCollections.observableArrayList();
        
        try {
            conn.executaSQL("SELECT descricao FROM dbo.GrupoCompra");
            
            while(conn.rs.next()){
                listaDescricao.add(conn.rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        
        cbGrupoCompra.setItems(listaDescricao);
    }

    public void calculaPMC(){     
        double custoProduto = Double.parseDouble(txtCustoUnit.getText().replaceAll(",", "."));
        double markUp = Double.parseDouble(txtMarkUp.getText().replaceAll(",", "."));        
        double PMC = (custoProduto * (markUp/100)) + custoProduto;

        BigDecimal PMCfinal = new BigDecimal(PMC).setScale(2, BigDecimal.ROUND_HALF_UP);
        txtPMC.setText(PMCfinal.toString());
        txtPMC.requestFocus();
    }
    
    public void calculaPromo(){
        String promo = txtPromo.getText().replaceAll(",", ".");
        
        double valorPromo = Double.parseDouble(promo)/100;
        double PMC = Double.parseDouble(txtPMC.getText());
        double valorFinal = PMC - (PMC * valorPromo);
        
        BigDecimal promoFinal = new BigDecimal(valorFinal).setScale(2, BigDecimal.ROUND_HALF_UP);
        txtPcoPromo.setText(promoFinal.toString());
        txtPcoPromo.requestFocus();
    }
    
    private void verificaTB(){
        if(tbControlado.isSelected()){
            controlado = "S";
        } else {
            controlado = "N";
        }
        
        if(tbContinuo.isSelected()){
            usoContinuo = "S";
        } else {
            usoContinuo = "N";
        }
        
        if(tbPIS.isSelected()){
            pisCOFINS = "S";
        } else {
            pisCOFINS = "N";
        }
        
        if(tbPedElet.isSelected()){
            pedEletronico = "S";
        } else {
            pedEletronico = "N";
        }
    }

    private void convertStatus(){
        switch(cbEstado.getSelectionModel().getSelectedIndex()){
            case 0:
                this.status = "A";
                break;
            case 1:
                this.status = "I";
                break;
            default:
                break;
        }
    }
    
    private void convertUndVenda() {
        switch (cbUnidVenda.getSelectionModel().getSelectedIndex()) {
            case 0:
                this.undVenda = "UN";
                break;
            case 1:
                this.undVenda = "PC";
                break;
            case 2:
                this.undVenda = "DP";
                break;
            case 3:
                this.undVenda = "FD";
                break;
            case 4:
                this.undVenda = "CX";
                break;
            default:
                break;
        }
    }
    
    private void convertTipoPreco() {
        switch (cbTipoPreco.getSelectionModel().getSelectedIndex()) {
            case 0:
                this.tipoPreco = "M";
                break;
            case 1:
                this.tipoPreco = "L";
                break;
            default:
                break;
        }
    }
    
    private void convertIAT() {
        switch (cbIAT.getSelectionModel().getSelectedIndex()) {
            case 0:
                this.IAT = "T";
                break;
            case 1:
                this.IAT = "A";
                break;
            default:
                break;
        }
    }
    
    private void convertIPPT() {
        switch (cbIPPT.getSelectionModel().getSelectedIndex()) {
            case 0:
                this.IPPT = "P";
                break;
            case 1:
                this.IPPT = "T";
                break;
            default:
                break;
        }
    }
    
    private void convertLista() {
        if (cbLista.getSelectionModel().isEmpty()) {
            idLista = 0;
        } else {
            String lista = cbLista.getSelectionModel().getSelectedItem();
            String[] splitLista = lista.split(" - ");

            String SQL = "SELECT idLista FROM dbo.ListaMarkUp WHERE descricao LIKE '" + splitLista[0] + "'";

            try {
                conn.executaSQL(SQL);
                conn.rs.first();

                idLista = conn.rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    
    private void convertSituTribICMS(){
        switch(cbSitTribICMS.getSelectionModel().getSelectedIndex()){
            case 0:
                this.situTribICMS = "T"; //Tributado
                break;
            case 1:
                this.situTribICMS = "F"; //Substituicao Tributaria
                break;
            case 2:
                this.situTribICMS = "I"; //Isento
                break;
            case 3:
                this.situTribICMS = "N"; //Nao Tributado
                break;
            case 4:
                this.situTribICMS = "Z"; //Isento ou nao tributado
                break;
            case 5:
                this.situTribICMS = "D"; //Diferimento
                break;
            case 6:
                this.situTribICMS = "S"; //Suspensao
                break;
            case 7:
                this.situTribICMS = "X"; //Outros
                break;
            default:
                break;
        }
    }
    
    private void convertSituTribPIS(){
        switch(cbSitTribPIS.getSelectionModel().getSelectedIndex()){
            case 0:
                this.situTribPIS = "T"; //Tributado
                break;
            case 1:
                this.situTribPIS = "M"; //Monofasico
                break;
            case 2:
                this.situTribPIS = "F"; //Substituicao Tributaria
                break;
            case 3:
                this.situTribPIS = "Z"; //Aliquota Zero
                break;
            case 4:
                this.situTribPIS = "I"; //Isento
                break;
            case 5:
                this.situTribPIS = "N"; //Sem icidencia
                break;
            case 6:
                this.situTribPIS = "S"; //Suspensao
                break;
            case 7:
                this.situTribPIS = "O"; //Outros
                break;
            default:
                break;
        }
    }
    
    private void convertOrigem() {
        switch (cbOrigem.getSelectionModel().getSelectedIndex()) {
            case 0:
                this.origem = "0";
                break;
            case 1:
                this.origem = "1";
                break;
            case 2:
                this.origem = "2";
                break;
            case 3:
                this.origem = "3";
                break;
            case 4:
                this.origem = "4";
                break;
            case 5:
                this.origem = "5";
                break;
            case 6:
                this.origem = "6";
                break;
            case 7:
                this.origem = "7";
                break;
            default:
                break;
        }
    }
    
    private void convertGrupoCompra() {
        if (cbLista.getSelectionModel().isEmpty()) {
            idGrupoCompra = 0;
        } else {
            String lista = cbGrupoCompra.getSelectionModel().getSelectedItem();

            String SQL = "SELECT idGrupoC FROM dbo.GrupoCompra WHERE descricao LIKE '" + lista + "'";

            try {
                conn.executaSQL(SQL);
                conn.rs.first();

                idGrupoCompra = conn.rs.getInt(1);
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }

    @FXML
    public void limparCampos(){
        txtCodBarras.clear(); txtDescricao.clear(); txtFabricante.clear(); txtGrupo.clear(); txtClasse.clear(); txtPrincipio.clear(); txtIPI.clear();
        txtQtdCompra.setText("1"); txtQtdVenda.setText("1"); txtCustoUnit.setText("0.00"); txtMarkUp.setText("0.00"); txtPMC.setText("0.00"); txtPromo.setText("0.00");
        txtPcoPromo.setText("0.00"); txtDescMax.setText("0.00"); txtComissao.setText("0.00"); txtCFOP.setText("5405"); txtNCM.setText("30049099"); txtCEST.setText("1300400");  
        txtCST.setText("500"); txtAliquota.setText("0"); txtCSTEntrada.setText("0"); txtCSTSaida.setText("0"); txtAliqPIS.setText("0"); txtAliqCOFINS.setText("0"); txtNatReceita.setText("0");
        
        cbEstado.getSelectionModel().selectFirst(); cbUnidVenda.getSelectionModel().selectFirst(); cbTipoPreco.getSelectionModel().selectFirst(); cbIAT.getSelectionModel().select(1);
        cbIPPT.getSelectionModel().select(1); cbOrigem.getSelectionModel().selectFirst(); cbSitTribICMS.getSelectionModel().select(1); cbSitTribPIS.getSelectionModel().select(2);
        
        tbPedElet.setSelected(true); tbPIS.setSelected(true);
        
        ProdutoM.posAtualLC = 0;     
        for (int i = 0; i < ProdutoM.listaCodBarras.size(); i++) {
            ProdutoM.listaCodBarras.set(i, null);
        }
    }
    
    public void atualizaFabricante(){
        txtFabricante.setText(FabricanteM.nomeS);
        idFabricante = FabricanteM.idFabricanteS;
    }
    
    public void atualizaGrupo(){
        txtGrupo.setText(GrupoM.descricaoS);
        idGrupo = GrupoM.idGrupoS;
    }
    
    public void atualizaClasse(){
        txtClasse.setText(ClasseM.descricaoS);
        idClasse = ClasseM.idClasseS;
    }
    
    public void atualizaPrincipio(){
        txtPrincipio.setText(PrincipioAtivoM.descricaoS);
        idPrincipio = PrincipioAtivoM.idPrincipioS;
    }

    public void moveCursor(){
        Control[] focusOrder = new Control[] {
            txtCodBarras, txtDescricao, cbEstado, txtFabricante, txtGrupo, txtClasse, txtPrincipio, txtQtdCompra, txtQtdVenda, cbUnidVenda, cbTipoPreco, cbGrupoCompra, cbLista, cbIAT, cbIPPT, txtCustoUnit,
            txtMarkUp, txtPMC, txtPromo, txtPcoPromo, txtDescMax, txtComissao, txtCFOP, txtNCM, txtCEST, cbOrigem, txtIPI, cbSitTribICMS, txtCST, txtAliquota, cbSitTribPIS, txtCSTEntrada, txtCSTSaida,
            txtAliqPIS, txtAliqCOFINS, txtNatReceita
        };
        
        for (int i = 0; i < focusOrder.length-1; i++) {
            Control nextControl = focusOrder[i + 1];
            focusOrder[i].addEventHandler(ActionEvent.ACTION, e -> nextControl.requestFocus());
        }
    }
    
    @Override
    public void inserirBanco() {
        MetodosController met = new MetodosController();
        ArrayList<TextField> aTxt = new ArrayList<>();
        aTxt.add(txtDescricao);
        aTxt.add(txtFabricante);
        aTxt.add(txtGrupo);
        aTxt.add(txtClasse);

        if (met.verificaCampos(4, aTxt)) {
            verificaTB();
            convertStatus();
            convertUndVenda();
            convertTipoPreco();
            convertIAT();
            convertIPPT();
            convertLista();
            convertGrupoCompra();

            ProdutoM prod = new ProdutoM(txtDescricao.getText(), idFabricante, idGrupo, idClasse, idPrincipio, idGrupoCompra, idLista, controlado, 
                    usoContinuo, pisCOFINS, pedEletronico, Integer.parseInt(txtQtdCompra.getText()), tipoPreco, undVenda, Integer.parseInt(txtQtdVenda.getText()), IPPT, IAT, 
                    (Double.parseDouble(txtComissao.getText().replaceAll(",", "."))/100), (Double.parseDouble(txtDescMax.getText().replaceAll(",", "."))/100), Double.parseDouble(txtCustoUnit.getText().replaceAll(",", ".")), 
                    (Double.parseDouble(txtMarkUp.getText().replaceAll(",", "."))/100), Double.parseDouble(txtPMC.getText().replaceAll(",", ".")), (Double.parseDouble(txtPromo.getText().replaceAll(",", "."))/100), 
                    Double.parseDouble(txtPcoPromo.getText().replaceAll(",", ".")), status);
            
            String SQL = "INSERT INTO Produto.Produto (descricao, idFabricante, idGrupo, idClasse, idPrincipio, idGrupoCompra, idLista, controlado, usoContinuo, pisCOFINS, pedEletronico, qtdCompra, tipoPreco, undVenda,"
                    + "qtdVenda, IPPT, IAT, comissao, desconto, custoUnit, markUP, PMC, promocao, pcoPromocao, status) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setString(1, prod.getDescricao());
                pst.setInt(2, prod.getIdFabricante());
                pst.setInt(3, prod.getIdGrupo());
                pst.setInt(4, prod.getIdClasse());
                pst.setInt(5, prod.getIdPrincipio());
                pst.setInt(6, prod.getIdGrupoCompra());
                pst.setInt(7, prod.getIdLista());
                pst.setString(8, prod.getControlado());
                pst.setString(9, prod.getUsoContinuo());
                pst.setString(10, prod.getPisCOFINS());
                pst.setString(11, prod.getPedEletronico());
                pst.setInt(12, prod.getQtdCompra());
                pst.setString(13, prod.getTipoPreco());
                pst.setString(14, prod.getUndVenda());
                pst.setInt(15, prod.getQtdVenda());
                pst.setString(16, prod.getIPPT());
                pst.setString(17, prod.getIAT());
                pst.setDouble(18, prod.getComissao());
                pst.setDouble(19, prod.getDescontoMax());
                pst.setDouble(20, prod.getCustoUnit());
                pst.setDouble(21, prod.getMarkUP());
                pst.setDouble(22, prod.getPMC());
                pst.setDouble(23, prod.getPromocao());
                pst.setDouble(24, prod.getPcoPromocao());
                pst.setString(25, prod.getStatus());
                pst.executeUpdate();
                
                inserirBancoFisco();
                inserirBancoCB();
                met.insertSucess("Produto");

                limparCampos();
            } catch (SQLException ex) {
                met.insertException(ex);
            }
        }
    }
    
    private void inserirBancoFisco(){
        try {
            conn.executaSQL("SELECT idProduto FROM Produto.Produto");
            conn.rs.last();
            idProduto = conn.rs.getInt(1);
            System.out.println(idProduto);
        } catch (Exception e) {
        }
        
        convertOrigem();
        convertSituTribICMS();
        convertSituTribPIS();
        
        ProdutoM prodF = new ProdutoM(idProduto, txtCFOP.getText(), txtNCM.getText(), txtCEST.getText(), origem, Double.parseDouble(txtIPI.getText().replaceAll(",", ".")), situTribICMS, txtCST.getText(), 
                Double.parseDouble(txtAliquota.getText().replaceAll(",", ".")),  situTribPIS, txtCSTEntrada.getText(), txtCSTSaida.getText(), Double.parseDouble(txtAliqPIS.getText().replaceAll(",", ".")), 
                Double.parseDouble(txtAliqCOFINS.getText().replaceAll(",", ".")), txtNatReceita.getText(), "00");
        
        String SQL = "INSERT INTO Produto.Fisco (idProduto, idCFOP, NCM, CEST, origem, IPI, situTrib, CST, aliquotaICMS, situTribPIS, CSTEntrada, CSTSaida, aliquotaPIS, aliquotaCOFINS, "
                + "naturezaReceita, segmento) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
            
            pst.setInt(1, prodF.getIdProduto());
            pst.setString(2, prodF.getIdCFOP());
            pst.setString(3, prodF.getNCM());
            pst.setString(4, prodF.getCEST());
            pst.setString(5, prodF.getOrigem());
            pst.setDouble(6, prodF.getIPI());
            pst.setString(7, prodF.getSituTrib());
            pst.setString(8, prodF.getCST());
            pst.setDouble(9, prodF.getAliquotaICMS());
            pst.setString(10, prodF.getSituTribPIS());
            pst.setString(11, prodF.getCSTEntrada());
            pst.setString(12, prodF.getCSTSaida());
            pst.setDouble(13, prodF.getAliquotaPIS());
            pst.setDouble(14, prodF.getAliquotaCOFINS());
            pst.setString(15, prodF.getNaturezaReceita());
            pst.setString(16, prodF.getSegmento());
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    private void inserirBancoCB(){
        ProdutoM prodCB = new ProdutoM(idProduto, txtCodBarras.getText());
        
        String SQL = "INSERT INTO Produto.CodigoBarras (idProduto, codBarras01, codBarras02, codBarras03, codBarras04, codBarras05, codBarras06, codBarras07, codBarras08, codBarras09, codBarras10, codBarras11,"
                + "codBarras12, codBarras13, codBarras14, codBarras15) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        
        try {
            PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
            
            pst.setInt(1, prodCB.getIdProduto());
            pst.setString(2, prodCB.getCodBarras01());
            pst.setString(3, ProdutoM.listaCodBarras.get(0));
            pst.setString(4, ProdutoM.listaCodBarras.get(1));
            pst.setString(5, ProdutoM.listaCodBarras.get(2));
            pst.setString(6, ProdutoM.listaCodBarras.get(3));
            pst.setString(7, ProdutoM.listaCodBarras.get(4));
            pst.setString(8, ProdutoM.listaCodBarras.get(5));
            pst.setString(9, ProdutoM.listaCodBarras.get(6));
            pst.setString(10, ProdutoM.listaCodBarras.get(7));
            pst.setString(11, ProdutoM.listaCodBarras.get(8));
            pst.setString(12, ProdutoM.listaCodBarras.get(9));
            pst.setString(13, ProdutoM.listaCodBarras.get(10));
            pst.setString(14, ProdutoM.listaCodBarras.get(11));
            pst.setString(15, ProdutoM.listaCodBarras.get(12));
            pst.setString(16, ProdutoM.listaCodBarras.get(13));
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}