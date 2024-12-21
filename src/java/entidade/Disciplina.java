/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package entidade;


public class Disciplina {

    private int id;
    private String nome;
    private String requisito;
    private String ementa;
    private int cargaHoraria;

    public Disciplina() {
        this.id = 0;
        this.nome = "";
        this.requisito = "";
        this.ementa = "";
        this.cargaHoraria = 0;
    }

    public Disciplina(int id, String nome, int cargaHoraria) {
       throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    public String getRequisito() {
            return requisito;
      }

    public void setRequisito(String requisito) {
        this.requisito = requisito;
    }
     public String getEmenta() {
            return ementa;
      }

    public void setEmenta(String ementa) {
        this.ementa = ementa;
    }
    
      public Disciplina(int id, String nome, String requisito,String ementa, int cargaHoraria) {
        this.id = id;
        this.nome = nome;
        this.requisito = requisito;
        this.ementa = ementa;
        this.cargaHoraria = cargaHoraria;
    }
      
     public Disciplina(String nome, String requisito,String ementa, int cargaHoraria ) {
       // this.id = id;
        this.nome = nome;
        this.requisito = requisito;
        this.ementa = ementa;
        this.cargaHoraria = cargaHoraria;
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
    
     public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }  

}
