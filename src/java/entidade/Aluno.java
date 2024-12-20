/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidade;

/**
 *
 * @author gusta
 */
public class Aluno {

    private int id;  
    private String nome;
    private String email;
    private String celular;
    private String cidade;
    private String bairro;
    private String cep;
    private String cpf; 
    private String senha;
    private String endereco;

    public Aluno(int id, String nome) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }
    
    
    public Aluno(int id, String nome, String email, String celular, String cpf, String senha, String endereco, String cidade, String bairro, String cep) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.cpf = cpf;
        this.senha = senha;
        this.endereco = endereco;
        this.cidade = cidade;
        this.bairro = bairro;
        this.cep = cep;
    }
    
    public Aluno( String nome, String email, String celular, String cpf, String senha, String endereco, String cidade, String bairro, String cep) {
        //this.id = id;
        this.nome = nome;
        this.email = email;
        this.celular = celular;
        this.cpf = cpf;
        this.senha = senha;
        this.endereco = endereco;
        this.cidade = cidade;
        this.bairro = bairro;
        this.cep = cep;
    }


    public Aluno(String cpf, String senha) {
        this.cpf = cpf;
        this.senha = senha;
    }

    public Aluno() {
        this.id = 0;
        this.nome = "";
        this.cpf = "";
        this.endereco = "";
        this.senha = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    

    
}
