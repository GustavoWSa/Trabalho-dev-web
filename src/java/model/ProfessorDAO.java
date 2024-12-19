package model;

import java.sql.*;
import java.util.ArrayList;
import entidade.Professor;

public class ProfessorDAO {

    public void inserir(Professor professor) throws SQLException {
        String sql = "INSERT INTO Professor (nome, email, cpf, senha) VALUES (?, ?, ?, ?)";
        try (Connection con = Conexao.getConexao(); 
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, professor.getNome());
            pst.setString(2, professor.getEmail());
            pst.setString(3, professor.getCpf());
            pst.setString(4, professor.getSenha());
            pst.executeUpdate();
        }
    }

    public Professor getProfessor(int id) throws SQLException {
        String sql = "SELECT * FROM Professor WHERE id = ?";
        try (Connection con = Conexao.getConexao(); 
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                Professor professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNome(rs.getString("nome"));
                professor.setEmail(rs.getString("email"));
                professor.setCpf(rs.getString("cpf"));
                professor.setSenha(rs.getString("senha"));
                return professor;
            }
        }
        return null;
    }

    public void atualizar(Professor professor) throws SQLException {
        String sql = "UPDATE Professor SET nome = ?, email = ?, cpf = ?, senha = ? WHERE id = ?";
        try (Connection con = Conexao.getConexao(); 
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, professor.getNome());
            pst.setString(2, professor.getEmail());
            pst.setString(3, professor.getCpf());
            pst.setString(4, professor.getSenha());
            pst.setInt(5, professor.getId());
            pst.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM Professor WHERE id = ?";
        try (Connection con = Conexao.getConexao(); 
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        }
    }

    public ArrayList<Professor> listarProfessores() throws SQLException {
        ArrayList<Professor> professores = new ArrayList<>();
        String sql = "SELECT * FROM Professor";
        try (Connection con = Conexao.getConexao(); 
             PreparedStatement pst = con.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Professor professor = new Professor();
                professor.setId(rs.getInt("id"));
                professor.setNome(rs.getString("nome"));
                professor.setEmail(rs.getString("email"));
                professor.setCpf(rs.getString("cpf"));
                professor.setSenha(rs.getString("senha"));
                professores.add(professor);
            }
        }
        return professores;
    }
}
