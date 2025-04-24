package org.employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class EmployeeComparatorTest {

    private Employee<Integer> emp1;
    private Employee<Integer> emp2;
    private Employee<Integer> emp3;

    @BeforeEach
    void setUp() {
        emp1 = new Employee<>(1, "Alice", "IT", 50000, 4.2, 5, true);
        emp2 = new Employee<>(2, "Bob", "HR", 75000, 3.8, 7, true);
        emp3 = new Employee<>(3, "Charlie", "IT", 60000, 4.5, 3, true);
    }

    @Test
    void testSalaryComparatorAscending() {
        List<Employee<Integer>> list = Arrays.asList(emp1, emp2, emp3);
        list.sort(EmployeeComparator.salaryComparator());

        assertEquals(emp1, list.get(0)); // lowest salary
        assertEquals(emp3, list.get(1));
        assertEquals(emp2, list.get(2)); // highest salary
    }

    @Test
    void testPerformanceComparatorDescending() {
        List<Employee<Integer>> list = Arrays.asList(emp1, emp2, emp3);
        list.sort(EmployeeComparator.performanceComparator());

        assertEquals(emp3, list.get(0)); // highest rating
        assertEquals(emp1, list.get(1));
        assertEquals(emp2, list.get(2)); // lowest rating
    }

    @Test
    void testSalaryComparatorThrowsOnNull() {
        Comparator<Employee<Integer>> comparator = EmployeeComparator.salaryComparator();
        assertThrows(IllegalArgumentException.class, () -> comparator.compare(emp1, null));
        assertThrows(IllegalArgumentException.class, () -> comparator.compare(null, emp1));
    }

    @Test
    void testPerformanceComparatorThrowsOnNull() {
        Comparator<Employee<Integer>> comparator = EmployeeComparator.performanceComparator();
        assertThrows(IllegalArgumentException.class, () -> comparator.compare(emp2, null));
        assertThrows(IllegalArgumentException.class, () -> comparator.compare(null, emp3));
    }

    @Test
    void testSalaryComparatorEqualSalaries() {
        Employee<Integer> cloneEmp1 = new Employee<>(4, "CloneAlice", "IT", 50000, 3.0, 2, true);
        Comparator<Employee<Integer>> comparator = EmployeeComparator.salaryComparator();
        assertEquals(0, comparator.compare(emp1, cloneEmp1));
    }

    @Test
    void testPerformanceComparatorEqualRatings() {
        Employee<Integer> cloneEmp2 = new Employee<>(5, "CloneBob", "HR", 50000, 4.2, 3, true);
        Comparator<Employee<Integer>> comparator = EmployeeComparator.performanceComparator();
        assertEquals(0, comparator.compare(emp1, cloneEmp2));
    }
}

