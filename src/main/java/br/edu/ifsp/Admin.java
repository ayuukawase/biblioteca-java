package br.edu.ifsp;

import java.util.ArrayList;
import java.util.List;

public class Admin {
    private int id;
    private String senha;

    public Admin(int id, String senha) {
        this.id = id;
        this.senha = senha;
    }

    public int getId()       { return id; }
    public String getSenha() { return senha; }

    // ================= LIVROS =================

    // Adiciona um livro novo
    public void criarLivro(Livro livro) {
        Biblioteca b = Biblioteca.getInstance();
        b.getLivros().add(livro); // coloca na lista
        b.salvar();               // grava no JSON
    }

    // Troca o livro que tem o mesmo id pelo livro atualizado
    public void atualizarLivro(Livro livroAtualizado) {
        Biblioteca b = Biblioteca.getInstance();
        List<Livro> livros = b.getLivros();
        for (int i = 0; i < livros.size(); i++) {
            if (livros.get(i).getId() == livroAtualizado.getId()) {
                livros.set(i, livroAtualizado); // substitui na posição i
                break;                          // já achou, para o laço
            }
        }
        b.salvar();
    }

    // Remove o livro com o id informado
    public void deletarLivro(int idLivro) {
        Biblioteca b = Biblioteca.getInstance();
        b.getLivros().removeIf(l -> l.getId() == idLivro); // remove todo livro cujo id bate
        b.salvar();
    }

    // ================= USUÁRIOS =================

    // Cadastra um Aluno ou um Servidor, cada um na sua lista
    public void cadastrarUsuario(Usuario usuario) {
        Biblioteca b = Biblioteca.getInstance();
        if (usuario instanceof Aluno) {                    // instanceof: verifica a classe do objeto
            b.getAlunos().add((Aluno) usuario);            // (Aluno) = converte para Aluno
        } else if (usuario instanceof Servidor) {
            b.getServidores().add((Servidor) usuario);
        }
        b.salvar();
    }

    // Suspende o usuário (status = false); ele não consegue mais pegar livros
    public void suspenderUsuario(int idUsuario) {
        Biblioteca b = Biblioteca.getInstance();
        Usuario u = b.buscarUsuario(idUsuario);
        if (u != null) {
            u.setStatus(false);
            b.salvar();
        }
    }

    // Reativa o usuário (status = true); ele volta a poder pegar livros
    public void reativarUsuario(int idUsuario) {
        Biblioteca b = Biblioteca.getInstance();
        Usuario u = b.buscarUsuario(idUsuario);
        if (u != null) {
            u.setStatus(true);
            b.salvar();
        }
    }

    // Remove o usuário (aluno ou servidor) com o id informado
    public void deletarUsuario(int idUsuario) {
        Biblioteca b = Biblioteca.getInstance();
        b.getAlunos().removeIf(a -> a.getId() == idUsuario);
        b.getServidores().removeIf(s -> s.getId() == idUsuario);
        b.salvar();
    }

    // ================= E-BOOKS =================

    public void inserirEBook(EBook ebook) {
        Biblioteca b = Biblioteca.getInstance();
        b.getEbooks().add(ebook);
        b.salvar();
    }

    public void atualizarEBook(EBook ebookAtualizado) {
        Biblioteca b = Biblioteca.getInstance();
        List<EBook> ebooks = b.getEbooks();
        for (int i = 0; i < ebooks.size(); i++) {
            if (ebooks.get(i).getId() == ebookAtualizado.getId()) {
                ebooks.set(i, ebookAtualizado);
                break;
            }
        }
        b.salvar();
    }

    public void deletarEBook(int idEBook) {
        Biblioteca b = Biblioteca.getInstance();
        b.getEbooks().removeIf(e -> e.getId() == idEBook);
        b.salvar();
    }

    // ================= EMPRÉSTIMOS =================

    // Lista só os empréstimos feitos por alunos (ignora os dos servidores)
    public List<Emprestimo> listarEmprestimosAlunos() {
        Biblioteca b = Biblioteca.getInstance();
        List<Emprestimo> resultado = new ArrayList<>();
        for (Emprestimo e : b.getEmprestimos()) {
            for (Aluno a : b.getAlunos()) {
                if (a.getId() == e.getIdUsuario()) { // o dono do empréstimo é um aluno?
                    resultado.add(e);
                    break;
                }
            }
        }
        return resultado;
    }
}