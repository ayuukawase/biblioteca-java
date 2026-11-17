package br.edu.ifsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

// Tela do aluno/servidor: pedir empréstimo, devolver livros e abrir e-books.
public class TelaUsuario extends BorderPane {

    private final Usuario usuario;
    private final Biblioteca b = Biblioteca.getInstance();

    private final TableView<Livro> tabelaDisponiveis = new TableView<>();
    private final TableView<Emprestimo> tabelaMeus = new TableView<>();
    private final TableView<EBook> tabelaEbooks = new TableView<>();

    public TelaUsuario(Usuario usuario) {
        this.usuario = usuario;

        VBox topo = new VBox(Estilo.cabecalho("Olá, " + usuario.getNome(), App::mostrarLogin));
        if (!usuario.isStatus()) { // conta suspensa: avisa logo no topo
            Label aviso = new Label("Sua conta está suspensa. Você pode devolver livros e ler e-books, "
                    + "mas não pode pedir novos empréstimos.");
            aviso.setWrapText(true);
            aviso.setMaxWidth(Double.MAX_VALUE);
            aviso.setStyle("-fx-background-color:#FDECEA; -fx-text-fill:" + Estilo.VERMELHO + "; -fx-padding:10 20;");
            topo.getChildren().add(aviso);
        }
        setTop(topo);

        TabPane abas = new TabPane(criarAbaPedir(), criarAbaMeus(), criarAbaEbooks());
        abas.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        setCenter(abas);

        atualizar();
    }

    // ---------- Aba 1: Pedir empréstimo ----------
    private Tab criarAbaPedir() {
        tabelaDisponiveis.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaDisponiveis.setPlaceholder(new Label("Nenhum livro disponível no momento."));
        tabelaDisponiveis.getColumns().addAll(
                Estilo.<Livro>coluna("ID", l -> String.valueOf(l.getId())),
                Estilo.<Livro>coluna("Título", Livro::getNome),
                Estilo.<Livro>coluna("Categorias", l -> String.join(", ", l.getCategorias())));
        VBox.setVgrow(tabelaDisponiveis, Priority.ALWAYS);

        Button pedir = Estilo.botao("Pedir empréstimo do livro selecionado");
        pedir.setOnAction(e -> {
            Livro livro = tabelaDisponiveis.getSelectionModel().getSelectedItem();
            if (livro == null) {
                Estilo.aviso(AlertType.WARNING, "Selecione um livro na tabela.");
                return;
            }
            Emprestimo emprestimo = usuario.pegarLivro(livro); // null se suspenso ou indisponível
            if (emprestimo == null) {
                Estilo.aviso(AlertType.ERROR, usuario.isStatus()
                        ? "Este livro não está mais disponível."
                        : "Sua conta está suspensa.");
            } else {
                Estilo.aviso(AlertType.INFORMATION, "Empréstimo feito! Devolva até "
                        + Estilo.data(emprestimo.getDataPrevista()) + ".");
            }
            atualizar();
        });

        VBox conteudo = new VBox(12, tabelaDisponiveis, pedir);
        conteudo.setPadding(new Insets(16));
        return new Tab("Pedir empréstimo", conteudo);
    }

    // ---------- Aba 2: Meus empréstimos / devolução ----------
    private Tab criarAbaMeus() {
        tabelaMeus.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaMeus.setPlaceholder(new Label("Você ainda não pegou nenhum livro."));
        tabelaMeus.getColumns().addAll(
                Estilo.<Emprestimo>coluna("Livro", e -> {
                    Livro l = b.buscarLivro(e.getIdLivro());
                    return l != null ? l.getNome() : "(livro removido)";
                }),
                Estilo.<Emprestimo>coluna("Emprestado em", e -> Estilo.data(e.getDataEmprestimo())),
                Estilo.<Emprestimo>coluna("Devolver até", e -> Estilo.data(e.getDataPrevista())),
                Estilo.<Emprestimo>coluna("Devolvido em", e -> Estilo.data(e.getDataDevolucao())),
                Estilo.<Emprestimo>coluna("Situação", Estilo::situacao));
        VBox.setVgrow(tabelaMeus, Priority.ALWAYS);

        Button devolver = Estilo.botao("Devolver livro selecionado");
        devolver.setOnAction(e -> {
            Emprestimo emprestimo = tabelaMeus.getSelectionModel().getSelectedItem();
            if (emprestimo == null) {
                Estilo.aviso(AlertType.WARNING, "Selecione um empréstimo na tabela.");
                return;
            }
            if (emprestimo.getDataDevolucao() != null) {
                Estilo.aviso(AlertType.INFORMATION, "Este livro já foi devolvido.");
                return;
            }
            usuario.devolverLivro(emprestimo);
            Estilo.aviso(AlertType.INFORMATION, "Livro devolvido. Obrigada!");
            atualizar();
        });

        VBox conteudo = new VBox(12, tabelaMeus, devolver);
        conteudo.setPadding(new Insets(16));
        return new Tab("Meus empréstimos", conteudo);
    }

    // ---------- Aba 3: E-books ----------
    private Tab criarAbaEbooks() {
        tabelaEbooks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tabelaEbooks.setPlaceholder(new Label("Nenhum e-book cadastrado."));
        tabelaEbooks.getColumns().addAll(
                Estilo.<EBook>coluna("ID", x -> String.valueOf(x.getId())),
                Estilo.<EBook>coluna("Título", EBook::getNome),
                Estilo.<EBook>coluna("Categorias", x -> String.join(", ", x.getCategorias())));
        VBox.setVgrow(tabelaEbooks, Priority.ALWAYS);

        Button abrir = Estilo.botao("Abrir para ler");
        abrir.setOnAction(e -> {
            EBook ebook = tabelaEbooks.getSelectionModel().getSelectedItem();
            if (ebook == null) {
                Estilo.aviso(AlertType.WARNING, "Selecione um e-book na tabela.");
                return;
            }
            App.abrirArquivo(ebook.getArquivo()); // abre o PDF no programa padrão
        });

        VBox conteudo = new VBox(12, tabelaEbooks, abrir);
        conteudo.setPadding(new Insets(16));
        return new Tab("E-books", conteudo);
    }

    // ---------- Utilitários ----------
    private void atualizar() {
        List<Livro> disponiveis = new ArrayList<>();
        for (Livro l : b.getLivros()) {
            if (l.isStatus()) disponiveis.add(l);
        }
        tabelaDisponiveis.setItems(FXCollections.observableArrayList(disponiveis));

        tabelaDisponiveis.refresh(); // força a tabela a redesenhar as linhas
        tabelaMeus.refresh();
        tabelaEbooks.refresh();

        List<Emprestimo> meus = new ArrayList<>();
        for (Emprestimo e : b.getEmprestimos()) {
            if (e.getIdUsuario() == usuario.getId()) meus.add(e);
        }
        Collections.reverse(meus); // mais recentes primeiro
        tabelaMeus.setItems(FXCollections.observableArrayList(meus));

        tabelaEbooks.setItems(FXCollections.observableArrayList(b.getEbooks()));
    }
}