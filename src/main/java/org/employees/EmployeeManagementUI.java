package org.employees;

import java.util.*;
import java.util.function.Supplier;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class EmployeeManagementUI {

    private final EmployeeDatabase<Integer> database = new EmployeeDatabase<>();
    private final TextArea outputArea = new TextArea();

    @SuppressWarnings("exports")
    public VBox buildUI() {
        VBox root = new VBox(15,
                buildMainBox(),
                buildSalaryManagementSection(),
                buildOutputSection()
        );
        root.setPadding(new Insets(15));
        return root;
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

        formGrid.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
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
        return new HBox(40, buildFormSection(), buildUpdateSection(), buildSearchSection());
    }

    private VBox buildOutputSection() {
        outputArea.setEditable(false);
        outputArea.setWrapText(true);

        Button viewAllBtn = new Button("View All Employees");
        viewAllBtn.setOnAction(e -> handleViewAll());

        HBox displayButtons = new HBox(10, viewAllBtn, buildDisplaySection(), buildSortingSection());
        VBox box = new VBox(10, displayButtons, outputArea);
        box.setPadding(new Insets(10));
        box.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        return box;
    }

    private VBox buildSearchSection() {
        VBox searchBox = new VBox(10,
                buildSearchByDepartment(),
                buildSearchByName(),
                buildPerformanceFilter(),
                buildSalaryRangeFilter()
        );
        searchBox.setPadding(new Insets(10));
        searchBox.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        return searchBox;
    }

    private HBox buildSalaryManagementSection() {
        HBox section = new HBox(10, buildRaisePane(), buildTopPaidPane(), buildAverageSalaryPane());
        section.setPadding(new Insets(10));
        section.setStyle("-fx-border-color: lightgray; -fx-border-width: 1;");
        return section;
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

    private void handleUpdateEmployee(TextField idField, ComboBox<String> fieldDropdown, TextField newValueField) {
        try {
            int id = Integer.parseInt(idField.getText());
            String field = fieldDropdown.getValue();
            String input = newValueField.getText();
            Object value;

            switch (field.toLowerCase()) {
                case "salary":
                case "performancerating": value = Double.parseDouble(input); break;
                case "yearsofexperience": value = Integer.parseInt(input); break;
                case "isactive": value = Boolean.parseBoolean(input); break;
                default: value = input;
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

    private void handleViewAll() {
        StringBuilder sb = new StringBuilder("=== All Employees ===\n");
        for (Employee<Integer> emp : database.getAllEmployees()) {
            sb.append(emp).append("\n");
        }
        outputArea.setText(sb.toString());
    }

    private VBox buildDisplaySection() {
        Button forEachBtn = new Button("Display (for-each)");
        Button streamBtn = new Button("Display (stream)");

        forEachBtn.setOnAction(e -> outputArea.setText(EmployeeDisplayUtil.displayWithForEach(database)));
        streamBtn.setOnAction(e -> outputArea.setText(EmployeeDisplayUtil.displayWithStream(database)));

        return new VBox(10, new HBox(10, forEachBtn, streamBtn));
    }

    private HBox buildSortingSection() {
        return new HBox(10,
                createSortButton("Sort by Experience", () -> sortAndDisplay(this::sortByExperience)),
                createSortButton("Sort by Salary", () -> sortAndDisplay(this::sortBySalary)),
                createSortButton("Sort by Performance", () -> sortAndDisplay(this::sortByPerformance))
        );
    }

    private Button createSortButton(String label, Runnable action) {
        Button button = new Button(label);
        button.setOnAction(e -> action.run());
        return button;
    }

    private void sortAndDisplay(Supplier<List<Employee<Integer>>> sorter) {
        List<Employee<Integer>> sorted = sorter.get();
        displayResults(sorted);
    }

    private List<Employee<Integer>> sortBySalary() {
        List<Employee<Integer>> list = new ArrayList<>(database.getAllEmployees());
        list.sort(EmployeeComparator.salaryComparator());
        return list;
    }

    private List<Employee<Integer>> sortByPerformance() {
        List<Employee<Integer>> list = new ArrayList<>(database.getAllEmployees());
        list.sort(EmployeeComparator.performanceComparator());
        return list;
    }

    private List<Employee<Integer>> sortByExperience() {
        List<Employee<Integer>> list = new ArrayList<>(database.getAllEmployees());
        Collections.sort(list);
        return list;
    }

    private void displayResults(List<Employee<Integer>> results) {
        StringBuilder sb = new StringBuilder("Search Results:\n");
        results.forEach(emp -> sb.append(emp).append("\n"));
        outputArea.setText(sb.toString());
    }

    private HBox buildSearchByDepartment() {
        TextField deptField = new TextField();
        Button btn = new Button("Search Department");
        btn.setOnAction(e -> {
            displayResults(database.searchByDepartment(deptField.getText()));
            deptField.clear();
        });
        return new HBox(10, new Label("Department:"), deptField, btn);
    }

    private HBox buildSearchByName() {
        TextField nameField = new TextField();
        Button btn = new Button("Search Name");
        btn.setOnAction(e -> {
            displayResults(database.searchByName(nameField.getText()));
            nameField.clear();
        });
        return new HBox(10, new Label("Name Contains:"), nameField, btn);
    }

    private HBox buildPerformanceFilter() {
        TextField ratingField = new TextField();
        Button btn = new Button("Filter Rating ≥");
        btn.setOnAction(e -> {
            displayResults(database.filterByPerformance(Double.parseDouble(ratingField.getText())));
            ratingField.clear();
        });
        return new HBox(10, new Label("Min Rating:"), ratingField, btn);
    }

    private VBox buildSalaryRangeFilter() {
        TextField minField = new TextField();
        TextField maxField = new TextField();
        Button btn = new Button("Filter Salary Range");
        btn.setOnAction(e -> {
            displayResults(database.filterBySalaryRange(
                Double.parseDouble(minField.getText()),
                Double.parseDouble(maxField.getText())));
            minField.clear(); maxField.clear();
        });

        return new VBox(10, new HBox(10, new Label("Min:"), minField, new Label("Max:"), maxField), btn);
    }

    private HBox buildRaisePane() {
        TextField thresholdField = new TextField();
        thresholdField.setPromptText("Rating Threshold");
        TextField raiseField = new TextField();
        raiseField.setPromptText("Raise %");

        Button raiseBtn = new Button("Apply Raise");
        raiseBtn.setOnAction(e -> {
            try {
                double threshold = Double.parseDouble(thresholdField.getText());
                double percent = Double.parseDouble(raiseField.getText());
                SalaryManagement.giveRaiseToTopPerformers(database, threshold, percent);
                outputArea.setText("Raise applied to top performers!");
            } catch (Exception ex) {
                outputArea.setText("Invalid input for raise.");
            }
        });

        return new HBox(10, new Label("Threshold:"), thresholdField, new Label("Raise%:"), raiseField, raiseBtn);
    }

    private HBox buildTopPaidPane() {
        Button topPaidBtn = new Button("Show Top 5 Paid");
        topPaidBtn.setOnAction(e -> {
            List<Employee<Integer>> topEmployees = SalaryManagement.getTop5HighestPaid(database);
            StringBuilder sb = new StringBuilder("Top 5 Highest Paid Employees:\n");
            topEmployees.forEach(emp -> sb.append(emp).append("\n"));
            outputArea.setText(sb.toString());
        });
        return new HBox(10, topPaidBtn);
    }

    private HBox buildAverageSalaryPane() {
        TextField deptField = new TextField();
        deptField.setPromptText("Department");
        Button avgBtn = new Button("Avg Salary");

        avgBtn.setOnAction(e -> {
            String dept = deptField.getText();
            double avg = SalaryManagement.calculateAverageSalaryByDepartment(database, dept);
            outputArea.setText("Average Salary in " + dept + ": $" + String.format("%.2f", avg));
        });

        return new HBox(10, new Label("Department:"), deptField, avgBtn);
    }
}
