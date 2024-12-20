package model;

import java.sql.*;
import java.util.ArrayList;
import entidade.Disciplina;

/*
--
-- Estrutura para tabela `disciplina`
--

DROP TABLE IF EXISTS `disciplina`;
CREATE TABLE IF NOT EXISTS `disciplina` (
  `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) CHARACTER SET utf8 NOT NULL,
  `requisito` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `ementa` varchar(255) CHARACTER SET utf8 DEFAULT NULL,
  `carga_horaria` smallint(5) UNSIGNED DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 

 */

public class DisciplinaDAO implements Dao<Disciplina> {

    @Override
    public Disciplina get(int id) {
        Conexao conexao = new Conexao();
        Disciplina disciplina = new Disciplina();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM disciplina WHERE id = ?");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                   disciplina.setId(Integer.parseInt(resultado.getString("id")));
                   disciplina.setNome(resultado.getString("nome"));
                   disciplina.setRequisito(resultado.getString("requisito"));
                   disciplina.setEmenta(resultado.getString("ementa"));
                   disciplina.setCargaHoraria(Integer.parseInt(resultado.getString("carga_horaria")));
                 }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (get disciplina) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return disciplina;
    }

    @Override
    public void insert(Disciplina t) {
        Conexao conexao = new Conexao();
        try {
           // Especifica as colunas no comando SQL
        PreparedStatement sql = conexao.getConexao().prepareStatement(
            "INSERT INTO disciplina (nome, requisito, ementa, carga_horaria) VALUES (?, ?, ?, ?)"
        );
          // Define os valores para cada coluna
        //sql.setInt(1, t.getId()); // Inclui o ID fornecido
            sql.setString(1, t.getNome());
            sql.setString(2, t.getRequisito());
            sql.setString(3, t.getEmenta());
            sql.setInt(4, t.getCargaHoraria());

        // Executa a query
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
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE disciplina SET nome = ?, requisito = ?, ementa = ?, carga_horaria = ? WHERE id = ?");
            sql.setString(1, t.getNome());
            sql.setString(2, t.getRequisito());
            sql.setString(3, t.getEmenta());
            sql.setInt(4, t.getCargaHoraria());
            sql.setInt(5, t.getId());
            sql.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Query de update (alterar disciplina) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM disciplina WHERE id = ?");
            sql.setInt(1, id);
            sql.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Query de delete (excluir disciplina) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    @Override
    public ArrayList<Disciplina> getAll() {
        ArrayList<Disciplina> disciplinas = new ArrayList<>();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM disciplina";
            PreparedStatement preparedStatement;
            preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
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
            }
        } catch (SQLException e) {
             System.err.println("Query de select (GetAll - disciplinas) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return disciplinas;
    }
}

