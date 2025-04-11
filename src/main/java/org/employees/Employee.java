package org.employees;

import java.util.Objects;

/**
 * Generic Employee class representing an employee in the system.
 *
 * @param <T> the type of the unique identifier (e.g., Integer, UUID)
 */
public class Employee<T> implements Comparable<Employee<T>> {
    private T employeeId;
    private String name;
    private String department;
    private double salary;
    private double performanceRating; // 0 to 5 scale
    private int yearsOfExperience;
    private boolean isActive;

    public Employee(T employeeId, String name, String department, double salary,
                    double performanceRating, int yearsOfExperience, boolean isActive) {
        this.employeeId = employeeId;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.performanceRating = performanceRating;
        this.yearsOfExperience = yearsOfExperience;
        this.isActive = isActive;
    }

    // Getters and setters
    public T getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(T employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(double performanceRating) {
        this.performanceRating = performanceRating;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // Natural ordering: sort by years of experience (descending)
    @Override
    public int compareTo(Employee<T> other) {
        return Integer.compare(other.yearsOfExperience, this.yearsOfExperience);
    }

    // Equals and hashCode based on employeeId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee<?>)) return false;
        Employee<?> other = (Employee<?>) o;
        return Objects.equals(employeeId, other.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }

    @Override
    public String toString() {
        return String.format("Employee [ID=%s, Name=%s, Dept=%s, Salary=%.2f, Rating=%.1f, Experience=%d yrs, Active=%b]",
                employeeId, name, department, salary, performanceRating, yearsOfExperience, isActive);
    }
}
