package org.employees;

import java.util.Objects;

public final class Employee<T> implements Comparable<Employee<T>> {
    private T employeeId;
    private String name;
    private String department;
    private double salary;
    private double performanceRating;
    private int yearsOfExperience;
    private boolean isActive;

    public Employee(T employeeId, String name, String department, double salary,
                    double performanceRating, int yearsOfExperience, boolean isActive) throws IllegalArgumentException {
        try {
            setEmployeeId(employeeId);
            setName(name);
            setDepartment(department);
            setSalary(salary);
            setPerformanceRating(performanceRating);
            setYearsOfExperience(yearsOfExperience);
            setActive(isActive);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Error initializing Employee: " + e.getMessage(), e);
        }
    }

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
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty or null.");
        }
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        if (department == null || department.trim().isEmpty()) {
            throw new IllegalArgumentException("Department cannot be empty or null.");
        }
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary cannot be negative.");
        }
        this.salary = salary;
    }

    public double getPerformanceRating() {
        return performanceRating;
    }

    public void setPerformanceRating(double performanceRating) {
        if (performanceRating < 0 || performanceRating > 5) {
            throw new IllegalArgumentException("Performance rating must be between 0 and 5.");
        }
        if (performanceRating == 0) {
            throw new IllegalArgumentException("Performance rating cannot be zero.");
        }
        this.performanceRating = performanceRating;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        if (yearsOfExperience < 0) {
            throw new IllegalArgumentException("Years of experience cannot be negative.");
        }
        if (yearsOfExperience == 0) {
            throw new IllegalArgumentException("Years of experience cannot be zero.");
        }
        this.yearsOfExperience = yearsOfExperience;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public int compareTo(Employee<T> other) {
        return Integer.compare(other.yearsOfExperience, this.yearsOfExperience);
    }

    @Override
    public boolean equals(Object o) {
        try {
            if (this == o) return true;
            if (!(o instanceof Employee<?>)) return false;
            Employee<?> other = (Employee<?>) o;
            return Objects.equals(employeeId, other.employeeId);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while comparing Employee objects.", e);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }

    @Override
    public String toString() {
        try {
            String strFormat = "Employee [ID=%s, Name=%s, Dept=%s, Salary=%.2f, Rating=%.1f, Experience=%d yrs, Active=%b]\n";
            return String.format(strFormat, employeeId, name, department, salary, performanceRating, yearsOfExperience, isActive);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while generating the string representation of Employee.", e);
        }
    }
}
