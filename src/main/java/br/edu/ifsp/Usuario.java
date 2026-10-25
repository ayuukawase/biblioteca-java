package br.edu.ifsp;

public class Usuario {
    private String nome;
    private int id;
    private String email;
    private String status;//pensar sobre isso
    private String senha;

    public Usuario(String nome, int id, String email, String status, String senha){
        this.nome = nome;
        this.id = id;
        this.email = email;
        this.status = status;
        this.senha = senha;
    }
    public String getNome(){
        return nome;
    }
    public int getId(){
        return id;
    }
    public String getEmail(){
        return email;
    }
    public String getStatus(){
        return status;
    }
    public String getSenha(){
        return senha;
    }
}
