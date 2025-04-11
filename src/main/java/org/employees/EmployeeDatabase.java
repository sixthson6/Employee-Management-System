package org.employees;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Employee database manager class handling storage and CRUD operations.
 *
 * @param <T> the type of the unique identifier for employees
 */
public class EmployeeDatabase<T> {
    // private Employee<T> employee; 
    private  final Map<T, Employee<T>> employeeMap = new HashMap<>();

    // Create / Add Employee
    public void addEmployee(Employee<T> employee) {
        employeeMap.put(employee.getEmployeeId(), employee);
    }

    // Read / Retrieve all Employees
    public Collection<Employee<T>> getAllEmployees() {
        return employeeMap.values();
    }

    // Delete / Remove Employee by ID
    public void removeEmployee(T employeeId) {
        employeeMap.remove(employeeId);
    }

    // Update a specific field dynamically
    public boolean updateEmployeeDetails(T employeeId, String field, Object newValue) {
        Employee<T> employee = employeeMap.get(employeeId);
        if (employee == null) return false;

        switch (field.toLowerCase()) {
            case "name":
                employee.setName((String) newValue);
                break;
            case "department":
                employee.setDepartment((String) newValue);
                break;
            case "salary":
                employee.setSalary((Double) newValue);
                break;
            case "performancerating":
                employee.setPerformanceRating((Double) newValue);
                break;
            case "yearsofexperience":
                employee.setYearsOfExperience((Integer) newValue);
                break;
            case "isactive":
                employee.setActive((Boolean) newValue);
                break;
            default:
                return false; // Unknown field
        }
        return true;
    }

    // Optional: Get one employee by ID
    public Employee<T> getEmployeeById(T employeeId) {
        return employeeMap.get(employeeId);
    }

    // Get employees by department
    public List<Employee<T>> getEmployeesByDepartment(String department) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    // Get employees by name keyword
    public List<Employee<T>> searchEmployeesByName(String keyword) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Get high performing employees (e.g., rating >= threshold)
    public List<Employee<T>> getHighPerformers(double threshold) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getPerformanceRating() >= threshold)
                .collect(Collectors.toList());
    }

    // Get employees within salary range
    public List<Employee<T>> getEmployeesBySalaryRange(double min, double max) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getSalary() >= min && emp.getSalary() <= max)
                .collect(Collectors.toList());
    }

    // Iterator to traverse employees
    public Iterator<Employee<T>> getEmployeeIterator() {
        return employeeMap.values().iterator();
    }

}
