
package controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class MenuPrincipal implements Initializable{
    MetodosController met = new MetodosController();

    @FXML
    private Label lbData, lbHora, lbIP, lbUser;
    @FXML
    private MenuButton mbProdutos, mbCompras;
    @FXML
    private MenuItem miCadProd, miPedCompras;
    @FXML
    private Button btVendas, btCaixa, btFuncionarios, btCliente, btConvenio, btMovimentos, btSair;
    @FXML
    private Tab tabPedCompras;
    @FXML
    private TabPane tpCompras;
    @FXML
    private AnchorPane anchorPaneC, anchorPaneD;
    @FXML
    private JFXButton jbtMenu;
    
    
    int fadeVerificadorC = 0;
    int fadeVerificadorD = 0;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //dataSistema();
        //horaSistema();
        //ipSistema();
        fadeInInicial();
    }
    
    @FXML
    void keyPressed(KeyEvent event){
        switch (event.getCode()) {
            case P:
                if(mbCompras.isShowing()){
                    miPedCompras.fire();
                } else {
                    mbProdutos.show();
                }
            break;
            case C:
                if(mbProdutos.isShowing()){
                    miCadProd.fire();
                } else {
                    mbCompras.show();
                }
            break;
            case V:
                btVendas.fire();
            break;
            case F11:
                btSair.fire();
            break;
            default:
                break;
            }
    }
    
    /*/private void dataSistema(){
        Date dataSistema = new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        lbData.setText(formato.format(dataSistema));
    }
    
    private void horaSistema() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(0),
                        new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        Calendar time = Calendar.getInstance();
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                        lbHora.setText(simpleDateFormat.format(time.getTime()));
                    }
                }
                ),
                new KeyFrame(Duration.seconds(1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }
    
    private void ipSistema(){
        try {
            String IP = Inet4Address.getLocalHost().getHostAddress();
            lbIP.setText(IP);
        } catch (UnknownHostException ex) {
            System.out.println(ex);
        }
    }/*/

    
    private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);
    }
        
    public void fecharJanela(){
        Stage stage = (Stage) btSair.getScene().getWindow();
        stage.close();
    }
    
    private Stage stageFilial, stageGrupoFilial, stageProduto, stageClasse, stageGrupo, stageFormasPag, stageGrupoCompra, stageFabricante, stageCFOP, stageMarkUP, stageFornecedor, stagePrincipioA, stageFuncionario, stageCartao, 
            stageConvenio, stageCliente, stageUsuario, stageVendas;
    
    @FXML
    private void abrirFilial() {
        if(stageFilial == null){
            stageFilial = met.abrirTela("/br/com/techfarma/view/consulta/Filial.fxml", 999, 700, stageFilial);
            stageFilial.setOnHiding(we -> stageFilial = null);
            stageFilial.show();
        } else if (stageFilial.isShowing()){
            stageFilial.toFront();
        }
    }
    
    @FXML
    private void abrirGrupoFilial() {
        if(stageGrupoFilial == null){
            stageGrupoFilial = met.abrirTela("/br/com/techfarma/view/consulta/Grupo Filial.fxml", 999, 700, stageGrupoFilial);
            stageGrupoFilial.setOnHiding(we -> stageGrupoFilial = null);
            stageGrupoFilial.show();
        } else if (stageGrupoFilial.isShowing()){
            stageGrupoFilial.toFront();
        }
    }
    
    @FXML
    private void abrirProduto() {
        if(stageProduto == null){
            stageProduto = met.abrirTela("/br/com/techfarma/view/cadastro/Produto.fxml", 950, 768, stageProduto);
            stageProduto.setOnHiding(we -> stageProduto = null);
            stageProduto.show();
        } else if (stageProduto.isShowing()){
            stageProduto.toFront();
        }
    }
    
    @FXML
    private void abrirClasse() {
        if(stageClasse == null){
            stageClasse = met.abrirTela("/br/com/techfarma/view/consulta/Classe.fxml", 1000, 768, stageClasse);
            stageClasse.setOnHiding(we -> stageClasse = null);
            stageClasse.show();
        } else if (stageClasse.isShowing()){
            stageClasse.toFront();
        }
    }
    
    @FXML
    private void abrirGrupo() {
        if(stageGrupo == null){
            stageGrupo = met.abrirTela("/br/com/techfarma/view/consulta/Grupo.fxml", 1000, 768, stageGrupo);
            stageGrupo.setOnHiding(we -> stageGrupo = null);
            stageGrupo.show();
        } else if (stageGrupo.isShowing()){
            stageGrupo.toFront();
        }
    }
    
    @FXML
    private void abrirFabricante(){
        if(stageFabricante == null){
            stageFabricante = met.abrirTela("/br/com/techfarma/view/consulta/Fabricante.fxml", 1000, 768, stageFabricante);
            stageFabricante.setOnHiding(we -> stageFabricante = null);
            stageFabricante.show();
        } else if (stageFabricante.isShowing()){
            stageFabricante.toFront();
        }
    }
    
    @FXML
    private void abrirPrincipioA(){
        if(stagePrincipioA == null){
            stagePrincipioA = met.abrirTela("/br/com/techfarma/view/consulta/Principio Ativo.fxml", 999, 700, stagePrincipioA);
            stagePrincipioA.setOnHiding(we -> stagePrincipioA = null);
            stagePrincipioA.show();
        } else if (stagePrincipioA.isShowing()){
            stagePrincipioA.toFront();
        }
    }
    
    @FXML
    private void abrirFormasPag() {
        if(stageFormasPag == null){
            stageFormasPag = met.abrirTela("/br/com/techfarma/view/consulta/Formas Pagamento.fxml", 999, 700, stageFormasPag);
            stageFormasPag.setOnHiding(we -> stageFormasPag = null);
            stageFormasPag.show();
        } else if (stageFormasPag.isShowing()){
            stageFormasPag.toFront();
        }
    }
    
    @FXML
    private void abrirCFOP(){
        if(stageCFOP == null){
            stageCFOP = met.abrirTela("/br/com/techfarma/view/consulta/CFOP.fxml", 999, 700, stageCFOP);
            stageCFOP.setOnHiding(we -> stageCFOP = null);
            stageCFOP.show();
        } else if (stageCFOP.isShowing()){
            stageCFOP.toFront();
        }
    }
    
    @FXML
    private void abrirFornecedor(){
        if(stageFornecedor == null){
            stageFornecedor = met.abrirTela("/br/com/techfarma/view/consulta/Fornecedor.fxml", 999, 700, stageFornecedor);
            stageFornecedor.setOnHiding(we -> stageFornecedor = null);
            stageFornecedor.show();
        } else if (stageFornecedor.isShowing()){
            stageFornecedor.toFront();
        }
    }
    
    @FXML
    private void abrirCliente(){
        if(stageCliente == null){
            stageCliente = met.abrirTela("/br/com/techfarma/view/consulta/Cliente.fxml", 1001, 700, stageCliente);
            stageCliente.setOnHiding(we -> stageCliente = null);
            stageCliente.show();
        } else if (stageCliente.isShowing()){
            stageCliente.toFront();
        }
    }
    
    @FXML
    private void abrirFuncionario(){
        if(stageFuncionario == null){
            stageFuncionario = met.abrirTela("/br/com/techfarma/view/consulta/Funcionario.fxml", 1000, 768, stageFuncionario);
            stageFuncionario.setOnHiding(we -> stageFuncionario = null);
            stageFuncionario.show();
        } else if (stageFuncionario.isShowing()){
            stageFuncionario.toFront();
        }
    }
    
    @FXML
    private void abrirConvenio(){
        if(stageConvenio == null){
            stageConvenio = met.abrirTela("/br/com/techfarma/view/consulta/Convenio.fxml", 999, 700, stageConvenio);
            stageConvenio.setOnHiding(we -> stageConvenio = null);
            stageConvenio.show();
        } else if (stageConvenio.isShowing()){
            stageConvenio.toFront();
        }
    }
    
    @FXML
    private void abrirCartao(){
        if(stageCartao == null){
            stageCartao = met.abrirTela("/br/com/techfarma/view/consulta/Cartao.fxml", 999, 700, stageCartao);
            stageCartao.setOnHiding(we -> stageCartao = null);
            stageCartao.show();
        } else if (stageCartao.isShowing()){
            stageCartao.toFront();
        }
    }
    
    @FXML
    private void abrirUsuario(){
        if(stageUsuario == null){
            stageUsuario = met.abrirTela("/br/com/techfarma/view/consulta/Usuario.fxml", 999, 700, stageUsuario); 
            stageUsuario.setOnHiding(we -> stageUsuario = null);
            stageUsuario.show();
        } else if (stageUsuario.isShowing()){
            stageUsuario.toFront();
        }
    }
    
    @FXML
    private void abrirGrupoC() {
        if(stageGrupoCompra == null){
            stageGrupoCompra = met.abrirTela("/br/com/techfarma/view/cadastro/Grupo Compra.fxml", 999, 700, stageGrupoCompra);
            stageGrupoCompra.setOnHiding(we -> stageGrupoCompra = null);
            stageGrupoCompra.show();
        } else if (stageGrupoCompra.isShowing()){
            stageGrupoCompra.toFront();
        }
    }
    
    @FXML
    private void abrirCadMarkUp(){
        if(stageMarkUP == null){
            stageMarkUP = met.abrirTela("/br/com/techfarma/view/cadastro/MarkUp.fxml", 999, 700, stageMarkUP);
            stageMarkUP.setOnHiding(we -> stageMarkUP = null);
            stageMarkUP.show();
        } else if (stageMarkUP.isShowing()){
            stageMarkUP.toFront();
        }
    }

    @FXML
    private void abrirVendas(){
        if(stageVendas == null){
            stageVendas = met.abrirTela("/br/com/techfarma/view/venda/Venda.fxml", 999, 700, stageVendas);
            stageVendas.setOnHiding(we -> stageVendas = null);
            stageVendas.show();
        } else if (stageVendas.isShowing()){
            stageVendas.toFront();
        }
    }
    
    @FXML
    private void abrirPedCompras(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Compras.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
        }
    }
    
    @FXML
    private void abrirCaixa(){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Caixa.fxml"));
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            centerStage(stage, 654, 400);
            //stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
        }
    }
    
    private void fadeInInicial(){
        FadeTransition fade = new FadeTransition(Duration.millis(1750), anchorPaneC);
        FadeTransition fadeButton = new FadeTransition(Duration.millis(1750), jbtMenu);
        
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fadeButton.setFromValue(0.0);
        fadeButton.setToValue(1.0);
        
        if (fadeVerificadorC == 0) {
            anchorPaneC.setVisible(true);
            anchorPaneC.setDisable(false);
            
            jbtMenu.setVisible(true);
            jbtMenu.setDisable(false);
            
            fade.play();
            fadeButton.play();
            
            fadeVerificadorC = 1;
        }
    }
    
    @FXML
    private void fadeInAnchorC(ActionEvent event){
        FadeTransition fade = new FadeTransition(Duration.millis(500), anchorPaneC);
        FadeTransition fadeButton = new FadeTransition(Duration.millis(500), jbtMenu);
        
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fadeButton.setFromValue(0.0);
        fadeButton.setToValue(1.0);
        
        if (fadeVerificadorC == 0) {
            anchorPaneC.setVisible(true);
            anchorPaneC.setDisable(false);
            
            jbtMenu.setVisible(true);
            jbtMenu.setDisable(false);
            
            fade.play();
            fadeButton.play();
            
            fadeVerificadorC = 1;
        }
    }
    
    @FXML
    private void fadeOutAnchorC(ActionEvent event){
        anchorPaneC.setDisable(true);
        jbtMenu.setDisable(true);
        
        FadeTransition fade = new FadeTransition(Duration.millis(500), anchorPaneC);
        FadeTransition fadeButton = new FadeTransition(Duration.millis(500), jbtMenu);
        FadeTransition fadeAD = new FadeTransition(Duration.millis(500), anchorPaneD);
        
        if(fadeVerificadorC == 1){
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            fadeButton.setFromValue(1.0);
            fadeButton.setToValue(0.0);

            fade.play();
            fadeButton.play();
            
            fadeVerificadorC = 0;  
            
            if(fadeVerificadorD == 1){
                fadeAD.setFromValue(1.0);
                fadeAD.setToValue(0.0);
            
                fadeAD.play();
                fadeVerificadorD = 0;
            }
        }    
    }
    
    @FXML
    private void fadeInAnchorD(ActionEvent event){
        anchorPaneD.setDisable(true);
        
        FadeTransition fade = new FadeTransition(Duration.millis(200), anchorPaneD);
        
        if (fadeVerificadorD == 0) {
            fade.setFromValue(0.0);
            fade.setToValue(1.0);
        
            anchorPaneD.setVisible(true);
            anchorPaneD.setDisable(false);
            fade.play();
            
            fadeVerificadorD = 1;
        } else if (fadeVerificadorD == 1) {
            fade.setFromValue(1.0);
            fade.setToValue(0.0);
            
            anchorPaneD.setDisable(true);
            fade.play();
            
            fadeVerificadorD = 0;
        }
    }
}