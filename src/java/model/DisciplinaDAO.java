package model;

import java.sql.*;
import java.util.ArrayList;
import entidade.Disciplina;

public class DisciplinaDAO implements Dao<Disciplina> {

    @Override
    public Disciplina get(int id) {
        Conexao conexao = new Conexao();
        Disciplina disciplina = new Disciplina();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM disciplina WHERE id = ?");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();

            if (resultado != null && resultado.next()) {
                disciplina.setId(resultado.getInt("id"));
                disciplina.setNome(resultado.getString("nome"));
                disciplina.setRequisito(resultado.getString("requisito"));
                disciplina.setEmenta(resultado.getString("ementa"));
                disciplina.setCargaHoraria(resultado.getInt("carga_horaria"));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao obter disciplina: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return disciplina;
    }

    @Override
    public void insert(Disciplina t) {
        Conexao conexao = new Conexao();
        try {
            // Definindo a consulta SQL de inserção
            String query = "INSERT INTO disciplina (nome, requisito, ementa, carga_horaria) VALUES (?, ?, ?, ?)";
            
            // Preparando a instrução SQL
            PreparedStatement sql = conexao.getConexao().prepareStatement(query);
            
            // Definindo os valores dos parâmetros
            sql.setString(1, t.getNome());
            sql.setString(2, t.getRequisito());
            sql.setString(3, t.getEmenta());
            sql.setInt(4, t.getCargaHoraria());

            // Executando a inserção de dados no banco
            sql.executeUpdate();  // Usamos executeUpdate para executar o INSERT
            System.out.println("Disciplina inserida com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao inserir disciplina: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void update(Disciplina t) {
        Conexao conexao = new Conexao();
        try {
            String query = "UPDATE disciplina SET nome = ?, requisito = ?, ementa = ?, carga_horaria = ? WHERE id = ?";
            PreparedStatement sql = conexao.getConexao().prepareStatement(query);
            sql.setString(1, t.getNome());
            sql.setString(2, t.getRequisito());
            sql.setString(3, t.getEmenta());
            sql.setInt(4, t.getCargaHoraria());
            sql.setInt(5, t.getId());
            sql.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar disciplina: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            String query = "DELETE FROM disciplina WHERE id = ?";
            PreparedStatement sql = conexao.getConexao().prepareStatement(query);
            sql.setInt(1, id);
            sql.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir disciplina: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public ArrayList<Disciplina> getAll() {
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String query = "SELECT * FROM disciplina";
            PreparedStatement preparedStatement = conexao.getConexao().prepareStatement(query);
            ResultSet resultado = preparedStatement.executeQuery();

            while (resultado.next()) {
                Disciplina disciplina = new Disciplina(
                        resultado.getInt("id"),
                        resultado.getString("nome"),
                        resultado.getString("requisito"),
                        resultado.getString("ementa"),
                        resultado.getInt("carga_horaria")
                );
                disciplinas.add(disciplina);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar disciplinas: " + e.getMessage());
        } finally {
            conexao.closeConexao();
        }
        return disciplinas;
    }
}
