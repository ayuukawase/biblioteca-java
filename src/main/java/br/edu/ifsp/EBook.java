package br.edu.ifsp;

import java.util.ArrayList;
import java.util.List;

public class EBook {
    private int id;
    private String nome;
    private List<String> categorias = new ArrayList<>(); // igual ao diagrama: List<String>
    private String arquivo; // nome/caminho do arquivo do e-book

    public EBook(int id, String nome, List<String> categorias, String arquivo) {
        this.id = id;
        this.nome = nome;
        this.categorias = categorias;
        this.arquivo = arquivo;
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
    public String getArquivo(){
        return arquivo;
    }
}