package org.employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SalaryManagementTest {

    private EmployeeDatabase<Integer> database;

    @BeforeEach
    void setUp() {
        database = new EmployeeDatabase<>();
        try {
            database.addEmployee(new Employee<>(1, "Alice", "IT", 5000.0, 4.5, 5, true));
            database.addEmployee(new Employee<>(2, "Bob", "HR", 4500.0, 4.8, 4, true));
            database.addEmployee(new Employee<>(3, "Charlie", "IT", 7000.0, 3.2, 7, true));
            database.addEmployee(new Employee<>(4, "Diana", "Sales", 3000.0, 4.9, 2, true));
            database.addEmployee(new Employee<>(5, "Eve", "HR", 4000.0, 4.1, 3, true));
            database.addEmployee(new Employee<>(6, "Frank", "Sales", 10000.0, 4.7, 8, true)); // highest salary
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }
    }

    @Test
    void testGiveRaiseToTopPerformers() {
        SalaryManagement.giveRaiseToTopPerformers(database, 4.5, 10);

        Employee<Integer> alice = database.getEmployeeById(1);
        Employee<Integer> bob = database.getEmployeeById(2);
        Employee<Integer> diana = database.getEmployeeById(4);
        Employee<Integer> frank = database.getEmployeeById(6);

        assertEquals(5500.0, alice.getSalary(), 0.01);
        assertEquals(4950.0, bob.getSalary(), 0.01);
        assertEquals(3300.0, diana.getSalary(), 0.01);
        assertEquals(11000.0, frank.getSalary(), 0.01);
    }

    @Test
    void testGiveRaiseToTopPerformersWithNoMatch() {
        SalaryManagement.giveRaiseToTopPerformers(database, 5.0, 20);
        assertEquals(5000.0, database.getEmployeeById(1).getSalary());
        assertEquals(4500.0, database.getEmployeeById(2).getSalary());
    }

    @Test
    void testGetTop5HighestPaid() {
        List<Employee<Integer>> top5 = SalaryManagement.getTop5HighestPaid(database);

        assertEquals(5, top5.size());
        assertEquals(10000.0, top5.get(0).getSalary());
        assertTrue(top5.get(1).getSalary() <= top5.get(0).getSalary());
    }

    @Test
    void testGetTop5HighestPaidWithLessThan5Employees() {
        EmployeeDatabase<Integer> smallDB = new EmployeeDatabase<>();
        try {
            smallDB.addEmployee(new Employee<>(10, "Mini", "Legal", 8000.0, 3.5, 10, true));
        } catch (Exception e) {
            fail("Exception during setup: " + e.getMessage());
        }

        List<Employee<Integer>> top = SalaryManagement.getTop5HighestPaid(smallDB);
        assertEquals(1, top.size());
        assertEquals(8000.0, top.get(0).getSalary());
    }

    @Test
    void testCalculateAverageSalaryByDepartment() {
        double avgHR = SalaryManagement.calculateAverageSalaryByDepartment(database, "HR");
        assertEquals((4500.0 + 4000.0) / 2, avgHR, 0.01);
    }

    @Test
    void testCalculateAverageSalaryByDepartmentCaseInsensitive() {
        double avgIt = SalaryManagement.calculateAverageSalaryByDepartment(database, "it");
        assertEquals((5000.0 + 7000.0) / 2, avgIt, 0.01);
    }

    @Test
    void testCalculateAverageSalaryByDepartmentWithNoMatch() {
        double avg = SalaryManagement.calculateAverageSalaryByDepartment(database, "NonExistent");
        assertEquals(0.0, avg);
    }

    @Test
    void testCalculateAverageSalaryHandlesNullDepartment() {
        double avg = SalaryManagement.calculateAverageSalaryByDepartment(database, null);
        assertEquals(0.0, avg);
    }
}

