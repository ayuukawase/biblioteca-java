package br.edu.ifsp;

// Servidor também herda de Usuario
public class Servidor extends Usuario {
    private TipoServidor tipo; // PROFESSOR ou TECNICO_ADMINISTRATIVO

    public Servidor(String nome, int id, String email, boolean status, String senha, TipoServidor tipo) {
        super(nome, id, email, status, senha); // dados comuns vão para Usuario
        this.tipo = tipo;
    }

    public TipoServidor getTipo() {
        return tipo;
    }

    // true se o servidor for professor
    public boolean isProfessor() {
        return tipo == TipoServidor.PROFESSOR;
    }
}