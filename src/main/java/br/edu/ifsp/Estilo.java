package br.edu.ifsp;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;

// Peças que as três telas usam (cores, botões, cabeçalho, alertas, colunas de tabela).
// Fica num arquivo só para não repetir código.
final class Estilo {

    static final String AZUL = "#1F4E8C";
    static final String VERMELHO = "#B3261E";

    private static final DateTimeFormatter FORMATO = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Estilo() { } // não precisa criar objetos desta classe

    // Botão verde (ação principal)
    static Button botao(String texto) {
        Button b = new Button(texto);
        b.setStyle("-fx-background-color:" + AZUL + "; -fx-text-fill:white; -fx-font-weight:bold;"
                + "-fx-padding:8 16; -fx-background-radius:6; -fx-cursor:hand;");
        return b;
    }

    // Botão vermelho (ação que apaga alguma coisa)
    static Button botaoPerigo(String texto) {
        Button b = new Button(texto);
        b.setStyle("-fx-background-color:" + VERMELHO + "; -fx-text-fill:white; -fx-font-weight:bold;"
                + "-fx-padding:8 16; -fx-background-radius:6; -fx-cursor:hand;");
        return b;
    }

    // Faixa verde no topo com o título e o botão Sair
    static HBox cabecalho(String titulo, Runnable aoSair) {
        Label t = new Label(titulo);
        t.setStyle("-fx-text-fill:white; -fx-font-size:18px; -fx-font-weight:bold;");

        Region espaco = new Region();                 // empurra o botão Sair para a direita
        HBox.setHgrow(espaco, Priority.ALWAYS);

        Button sair = new Button("Sair");
        sair.setStyle("-fx-background-color:transparent; -fx-text-fill:white; -fx-border-color:white;"
                + "-fx-border-radius:6; -fx-background-radius:6; -fx-cursor:hand;");
        sair.setOnAction(e -> aoSair.run());

        HBox caixa = new HBox(10, t, espaco, sair);
        caixa.setAlignment(Pos.CENTER_LEFT);
        caixa.setPadding(new Insets(14, 20, 14, 20));
        caixa.setStyle("-fx-background-color:" + AZUL + ";");
        return caixa;
    }

    // Cria uma coluna de tabela. "extrator" diz qual texto mostrar para cada linha.
    // (Assim não precisa de PropertyValueFactory, que exige mexer no module-info.)
    static <T> TableColumn<T, String> coluna(String titulo, Function<T, String> extrator) {
        TableColumn<T, String> c = new TableColumn<>(titulo);
        c.setCellValueFactory(linha -> new SimpleStringProperty(extrator.apply(linha.getValue())));
        return c;
    }

    // 2026-11-01 -> 01/11/2026 (null vira "—")
    static String data(LocalDate d) {
        return d == null ? "—" : d.format(FORMATO);
    }

    // Texto da situação de um empréstimo
    static String situacao(Emprestimo e) {
        if (e.getDataDevolucao() != null) return "Devolvido";
        if (e.estaAtrasado()) return "Atrasado";
        return "Em andamento";
    }

    static void aviso(AlertType tipo, String mensagem) {
        Alert a = new Alert(tipo, mensagem);
        a.setHeaderText(null);
        a.showAndWait();
    }

    static boolean confirmar(String mensagem) {
        Alert a = new Alert(AlertType.CONFIRMATION, mensagem, ButtonType.YES, ButtonType.NO);
        a.setHeaderText(null);
        return a.showAndWait().orElse(ButtonType.NO) == ButtonType.YES;
    }
}