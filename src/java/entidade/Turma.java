package entidade;

public class Turma {
    private int id;
    private int professor_id;
    private int disciplina_id;
    private int aluno_id;
    private String codigoTurma;
    private double nota;


    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfessor_id() {
        return professor_id;
    }

    public void setProfessor_id(int professor_id) {
        this.professor_id = professor_id;
    }

    public int getDisciplina_id() {
        return disciplina_id;
    }

    public void setDisciplina_id(int disciplina_id) {
        this.disciplina_id = disciplina_id;
    }

    public int getAluno_id() {
        return aluno_id;
    }

    public void setAluno_id(int aluno_id) {
        this.aluno_id = aluno_id;
    }

    public String getCodigo_turma() {
        return codigoTurma;
    }

    public void setCodigo_turma(String codigoTurma) {
        this.codigoTurma = codigoTurma;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }
    public Turma(int id, int professor_id, int disciplina_id, int aluno_id, String codigoTurma, double nota) {
        this.id = id;
        this.professor_id = professor_id;
        this.disciplina_id = disciplina_id;
        this.aluno_id = aluno_id;
        this.codigoTurma = codigoTurma;
        this.nota = nota;
    }
    public Turma(int professor_id, int disciplina_id, int aluno_id, String codigoTurma, double nota) {
        
        this.professor_id = professor_id;
        this.disciplina_id = disciplina_id;
        this.aluno_id = aluno_id;
        this.codigoTurma = codigoTurma;
        this.nota = nota;
    }
    public Turma() {
        this.id = 0;
        this.professor_id = 0;
        this.disciplina_id = 0;
        this.aluno_id = 0;
        this.codigoTurma = "";
        this.nota = 0;
    }
}
