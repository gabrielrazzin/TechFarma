package controller;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.dialog.ExceptionDialog;

public class MetodosController {
    
    private void centerStage(Stage stage, double width, double height) {
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY(((screenBounds.getHeight() - height) / 2) + 20);
    }
    
    public Stage abrirTela(String caminho, double largura, double altura, Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(caminho));
            
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            centerStage(stage, largura, altura);
            
            return stage;
        } catch (IOException e) {
            System.out.println(e);
        }
        
        return stage;
    }
    
    public Stage abrirTelaEspera(String caminho, Stage stage) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(caminho));
            
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            return stage;
        } catch (IOException e) {
        }
        
        return stage;
    }
    
    public Stage abrirAlerta(Stage stage) {
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/br/com/techfarma/view/view/Alerta.fxml"));
            
            Scene scene = new Scene(root);
            stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            
            return stage;
        } catch (IOException e) {
        }
        
        return stage;
    }
    
    public void fieldValidator(){
        Alert errorDg = new Alert(Alert.AlertType.WARNING);
        errorDg.initStyle(StageStyle.DECORATED);
        errorDg.setTitle("Atenção");
        errorDg.setHeaderText("Erro na validação de dados");
        errorDg.setContentText("Preencha o(s) campo(s) indicado(s).");
        errorDg.show();
    }
    
    public void insertSucess(String content){
        Alert successDg = new Alert(Alert.AlertType.INFORMATION);
        successDg.initStyle(StageStyle.DECORATED);
        successDg.setTitle("Sucesso!");
        successDg.setHeaderText(content+ " cadastrado(a) com sucesso!");
        successDg.show();
    }
    
    public void insertError(String titulo, String header, String content){
        Alert errorDg = new Alert(Alert.AlertType.ERROR);
        errorDg.initStyle(StageStyle.DECORATED);
        errorDg.setTitle(titulo);
        errorDg.setHeaderText(header);
        errorDg.setContentText(content);
        errorDg.show();
    }
    
    public void insertException(SQLException ex){
        ExceptionDialog exDg = new ExceptionDialog(new Exception(ex));
        exDg.initStyle(StageStyle.DECORATED);
        exDg.setTitle("Atenção");
        exDg.setHeaderText("Erro durante a inserção ao Banco de Dados, entre em contato com o suporte.");
        exDg.show();
    }
    
    public void infoDialog(String titulo, String header, String content){
        Alert infoDg = new Alert(Alert.AlertType.INFORMATION);
        infoDg.initStyle(StageStyle.DECORATED);
        infoDg.setTitle(titulo);
        infoDg.setHeaderText(header);
        infoDg.setContentText(content);
        infoDg.show();
    }
    
    public void removeSucess(String content){
        Alert removeDg = new Alert(Alert.AlertType.INFORMATION);
        removeDg.initStyle(StageStyle.DECORATED);
        removeDg.setTitle("Sucesso!");
        removeDg.setHeaderText(content+ " removido (a) com sucesso!");
        removeDg.show();
    }
    
    public void removeConfirmation(String content){
        Alert removeConf = new Alert(Alert.AlertType.CONFIRMATION);
        removeConf.initStyle(StageStyle.DECORATED);
        removeConf.setTitle("Tem certeza que deseja prosseguir?");
        
        Optional <ButtonType> action = removeConf.showAndWait();
        
        if(action.get() == ButtonType.OK){
            
        }
        removeConf.setHeaderText(content+ " removido (a) com sucesso!");
        removeConf.show();
    }
    
    public void updateSucess(String content){
        Alert removeDg = new Alert(Alert.AlertType.INFORMATION);
        removeDg.initStyle(StageStyle.DECORATED);
        removeDg.setTitle("Sucesso!");
        removeDg.setHeaderText(content+ " alterado (a) com sucesso!");
        removeDg.show();
    }
    
    Image bandeira;

    public Image getBandeira() {
        return bandeira;
    }

    public void insertBandeira(String caminho){
        File arquivo = new File(caminho);
        bandeira = new Image(arquivo.toURI().toString());
    }
    
    public boolean verificaCampos(int qtdCampos, ArrayList<TextField> aTxt) {
        boolean verificador = false;

        for (int i = 0; i < qtdCampos; i++) {
            if (aTxt.get(i).getText().isEmpty()) {
                aTxt.get(i).setId("validacao");
                aTxt.get(i).getStylesheets().add("/br/com/techfarma/view/css/Validation.css");
                aTxt.get(i).requestFocus();
                verificador = true;
            } else {
                aTxt.get(i).setId("validado");
            }
        }

        if (verificador) {
            fieldValidator();
            return false;
        } else {
            return true;
        }     
    }
    
    public boolean verificaComboBox(int qtdComboBox, ArrayList<ComboBox> aCB){
        boolean verificador = false;
        
        for (int i = 0; i < qtdComboBox; i++){
            if(aCB.get(i).getSelectionModel().isEmpty()){
                aCB.get(i).setId("validacao");
                aCB.get(i).getStylesheets().add("/br/com/techfarma/view/css/Validation.css");
                verificador = true;
            } else {
                aCB.get(i).setId("validado");
            }
        }
        
        if(verificador){
            return false;
        } else {
            return true;
        } 
    }
}
