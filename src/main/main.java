package main;

import br.com.techfarma.controller.nfe.ModeloNFCeC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class main extends Application {
    //private ObservableList<FabricanteView> data;
    //ConexaoController conn = new ConexaoController();
    @Override
    public void start(Stage stage) throws Exception {
        //Parent root = FXMLLoader.load(getClass().getResource("/br/com/techfarma/view/view/Login.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/br/com/techfarma/view/view/Menu.fxml"));
    Parent root = FXMLLoader.load(getClass().getResource("/br/com/techfarma/view/view/EnvioContingencia.fxml"));
//
        //Parent root = FXMLLoader.load(getClass().getResource("/br/com/techfarma/view/cadastro/Cliente.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/br/com/techfarma/view/view/Configuracoes.fxml"));
//Parent root = FXMLLoader.load(getClass().getResource("/br/com/techfarma/view/view/Venda - Cartao.fxml"));

//        Parent root = FXMLLoader.load(getClass().getResource("/br/com/techfarma/view/caixa/Caixa - Checkout.fxml"));
//        Parent root = FXMLLoader.load(getClass().getResource("/br/com/techfarma/view/venda/Venda.fxml"));
        
        Scene scene = new Scene(root); 
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setTitle("Login");
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setResizable(false);
        stage.centerOnScreen();
        //stage.setMaximized(true);
        //stage.getIcons().add(new Image("/view/Images/gabriel.png"));
        //stage.initStyle(StageStyle.UNDECORATED);
        //stage.setFullScreen(true);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}