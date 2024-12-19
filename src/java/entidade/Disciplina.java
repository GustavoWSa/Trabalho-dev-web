package entidade;

public class Disciplina {
    private int id;
    private String nome;
    private String requisito;
    private String emetenta;
    private int cargaHoraria;

    // Construtores
    public Disciplina() {}

    public Disciplina(int id, String nome, String requisito, String emetenta, int cargaHoraria) {
        this.id = id;
        this.nome = nome;
        this.requisito = requisito;
        this.emetenta = emetenta;
        this.cargaHoraria = cargaHoraria;
    }

    // Getters e Setters
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

    public String getRequisito() {
        return requisito;
    }

    public void setRequisito(String requisito) {
        this.requisito = requisito;
    }

    public String getEmetenta() {
        return emetenta;
    }

    public void setEmetenta(String emetenta) {
        this.emetenta = emetenta;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }
}
