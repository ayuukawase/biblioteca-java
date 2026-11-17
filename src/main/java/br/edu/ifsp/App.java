package br.edu.ifsp;

import java.io.File;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

import java.awt.Desktop;
import javafx.application.Platform;

/**
 * Ponto de entrada. Sempre abre a tela de login primeiro
 * e sabe trocar entre as telas do sistema.
 */
public class App extends Application {

    private static Stage palco;            // a janela
    private static HostServices servicos;  // usado para abrir arquivos (PDF do e-book)

    @Override
    public void start(Stage stage) {
        palco = stage;
        servicos = getHostServices();
        stage.setTitle("Biblioteca");
        mostrarLogin();                    // a primeira tela é sempre o login
        stage.show();
    }

    public static void mostrarLogin()               { trocar(new Login(), 440, 480); }
    public static void mostrarAdmin(Admin admin)    { trocar(new TelaAdmin(admin), 980, 640); }
    public static void mostrarUsuario(Usuario u)    { trocar(new TelaUsuario(u), 980, 640); }

    // Coloca outra tela dentro da janela
    private static void trocar(Parent raiz, double largura, double altura) {
        palco.setScene(new Scene(raiz, largura, altura));
        palco.sizeToScene();
        palco.centerOnScreen();
    }

    // Abre o arquivo do e-book no programa padrão do computador (leitor de PDF etc.)
    public static void abrirArquivo(String caminho) {
        File arquivo = new File(caminho);
        if (!arquivo.exists()) {
            Estilo.aviso(AlertType.ERROR, "Arquivo não encontrado:\n" + arquivo.getAbsolutePath());
            return;
        }
        servicos.showDocument(arquivo.toURI().toString());
    }

    public static void main(String[] args) {
        launch();
    }
}