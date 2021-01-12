package br.com.techfarma.controller.venda;

import java.math.BigDecimal;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;
import br.com.techfarma.model.consulta.VendaM;
import controller.ConexaoController;
import controller.MetodosController;
import java.io.IOException;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Label;
import javafx.util.converter.BigDecimalStringConverter;
import javafx.util.converter.NumberStringConverter;
import javax.swing.JOptionPane;

public class VendaC implements Initializable{
    ConexaoController conn = new ConexaoController();
    MetodosController met = new MetodosController();
    
    private Stage stageEntrega, stageConsulta, stageFormaPag, stageCadCliente;
    
    @FXML
    private TextField txtVendedor, txtPesquisa;
    @FXML
    public TableView<VendaM> tvVenda;
    @FXML
    public TableColumn tcCod, tcDescricao, tcPromo, tcPMC, tcPcoUnit, tcValorTotal;
    @FXML
    public TableColumn<VendaM, BigDecimal> tcDesconto;
    @FXML
    public TableColumn<VendaM, Integer> tcQtd;
    @FXML
    private Label lbDesconto, lbQtd, lbPMC, lbSubTotal, lbTotal, lbIdVenda;
    
    private String vendedor = null;
    private String verificadorEntrega = "N";
    private String codBarras;
    private float idCFOP;
    private String NCM;
    private String CEST;
    
    private int numVendedor;
    private int idVenda = 0;
    private int valorLinhaQtd = 0;
    private BigDecimal valorLinhaPMC;
    private int valorTotalQtd = 0;
    
    private BigDecimal valorTotalPMC = BigDecimal.ZERO;  
    private BigDecimal valorLinhaDesconto;
    private BigDecimal valorTotalDesconto = BigDecimal.ZERO; 
    private BigDecimal valorLinhaValorTotal;
    private BigDecimal valorTotalValorTotal = BigDecimal.ZERO;
    
    private List<Integer> listaProdutos = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
        txtPesquisa.setDisable(true);
        
        inicializaTabela();
        newIdVenda();
        lbIdVenda.setText(String.valueOf(idVenda));
        
        tvVenda.setEditable(true);
        editableCols();
    }
    
    private void inicializaTabela(){
        tcCod.setCellValueFactory(new PropertyValueFactory<>("idProduto"));
        tcDescricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        tcQtd.setCellValueFactory(new PropertyValueFactory<>("qtd"));
        tcDesconto.setCellValueFactory(new PropertyValueFactory<>("descontoPerc"));
        tcPMC.setCellValueFactory(new PropertyValueFactory<>("PMC"));
        tcPcoUnit.setCellValueFactory(new PropertyValueFactory<>("valorUnitario"));
        tcValorTotal.setCellValueFactory(new PropertyValueFactory<>("valorTotal"));
    }
    
    private void pesquisaProduto() {
        VendaM.descricaoS = txtPesquisa.getText();
        
        if (VendaM.descricaoS.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor preencha o campo descrição.");
        } else {
            if (stageConsulta == null) {
                stageConsulta = met.abrirTelaEspera("/br/com/techfarma/view/venda/Venda - CP.fxml", stageConsulta);
                stageConsulta.setOnHiding(we -> stageConsulta = null);
                stageConsulta.showAndWait();
            } else if (stageConsulta.isShowing()) {
                stageConsulta.toFront();
            }

            if (VendaM.newElementS) {
                atualizaTela();
                txtPesquisa.clear();
            } else {
                txtPesquisa.clear();
            }
        }
    }
    
    private void habilitarEntrega() throws IOException{
        if (stageEntrega == null) {
            stageEntrega = met.abrirTelaEspera("/br/com/techfarma/view/venda/Venda - Entrega.fxml", stageEntrega);
            stageEntrega.setOnHiding(we -> stageEntrega = null);
            stageEntrega.showAndWait();
        } else if (stageEntrega.isShowing()) {
            stageEntrega.toFront();
        }
        
        try {
            if (!VendaM.endereco.isEmpty()) {
                verificadorEntrega = "S";
            }
        } catch (NullPointerException e) {
             verificadorEntrega = "N";
        }
    }

    public void atualizaTela() {
        if (verificaDuplicidade()) {
            calculaPcoTotal();
            atualizaLabels();
            tvVenda.refresh();
        } else {
            tvVenda.setItems(VendaM.getLista());
            calculaPcoTotal();
            atualizaLabels();
        }
    }
    
    private void limparTela(){
        txtVendedor.clear();
        txtVendedor.setDisable(false);
        txtPesquisa.clear();
        txtPesquisa.setDisable(true);
        VendaM.getLista().clear();
        atualizaLabels();
        listaProdutos.clear();
        txtVendedor.requestFocus();
    }
    
    @FXML
    void keyPesquisa(KeyEvent event) throws Exception{
        switch (event.getCode()) {
            case ENTER:
                pesquisaProduto();
                break;
            case F7:
                if(tvVenda.getItems().size() > 0){
                    VendaM.valorTotalFinal = Double.parseDouble(lbTotal.getText());
                    
                    if (stageFormaPag == null) {
                        stageFormaPag = met.abrirTelaEspera("/br/com/techfarma/view/venda/Venda - FP.fxml", stageFormaPag);
                        stageFormaPag.setOnHiding(we -> stageFormaPag = null);
                        stageFormaPag.showAndWait();
                    } else if (stageFormaPag.isShowing()) {
                        stageFormaPag.toFront();
                    }

                    if(VendaM.finalizaVendaS){
                        registraVenda();
                        limparTela();
                        VendaM.finalizaVendaS = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Para finalizar a venda é necessário pelo menos 1 item.");
                }
                
                txtPesquisa.requestFocus();
                break;
            case F8:
                habilitarEntrega();
                break;
            case F9:
                cadastraCliente();
                break;
            case DELETE:
                removeElemento();
                tvVenda.setItems(VendaM.getLista());
                break;
            default:
                break;
            }
    }
    
    @FXML
    void keyTableView(KeyEvent event){
        switch (event.getCode()) {
            case ESCAPE:
                if (!txtVendedor.getText().isEmpty()) {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja abandonar esta venda?");
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        limparTela();
                    }
                } else {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja sair da tela de vendas?");
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        Stage st = (Stage) txtPesquisa.getScene().getWindow();
                        VendaM.getLista().clear();
                        st.close();
                    }
                }
                break;
            default:
                break;
            }
    }
    
    @FXML
    void keyPressed(KeyEvent event) throws Exception {
        switch (event.getCode()) {
            case F5:
                calculaPcoTotal();
             break;
            case F7:
                if(tvVenda.getItems().size() > 0){
                    VendaM.valorTotalFinal = Double.parseDouble(lbTotal.getText());
                    
                    if (stageFormaPag == null) {
                        stageFormaPag = met.abrirTelaEspera("/br/com/techfarma/view/venda/Venda - FP.fxml", stageFormaPag);
                        stageFormaPag.setOnHiding(we -> stageFormaPag = null);
                        stageFormaPag.showAndWait();
                    } else if (stageFormaPag.isShowing()) {
                        stageFormaPag.toFront();
                    }
                    
                    if(VendaM.finalizaVendaS){
                        registraVenda();
                        limparTela();
                        VendaM.finalizaVendaS = false;
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Para finalizar a venda é necessário pelo menos 1 item.");
                }
                
                txtPesquisa.requestFocus();
                break;
            case F8:
                habilitarEntrega();
                break;
            case F9:
                cadastraCliente();
                break;
            case DELETE:
                removeElemento();
                tvVenda.setItems(VendaM.getLista());
                break;
            case ESCAPE:
                if (!txtVendedor.getText().isEmpty()) {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja abandonar esta venda?");
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        limparTela();
                    }
                } else {
                    int dialogResult = JOptionPane.showConfirmDialog(null, "Deseja sair da tela de vendas?");
                    if (dialogResult == JOptionPane.YES_OPTION) {
                        Stage st = (Stage) txtPesquisa.getScene().getWindow();
                        VendaM.getLista().clear();
                        st.close();
                    }
                }
                break;
            default:
                break;
            }
    }
    
    private void cadastraCliente(){
        met.abrirTelaEspera("/br/com/techfarma/view/venda/Venda - Cadastro Cliente.fxml", stageCadCliente);
    }
    
    private BigDecimal newValue;
    
    private void editableCols() {
        tcQtd.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        tcQtd.setOnEditCommit((CellEditEvent<VendaM, Integer> event) -> {
            TablePosition<VendaM, Integer> pos = event.getTablePosition();

            int newQtd = event.getNewValue();

            int row = pos.getRow();
            VendaM venda = event.getTableView().getItems().get(row);
            venda.setQtd(newQtd);

            calculaPcoTotal();
            atualizaLabels();
        });
        
        
        tcDesconto.setCellFactory(TextFieldTableCell.forTableColumn(new BigDecimalStringConverter()));
        tcDesconto.setOnEditCommit((CellEditEvent<VendaM, BigDecimal> event) -> {
            TablePosition<VendaM, BigDecimal> pos = event.getTablePosition();

            newValue = event.getNewValue();

            int row = pos.getRow();
            VendaM venda = event.getTableView().getItems().get(row);
            venda.setDescontoPerc(newValue.setScale(2));
            calculaPcoTotal();
            atualizaLabels();

        });
    }
    
    private void calculaPcoTotal(){
        for(int i = 0; i < tvVenda.getItems().size(); i++){
            int idProduto = tvVenda.getItems().get(i).getIdProduto();
            String descricao = tvVenda.getItems().get(i).getDescricao();
            BigDecimal qtd = new BigDecimal(tvVenda.getItems().get(i).getQtd());
            BigDecimal descontoPerc = tvVenda.getItems().get(i).getDescontoPerc();
            BigDecimal valorPromocao = tvVenda.getItems().get(i).getValorPromocao();
            BigDecimal PMC = tvVenda.getItems().get(i).getPMC();
            BigDecimal valorUnitario = PMC.subtract(PMC.multiply(descontoPerc.divide(new BigDecimal(100)))).setScale(2, RoundingMode.UP);
            BigDecimal valorTotal = qtd.multiply(valorUnitario);
            
            VendaM.getLista().set(i, new VendaM(idProduto, descricao, qtd.intValue(), PMC, valorUnitario, valorPromocao, descontoPerc, valorTotal));
        }
    }
    
//    private void calculaValorDesconto(){ 
//        for(int i = 0; i < tvVenda.getItems().size(); i++){
//            BigDecimal descontoPerc = tvVenda.getItems().get(i).getDescontoPerc();
//            BigDecimal PMC = tvVenda.getItems().get(i).getPMC();
//            BigDecimal valorDesconto = PMC.multiply(descontoPerc.divide(new BigDecimal(100)));
//            
//            VendaM.getLista().set(i, element)
//        }
//    }
    
    private boolean verificaDuplicidade() {
        int ultimoInserido = VendaM.getLista().get(VendaM.getLista().size() - 1).getIdProduto();

        if (listaProdutos.contains(ultimoInserido)) {
            for (int i = 0; i < VendaM.getLista().size(); i++) {
                if (ultimoInserido == VendaM.getLista().get(i).getIdProduto()) {
                    VendaM.getLista().get(i).setQtd(VendaM.getLista().get(i).getQtd() + 1); // Adiciona +1 à quantidade do elemento atual
                    VendaM.getLista().remove(VendaM.getLista().size() - 1); // Remove o elemento duplicado
                }
            }
            return true;
        } else {
            listaProdutos.add(ultimoInserido); // Adiciona novo elemento
        }

        return false;
    }

    private void atualizaLabels(){
        for(int i = 0; i < tvVenda.getItems().size(); i++){
            valorLinhaQtd = VendaM.getLista().get(i).getQtd();
            valorLinhaPMC = VendaM.getLista().get(i).getPMC();
            valorLinhaValorTotal = VendaM.getLista().get(i).getValorTotal();
            
            if(VendaM.getLista().get(i).getDescontoPerc().equals(0.00)){
                valorLinhaDesconto = BigDecimal.ZERO;
            } else {
                valorLinhaDesconto = (valorLinhaPMC.subtract(VendaM.getLista().get(i).getValorUnitario())).multiply(new BigDecimal (VendaM.getLista().get(i).getQtd()));
            }
            
            valorTotalQtd = valorTotalQtd + valorLinhaQtd;
            valorTotalPMC = valorTotalPMC.add(valorLinhaPMC.multiply(BigDecimal.valueOf(valorLinhaQtd)));
            valorTotalDesconto = valorTotalDesconto.add(valorLinhaDesconto);
            valorTotalValorTotal = valorTotalValorTotal.add(valorLinhaValorTotal);
        }
        
        lbQtd.setText(String.valueOf(valorTotalQtd));
        lbPMC.setText(valorTotalPMC.toString());
        lbDesconto.setText(valorTotalDesconto.toString());
        lbSubTotal.setText(valorTotalValorTotal.toString());
        lbTotal.setText(valorTotalValorTotal.toString());
        
        valorTotalQtd = 0;
        valorTotalPMC = BigDecimal.ZERO;
        valorTotalDesconto = BigDecimal.ZERO;
        valorTotalValorTotal = BigDecimal.ZERO;
    }
    
    private void removeElemento(){
        listaProdutos.remove(tvVenda.getSelectionModel().getSelectedIndex());
        VendaM.getLista().remove(tvVenda.getSelectionModel().getSelectedIndex());
        calculaPcoTotal();
//        calculaValorDesconto();
        atualizaLabels();
    }

    private void vendaCounter() {
        try {
            conn.executaSQL("SELECT idVenda FROM dbo.numVendas");
            conn.rs.first();

            idVenda = conn.rs.getInt(1);
        } catch (SQLException ex) {
            String SQL = "INSERT INTO dbo.numVendas VALUES (?)";

            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);

                pst.setInt(1, idVenda);
                pst.executeUpdate();

            } catch (SQLException e) {
                System.out.println(e);
            }
        }
    }
    
    private void newIdVenda(){
        vendaCounter();
        idVenda = idVenda + 1;
        
        String SQL = "UPDATE dbo.numVendas SET idVenda = ?";
        
        try {
            PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
            
            pst.setInt(1, idVenda);
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    public void consultaVendedor() {
        if (txtVendedor.getText() == null || txtVendedor.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Digite o número do Vendedor para iniciar a Venda.");
            txtVendedor.clear();
        } else {
            try {
                conn.executaSQL("SELECT nome FROM dbo.Funcionario WHERE idFuncionario = " + txtVendedor.getText() + "");
                conn.rs.last();

                System.out.println(conn.rs.last());
                vendedor = conn.rs.getString(1);

                txtVendedor.setText(vendedor);
                txtVendedor.setDisable(true);
                txtPesquisa.setDisable(false);

                newIdVenda();
                lbIdVenda.setText(String.valueOf(idVenda));
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Vendedor não cadastrado.");
                txtVendedor.clear();
            }
        }
    }
    
    private void registraVenda() throws Exception {
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");

        java.sql.Date dataSQL = java.sql.Date.valueOf(dateFormat.format(date));
        java.sql.Time horaSQL = java.sql.Time.valueOf(timeFormat.format(date));

        for (int i = 0; i < tvVenda.getItems().size(); i++) {
            BigDecimal desconto = new BigDecimal(tcDesconto.getCellData(i).toString());
            BigDecimal promocao = new BigDecimal(BigInteger.ZERO);

            if (tcPromo.getCellData(i) == null) {
                promocao = BigDecimal.ZERO;
            } else {
                promocao = new BigDecimal(tcPromo.getCellData(i).toString());
            }

            conn.executaSQL("SELECT codBarras01 FROM Produto.CodigoBarras WHERE idProduto = " + tvVenda.getItems().get(i).getIdProduto() + "");
            try {
                conn.rs.first();
                codBarras = conn.rs.getString(1);
            } catch (SQLException e) {
            }
            
            
            conn.executaSQL("SELECT idCFOP FROM Produto.Fisco WHERE idProduto = " + tvVenda.getItems().get(i).getIdProduto() + "");
            try {
                conn.rs.first();
                idCFOP = conn.rs.getFloat(1);
            } catch (SQLException e) {
            }
            
            conn.executaSQL("SELECT NCM FROM Produto.Fisco WHERE idProduto = " + tvVenda.getItems().get(i).getIdProduto() + "");
            try {
                conn.rs.first();
                NCM = conn.rs.getString(1);
            } catch (SQLException e) {
            }
            
            conn.executaSQL("SELECT CEST FROM Produto.Fisco WHERE idProduto = " + tvVenda.getItems().get(i).getIdProduto() + "");
            try {
                conn.rs.first();
                CEST = conn.rs.getString(1);
            } catch (SQLException e) {
            }
            
            VendaM.concluidoS = "N";

            try {
                BigDecimal PMC = new BigDecimal(tcPMC.getCellData(i).toString());
                BigDecimal valorDesc = new BigDecimal((Double.parseDouble(tcPMC.getCellData(i).toString()) - Double.parseDouble(tcPcoUnit.getCellData(i).toString())) * Integer.parseInt(tcQtd.getCellData(i).toString())).setScale(2, BigDecimal.ROUND_HALF_UP);
                BigDecimal txDesconto = new BigDecimal(tcDesconto.getCellData(i).toString());
                BigDecimal valorUnitario = new BigDecimal(tcPcoUnit.getCellData(i).toString());
                BigDecimal valorTotal = new BigDecimal(tcValorTotal.getCellData(i).toString());
                BigDecimal troco = new BigDecimal(BigInteger.ZERO);

                VendaM newVenda = new VendaM(1, Integer.parseInt(lbIdVenda.getText()), VendaM.barCodeVenda, 0, numVendedor, VendaM.idCliente, VendaM.idConvenio, 0, idCFOP, "N",
                        Integer.parseInt(tcCod.getCellData(i).toString()), codBarras, tcDescricao.getCellData(i).toString(), NCM, CEST, "N", Integer.parseInt(tcQtd.getCellData(i).toString()), "UN", PMC, valorDesc, BigDecimal.ZERO, txDesconto, valorUnitario, valorTotal,
                        troco, "AL", "V", verificadorEntrega, VendaM.endereco, VendaM.bairro, VendaM.referencia, dataSQL, horaSQL, txtVendedor.getText(), 0, VendaM.concluidoS);

                System.out.println(newVenda);
                String SQL = "INSERT INTO Vendas.Vendas (idFilial, idVenda, idBarCode, idVendaTroca, idVendedor, idCliente, idConvenio, idCupom, idCFOP, bloqueado, idProduto, codigoBarras, descricao, NCM, CEST, usoContinuo, quantidade, unidade, PMC, valorDesc, diferencaPromo, txDesconto,"
                        + "valorUnitario, valorTotal, troco, aliquota, tipoVenda, entrega, endereco, bairro, referencia, dataVenda, horaVenda, vendedor, terminal, concluido) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                try {
                    PreparedStatement pst = conn.getConexao().prepareStatement(SQL);

                    pst.setInt(1, newVenda.getIdFilial());
                    pst.setInt(2, newVenda.getIdVenda());
                    pst.setString(3, newVenda.getBarCodeVenda());
                    pst.setInt(4, newVenda.getIdVendaTroca());
                    pst.setInt(5, newVenda.getIdVendedor());
                    pst.setInt(6, newVenda.getIdCliente());
                    pst.setInt(7, newVenda.getIdConvenio());
                    pst.setInt(8, newVenda.getIdCupom());
                    pst.setFloat(9, newVenda.getIdCFOP());
                    pst.setString(10, newVenda.getBloqueado());
                    pst.setInt(11, newVenda.getIdProduto());
                    pst.setString(12, newVenda.getCodigoBarras());
                    pst.setString(13, newVenda.getDescricao());
                    pst.setString(14, newVenda.getNCM());
                    pst.setString(15, newVenda.getCEST());
                    pst.setString(16, newVenda.getUsoContinuo());
                    pst.setInt(17, newVenda.getQtd());
                    pst.setString(18, newVenda.getUnidade());
                    pst.setBigDecimal(19, newVenda.getPMC());
                    pst.setBigDecimal(20, newVenda.getValorDesc());
                    pst.setBigDecimal(21, newVenda.getDiferencaPromocao());
                    pst.setBigDecimal(22, newVenda.getDescontoPerc());
                    pst.setBigDecimal(23, newVenda.getValorUnitario());
                    pst.setBigDecimal(24, newVenda.getValorTotal());
                    pst.setBigDecimal(25, newVenda.getTroco());
                    pst.setString(26, newVenda.getAliquota());
                    pst.setString(27, newVenda.getTipoVenda());
                    pst.setString(28, newVenda.getEntrega());
                    pst.setString(29, newVenda.getEndereco());
                    pst.setString(30, newVenda.getBairro());
                    pst.setString(31, newVenda.getReferencia());
                    pst.setDate(32, newVenda.getDataVenda());
                    pst.setTime(33, newVenda.getHoraVenda());
                    pst.setString(34, newVenda.getVendedor());
                    pst.setInt(35, newVenda.getTerminal());
                    pst.setString(36, newVenda.getConcluido());

                    pst.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println(ex);
                }
            } catch (NumberFormatException exs) {
                System.out.println(exs);
            }
        }
        
        registraFormaPag();
    }
    
    private void registraFormaPag(){
        String SQL = "INSERT INTO Vendas.FP (idFilial, idVenda, idBarCode, idPagamento, idCartao, idCliente, idConvenio, tipoCartao, valor, troco, status) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        
        VendaM newVendaFP = new VendaM(1, Integer.parseInt(lbIdVenda.getText()), VendaM.barCodeVenda, VendaM.idPagamento, VendaM.idCartao, 0, 0, VendaM.tipoCartao, VendaM.valorTotalFinal, VendaM.trocoFinal, "P");
        
        try {
            PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
            
            pst.setInt(1, newVendaFP.getIdFilial());
            pst.setInt(2, newVendaFP.getIdVenda());
            pst.setString(3, VendaM.getIdBarCode());
            pst.setString(4, VendaM.idPagamento);
            pst.setInt(5, VendaM.idCartao);
            pst.setInt(6, newVendaFP.getIdCliente());
            pst.setInt(7, newVendaFP.getIdConvenio());
            pst.setString(8, VendaM.tipoCartao);
            pst.setBigDecimal(9, BigDecimal.valueOf(VendaM.valorTotalFinal));
            pst.setBigDecimal(10, BigDecimal.valueOf(VendaM.trocoFinal));
            pst.setString(11, newVendaFP.getConcluido());
            
            pst.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}