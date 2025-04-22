package org.employees;

import java.util.Comparator;

public class EmployeeComparator {

    public static <T> Comparator<Employee<T>> salaryComparator() {
        return (e1, e2) -> Double.compare(e2.getSalary(), e1.getSalary()); 
    }

    public static <T> Comparator<Employee<T>> performanceComparator() {
        return (e1, e2) -> Double.compare(e2.getPerformanceRating(), e1.getPerformanceRating());
    }
}
