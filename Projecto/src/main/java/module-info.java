module com.sin.executavel {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sin.executavel to javafx.fxml;
    exports com.sin.executavel;
}
