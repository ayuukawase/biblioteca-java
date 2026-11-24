package br.edu.ifsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

// Tela do administrador: livros (adicionar/excluir), fazer empréstimo, ver empréstimos
// suspender/reativar usuários e gerenciar e-books (adicionar/atualizar/excluir).
public class TelaAdmin extends BorderPane {

    private final Admin admin;
    private final Biblioteca b = Biblioteca.getInstance();

    private final TableView<Livro> tabelaLivros = new TableView<>();
    private final TableView<Emprestimo> tabelaEmprestimos = new TableView<>();
    private final TableView<Usuario> tabelaUsuarios = new TableView<>();
    private final TableView<EBook> tabelaEbooks = new TableView<>();
    private final ComboBox<Usuario> comboUsuario = new ComboBox<>();
    private final ComboBox<Livro> comboLivro = new ComboBox<>();

    public TelaAdmin(Admin admin) {
        this.admin = admin;

        setTop(Estilo.cabecalho("Biblioteca — Administrador", App::mostrarLogin));

        TabPane abas = new TabPane(criarAbaLivros(), criarAbaEmprestar(), criarAbaEmprestimos(), criarAbaUsuarios(), criarAbaEbooks());
        abas.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE); // sem "x" nas abas
        setCenter(abas);

        atualizar();
    }

    // ---------- Aba 1: Livros ----------
    private Tab criarAbaLivros() {
        tabelaLivros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaLivros.setPlaceholder(new Label("Nenhum livro cadastrado."));
        tabelaLivros.getColumns().addAll(
                Estilo.<Livro>coluna("ID", l -> String.valueOf(l.getId())),
                Estilo.<Livro>coluna("Título", Livro::getNome),
                Estilo.<Livro>coluna("Categorias", l -> String.join(", ", l.getCategorias())),
                Estilo.<Livro>coluna("Situação", l -> l.isStatus() ? "Disponível" : "Emprestado"));
        VBox.setVgrow(tabelaLivros, Priority.ALWAYS);

        TextField campoNome = new TextField();
        campoNome.setPromptText("Título do livro");
        HBox.setHgrow(campoNome, Priority.ALWAYS);

        TextField campoCategorias = new TextField();
        campoCategorias.setPromptText("Categorias, separadas por vírgula");
        HBox.setHgrow(campoCategorias, Priority.ALWAYS);

        Button adicionar = Estilo.botao("Adicionar livro");
        adicionar.setOnAction(e -> {
            String nome = campoNome.getText().trim();
            if (nome.isEmpty()) {
                Estilo.aviso(AlertType.WARNING, "Digite o título do livro.");
                return;
            }
            // "Romance, Clássico" -> ["Romance", "Clássico"]
            List<String> categorias = new ArrayList<>();
            for (String c : campoCategorias.getText().split(",")) {
                if (!c.trim().isEmpty()) categorias.add(c.trim());
            }
            admin.criarLivro(new Livro(proximoIdLivro(), nome, categorias, true)); // true = disponível
            campoNome.clear();
            campoCategorias.clear();
            atualizar();
        });

        Button excluir = Estilo.botaoPerigo("Excluir livro selecionado");
        excluir.setOnAction(e -> {
            Livro livro = tabelaLivros.getSelectionModel().getSelectedItem();
            if (livro == null) {
                Estilo.aviso(AlertType.WARNING, "Selecione um livro na tabela.");
                return;
            }
            if (!livro.isStatus()) {
                Estilo.aviso(AlertType.WARNING, "Este livro está emprestado. Espere a devolução para excluir.");
                return;
            }
            if (Estilo.confirmar("Excluir \"" + livro.getNome() + "\"?")) {
                admin.deletarLivro(livro.getId());
                atualizar();
            }
        });

        HBox formulario = new HBox(10, campoNome, campoCategorias, adicionar);
        VBox conteudo = new VBox(12, tabelaLivros, formulario, excluir);
        conteudo.setPadding(new Insets(16));
        return new Tab("Livros", conteudo);
    }

    // ---------- Aba 2: Fazer empréstimo ----------
    private Tab criarAbaEmprestar() {
        comboUsuario.setPromptText("Escolha o usuário");
        comboUsuario.setMaxWidth(Double.MAX_VALUE);
        comboUsuario.setConverter(new StringConverter<>() { // como cada item aparece escrito
            @Override public String toString(Usuario u) {
                if (u == null) return "";
                String tipo = (u instanceof Aluno) ? "aluno" : "servidor";
                return u.getNome() + " (" + tipo + ", id " + u.getId() + ")";
            }
            @Override public Usuario fromString(String s) { return null; }
        });

        comboLivro.setPromptText("Escolha o livro disponível");
        comboLivro.setMaxWidth(Double.MAX_VALUE);
        comboLivro.setConverter(new StringConverter<>() {
            @Override public String toString(Livro l) {
                return l == null ? "" : l.getNome() + " (id " + l.getId() + ")";
            }
            @Override public Livro fromString(String s) { return null; }
        });

        Button emprestar = Estilo.botao("Registrar empréstimo");
        emprestar.setOnAction(e -> {
            Usuario usuario = comboUsuario.getValue();
            Livro livro = comboLivro.getValue();
            if (usuario == null || livro == null) {
                Estilo.aviso(AlertType.WARNING, "Escolha o usuário e o livro.");
                return;
            }
            Emprestimo emprestimo = usuario.pegarLivro(livro); // null se suspenso ou indisponível
            if (emprestimo == null) {
                Estilo.aviso(AlertType.ERROR, "Não foi possível emprestar: o usuário está suspenso ou o livro está indisponível.");
            } else {
                Estilo.aviso(AlertType.INFORMATION, "Empréstimo registrado. Devolução até "
                        + Estilo.data(emprestimo.getDataPrevista()) + ".");
            }
            atualizar();
        });

        VBox conteudo = new VBox(12, new Label("Usuário"), comboUsuario, new Label("Livro"), comboLivro, emprestar);
        conteudo.setPadding(new Insets(16));
        conteudo.setMaxWidth(520);

        BorderPane moldura = new BorderPane(); // mantém o formulário alinhado à esquerda, sem esticar
        moldura.setLeft(conteudo);
        return new Tab("Fazer empréstimo", moldura);
    }

    // ---------- Aba 3: Empréstimos ----------
    private Tab criarAbaEmprestimos() {
        tabelaEmprestimos.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaEmprestimos.setPlaceholder(new Label("Nenhum empréstimo registrado."));
        tabelaEmprestimos.getColumns().addAll(
                Estilo.<Emprestimo>coluna("Usuário", e -> {
                    Usuario u = b.buscarUsuario(e.getIdUsuario());
                    return u != null ? u.getNome() : "(id " + e.getIdUsuario() + ")";
                }),
                Estilo.<Emprestimo>coluna("Livro", e -> {
                    Livro l = b.buscarLivro(e.getIdLivro());
                    return l != null ? l.getNome() : "(livro removido)";
                }),
                Estilo.<Emprestimo>coluna("Emprestado em", e -> Estilo.data(e.getDataEmprestimo())),
                Estilo.<Emprestimo>coluna("Devolver até", e -> Estilo.data(e.getDataPrevista())),
                Estilo.<Emprestimo>coluna("Devolvido em", e -> Estilo.data(e.getDataDevolucao())),
                Estilo.<Emprestimo>coluna("Situação", Estilo::situacao));

        VBox conteudo = new VBox(tabelaEmprestimos);
        VBox.setVgrow(tabelaEmprestimos, Priority.ALWAYS);
        conteudo.setPadding(new Insets(16));
        return new Tab("Empréstimos", conteudo);
    }

    // ---------- Aba 4: Usuários (suspender / reativar) ----------
    private Tab criarAbaUsuarios() {
        tabelaUsuarios.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaUsuarios.setPlaceholder(new Label("Nenhum usuário cadastrado."));
        tabelaUsuarios.getColumns().addAll(
                Estilo.<Usuario>coluna("ID", u -> String.valueOf(u.getId())),
                Estilo.<Usuario>coluna("Nome", Usuario::getNome),
                Estilo.<Usuario>coluna("Tipo", u -> (u instanceof Aluno) ? "Aluno" : "Servidor"),
                Estilo.<Usuario>coluna("E-mail", Usuario::getEmail),
                Estilo.<Usuario>coluna("Situação", u -> u.isStatus() ? "Ativo" : "Suspenso"));
        VBox.setVgrow(tabelaUsuarios, Priority.ALWAYS);

        Button suspender = Estilo.botaoPerigo("Suspender");
        suspender.setOnAction(e -> {
            Usuario usuario = tabelaUsuarios.getSelectionModel().getSelectedItem();
            if (usuario == null) {
                Estilo.aviso(AlertType.WARNING, "Selecione um usuário na tabela.");
                return;
            }
            if (!usuario.isStatus()) {
                Estilo.aviso(AlertType.INFORMATION, "Este usuário já está suspenso.");
                return;
            }
            admin.suspenderUsuario(usuario.getId()); // grava no JSON
            atualizar();
        });

        Button reativar = Estilo.botao("Reativar");
        reativar.setOnAction(e -> {
            Usuario usuario = tabelaUsuarios.getSelectionModel().getSelectedItem();
            if (usuario == null) {
                Estilo.aviso(AlertType.WARNING, "Selecione um usuário na tabela.");
                return;
            }
            if (usuario.isStatus()) {
                Estilo.aviso(AlertType.INFORMATION, "Este usuário já está ativo.");
                return;
            }
            admin.reativarUsuario(usuario.getId()); // grava no JSON
            atualizar();
        });

        HBox botoes = new HBox(10, suspender, reativar);
        VBox conteudo = new VBox(12, tabelaUsuarios, botoes);
        conteudo.setPadding(new Insets(16));
        return new Tab("Usuários", conteudo);
    }

    // ---------- Aba 5: E-books (adicionar / atualizar / excluir) ----------
    private Tab criarAbaEbooks() {
        tabelaEbooks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaEbooks.setPlaceholder(new Label("Nenhum e-book cadastrado."));
        tabelaEbooks.getColumns().addAll(
                Estilo.<EBook>coluna("ID", x -> String.valueOf(x.getId())),
                Estilo.<EBook>coluna("Título", EBook::getNome),
                Estilo.<EBook>coluna("Categorias", x -> String.join(", ", x.getCategorias())),
                Estilo.<EBook>coluna("Arquivo", EBook::getArquivo));
        VBox.setVgrow(tabelaEbooks, Priority.ALWAYS);

        TextField campoNome = new TextField();
        campoNome.setPromptText("Título do e-book");
        HBox.setHgrow(campoNome, Priority.ALWAYS);

        TextField campoCategorias = new TextField();
        campoCategorias.setPromptText("Categorias, separadas por vírgula");
        HBox.setHgrow(campoCategorias, Priority.ALWAYS);

        TextField campoArquivo = new TextField();
        campoArquivo.setPromptText("Arquivo (ex.: books/clean_code.pdf)");
        HBox.setHgrow(campoArquivo, Priority.ALWAYS);

        // Ao clicar numa linha da tabela, os campos recebem os dados dela (para poder editar)
        tabelaEbooks.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                campoNome.setText(novo.getNome());
                campoCategorias.setText(String.join(", ", novo.getCategorias()));
                campoArquivo.setText(novo.getArquivo());
            }
        });

        Runnable limparCampos = () -> {
            campoNome.clear();
            campoCategorias.clear();
            campoArquivo.clear();
        };

        Button adicionar = Estilo.botao("Adicionar e-book");
        adicionar.setOnAction(e -> {
            String nome = campoNome.getText().trim();
            String arquivo = campoArquivo.getText().trim();
            if (nome.isEmpty() || arquivo.isEmpty()) {
                Estilo.aviso(AlertType.WARNING, "Digite o título e o caminho do arquivo.");
                return;
            }
            admin.inserirEBook(new EBook(proximoIdEBook(), nome, separarCategorias(campoCategorias.getText()), arquivo));
            atualizar();
            limparCampos.run();
        });

        Button salvar = Estilo.botao("Salvar alterações");
        salvar.setOnAction(e -> {
            EBook selecionado = tabelaEbooks.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Estilo.aviso(AlertType.WARNING, "Selecione um e-book na tabela.");
                return;
            }
            String nome = campoNome.getText().trim();
            String arquivo = campoArquivo.getText().trim();
            if (nome.isEmpty() || arquivo.isEmpty()) {
                Estilo.aviso(AlertType.WARNING, "Digite o título e o caminho do arquivo.");
                return;
            }
            // mesmo id, dados novos
            admin.atualizarEBook(new EBook(selecionado.getId(), nome, separarCategorias(campoCategorias.getText()), arquivo));
            atualizar();
            limparCampos.run();
        });

        Button excluir = Estilo.botaoPerigo("Excluir e-book selecionado");
        excluir.setOnAction(e -> {
            EBook selecionado = tabelaEbooks.getSelectionModel().getSelectedItem();
            if (selecionado == null) {
                Estilo.aviso(AlertType.WARNING, "Selecione um e-book na tabela.");
                return;
            }
            if (Estilo.confirmar("Excluir \"" + selecionado.getNome() + "\"?")) {
                admin.deletarEBook(selecionado.getId());
                atualizar();
                limparCampos.run();
            }
        });

        HBox formulario = new HBox(10, campoNome, campoCategorias, campoArquivo);
        HBox botoes = new HBox(10, adicionar, salvar, excluir);
        VBox conteudo = new VBox(12, tabelaEbooks, formulario, botoes);
        conteudo.setPadding(new Insets(16));
        return new Tab("E-books", conteudo);
    }

    // ---------- Utilitários ----------

    // Recarrega tudo que aparece na tela a partir dos dados
    private void atualizar() {
        tabelaLivros.setItems(FXCollections.observableArrayList(b.getLivros()));

        List<Emprestimo> emprestimos = new ArrayList<>(b.getEmprestimos());
        Collections.reverse(emprestimos); // mais recentes primeiro
        tabelaEmprestimos.setItems(FXCollections.observableArrayList(emprestimos));

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.addAll(b.getAlunos());
        usuarios.addAll(b.getServidores());
        tabelaUsuarios.setItems(FXCollections.observableArrayList(usuarios));
        tabelaEbooks.setItems(FXCollections.observableArrayList(b.getEbooks()));
        comboUsuario.setValue(null);
        comboUsuario.setItems(FXCollections.observableArrayList(usuarios));

        List<Livro> disponiveis = new ArrayList<>();
        for (Livro l : b.getLivros()) {
            if (l.isStatus()) disponiveis.add(l);
        }
        comboLivro.setValue(null);
        comboLivro.setItems(FXCollections.observableArrayList(disponiveis));

        tabelaLivros.refresh();
        tabelaEmprestimos.refresh();
        tabelaUsuarios.refresh();
        tabelaEbooks.refresh();
    }

    // Novo e-book recebe o maior id existente + 1
    private int proximoIdEBook() {
        int maior = 0;
        for (EBook x : b.getEbooks()) {
            if (x.getId() > maior) maior = x.getId();
        }
        return maior + 1;
    }

    // "Romance, Clássico" -> ["Romance", "Clássico"]
    private List<String> separarCategorias(String texto) {
        List<String> categorias = new ArrayList<>();
        for (String c : texto.split(",")) {
            if (!c.trim().isEmpty()) categorias.add(c.trim());
        }
        return categorias;
    }

    // Novo livro recebe o maior id existente + 1
    private int proximoIdLivro() {
        int maior = 0;
        for (Livro l : b.getLivros()) {
            if (l.getId() > maior) maior = l.getId();
        }
        return maior + 1;
    }
}