package dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoMySQL {

    private static Connection instance;

    private ConexaoMySQL() {}

    public static Connection getInstance() throws SQLException {
        if (instance == null || instance.isClosed()) {
            try {
                Properties props = new Properties();
                InputStream input = ConexaoMySQL.class
                        .getClassLoader()
                        .getResourceAsStream("config.properties");

                if (input == null) {
                    throw new SQLException("Arquivo config.properties não encontrado!");
                }

                props.load(input);

                String url  = props.getProperty("db.url");
                String user = props.getProperty("db.user");
                String pass = props.getProperty("db.password");

                Class.forName("com.mysql.jdbc.Driver");
                instance = DriverManager.getConnection(url, user, pass);

            } catch (ClassNotFoundException e) {
                throw new SQLException("Driver MySQL não encontrado. Verifique o Build Path.", e);
            } catch (IOException e) {
                throw new SQLException("Erro ao ler config.properties.", e);
            }
        }
        return instance;
    }

    public static void fechar() {
        try {
            if (instance != null && !instance.isClosed()) {
                instance.close();
                instance = null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}