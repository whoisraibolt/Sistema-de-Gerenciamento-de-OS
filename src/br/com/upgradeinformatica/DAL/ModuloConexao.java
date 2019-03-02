package br.com.upgradeinformatica.DAL;

import java.sql.*;

/**
 *
 * @author Alexandra Miguel Raibolt da Silva
 */
public class ModuloConexao {
    /* Método responsável por estabelecer a conexão com o banco de dados */
    public static Connection conector() {
        java.sql.Connection conexao = null;

        /* Registra o driver */
        String driver = "com.mysql.jdbc.Driver";

        /* Informações referenres ao banco de dados */
        String URL = "jdbc:mysql://localhost:3306/UPGRADE_INFORMATICA";
        String user = "root";
        String password = "";

        /* Estabelecendo a conexão com o banco de dados */
        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(URL, user, password);

            return conexao;

        } catch (ClassNotFoundException | SQLException e) {
            /* System.out.println(e); */
            return null;
        }
    }

    public static void closeConnection(Connection conexao, PreparedStatement pst) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}