package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidade.Aluno;

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


public class AlunoDAO {

    public void Inserir(Aluno Aluno) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("INSERT INTO Alunos (id, nome, email, celular, cpf, senha, endereco, cidade, bairro, cep)"
                    + " VALUES (?,?,?,?,?,?,?,?,?,?)");
            sql.setInt(1, Aluno.getId());
            sql.setString(2, Aluno.getNome());
            sql.setString(3, Aluno.getEmail());
            sql.setString(4, Aluno.getCelular());
            sql.setString(5, Aluno.getCpf());
            sql.setString(6, Aluno.getSenha());
            sql.setString(7, Aluno.getEndereco());
            sql.setString(8, Aluno.getCidade());
            sql.setString(9, Aluno.getBairro());
            sql.setString(10, Aluno.getCep());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            conexao.closeConexao();
        }
    }

    public Aluno getAluno(int id) throws Exception {
        Conexao conexao = new Conexao();
        try {
            Aluno Aluno = new Aluno();
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM Alunos WHERE ID = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Aluno.setId(Integer.parseInt(resultado.getString("ID")));
                    Aluno.setNome(resultado.getString("NOME"));
                    Aluno.setEmail(resultado.getString("EMAIL"));
                    Aluno.setCelular(resultado.getString("CELULAR"));
                    Aluno.setCpf(resultado.getString("CPF"));
                    Aluno.setSenha(resultado.getString("SENHA"));
                    Aluno.setEndereco(resultado.getString("ENDERECO"));
                    Aluno.setCidade(resultado.getString("CIDADE"));
                    Aluno.setBairro(resultado.getString("BAIRRO"));
                    Aluno.setCep(resultado.getString("CEP"));
                }
            }
            return Aluno;

        } catch (SQLException e) {
            throw new RuntimeException("Query de select (get) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public void Alterar(Aluno Aluno) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE Alunos SET nome = ?, email = ?, celular = ?, cpf = ?, senha = ?, endereco = ?, cidade = ?, bairro = ?, cep = ?  WHERE ID = ? ");
            sql.setString(1, Aluno.getNome());
            sql.setString(2, Aluno.getEmail());
            sql.setString(3, Aluno.getCelular());
            sql.setString(4, Aluno.getCpf());
            sql.setString(5, Aluno.getSenha());
            sql.setString(6, Aluno.getEndereco());
            sql.setString(7, Aluno.getCidade());
            sql.setString(8, Aluno.getBairro());
            sql.setString(9, Aluno.getCep());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de update (alterar) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public void Excluir(Aluno Aluno) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM Aluno WHERE ID = ? ");
            sql.setInt(1, Aluno.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de delete (excluir) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public ArrayList<Aluno> ListaDeAlunos() {
        ArrayList<Aluno> meusAlunos = new ArrayList();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM Aluno order by nome";
            PreparedStatement preparedStatement;
            preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Aluno Aluno = new Aluno(resultado.getString("NOME"),
                            resultado.getString("EMAIL"),
                            resultado.getString("CELULAR"),
                            resultado.getString("CPF"),
                            resultado.getString("SENHA"),
                            resultado.getString("ENDERECO"),
                            resultado.getString("CIDADE"),
                            resultado.getString("BAIRRO"),
                            resultado.getString("CEP"));
                    Aluno.setId(Integer.parseInt(resultado.getString("id")));
                    meusAlunos.add(Aluno);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query de select (ListaDeAlunos) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return meusAlunos;
    }

    public Aluno Logar(Aluno Aluno) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM Alunos WHERE cpf=? and senha =? LIMIT 1");
            sql.setString(1, Aluno.getCpf());
            sql.setString(2, Aluno.getSenha());
            ResultSet resultado = sql.executeQuery();
            Aluno AlunoObtido = new Aluno();
            if (resultado != null) {
                while (resultado.next()) {
                    AlunoObtido.setId(Integer.parseInt(resultado.getString("ID")));
                    AlunoObtido.setNome(resultado.getString("NOME"));
                    AlunoObtido.setCpf(resultado.getString("CPF"));
                    AlunoObtido.setEndereco(resultado.getString("ENDERECO"));
                    AlunoObtido.setSenha(resultado.getString("SENHA"));
                }
            }
            return AlunoObtido;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Query de select (get) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

}
