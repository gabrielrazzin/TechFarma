package controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ProgressBar;

public class CurvaABC implements Initializable {
    ConexaoController conn = new ConexaoController();
    
    double totalVendas;
    private int tamTotal;
    
    ObservableList<CurvaABCM> data = FXCollections.observableArrayList();
    ObservableList<CurvaABCM> data2 = FXCollections.observableArrayList();
    
    @FXML
    private ProgressBar pbCurvaABC;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
    }

    public void executaThread(){
        calculaTotalProduto();
        calculaPercCurva();
    }
    
    private void calculaTotalVendas() {
        conn.executaSQL("SELECT SUM (PP.PMC * PE.estoque) AS TotalVenda FROM Produto.Produto AS PP, Produto.Estoque AS PE WHERE PP.idProduto = PE.idProduto");
       
        try {
            conn.rs.first();
            totalVendas = conn.rs.getDouble(1);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
//    private void calculaTotalPMC(){
//        conn.executaSQL("SELECT PP.idProduto, (PP.PMC * PE.estoque) AS TotalVenda FROM Produto.Produto AS PP, Produto.Estoque AS PE WHERE PP.idProduto = PE.idProduto");
//        
//        try {
//            while(conn.rs.next()){
//                data.add(new CurvaABCM(conn.rs.getInt(1), conn.rs.getDouble(2)));
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//    }

//    private void calculaPorcentagemVenda(){
//        calculaTotalVendas();
//        calculaTotalPMC();
//        DecimalFormat df = new DecimalFormat("#.##");
//        
//        for(int i = 0; i < data.size(); i++){
//            double perc = Double.valueOf(df.format((data.get(i).getTotalPMC() / totalVendas) * 100).replaceAll(",", "."));
//            data2.add(new CurvaABCM(data.get(i).getIdProduto(), data.get(i).getTotalPMC(), perc));
//        }
//        
//        data2.sort(new Comparator<CurvaABCM>() {
//            @Override
//            public int compare(CurvaABCM o1, CurvaABCM o2) {
//                if(o1.getTotalPMC() > )
//            }
//        });
//        
//        System.out.println(data2);
//    }
    
    private void calculaTotalProduto(){
        conn.executaSQL("SELECT PP.idProduto, (PP.PMC * PE.estoque) AS TotalVenda FROM Produto.Produto AS PP, Produto.Estoque AS PE WHERE PP.idProduto = PE.idProduto ORDER BY idProduto");
        
        try {
            for(int i = 0; i <= 5000; i++){
                conn.rs.next();
                String SQL = "INSERT INTO temp_TotalPMC (idProduto, totalPMC) VALUES (?,?)";
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setInt(1, conn.rs.getInt(1));
                pst.setDouble(2, conn.rs.getDouble(2));
                
                pst.executeUpdate();
            }
        } catch (SQLException e) {
        }
    }
    
    private void calculaPorcentagem(){
        calculaTotalVendas();
        DecimalFormat df = new DecimalFormat("#.##");
        int idProduto;
        double totalPerc;
        
        conn.executaSQL("SELECT idProduto, totalPMC FROM temp_TotalPMC");
        
        try {
            for(int i = 0; i <= 5000; i++){
                conn.rs.next();
                idProduto = conn.rs.getInt(1);
                totalPerc = Double.valueOf(df.format((conn.rs.getDouble(2) / totalVendas) * 100).replaceAll(",", "."));

                String SQL = "UPDATE temp_TotalPMC SET totalPerc = ? WHERE idProduto = " + idProduto + "";

                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                pst.setDouble(1, totalPerc);
                pst.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    private void calculaTamTabela(){
        conn.executaSQL("SELECT COUNT (*) AS TamTotal FROM temp_TotalPMC");
        try {
            conn.rs.first();
            tamTotal = conn.rs.getInt("TamTotal");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
    
    
    private void calculaPercCurva() {
        calculaPorcentagem();
        calculaTamTabela();
        conn.executaSQL("SELECT idProduto, totalPerc FROM temp_TotalPMC ORDER BY totalPerc DESC");
        double anterior = 0.00;
        double atual = 0.00;

        try {
            conn.rs.first();
            for (int i = 1; i <= 5000; i++) {
                if (i == 1) {
                    String SQL = "UPDATE temp_TotalPMC SET totalPerc2 = ? WHERE idProduto = " + conn.rs.getInt(1) + "";
                    atual = conn.rs.getDouble(2);
                    PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                    pst.setDouble(1, conn.rs.getDouble(2));
                    pst.executeUpdate();
                    anterior = atual;
                } else {
                    String SQL = "UPDATE temp_TotalPMC SET totalPerc2 = ? WHERE idProduto = " + conn.rs.getInt(1) + "";

                    atual = anterior + conn.rs.getDouble(2);
                    PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                    pst.setDouble(1, atual);
                    pst.executeUpdate();
                    anterior = atual;
                }
                
                conn.rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void calculaCurva(){
        calculaTamTabela();
        conn.executaSQL("SELECT idProduto, totalPerc2 FROM temp_TotalPMC ORDER BY totalPerc2 ASC");
        
        try {
            conn.rs.first();

            for (int i = 1; i <= tamTotal; i++) {
                if(conn.rs.getDouble(2) < 80){
                    String SQL = "UPDATE temp_TotalPMC SET curva = 'A' WHERE idProduto = " + conn.rs.getInt(1) + "";
                    PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                    pst.executeUpdate();
                } else if (conn.rs.getDouble(2) > 80 && conn.rs.getDouble(2) <= 95) {
                    String SQL = "UPDATE temp_TotalPMC SET curva = 'B' WHERE idProduto = " + conn.rs.getInt(1) + "";
                    PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                    pst.executeUpdate();
                } else {
                    String SQL = "UPDATE temp_TotalPMC SET curva = 'C' WHERE idProduto = " + conn.rs.getInt(1) + "";
                    PreparedStatement pst = conn.getConexao().prepareStatement(SQL);
                    pst.executeUpdate();
                }
                conn.rs.next();
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
