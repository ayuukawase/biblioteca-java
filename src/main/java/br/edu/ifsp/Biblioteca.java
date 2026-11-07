package br.edu.ifsp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// Guarda todos os dados do sistema e sabe ler/gravar o arquivo JSON.
// Os nomes dos atributos abaixo são os mesmos nomes das chaves do JSON.
public class Biblioteca {

    private static final Path ARQUIVO = Paths.get("dados", "biblioteca.json"); // onde o JSON fica

    // Gson é a biblioteca que converte JSON <-> objetos Java
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter()) // ensina a lidar com datas
            .setPrettyPrinting()                                          // JSON identado ao gravar
            .create();

    private static Biblioteca instancia; // única cópia dos dados na memória (Singleton)

    // Listas preenchidas pelo Gson ao ler o JSON
    private List<Admin> admins = new ArrayList<>();
    private List<Aluno> alunos = new ArrayList<>();
    private List<Servidor> servidores = new ArrayList<>();
    private List<Livro> livros = new ArrayList<>();
    private List<EBook> ebooks = new ArrayList<>();
    private List<Emprestimo> emprestimos = new ArrayList<>();

    private Biblioteca() { } // privado: ninguém cria direto, só via getInstance()

    // Devolve os dados; carrega do arquivo na primeira vez
    public static Biblioteca getInstance() {
        if (instancia == null) {
            instancia = carregar();
        }
        return instancia;
    }

    // Lê o arquivo JSON e transforma em um objeto Biblioteca
    private static Biblioteca carregar() {
        try (Reader leitor = Files.newBufferedReader(ARQUIVO, StandardCharsets.UTF_8)) {
            Biblioteca b = GSON.fromJson(leitor, Biblioteca.class); // JSON -> objeto
            return (b != null) ? b : new Biblioteca();              // arquivo vazio: começa do zero
        } catch (IOException e) {
            return new Biblioteca();                                // arquivo não existe: começa do zero
        }
    }

    // Grava os dados atuais no arquivo JSON (chame depois de qualquer alteração)
    public void salvar() {
        try {
            Files.createDirectories(ARQUIVO.getParent());           // cria a pasta "dados" se não existir
            try (Writer escritor = Files.newBufferedWriter(ARQUIVO, StandardCharsets.UTF_8)) {
                GSON.toJson(this, escritor);                        // objeto -> JSON
            }
        } catch (IOException e) {
            e.printStackTrace();                                    // mostra o erro no console
        }
    }

    // ---------- Acesso às listas ----------
    public List<Admin> getAdmins()           { return admins; }
    public List<Aluno> getAlunos()           { return alunos; }
    public List<Servidor> getServidores()    { return servidores; }
    public List<Livro> getLivros()           { return livros; }
    public List<EBook> getEbooks()           { return ebooks; }
    public List<Emprestimo> getEmprestimos() { return emprestimos; }

    // ---------- Buscas por id ----------
    // Procura entre alunos e servidores (ambos são Usuario)
    public Usuario buscarUsuario(int id) {
        for (Aluno a : alunos) {
            if (a.getId() == id) return a;
        }
        for (Servidor s : servidores) {
            if (s.getId() == id) return s;
        }
        return null; // não achou
    }

    public Livro buscarLivro(int id) {
        for (Livro l : livros) {
            if (l.getId() == id) return l;
        }
        return null;
    }

    public EBook buscarEBook(int id) {
        for (EBook e : ebooks) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    public Admin buscarAdmin(int id) {
        for (Admin a : admins) {
            if (a.getId() == id) return a;
        }
        return null;
    }
}