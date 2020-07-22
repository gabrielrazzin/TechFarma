/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.techfarma.controller.caixa;

import controller.ConexaoController;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javax.swing.JOptionPane;
import model.ControleCaixaM;

/**
 *
 * @author Gabriel
 */
public class ControleCaixaC implements Initializable {

    ConexaoController conn = new ConexaoController();
    ControleCaixaM caixa = new ControleCaixaM();
    
    //Variáveis globais
    String status;
    int idFechamento;

    @FXML
    private Button btVendas, btACaixa, btAtualizar, btFCaixa, btRotFisc, btMenuF, btNotasM, btConfig, btSair;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        conn.Conexao();
    }

    public void verificaCaixa() {
        try {
            conn.executaSQL("SELECT idFechamento, status FROM dbo.FechamentoCaixa");
            
            if(conn.rs.last()){
                idFechamento = conn.rs.getInt("idFechamento");
                status = conn.rs.getString("status");
            } else {
                status = "C"; // Caso seja o primeiro caixa do banco de dados, inicializa a variável STATUS como C para determinar que o caixa está fechado.
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public void abrirCaixa() {
        verificaCaixa();

        if (status.equals("A")) {
            JOptionPane.showMessageDialog(null, "Atenção: Um caixa já está aberto!");
        } else {
            String SQL = "INSERT INTO dbo.FechamentoCaixa (idFilial, idOperador, idUsuario, idTerminal, dataAbertura, horaAbertura, status) VALUES (?,?,?,?,?,?,?)";

            caixa.setDataAbertura(new java.sql.Date(System.currentTimeMillis()));
            caixa.setHoraAbertura(new java.sql.Time(System.currentTimeMillis()));
            caixa.setStatus("A");

            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);

                pst.setInt(1, 1);
                pst.setInt(2, 1);
                pst.setInt(3, 1);
                pst.setInt(4, 1);
                pst.setDate(5, caixa.getDataAbertura());
                pst.setTime(6, caixa.getHoraAbertura());
                pst.setString(7, caixa.getStatus());

                JOptionPane.showMessageDialog(null, "Caixa aberto com sucesso!");
                pst.executeUpdate();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }

    public void fecharCaixa() {
        verificaCaixa();

        if (status.equals("C")) {
            JOptionPane.showMessageDialog(null, "Não há nenhum caixa aberto.");
        } else {
            String SQL = "UPDATE dbo.FechamentoCaixa SET dataFechamento = ?, horaFechamento = ?, status = ? WHERE idFechamento = " + idFechamento;

            caixa.setDataFechamento(new java.sql.Date(System.currentTimeMillis()));
            caixa.setHoraFechamento(new java.sql.Time(System.currentTimeMillis()));
            caixa.setStatus("C");

            try {
                PreparedStatement pst = conn.getConexao().prepareStatement(SQL);

                pst.setDate(1, caixa.getDataFechamento());
                pst.setTime(2, caixa.getHoraFechamento());
                pst.setString(3, caixa.getStatus());

                pst.executeUpdate();
                JOptionPane.showMessageDialog(null, "Caixa encerrado com sucesso!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, ex);
            }
        }
    }
}
