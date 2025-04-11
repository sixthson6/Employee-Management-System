module org.employees {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.employees to javafx.fxml;
    exports org.employees;
}
