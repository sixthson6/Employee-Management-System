package org.employees;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EmployeeTest {

    @Test
    void testConstructorAndGetters() {
        Employee<Integer> employee = new Employee<>(1, "Alice", "HR", 50000.0, 4.5, 5, true);

        assertEquals(1, employee.getEmployeeId());
        assertEquals("Alice", employee.getName());
        assertEquals("HR", employee.getDepartment());
        assertEquals(50000.0, employee.getSalary());
        assertEquals(4.5, employee.getPerformanceRating());
        assertEquals(5, employee.getYearsOfExperience());
        assertTrue(employee.isActive());
    }

    @Test
    void testSetters() {
        Employee<Integer> employee = new Employee<>(1, "Alice", "HR", 50000.0, 4.5, 5, true);

        employee.setName("Bob");
        employee.setDepartment("Engineering");
        employee.setSalary(60000.0);
        employee.setPerformanceRating(4.8);
        employee.setYearsOfExperience(10);
        employee.setActive(false);

        assertEquals("Bob", employee.getName());
        assertEquals("Engineering", employee.getDepartment());
        assertEquals(60000.0, employee.getSalary());
        assertEquals(4.8, employee.getPerformanceRating());
        assertEquals(10, employee.getYearsOfExperience());
        assertFalse(employee.isActive());
    }

    @Test
    void testInvalidValues() {
        assertThrows(IllegalArgumentException.class, () -> new Employee<>(1, "", "HR", 50000.0, 4.5, 5, true));
        assertThrows(IllegalArgumentException.class, () -> new Employee<>(1, "Alice", "", 50000.0, 4.5, 5, true));
        assertThrows(IllegalArgumentException.class, () -> new Employee<>(1, "Alice", "HR", -1000.0, 4.5, 5, true));
        assertThrows(IllegalArgumentException.class, () -> new Employee<>(1, "Alice", "HR", 50000.0, -1.0, 5, true));
        assertThrows(IllegalArgumentException.class, () -> new Employee<>(1, "Alice", "HR", 50000.0, 6.0, 5, true));
        assertThrows(IllegalArgumentException.class, () -> new Employee<>(1, "Alice", "HR", 50000.0, 4.5, -1, true));
    }

    @Test
    void testEqualsAndHashCode() {
        Employee<Integer> employee1 = new Employee<>(1, "Alice", "HR", 50000.0, 4.5, 5, true);
        Employee<Integer> employee2 = new Employee<>(1, "Bob", "Engineering", 60000.0, 4.8, 10, false);
        Employee<Integer> employee3 = new Employee<>(2, "Charlie", "Marketing", 40000.0, 4.0, 3, true);

        assertEquals(employee1, employee2); // Same employeeId
        assertNotEquals(employee1, employee3); // Different employeeId
        assertEquals(employee1.hashCode(), employee2.hashCode());
        assertNotEquals(employee1.hashCode(), employee3.hashCode());
    }

    @Test
    void testCompareTo() {
        Employee<Integer> employee1 = new Employee<>(1, "Alice", "HR", 50000.0, 4.5, 5, true);
        Employee<Integer> employee2 = new Employee<>(2, "Bob", "Engineering", 60000.0, 4.8, 10, false);

        assertTrue(employee1.compareTo(employee2) > 0); // employee1 has less experience
        assertTrue(employee2.compareTo(employee1) < 0); // employee2 has more experience
    }

    @Test
    void testToString() {
        Employee<Integer> employee = new Employee<>(1, "Alice", "HR", 50000.0, 4.5, 5, true);
        String expected = "Employee [ID=1, Name=Alice, Dept=HR, Salary=50000.00, Rating=4.5, Experience=5 yrs, Active=true]\n";
        assertEquals(expected, employee.toString());
    }
}

