package br.edu.ifsp;

import java.util.ArrayList;
import java.util.List;

public class Livro {
    private int id;
    private String nome;
    private List<String> categorias = new ArrayList<>(); // lista de categorias do livro
    private boolean status; // true = disponível, false = emprestado

    public Livro(int id, String nome, List<String> categorias, boolean status) {
        this.id = id;
        this.nome = nome;
        this.categorias = categorias;
        this.status = status;
    }

    public int getId(){
        return id;
    }
    public String getNome(){
        return nome;
    }
    public List<String> getCategorias(){
        return categorias;
    }
    public boolean isStatus(){
        return status;
    }
    public void setStatus(boolean status){
        this.status = status;
    }
}