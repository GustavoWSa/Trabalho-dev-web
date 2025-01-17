package model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import entidade.Administrador;
import java.sql.Connection;

/*
-- Estrutura da tabela `Administradors`

CREATE TABLE IF NOT EXISTS `Administrador` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nome` varchar(40) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `senha` varchar(8) NOT NULL,
  `endereco` varchar(40) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

 */
public class AdministradorDAO implements Dao<Administrador> {

    public void Inserir(Administrador Administrador) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("INSERT INTO Administrador (nome, cpf, senha ,aprovado, endereco)"
                    + " VALUES (?,?,?,?,?)");
            sql.setString(1, Administrador.getNome());
            sql.setString(2, Administrador.getCpf());
            sql.setString(3, Administrador.getSenha());
            sql.setString(4, Administrador.getAprovado());
            sql.setString(5, Administrador.getEndereco());
            
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException();
        } finally {
            conexao.closeConexao();
        }
    }

    public Administrador getAdministrador(int id) throws Exception {
        Conexao conexao = new Conexao();
        try {
            Administrador Administrador = new Administrador();
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM Administrador WHERE ID = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Administrador.setId(Integer.parseInt(resultado.getString("ID")));
                    Administrador.setNome(resultado.getString("NOME"));
                    Administrador.setCpf(resultado.getString("CPF"));
                    Administrador.setSenha(resultado.getString("SENHA"));
                    Administrador.setAprovado(resultado.getString("APROVADO"));
                    Administrador.setEndereco(resultado.getString("ENDERECO"));
                    
                }
            }
            return Administrador;

        } catch (SQLException e) {
            throw new RuntimeException("Query de select (get) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public void Alterar(Administrador Administrador) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("UPDATE Administrador SET nome = ?, cpf = ?,  senha = ?, aprovado = ?, endereco = ?  WHERE ID = ? ");
            sql.setString(1, Administrador.getNome());
            sql.setString(2, Administrador.getCpf());
            sql.setString(3, Administrador.getAprovado());
            sql.setString(4, Administrador.getSenha());
            sql.setString(5, Administrador.getEndereco());
            
            sql.setInt(5, Administrador.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de update (alterar) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public void Excluir(Administrador Administrador) throws Exception {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM Administrador WHERE ID = ? ");
            sql.setInt(1, Administrador.getId());
            sql.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Query de delete (excluir) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }

    public ArrayList<Administrador> ListaDeAdministrador() {
        ArrayList<Administrador> meusAdministradores = new ArrayList();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM Administrador order by nome";
            PreparedStatement preparedStatement;
            preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Administrador Administrador = new Administrador(resultado.getString("NOME"),
                            resultado.getString("CPF"),
                            resultado.getString("ENDERECO"),
                            resultado.getString("SENHA"));
                    Administrador.setId(Integer.parseInt(resultado.getString("id")));
                    meusAdministradores.add(Administrador);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Query de select (ListaDeAdministradores) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return meusAdministradores;
    }

    public Administrador Logar(String CPF, String Senha) throws Exception {
        Conexao conexao = new Conexao();
        Administrador Administrador = new Administrador();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM Administrador WHERE cpf=? and senha =? LIMIT 1");
            sql.setString(1, CPF);
            sql.setString(2, Senha);
            ResultSet resultado = sql.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Administrador.setId(Integer.parseInt(resultado.getString("ID")));
                    Administrador.setNome(resultado.getString("NOME"));
                    Administrador.setCpf(resultado.getString("CPF"));
                    Administrador.setAprovado(resultado.getString("APROVADO"));
                    Administrador.setSenha(resultado.getString("SENHA"));
                }
            }
            else{
                return Administrador = null;
            }
            return Administrador;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new RuntimeException("Query de select (get) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }
    @Override
    public void delete(int id) {
        Conexao conexao = new Conexao();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("DELETE FROM Administrador WHERE ID = ? ");
            sql.setInt(1, id);
            sql.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Query de delete (excluir administrador) incorreta");
        } finally {
            conexao.closeConexao();
        }
    }
    @Override
    public ArrayList<Administrador> getAll() {

        ArrayList<Administrador> meusAdministradores = new ArrayList();
        Conexao conexao = new Conexao();
        try {
            String selectSQL = "SELECT * FROM Administrador";
            PreparedStatement preparedStatement;
            preparedStatement = conexao.getConexao().prepareStatement(selectSQL);
            ResultSet resultado = preparedStatement.executeQuery();
            if (resultado != null) {
                while (resultado.next()) {
                    Administrador Administrador;
                    Administrador = new Administrador(
                                                        resultado.getString("Nome"),
                            resultado.getString("Cpf"),
                            resultado.getString("Senha"),
                            resultado.getString("Aprovado"),
                            resultado.getString("Endereco")          
                        );
                    meusAdministradores.add(Administrador);
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (GetAll - administradores) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return meusAdministradores;
    }

    @Override
    public Administrador get(int id) {
        Conexao conexao = new Conexao();
        Administrador administrador = new Administrador();
        try {
            PreparedStatement sql = conexao.getConexao().prepareStatement("SELECT * FROM Administrador WHERE ID = ? ");
            sql.setInt(1, id);
            ResultSet resultado = sql.executeQuery();

            if (resultado != null) {
                while (resultado.next()) {
                    administrador.setId(Integer.parseInt(resultado.getString("id")));
                    administrador.setNome(resultado.getString("nome"));
                    administrador.setCpf(resultado.getString("cpf"));
                    administrador.setAprovado(resultado.getString("aprovado"));
                    administrador.setEndereco(resultado.getString("endereco"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Query de select (get Administrador) incorreta");
        } finally {
            conexao.closeConexao();
        }
        return administrador;
    }

    @Override
    public void insert(Administrador t) {
    Conexao conexao = new Conexao();
    try {
        // Especifica as colunas no comando SQL
        PreparedStatement sql = conexao.getConexao().prepareStatement(
            "INSERT INTO Administrador (nome, cpf, senha,aprovado, endereco) VALUES (?, ?, ?, ?, ?)"
        );

        // Define os valores para cada coluna
        //sql.setInt(1, t.getId()); // Inclui o ID fornecido
        sql.setString(1, t.getNome());
        sql.setString(2, t.getCpf());
        sql.setString(3, t.getSenha());
        sql.setString(4, t.getAprovado());
        sql.setString(5, t.getEndereco());

        // Executa a query
        sql.executeUpdate();
        System.out.println("Administrador inserido com sucesso!");

    } catch (SQLException e) {
        System.err.println("Erro ao inserir administrador: " + e.getMessage());
    } finally {
        conexao.closeConexao();
    }
}



    @Override
public void update(Administrador administrador) {
    Conexao conexao = new Conexao();
    String sql = "UPDATE administradors SET nome = ?,  cpf = ?, senha = ?,aprovado = ?, endereco = ? WHERE id = ?";

    try (Connection conn = conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        // Definindo os valores dos campos para a atualização
        stmt.setString(1, administrador.getNome());
        stmt.setString(2, administrador.getCpf());
        stmt.setString(3, administrador.getSenha());
        stmt.setString(4, administrador.getAprovado());
        stmt.setString(5, administrador.getEndereco());
        stmt.setInt(6, administrador.getId());

        // Executa a atualização no banco de dados
        int rowsAffected = stmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Administrador atualizado com sucesso!");
        } else {
            System.out.println("Nenhum administrador encontrado com o ID especificado para atualização.");
        }

    } catch (SQLException e) {
        System.err.println("Erro ao atualizar administrador: " + e.getMessage());
        e.printStackTrace();
    } finally {
        conexao.closeConexao();
    }
}
}
