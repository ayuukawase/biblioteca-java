package br.edu.ifsp;

public class Aluno extends Usuario{
    private String curso;

    public Aluno(String nome, int id, String email, String status, String senha, String curso) {
        super(nome, id, email, status, senha); // tem que ser a primeira linha
        this.curso = curso;
    }

    public String getMatricula() {
        return curso;
    }
}
