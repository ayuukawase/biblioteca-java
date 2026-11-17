package br.edu.ifsp;

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

// Tela de login: é a primeira coisa que aparece.
public class Login extends StackPane {

    private static final String PERFIL_USUARIO = "Aluno ou servidor";
    private static final String PERFIL_ADMIN = "Administrador";

    public Login() {
        setStyle("-fx-background-color:#EEF2EF;");

        Label titulo = new Label("Biblioteca");
        titulo.setStyle("-fx-font-size:26px; -fx-font-weight:bold; -fx-text-fill:" + Estilo.AZUL + ";");
        Label subtitulo = new Label("Entre para continuar");
        subtitulo.setStyle("-fx-text-fill:#555;");

        // O id do admin pode ser igual ao de um aluno (ex.: os dois são 1),
        // por isso o usuário escolhe se está entrando como admin ou não.
        ComboBox<String> perfil = new ComboBox<>(FXCollections.observableArrayList(PERFIL_USUARIO, PERFIL_ADMIN));
        perfil.getSelectionModel().selectFirst();
        perfil.setMaxWidth(Double.MAX_VALUE);

        TextField campoId = new TextField();
        campoId.setPromptText("ID");

        PasswordField campoSenha = new PasswordField();
        campoSenha.setPromptText("Senha");

        Label erro = new Label();
        erro.setWrapText(true);
        erro.setStyle("-fx-text-fill:" + Estilo.VERMELHO + ";");

        Button entrar = Estilo.botao("Entrar");
        entrar.setMaxWidth(Double.MAX_VALUE);
        entrar.setDefaultButton(true); // Enter aciona o botão

        entrar.setOnAction(e -> {
            int id;
            try {
                id = Integer.parseInt(campoId.getText().trim()); // texto -> número
            } catch (NumberFormatException ex) {
                erro.setText("O ID deve ser um número.");
                return;
            }
            String senha = campoSenha.getText();
            Biblioteca b = Biblioteca.getInstance();

            if (PERFIL_ADMIN.equals(perfil.getValue())) {
                Admin admin = b.autenticarAdmin(id, senha);
                if (admin == null) erro.setText("ID ou senha incorretos.");
                else App.mostrarAdmin(admin);
            } else {
                Usuario usuario = b.autenticarUsuario(id, senha);
                if (usuario == null) erro.setText("ID ou senha incorretos.");
                else App.mostrarUsuario(usuario);
            }
        });

        VBox cartao = new VBox(12, titulo, subtitulo, perfil, campoId, campoSenha, entrar, erro);
        cartao.setPadding(new Insets(28));
        cartao.setMaxWidth(340);
        cartao.setMaxHeight(VBox.USE_PREF_SIZE);
        cartao.setStyle("-fx-background-color:white; -fx-background-radius:10;");

        setAlignment(Pos.CENTER);
        getChildren().add(cartao);
    }
}