module org.employees {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.logging;

    opens org.employees to javafx.fxml;
    exports org.employees;
}
