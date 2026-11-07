package br.edu.ifsp;

// Aluno herda tudo de Usuario (nome, id, email, status, senha e os métodos)
public class Aluno extends Usuario {
    private String curso;

    public Aluno(String nome, int id, String email, boolean status, String senha, String curso) {
        super(nome, id, email, status, senha); // chama o construtor de Usuario (tem que ser a primeira linha)
        this.curso = curso;
    }

    public String getCurso() {
        return curso;
    }
}