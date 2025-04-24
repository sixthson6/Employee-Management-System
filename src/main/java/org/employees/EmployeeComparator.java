package org.employees;

import java.util.Comparator;
import java.util.logging.Logger;

public class EmployeeComparator {

    private static final Logger logger = Logger.getLogger(EmployeeComparator.class.getName());

    public static <T> Comparator<Employee<T>> salaryComparator() {
        return (e1, e2) -> {
            if (e1 == null || e2 == null) {
                throw new IllegalArgumentException("e1 and e2 cannot be null");
            }
            return Double.compare(e1.getSalary(), e2.getSalary());
        };
    }

    public static <T> Comparator<Employee<T>> performanceComparator() {
        return (e1, e2) -> {
            if (e1 == null || e2 == null) {
                throw new IllegalArgumentException("e1 and e2 cannot be null");
            }
            return Double.compare(e2.getPerformanceRating(), e1.getPerformanceRating());
        };
    }
}
