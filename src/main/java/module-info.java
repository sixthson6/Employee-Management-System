module org.employees {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens org.employees to javafx.fxml;
    exports org.employees;
}
