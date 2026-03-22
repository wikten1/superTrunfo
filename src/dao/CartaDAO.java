package dao;

import model.Carta;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartaDAO {

    public List<Carta> buscarTodas() {
        List<Carta> cartas = new ArrayList<>();
        String sql = "SELECT * FROM carta ORDER BY id";

        try {
            Connection conn = ConexaoMySQL.getInstance();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Carta c = new Carta(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("grupo"),
                    rs.getInt("super_trunfo") == 1,
                    rs.getDouble("comprimento_mm"),
                    rs.getDouble("peso_mg"),
                    rs.getDouble("velocidade_kmh"),
                    rs.getInt("longevidade_dias"),
                    rs.getDouble("envergadura_mm"),
                    rs.getDouble("populacao_bilhoes"),
                    rs.getString("imagem_path")
                );
                cartas.add(c);
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cartas: " + e.getMessage());
            e.printStackTrace();
        }

        return cartas;
    }

    public Carta buscarPorNome(String nome) {
        String sql = "SELECT * FROM carta WHERE nome = ?";
        Carta carta = null;

        try {
            Connection conn = ConexaoMySQL.getInstance();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nome);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                carta = new Carta(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("grupo"),
                    rs.getInt("super_trunfo") == 1,
                    rs.getDouble("comprimento_mm"),
                    rs.getDouble("peso_mg"),
                    rs.getDouble("velocidade_kmh"),
                    rs.getInt("longevidade_dias"),
                    rs.getDouble("envergadura_mm"),
                    rs.getDouble("populacao_bilhoes"),
                    rs.getString("imagem_path")
                );
            }

            rs.close();
            ps.close();

        } catch (SQLException e) {
            System.err.println("Erro ao buscar carta por nome: " + e.getMessage());
            e.printStackTrace();
        }

        return carta;
    }
}