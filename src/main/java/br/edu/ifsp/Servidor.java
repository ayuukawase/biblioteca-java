package br.edu.ifsp;

public class Servidor extends Usuario{
    private TipoServidor tipo;

    public Servidor(String nome, int id, String email, String status, String senha, TipoServidor tipo){
        super(nome, id, email, status, senha);
        this.tipo = tipo;
    }

    public TipoServidor getTipo(){
        return tipo;
    }
    public boolean isProfessor(){
        return tipo == TipoServidor.PROFESSOR;
    }
}
