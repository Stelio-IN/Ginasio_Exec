module com.sin.gym {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.sin.gym to javafx.fxml;
    exports com.sin.gym;
}
