package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import model.DiretorioDB;

public class ConexaoController {
    
    DiretorioDB ddb = new DiretorioDB();

    public Statement stm; // Realiza pesquisas no Banco de dados
    public ResultSet rs; // Armazena o resultado de uma pesquisa realizada pelo o Statement
    private static String driver = "net.sourceforge.jtds.jdbc.Driver"; // Serviço do banco de dados
    private static String url = "jdbc:jtds:sqlserver://GABRIEL-PC:1433/farmaciamundial"; // Local do Banco de dados
    private static String user = "Mundialmaster"; // Usuário do Banco de dados
    private static String password = "123456"; // Senha do Banco de dados
    public static Connection conn; // Realiza a conexão com o Banco de dados
    
    public void Conexao(){
        try {
            System.setProperty("jdbc.Drivers", driver);
            conn = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao se conectar com o banco de dados: "+ex.getMessage());
        }
    }
    
    public Connection getConexao() {
        return conn;
    }
    
    public boolean verificaConexao(){
        try {
            System.setProperty("jdbc.Drivers", driver);
            conn = DriverManager.getConnection(url, user, password);
            //JOptionPane.showMessageDialog(null, "Conectado com sucesso !");
            //view.JFLogin.Icone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Unlocked.png")));
            return true;
        } catch (SQLException ex) {
            //view.JFLogin.Icone.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Imagens/Locked.png")));
            JOptionPane.showMessageDialog(null, "Erro de conexão: "+ex.getMessage());
            return false;
        }
    }
    
    public void executaSQL(String sql){
        try {
            stm = conn.createStatement(rs.TYPE_SCROLL_INSENSITIVE,rs.CONCUR_READ_ONLY);
            rs = stm.executeQuery(sql);
        } catch (SQLException ex) {
        }
    }
}