package org.employees;

import java.util.*;
import java.util.stream.Collectors;

public class SalaryManagement {

    
    public static <T> void giveRaiseToTopPerformers(EmployeeDatabase<T> database, double threshold, double raisePercent) {
        database.getAllEmployees().stream()
            .filter(e -> e.getPerformanceRating() >= threshold)
            .forEach(e -> {
                double newSalary = e.getSalary() * (1 + raisePercent / 100);
                e.setSalary(newSalary);
            });
    }

    
    public static <T> List<Employee<T>> getTop5HighestPaid(EmployeeDatabase<T> database) {
        return database.getAllEmployees().stream()
            .sorted(Comparator.comparingDouble(e -> ((Employee<T>) e).getSalary()).reversed())
            .limit(5)
            .collect(Collectors.toList());
    }

    
    public static <T> double calculateAverageSalaryByDepartment(EmployeeDatabase<T> database, String department) {
        return database.getAllEmployees().stream()
            .filter(e -> e.getDepartment().equalsIgnoreCase(department))
            .mapToDouble(Employee::getSalary)
            .average()
            .orElse(0.0);
    }
}
