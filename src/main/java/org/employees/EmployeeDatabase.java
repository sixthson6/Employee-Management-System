package org.employees;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.employees.EmployeeExceptions.EmployeeNotFoundException;
import org.employees.EmployeeExceptions.InvalidDepartmentException;
import org.employees.EmployeeExceptions.InvalidSalaryException;


public class EmployeeDatabase<T> {

    private  final Map<T, Employee<T>> employeeMap = new HashMap<>();
    private static final Logger logger = Logger.getLogger(EmployeeDatabase.class.getName());

    static {
        logger.setLevel(Level.ALL);
        for (Handler handler : Logger.getLogger("").getHandlers()) {
            handler.setLevel(Level.ALL);
        }
    }

    public void addEmployee(Employee<T> employee) throws InvalidSalaryException {
        if (employee == null) {
            throw new IllegalArgumentException("Employee cannot be null.");
        }
        if (employeeMap.containsKey(employee.getEmployeeId())) {
            throw new IllegalArgumentException("Employee with this ID already exists.");
        }
        if (employee.getSalary() < 0) {
            throw new EmployeeExceptions.InvalidSalaryException("Salary cannot be negative.");
        }
        employeeMap.put(employee.getEmployeeId(), employee);
    }


    public Collection<Employee<T>> getAllEmployees() {
        try {
            if (employeeMap.isEmpty()) {
                throw new IllegalStateException("No employees found in the database.");
            }
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, "Error retrieving employees: {0}", e.getMessage());
            return Collections.emptyList();
        }
        return employeeMap.values();
    }


    public void removeEmployee(T employeeId) {
        try {
            if (employeeId == null) {
                throw new IllegalArgumentException("Employee ID cannot be null.");
            }
            if (!employeeMap.containsKey(employeeId)) {
                throw new IllegalArgumentException("Employee with this ID does not exist.");
            }
        } catch (IllegalArgumentException e) {
            logger.log(Level.SEVERE, "Error removing employee: {0}", e.getMessage());
            return;
        }
        employeeMap.remove(employeeId);
    }


    public boolean updateEmployeeDetails(T employeeId, String field, Object newValue) {
        try {
            Employee<T> employee = employeeMap.get(employeeId);
            if (employee == null) {
                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
            }

            switch (field.toLowerCase()) {
                case "name":
                    employee.setName((String) newValue);
                    break;
                case "department":
                    if (newValue == null || ((String) newValue).trim().isEmpty()) {
                        throw new InvalidDepartmentException("Department cannot be empty or null.");
                    }
                    employee.setDepartment((String) newValue);
                    break;
                case "salary":
                    double salary = (Double) newValue;
                    if (salary < 0) {
                        throw new InvalidSalaryException("Salary cannot be negative.");
                    }
                    employee.setSalary(salary);
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
            logger.log(Level.INFO, "Employee updated: {0}", employee);
            return true;
        } catch (EmployeeNotFoundException | InvalidSalaryException | InvalidDepartmentException e) {
            logger.log(Level.SEVERE, "Error updating employee: {0}", e.getMessage());
            return false;
        }
    }


    public Employee<T> getEmployeeById(T employeeId) {
        try {
            if (employeeId == null) {
                throw new IllegalArgumentException("Employee ID cannot be null.");
            }
            if (!employeeMap.containsKey(employeeId)) {
                throw new EmployeeNotFoundException("Employee with ID " + employeeId + " not found.");
            }
        } catch (IllegalArgumentException | EmployeeNotFoundException e) {
            logger.log(Level.SEVERE, "Error in retrieving employee: {0}", e.getMessage());
            return null;
        }
        return employeeMap.get(employeeId);
    }


    public List<Employee<T>> getEmployeesByDepartment(String department) throws InvalidDepartmentException {
        if (department == null || department.trim().isEmpty()) {
            throw new EmployeeExceptions.InvalidDepartmentException("Department cannot be empty or null.");
        }
        return employeeMap.values().stream()
                .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                .collect(Collectors.toList());
    }

    public List<Employee<T>> searchEmployeesByName(String keyword) {
        try {
            return employeeMap.values().stream()
                    .filter(emp -> emp.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (NullPointerException | ClassCastException e) {
            logger.log(Level.WARNING, "Error searching employees by name: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<Employee<T>> getHighPerformers(double threshold) {
        try {
            return employeeMap.values().stream()
                    .filter(emp -> emp.getPerformanceRating() >= threshold)
                    .collect(Collectors.toList());
        } catch (NullPointerException | ClassCastException e) {
            logger.log(Level.WARNING, "Error retrieving high performers: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<Employee<T>> getEmployeesBySalaryRange(double min, double max) {
        try {
            return employeeMap.values().stream()
                    .filter(emp -> emp.getSalary() >= min && emp.getSalary() <= max)
                    .collect(Collectors.toList());
        } catch (NullPointerException | ClassCastException | IllegalArgumentException e) {
            logger.log(Level.WARNING, "Error filtering by salary range: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public Iterator<Employee<T>> getEmployeeIterator() {
        try {
            return employeeMap.values().iterator();
        } catch (IllegalStateException e) {
            logger.log(Level.WARNING, "Error getting employee iterator: " + e.getMessage(), e);
            return Collections.emptyIterator();
        }
    }

    public List<Employee<T>> searchByDepartment(String department) {
        try {
            return employeeMap.values().stream()
                    .filter(emp -> emp.getDepartment().equalsIgnoreCase(department))
                    .collect(Collectors.toList());
        } catch (NullPointerException | ClassCastException e) {
            logger.log(Level.WARNING, "Error searching employees by department: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<Employee<T>> searchByName(String keyword) {
        try {
            return employeeMap.values().stream()
                    .filter(emp -> emp.getName().toLowerCase().contains(keyword.toLowerCase()))
                    .collect(Collectors.toList());
        } catch (NullPointerException | ClassCastException e) {
            logger.log(Level.WARNING, "Error searching employees by name: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<Employee<T>> filterByPerformance(double minRating) {
        try {
            return employeeMap.values().stream()
                    .filter(emp -> emp.getPerformanceRating() >= minRating)
                    .collect(Collectors.toList());
        } catch (NullPointerException | ClassCastException e) {
            logger.log(Level.WARNING, "Error filtering employees by performance: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<Employee<T>> filterBySalaryRange(double minSalary, double maxSalary) {
        try {
            return employeeMap.values().stream()
                    .filter(emp -> emp.getSalary() >= minSalary && emp.getSalary() <= maxSalary)
                    .collect(Collectors.toList());
        } catch (NullPointerException | ClassCastException | IllegalArgumentException e) {
            logger.log(Level.WARNING, "Error filtering by salary range: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<Employee<T>> getTop5HighestPaid() {
        try {
            List<Employee<T>> employees = new ArrayList<>(employeeMap.values());
            return employees.stream()
                    .sorted((e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()))
                    .limit(5)
                    .collect(Collectors.toList());
        } catch (NullPointerException | ClassCastException e) {
            logger.log(Level.WARNING, "Error retrieving top 5 highest paid employees: " + e.getMessage(), e);
            return Collections.emptyList();
        }
    }

}
