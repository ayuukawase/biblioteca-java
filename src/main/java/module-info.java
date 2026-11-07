module br.edu.ifsp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens br.edu.ifsp to javafx.fxml, com.google.gson;
    exports br.edu.ifsp;
}