package org.employees;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {

    private final EmployeeDatabase<Integer> database = new EmployeeDatabase<>();
    private final TextArea outputArea = new TextArea();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management System");

        VBox root = new VBox(15,
                buildMainBox(),
                buildOutputSection()
        );
        root.setPadding(new Insets(15));

        Scene scene = new Scene(root, 750, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    
    private GridPane buildFormSection() {
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField departmentField = new TextField();
        TextField salaryField = new TextField();
        TextField ratingField = new TextField();
        TextField experienceField = new TextField();
        CheckBox isActiveCheckBox = new CheckBox("Active");

        Button addButton = new Button("Add Employee");
        addButton.setOnAction(e -> handleAddEmployee(idField, nameField, departmentField,
                salaryField, ratingField, experienceField, isActiveCheckBox));

        GridPane formGrid = new GridPane();
        formGrid.setHgap(10);
        formGrid.setVgap(8);
        formGrid.setPadding(new Insets(10));
        
        formGrid.add(new Label("ID:"), 0, 0);
        formGrid.add(idField, 1, 0);
        formGrid.add(new Label("Name:"), 0, 1);
        formGrid.add(nameField, 1, 1);
        formGrid.add(new Label("Department:"), 0, 2);
        formGrid.add(departmentField, 1, 2);
        formGrid.add(new Label("Salary:"), 0, 3);
        formGrid.add(salaryField, 1, 3);
        formGrid.add(new Label("Rating (0-5):"), 0, 4);
        formGrid.add(ratingField, 1, 4);
        formGrid.add(new Label("Experience:"), 0, 5);
        formGrid.add(experienceField, 1, 5);
        formGrid.add(isActiveCheckBox, 1, 6);
        formGrid.add(addButton, 1, 7);
        
        return formGrid;
    }


    private VBox buildUpdateSection() {
        TextField updateIdField = new TextField();
        ComboBox<String> fieldDropdown = new ComboBox<>();
        fieldDropdown.getItems().addAll("name", "department", "salary", "performanceRating", "yearsOfExperience", "isActive");
        TextField newValueField = new TextField();

        Button updateBtn = new Button("Update");
        Button deleteBtn = new Button("Delete");

        updateBtn.setOnAction(e -> handleUpdateEmployee(updateIdField, fieldDropdown, newValueField));
        deleteBtn.setOnAction(e -> handleDeleteEmployee(updateIdField));

        VBox box = new VBox(10,
                new Label("Emp ID:"), updateIdField,
                new Label("Field to Update:"), fieldDropdown,
                new Label("New Value:"), newValueField,
                updateBtn, deleteBtn
        );
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");

        return box;
    }

    private HBox buildMainBox() {
        GridPane formGrid = buildFormSection();
        VBox updateBox = buildUpdateSection();
    
        HBox mainBox = new HBox(40, formGrid, updateBox);
        mainBox.setPadding(new Insets(10));
        return mainBox;
    }
    

    private VBox buildOutputSection() {
        outputArea.setEditable(false);
        outputArea.setWrapText(true);

        Button viewAllBtn = new Button("View All Employees");
        viewAllBtn.setOnAction(e -> handleViewAll());

        VBox box = new VBox(10, viewAllBtn, outputArea);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");

        return box;
    }

    private void handleAddEmployee(TextField id, TextField name, TextField dept,
                                   TextField salary, TextField rating, TextField exp, CheckBox isActive) {
        try {
            int empId = Integer.parseInt(id.getText());
            String empName = name.getText();
            String empDept = dept.getText();
            double empSalary = Double.parseDouble(salary.getText());
            double empRating = Double.parseDouble(rating.getText());
            int empExp = Integer.parseInt(exp.getText());
            boolean active = isActive.isSelected();

            Employee<Integer> employee = new Employee<>(empId, empName, empDept, empSalary, empRating, empExp, active);
            database.addEmployee(employee);
            outputArea.setText("✅ Employee added:\n" + employee);

            id.clear(); name.clear(); dept.clear(); salary.clear();
            rating.clear(); exp.clear(); isActive.setSelected(false);
        } catch (Exception ex) {
            outputArea.setText("❌ Error adding employee. Please check inputs.");
        }
    }

    private void handleViewAll() {
        StringBuilder sb = new StringBuilder("=== All Employees ===\n");
        for (Employee<Integer> emp : database.getAllEmployees()) {
            sb.append(emp).append("\n");
        }
        outputArea.setText(sb.toString());
    }

    private void handleUpdateEmployee(TextField idField, ComboBox<String> fieldDropdown, TextField newValueField) {
        try {
            int id = Integer.parseInt(idField.getText());
            String field = fieldDropdown.getValue();
            String input = newValueField.getText();
            Object value;

            switch (field.toLowerCase()) {
                case "salary":
                case "performancerating":
                    value = Double.parseDouble(input);
                    break;
                case "yearsofexperience":
                    value = Integer.parseInt(input);
                    break;
                case "isactive":
                    value = Boolean.parseBoolean(input);
                    break;
                default:
                    value = input;
            }

            boolean success = database.updateEmployeeDetails(id, field, value);
            outputArea.setText(success ? "✅ Employee updated." : "❌ Update failed.");
        } catch (Exception ex) {
            outputArea.setText("❌ Invalid update input.");
        }
    }

    private void handleDeleteEmployee(TextField idField) {
        try {
            int id = Integer.parseInt(idField.getText());
            database.removeEmployee(id);
            outputArea.setText("🗑️ Employee with ID " + id + " deleted.");
        } catch (Exception ex) {
            outputArea.setText("❌ Invalid ID for deletion.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
