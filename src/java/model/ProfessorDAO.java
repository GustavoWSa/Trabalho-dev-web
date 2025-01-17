package model;

import entidade.Professor;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/*
--
-- Estrutura para tabela `professores`
--

DROP TABLE IF EXISTS `professores`;
CREATE TABLE IF NOT EXISTS `professores` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8 NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 NOT NULL,
  `cpf` varchar(14) CHARACTER SET utf8 NOT NULL,
  `senha` varchar(255) CHARACTER SET utf8 NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
*/

public class ProfessorDAO implements Dao<Professor> {

    @Override
    public Professor get(int id) {
        Conexao conexao = new Conexao();
        Professor professor = new Professor();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM professores WHERE id = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                    professor.setId(Integer.parseInt(resultado.getString("id")));
                    professor.setNome(resultado.getString("nome"));
                    professor.setEmail(resultado.getString("email"));
                    professor.setCpf(resultado.getString("cpf"));
                    professor.setSenha(resultado.getString("senha"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (get professor) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return professor;
    }

    @Override
    public void insert(Professor t) {
    Conexao conexao = new Conexao();
    try {
        // Especifica as colunas no comando SQL
        PreparedStatement sql = conexao.getConexao().prepareStatement(
            "INSERT INTO professores (nome, email, cpf, senha) VALUES (?, ?, ?, ?)"
        );

        // Define os valores para cada coluna
        //sql.setInt(1, t.getId()); // Inclui o ID fornecido
        sql.setString(1, t.getNome());
        sql.setString(2, t.getEmail());
        sql.setString(3, t.getCpf());
        sql.setString(4, t.getSenha());

        // Executa a query
        sql.executeUpdate();
        System.out.println("Professor inserido com sucesso!");

    } catch (SQLException e) {
        System.err.println("Erro ao inserir professor: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
}



    @Override
    public void update(Professor professor) {
        Conexao conexao = new Conexao();
        String sql = "UPDATE professores SET nome = ?, email = ?, cpf = ?, senha = ? WHERE id = ?";

        try (Connection conn = conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, professor.getNome());
            stmt.setString(2, professor.getEmail());
            stmt.setString(3, professor.getCpf());
            stmt.setString(4, professor.getSenha());
            stmt.setInt(5, professor.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar professor: " + e.getMessage());
        }
    }

    

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM Professores WHERE id = ? ");
            sql.setInt(1, id);
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de delete (excluir professor) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public ArrayList<Professor> getAll() {

        ArrayList<Professor> meusProfessores = new ArrayList();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM professores";
            PreparedStatement preparedStatement;
            preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Professor Professor = new Professor(
                            resultado.getInt("id"),
                            resultado.getString("nome"),
                            resultado.getString("email"),
                            resultado.getString("cpf"),
                            resultado.getString("senha")
                                
                    );
                    meusProfessores.add(Professor);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (GetAll - professores) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return meusProfessores;
    }

    public Professor Logar(String CPF, String Senha) throws Exception {
        Conexao conexao = new Conexao();
        Professor Professor = new Professor();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM Professores WHERE cpf=? and senha =? LIMIT 1");
            sql.setString(1, CPF);
            sql.setString(2, Senha);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Professor.setId(Integer.parseInt(resultado.getString("ID")));
                    Professor.setNome(resultado.getString("NOME"));
                    Professor.setCpf(resultado.getString("CPF"));
                    Professor.setEmail(resultado.getString("EMAIL"));
                    Professor.setSenha(resultado.getString("SENHA"));
                }
            }
            else{
                return Professor = null;
            }
            return Professor;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Query de select (get) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }
      
}

