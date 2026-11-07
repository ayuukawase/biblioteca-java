package br.edu.ifsp;

import java.time.LocalDate;

public class Emprestimo {
    private int idUsuario;            // quem pegou o livro (referência ao id do usuário)
    private int idLivro;              // qual livro foi emprestado (referência ao id do livro)
    private LocalDate dataEmprestimo; // dia em que o livro saiu
    private LocalDate dataPrevista;   // dia limite para devolver
    private LocalDate dataDevolucao;  // dia em que foi devolvido (null = ainda não devolvido)

    public Emprestimo(int idUsuario, int idLivro, LocalDate dataEmprestimo, LocalDate dataPrevista) {
        this.idUsuario = idUsuario;
        this.idLivro = idLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataPrevista = dataPrevista;
        // dataDevolucao não entra no construtor: começa null
    }

    public int getIdUsuario(){
        return idUsuario;
    }
    public int getIdLivro(){
        return idLivro;
    }
    public LocalDate getDataEmprestimo() {
        return dataEmprestimo;
    }
    public LocalDate getDataPrevista(){
        return dataPrevista;
    }
    public LocalDate getDataDevolucao(){
        return dataDevolucao;
    }
    public void setDataDevolucao(LocalDate dataDevolucao){
        this.dataDevolucao = dataDevolucao;
    }

    // true se ainda não foi devolvido e o prazo já passou
    public boolean estaAtrasado() {
        return dataDevolucao == null && LocalDate.now().isAfter(dataPrevista);
    }
}