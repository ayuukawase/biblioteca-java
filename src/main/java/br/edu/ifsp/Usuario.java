package br.edu.ifsp;

import java.time.LocalDate;

public class Usuario {
    private String nome;
    private int id;
    private String email;
    private boolean status; // true = ativo, false = suspenso
    private String senha;

    // Construtor: recebe os dados e guarda nos atributos do objeto
    public Usuario(String nome, int id, String email, boolean status, String senha) {
        this.nome = nome;     // "this" indica o atributo da classe (e não o parâmetro)
        this.id = id;
        this.email = email;
        this.status = status;
        this.senha = senha;
    }

    // ---------- Getters (só leem o valor do atributo) ----------
    public String getNome()  { return nome; }
    public int getId()       { return id; }
    public String getEmail() { return email; }
    public boolean isStatus(){ return status; }   // para boolean o padrão é "is"
    public String getSenha() { return senha; }

    // ---------- Setter do status (usado pelo Admin para suspender/reativar) ----------
    public void setStatus(boolean status) { this.status = status; }

    // Empresta um livro para este usuário. Devolve o Emprestimo criado ou null se não foi possível.
    public Emprestimo pegarLivro(Livro livro) {
        // Usuário suspenso ou livro indisponível: não pode emprestar
        if (!this.status || !livro.isStatus()) {
            return null;
        }

        LocalDate hoje = LocalDate.now();                 // data de hoje
        LocalDate prevista = hoje.plusDays(14);           // prazo de 14 dias (pode mudar)

        // Cria o empréstimo ligando este usuário (id) ao livro (id)
        Emprestimo emprestimo = new Emprestimo(this.id, livro.getId(), hoje, prevista);

        livro.setStatus(false);                           // livro agora está indisponível

        Biblioteca biblioteca = Biblioteca.getInstance(); // acessa os dados carregados do JSON
        biblioteca.getEmprestimos().add(emprestimo);      // guarda o empréstimo na lista
        biblioteca.salvar();                              // grava tudo de volta no arquivo JSON

        return emprestimo;
    }

    // Devolve o livro de um empréstimo
    public void devolverLivro(Emprestimo emprestimo) {
        emprestimo.setDataDevolucao(LocalDate.now());     // registra a data da devolução

        Biblioteca biblioteca = Biblioteca.getInstance();
        Livro livro = biblioteca.buscarLivro(emprestimo.getIdLivro()); // acha o livro pelo id
        if (livro != null) {
            livro.setStatus(true);                        // livro volta a ficar disponível
        }
        biblioteca.salvar();                              // grava no JSON
    }
}