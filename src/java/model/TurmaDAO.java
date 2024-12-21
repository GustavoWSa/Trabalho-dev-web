package model;

import entidade.Turma;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
--
-- Estrutura para tabela `turmas`
--

*/

public class TurmaDAO implements Dao<Turma> {

    @Override
    public Turma get(int id) {
        Conexao conexao = new Conexao();
        Turma turma = new Turma();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM turmas WHERE id = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                    turma.setId(Integer.parseInt(resultado.getString("id")));
                    turma.setCodigo_turma(resultado.getString(resultado.getString("codigo_turma")));
                    turma.setProfessor_id(Integer.parseInt("disciplina_id"));
                    turma.setDisciplina_id(Integer.parseInt("aluno_id"));
                    turma.setAluno_id(Integer.parseInt("codigo_turma"));
                    turma.setNota(Double.parseDouble("nota"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (get turma) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return turma;
    }

    @Override
    public void insert(Turma t) {
    Conexao conexao = new Conexao();
    try {
        // Especifica as colunas no comando SQL
        PreparedStatement sql = conexao.getConexao().prepareStatement(
            "INSERT INTO turmas (nome, email, cpf, senha) VALUES (?, ?, ?, ?)"
        );

        // Define os valores para cada coluna
        //sql.setInt(1, t.getId()); // Inclui o ID fornecido
        sql.setString(1, t.getCodigo_turma());
        sql.setString(2, t.getProfessor_id());
        sql.setString(3, t.getDisciplina_id());
        sql.setString(4, t.getAluno_id());

        // Executa a query
        sql.executeUpdate();
        System.out.println("Turma inserido com sucesso!");

    } catch (SQLException e) {
        System.err.println("Erro ao inserir turma: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
}



    @Override
    public void update(Turma turma) {
        Conexao conexao = new Conexao();
        String sql = "UPDATE turmas SET nome = ?, email = ?, cpf = ?, senha = ? WHERE id = ?";

        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, turma.getNome());
            stmt.setString(2, turma.getEmail());
            stmt.setString(3, turma.getDisciplina_id());
            stmt.setString(4, turma.getSenha());
            stmt.setInt(5, turma.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar turma: " + e.getMessage());
        }
    }

    

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM Turmas WHERE id = ? ");
            sql.setInt(1, id);
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de delete (excluir turma) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public ArrayList<Turma> getAll() {

        ArrayList<Turma> meusTurmas = new ArrayList();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM turmas";
            PreparedStatement preparedStatement;
            preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Turma Turma = new Turma(
                            resultado.getInt("id"),
                            resultado.getString("nome"),
                            resultado.getString("email"),
                            resultado.getString("cpf"),
                            resultado.getString("senha")
                                
                    );
                    meusTurmas.add(Turma);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (GetAll - turmas) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return meusTurmas;
    }
}
