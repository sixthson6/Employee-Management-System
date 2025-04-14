package org.employees;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class App extends Application {
    private final EmployeeDatabase<Integer> database = new EmployeeDatabase<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Employee Management System");

        // === Input Fields ===
        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField departmentField = new TextField();
        TextField salaryField = new TextField();
        TextField ratingField = new TextField();
        TextField experienceField = new TextField();
        CheckBox isActiveCheckBox = new CheckBox("Active");
        
        // === Update Fields ===
        TextField updateIdField = new TextField();
        ComboBox<String> updateFieldDropdown = new ComboBox<>();
        updateFieldDropdown.getItems().addAll("name", "department", "salary", "performanceRating", "yearsOfExperience", "isActive");
        TextField updateValueField = new TextField();

        // === Output Area ===
        TextArea outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setWrapText(true);
        
        // === Buttons ===
        Button addButton = new Button("Add Employee");
        Button viewAllButton = new Button("Show All Employees");
        Button updateButton = new Button("Update Employee");
        Button deleteButton = new Button("Delete Employee");

        // === Grid for Employee Creation ===
        GridPane formGrid = new GridPane();
        formGrid.setPadding(new Insets(10));
        formGrid.setVgap(8);
        formGrid.setHgap(10);

        formGrid.add(new Label("ID:"), 0, 0);
        formGrid.add(idField, 1, 0);
        formGrid.add(new Label("Name:"), 0, 1);
        formGrid.add(nameField, 1, 1);
        formGrid.add(new Label("Department:"), 0, 2);
        formGrid.add(departmentField, 1, 2);
        formGrid.add(new Label("Salary:"), 0, 3);
        formGrid.add(salaryField, 1, 3);
        formGrid.add(new Label("Rating (0–5):"), 0, 4);
        formGrid.add(ratingField, 1, 4);
        formGrid.add(new Label("Experience (years):"), 0, 5);
        formGrid.add(experienceField, 1, 5);
        formGrid.add(isActiveCheckBox, 1, 6);
        formGrid.add(addButton, 1, 7);
        
        // === Update and Delete Section ===
        // HBox updateBox = new HBox(10,
        //         new Label("Emp ID:"), updateIdField,
        //         new Label("Field:"), updateFieldDropdown,
        //         new Label("New Value:"), updateValueField,
        //         updateButton, deleteButton
        // );
        // updateBox.setPadding(new Insets(10));

        VBox updateBox = new VBox(10,
                new Label("Emp ID:"), updateIdField,
                new Label("Field:"), updateFieldDropdown,
                new Label("New Value:"), updateValueField,
                updateButton, deleteButton
                );
        updateBox.setPadding(new Insets(10));
        
        // === Output Section ===
        VBox outputBox = new VBox(10, viewAllButton, outputArea);
        outputBox.setPadding(new Insets(10));

        HBox mainBox = new HBox(50, formGrid, updateBox);
        mainBox.setPadding(new Insets(10));

        VBox root = new VBox(15, mainBox, outputBox);
        Scene scene = new Scene(root, 700, 500);
        primaryStage.setScene(scene);
        primaryStage.show();

        // === Button Actions ===
        addButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String dept = departmentField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                double rating = Double.parseDouble(ratingField.getText());
                int experience = Integer.parseInt(experienceField.getText());
                boolean isActive = isActiveCheckBox.isSelected();
                
                Employee<Integer> emp = new Employee<>(id, name, dept, salary, rating, experience, isActive);
                database.addEmployee(emp);
                outputArea.setText("Employee added:\n" + emp);
                clearFields(idField, nameField, departmentField, salaryField, ratingField, experienceField, isActiveCheckBox);
            } catch (Exception ex) {
                outputArea.setText("Error adding employee. Check input values.");
            }
        });
        
        viewAllButton.setOnAction(e -> {
            StringBuilder sb = new StringBuilder("=== All Employees ===\n");
            for (Employee<Integer> emp : database.getAllEmployees()) {
                sb.append(emp).append("\n");
            }
            outputArea.setText(sb.toString());
        });

        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(updateIdField.getText());
                String field = updateFieldDropdown.getValue();
                String rawValue = updateValueField.getText();
                Object value;
                
                switch (field.toLowerCase()) {
                    case "salary":
                    case "performancerating":
                    value = Double.parseDouble(rawValue);
                        break;
                        case "yearsofexperience":
                        value = Integer.parseInt(rawValue);
                        break;
                    case "isactive":
                        value = Boolean.parseBoolean(rawValue);
                        break;
                    default:
                        value = rawValue;
                }
                

                boolean updated = database.updateEmployeeDetails(id, field, value);
                outputArea.setText(updated ? "Employee updated." : "Update failed. Employee not found or invalid field.");
            } catch (Exception ex) {
                outputArea.setText("Error updating employee.");
            }
        });

        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(updateIdField.getText());
                database.removeEmployee(id);
                outputArea.setText("Employee with ID " + id + " removed.");
            } catch (Exception ex) {
                outputArea.setText("Error deleting employee.");
            }
        });
    }

    
    private void clearFields(TextField idField, TextField nameField, TextField departmentField, TextField salaryField,
            TextField ratingField, TextField experienceField, CheckBox isActiveCheckBox) {
                idField.clear();
                nameField.clear();
                departmentField.clear();
                salaryField.clear();
                ratingField.clear();
                experienceField.clear();
                isActiveCheckBox.setSelected(false);
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
