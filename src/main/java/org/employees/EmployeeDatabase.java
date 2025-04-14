package org.employees;

import java.util.*;
import java.util.stream.Collectors;
/**
 * Employee database manager class handling storage and CRUD operations.
 *
 * @param <T> the type of the unique identifier for employees
 */
public class EmployeeDatabase<T> {
    
    private  final Map<T, Employee<T>> employeeMap = new HashMap<>();

    
    public void addEmployee(Employee<T> employee) {
        employeeMap.put(employee.getEmployeeId(), employee);
    }

    
    public Collection<Employee<T>> getAllEmployees() {
        return employeeMap.values();
    }

    
    public void removeEmployee(T employeeId) {
        employeeMap.remove(employeeId);
    }

    
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
                return false;
        }
        return true;
    }

    
    public Employee<T> getEmployeeById(T employeeId) {
        return employeeMap.get(employeeId);
    }

    
    public List<Employee<T>> getEmployeesByDepartment(String department) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    
    public List<Employee<T>> searchEmployeesByName(String keyword) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    
    public List<Employee<T>> getHighPerformers(double threshold) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getPerformanceRating() >= threshold)
                .collect(Collectors.toList());
    }

    
    public List<Employee<T>> getEmployeesBySalaryRange(double min, double max) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getSalary() >= min && emp.getSalary() <= max)
                .collect(Collectors.toList());
    }


    public Iterator<Employee<T>> getEmployeeIterator() {
        return employeeMap.values().iterator();
    }

    
    public List<Employee<T>> searchByDepartment(String department) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    
    public List<Employee<T>> searchByName(String keyword) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }

    
    public List<Employee<T>> filterByPerformance(double minRating) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getPerformanceRating() >= minRating)
                .collect(Collectors.toList());
    }

    
    public List<Employee<T>> filterBySalaryRange(double minSalary, double maxSalary) {
        return employeeMap.values().stream()
                .filter(emp -> emp.getSalary() >= minSalary && emp.getSalary() <= maxSalary)
                .collect(Collectors.toList());
    }

}
