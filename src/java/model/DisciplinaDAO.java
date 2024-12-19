package model;

import entidade.Disciplina;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplinaDAO {
    private Connection connection;

    public DisciplinaDAO(Connection connection) {
        this.connection = connection;
    }

    // CREATE
    public void create(Disciplina disciplina) throws SQLException {
        String sql = "INSERT INTO disciplina (nome, requisito, emetenta, cargaHoraria) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, disciplina.getNome());
            stmt.setString(2, disciplina.getRequisito());
            stmt.setString(3, disciplina.getEmetenta());
            stmt.setInt(4, disciplina.getCargaHoraria());
            stmt.executeUpdate();
        }
    }

    // READ
    public List<Disciplina> listAll() throws SQLException {
        List<Disciplina> disciplinas = new ArrayList<>();
        String sql = "SELECT * FROM disciplina";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Disciplina disciplina = new Disciplina(
                    rs.getInt("id"),
                    rs.getString("nome"),
                    rs.getString("requisito"),
                    rs.getString("emetenta"),
                    rs.getInt("cargaHoraria")
                );
                disciplinas.add(disciplina);
            }
        }
        return disciplinas;
    }

    // UPDATE
    public void update(Disciplina disciplina) throws SQLException {
        String sql = "UPDATE disciplina SET nome = ?, requisito = ?, emetenta = ?, cargaHoraria = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, disciplina.getNome());
            stmt.setString(2, disciplina.getRequisito());
            stmt.setString(3, disciplina.getEmetenta());
            stmt.setInt(4, disciplina.getCargaHoraria());
            stmt.setInt(5, disciplina.getId());
            stmt.executeUpdate();
        }
    }

    // DELETE
    public void delete(int id) throws SQLException {
        String sql = "DELETE FROM disciplina WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
