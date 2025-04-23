package org.employees;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SalaryManagement {

    private static final Logger logger = Logger.getLogger(SalaryManagement.class.getName());

    /**
     * Raises the salary of all top-performing employees above a threshold.
     */
    public static <T> void giveRaiseToTopPerformers(EmployeeDatabase<T> database, double threshold, double raisePercent) {
        try {
            database.getAllEmployees().stream()
                    .filter(e -> {
                        try {
                            return e.getPerformanceRating() >= threshold;
                        } catch (Exception ex) {
                            logger.log(Level.WARNING, "Error filtering by performance rating: " + ex.getMessage(), ex);
                            return false;
                        }
                    })
                    .forEach(e -> {
                        try {
                            double newSalary = e.getSalary() * (1 + raisePercent / 100);
                            e.setSalary(newSalary);
                            logger.log(Level.INFO, "Raised salary for employee ID: " + e.getEmployeeId());
                        } catch (Exception ex) {
                            logger.log(Level.SEVERE, "Error giving raise to employee ID: " + e.getEmployeeId(), ex);
                        }
                    });
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Unexpected error in giveRaiseToTopPerformers: " + e.getMessage(), e);
        }
    }

    /**
     * Returns the top 5 highest-paid employees.
     */
    public static <T> List<Employee<T>> getTop5HighestPaid(EmployeeDatabase<T> database) {
        try {
            return database.getAllEmployees().stream()
                    .sorted((e1, e2) -> {
                        try {
                            return Double.compare(e2.getSalary(), e1.getSalary());
                        } catch (Exception ex) {
                            logger.log(Level.WARNING, "Error comparing salaries: " + ex.getMessage(), ex);
                            return 0;
                        }
                    })
                    .limit(5)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to retrieve top 5 highest paid employees: " + e.getMessage(), e);
            return List.of(); // Return empty list on failure
        }
    }

    /**
     * Calculates the average salary for a given department.
     */
    public static <T> double calculateAverageSalaryByDepartment(EmployeeDatabase<T> database, String department) {
        try {
            return database.getAllEmployees().stream()
                    .filter(e -> {
                        try {
                            return e.getDepartment().equalsIgnoreCase(department);
                        } catch (Exception ex) {
                            logger.log(Level.WARNING, "Error filtering by department: " + ex.getMessage(), ex);
                            return false;
                        }
                    })
                    .mapToDouble(e -> {
                        try {
                            return e.getSalary();
                        } catch (Exception ex) {
                            logger.log(Level.WARNING, "Error retrieving salary: " + ex.getMessage(), ex);
                            return 0.0;
                        }
                    })
                    .average()
                    .orElse(0.0);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Failed to calculate average salary: " + e.getMessage(), e);
            return 0.0;
        }
    }
}
