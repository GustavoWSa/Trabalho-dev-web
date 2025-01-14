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
                    turma.setCodigoTurma(resultado.getString(resultado.getString("codigo_turma")));
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
            "INSERT INTO turmas (professor_id, disciplina_id, aluno_id, codigoTurma, nota) VALUES (?, ?, ?, ?, ?)"
        );

        // Define os valores para cada coluna
        //sql.setInt(1, t.getId()); // Inclui o ID fornecido
        sql.setInt(1, t.getProfessor_id());
        sql.setInt(2, t.getDisciplina_id());
        sql.setInt(3, t.getAluno_id());
        sql.setString(4, t.getCodigoTurma());
        sql.setDouble(5, t.getNota());
        
        
        

        // Executa a query
        sql.executeUpdate();
        System.out.println("Turma inserida com sucesso!");

    } catch (SQLException e) {
        System.err.println("Erro ao inserir turma: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
}



    @Override
    public void update(Turma turma) {
        Conexao conexao = new Conexao();
        String sql = "UPDATE turmas SET professor_id = ?, disciplina_id = ?, aluno_id = ?, codigoTurma = ?, nota = ? WHERE id = ?";

        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, turma.getProfessor_id());
            stmt.setInt(2, turma.getDisciplina_id());
            stmt.setInt(3, turma.getAluno_id());
            stmt.setString(4, turma.getCodigoTurma());
            stmt.setDouble(5, turma.getNota());
            stmt.setInt(6, turma.getId());

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

        ArrayList<Turma> minhasTurmas = new ArrayList();
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
                            resultado.getInt("professor_id"),
                            resultado.getInt("disciplina_id"),
                            resultado.getInt("aluno_id"),
                            resultado.getString("codigoTurma"),
                            resultado.getDouble("nota")
                                
                    );
                    minhasTurmas.add(Turma);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (GetAll - turmas) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return minhasTurmas;
    }
}
