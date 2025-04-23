package org.employees;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.employees.EmployeeExceptions.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDatabaseTest {

    private EmployeeDatabase<String> database;
    private Employee<String> employee1;
    private Employee<String> employee2;

    @BeforeEach
    void setUp() {
        database = new EmployeeDatabase<>();
        employee1 = new Employee<>("E001", "John Doe", "IT", 60000, 4.5, 5, true);
        employee2 = new Employee<>("E002", "Jane Smith", "HR", 50000, 4.0, 3, true);
    }

    @Test
    void testAddEmployeeSuccess() {
        assertDoesNotThrow(() -> database.addEmployee(employee1));
        assertEquals(1, database.getAllEmployees().size());
    }

    @Test
    void testAddDuplicateEmployee() {
        assertDoesNotThrow(() -> database.addEmployee(employee1));
        assertDoesNotThrow(() -> database.addEmployee(employee1)); // logs but doesn’t throw
        assertEquals(1, database.getAllEmployees().size());
    }

    @Test
    void testRemoveExistingEmployee() throws InvalidSalaryException {
        database.addEmployee(employee1);
        database.removeEmployee("E001");
        assertEquals(0, database.getAllEmployees().size());
    }

    @Test
    void testRemoveNonExistentEmployee() {
        database.removeEmployee("NonExistent"); // should log but not throw
        assertEquals(0, database.getAllEmployees().size());
    }

    @Test
    void testUpdateValidEmployee() throws InvalidSalaryException {
        database.addEmployee(employee1);
        boolean result = database.updateEmployeeDetails("E001", "salary", 75000.0);
        assertTrue(result);
        assertEquals(75000.0, database.getEmployeeById("E001").getSalary());
    }

    @Test
    void testUpdateInvalidDepartment() throws InvalidSalaryException {
        database.addEmployee(employee1);
        boolean result = database.updateEmployeeDetails("E001", "department", "");
        assertFalse(result); // should log error
    }

    @Test
    void testUpdateNonExistentEmployee() {
        boolean result = database.updateEmployeeDetails("E999", "salary", 5000.0);
        assertFalse(result);
    }

    @Test
    void testGetEmployeeByIdSuccess() throws InvalidSalaryException {
        database.addEmployee(employee1);
        Employee<String> found = database.getEmployeeById("E001");
        assertNotNull(found);
        assertEquals("John Doe", found.getName());
    }

    @Test
    void testGetEmployeeByIdFailure() {
        assertNull(database.getEmployeeById("InvalidID"));
    }

    @Test
    void testGetEmployeesByDepartment() throws InvalidSalaryException, InvalidDepartmentException {
        database.addEmployee(employee1);
        database.addEmployee(employee2);
        List<Employee<String>> itEmployees = database.getEmployeesByDepartment("IT");
        assertEquals(1, itEmployees.size());
    }

    @Test
    void testSearchByNamePartialMatch() throws InvalidSalaryException {
        database.addEmployee(employee1);
        List<Employee<String>> result = database.searchEmployeesByName("john");
        assertEquals(1, result.size());
    }

    @Test
    void testHighPerformersFilter() throws InvalidSalaryException {
        database.addEmployee(employee1);
        database.addEmployee(employee2);
        List<Employee<String>> top = database.getHighPerformers(4.3);
        assertEquals(1, top.size());
        assertEquals("John Doe", top.get(0).getName());
    }

    @Test
    void testGetTop5HighestPaid() throws InvalidSalaryException {
        database.addEmployee(employee1);
        database.addEmployee(employee2);
        List<Employee<String>> topPaid = database.getTop5HighestPaid();
        assertEquals(2, topPaid.size());
        assertEquals("John Doe", topPaid.get(0).getName());
    }

    @Test
    void testGetAllEmployeesWhenEmpty() {
        assertTrue(database.getAllEmployees().isEmpty());
    }
}
