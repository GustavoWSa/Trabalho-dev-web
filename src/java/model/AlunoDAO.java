package model;

import entidade.Aluno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/*
--
-- Estrutura para tabela `alunos`
--

DROP TABLE IF EXISTS `alunos`;
CREATE TABLE IF NOT EXISTS `alunos` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8 NOT NULL,
  `email` varchar(50) CHARACTER SET utf8 NOT NULL,
  `celular` char(14) CHARACTER SET utf8 NOT NULL,
  `cpf` varchar(14) CHARACTER SET utf8 NOT NULL,
  `senha` varchar(255) CHARACTER SET utf8 NOT NULL,
  `endereco` varchar(50) CHARACTER SET utf8 DEFAULT NULL,
  `cidade` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  `bairro` varchar(30) CHARACTER SET utf8 DEFAULT NULL,
  `cep` varchar(9) CHARACTER SET utf8 DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
 */

public class AlunoDAO implements Dao<Aluno> {

    @Override
    public Aluno get(int id) {
        Conexao conexao = new Conexao();
        Aluno aluno = new Aluno();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM Alunos WHERE ID = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                    aluno.setId(Integer.parseInt(resultado.getString("id")));
                    aluno.setNome(resultado.getString("nome"));
                    aluno.setEmail(resultado.getString("email"));
                    aluno.setCelular(resultado.getString("celular"));
                    aluno.setCpf(resultado.getString("cpf"));
                    aluno.setSenha(resultado.getString("senha"));
                    aluno.setEndereco(resultado.getString("endereco"));
                    aluno.setCidade(resultado.getString("cidade"));
                    aluno.setBairro(resultado.getString("bairro"));
                    aluno.setCep(resultado.getString("cep"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (get aluno) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return aluno;
    }

    @Override
    public void insert(Aluno t) {
    Conexao conexao = new Conexao();
    try {
        // Especifica as colunas no comando SQL
        PreparedStatement sql = conexao.getConexao().prepareStatement(
            "INSERT INTO Alunos (nome, email, celular, cpf, senha, endereco, cidade, bairro, cep) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)"
        );

        // Define os valores para cada coluna
        //sql.setInt(1, t.getId()); // Inclui o ID fornecido
        sql.setString(1, t.getNome());
        sql.setString(2, t.getEmail());
        sql.setString(3, t.getCelular());
        sql.setString(4, t.getCpf());
        sql.setString(5, t.getSenha());
        sql.setString(6, t.getEndereco());
        sql.setString(7, t.getCidade());
        sql.setString(8, t.getBairro());
        sql.setString(9, t.getCep());

        // Executa a query
        sql.executeUpdate();
        System.out.println("Aluno inserido com sucesso!");

    } catch (SQLException e) {
        System.err.println("Erro ao inserir aluno: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
}



    @Override
    public void update(Aluno aluno) {
    Conexao conexao = new Conexao();
    String sql = "UPDATE alunos SET nome = ?, email = ?, celular = ?, cpf = ?, senha = ?, endereco = ?, cidade = ?, bairro = ?, cep = ? WHERE id = ?";
    try (PreparedStatement stmt = conexao.getConexao().prepareStatement(sql)) {
        // Definindo os valores dos campos para a atualização
        stmt.setString(1, aluno.getNome());
        stmt.setString(2, aluno.getEmail());
        stmt.setString(3, aluno.getCelular());
        stmt.setString(4, aluno.getCpf());
        stmt.setString(5, aluno.getSenha());
        stmt.setString(6, aluno.getEndereco());
        stmt.setString(7, aluno.getCidade());
        stmt.setString(8, aluno.getBairro());
        stmt.setString(9, aluno.getCep());
        stmt.setInt(10, aluno.getId());

        // Executa a atualização no banco de dados
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Aluno atualizado com sucesso!");
        } else {
            System.out.println("Nenhum aluno encontrado com o ID especificado para atualização.");
        }

    } catch (SQLException e) {
        System.err.println("Erro ao atualizar aluno: " + e.getMessage());
        e.printStackTrace();
    } finally {
        conexao.closeConexao();
    }
}



    

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM Alunos WHERE ID = ? ");
            sql.setInt(1, id);
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de delete (excluir aluno) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public ArrayList<Aluno> getAll() {

        ArrayList<Aluno> meusAlunos = new ArrayList();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM Alunos";
            PreparedStatement preparedStatement;
            preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Aluno Aluno = new Aluno(
                            resultado.getInt("ID"),
                            resultado.getString("Nome"),
                            resultado.getString("Email"),
                            resultado.getString("Celular"),
                            resultado.getString("Cpf"),
                            resultado.getString("Senha"),
                            resultado.getString("Endereco"),
                            resultado.getString("Cidade"),
                            resultado.getString("Bairro"),
                            resultado.getString("Cep")
                                
                    );
                    meusAlunos.add(Aluno);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (GetAll - alunos) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return meusAlunos;
    }
}
    public Aluno Logar(Aluno Aluno) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM Aluno WHERE cpf=? and senha =? LIMIT 1");
            sql.setString(1, Aluno.getCpf());
            sql.setString(2, Aluno.getSenha());
            ResultSet resultado = sql.executeQuery();
            Aluno  AlunoObtido = new Aluno();
            if (resultado != null) {
                while (resultado.next()) {
                    AlunoObtido.setId(Integer.parseInt(resultado.getString("ID")));
                    AlunoObtido.setNome(resultado.getString("NOME"));
                    AlunoObtido.setCpf(resultado.getString("CPF"));
                    AlunoObtido.setEndereco(resultado.getString("ENDERECO"));
                    AlunoObtido.setSenha(resultado.getString("SENHA"));
                }
            }
            return AdministradorObtido;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Query de select (get) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }
