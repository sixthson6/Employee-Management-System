package org.employees;

import org.employees.Employee;
import org.employees.EmployeeDatabase;

import java.util.List;
import java.util.stream.Collectors;

public class EmployeeDisplayUtil {

    // Display using for-each loop
    public static <T> String displayWithForEach(EmployeeDatabase<T> database) {
        StringBuilder sb = new StringBuilder(getHeader());
        for (Employee<T> emp : database.getAllEmployees()) {
            sb.append(formatEmployee(emp));
        }
        return sb.toString();
    }

    // Display using Streams
    public static <T> String displayWithStream(EmployeeDatabase<T> database) {
        return database.getAllEmployees().stream()
            .map(EmployeeDisplayUtil::formatEmployee)
            .collect(Collectors.joining("", getHeader(), ""));
    }

    // Common header
    private static String getHeader() {
        return String.format("%-5s %-15s %-15s %-10s %-10s %-10s %-10s%n",
                "ID", "Name", "Department", "Salary", "Rating", "Exp(Yrs)", "Active");
    }

    // Format one employee
    private static <T> String formatEmployee(Employee<T> emp) {
        return String.format("%-5s %-15s %-15s $%-9.2f %-10.1f %-10d %-10s%n",
                emp.getEmployeeId(), emp.getName(), emp.getDepartment(), emp.getSalary(),
                emp.getPerformanceRating(), emp.getYearsOfExperience(),
                emp.isActive());
    }
}
