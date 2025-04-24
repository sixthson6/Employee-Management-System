package org.employees;

import org.employees.EmployeeExceptions.EmployeeNotFoundException;
import org.employees.EmployeeExceptions.InvalidDepartmentException;
import static org.employees.EmployeeExceptions.InvalidSalaryException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class EmployeeDatabaseTest {

    private EmployeeDatabase<String> db;
    private Employee<String> emp1, emp2, emp3;

    @BeforeEach
    public void setup() {
        db = new EmployeeDatabase<>();
        emp1 = new Employee<>("E1", "Alice", "HR", 50000, 4.5, 5, true);
        emp2 = new Employee<>("E2", "Bob", "Engineering", 70000, 4.8, 7, true);
        emp3 = new Employee<>("E3", "Charlie", "Engineering", 30000, 3.2, 2, true);
        try {
            db.addEmployee(emp1);
            db.addEmployee(emp2);
            db.addEmployee(emp3);
        } catch (InvalidSalaryException e) {
            fail("Setup failed due to InvalidSalaryException: " + e.getMessage());
        }
    }

    @Test
    public void testAddDuplicateEmployee() {
        assertThrows(IllegalArgumentException.class, () -> db.addEmployee(emp1));
    }



    @Test
    public void testRemoveEmployee() {
        db.removeEmployee("E1");
        assertNull(db.getEmployeeById("E1"));
    }

    @Test
    public void testRemoveNonexistentEmployee() {
        db.removeEmployee("E999");  // Should log error but not throw
    }

    @Test
    public void testUpdateEmployeeDetailsSuccess() {
        boolean updated = db.updateEmployeeDetails("E1", "name", "Alicia");
        assertTrue(updated);
        assertEquals("Alicia", db.getEmployeeById("E1").getName());
    }

    @Test
    public void testUpdateWithInvalidSalary() {
        boolean updated = db.updateEmployeeDetails("E2", "salary", -10000.0);
        assertFalse(updated);
    }

    @Test
    public void testUpdateWithInvalidDepartment() {
        boolean updated = db.updateEmployeeDetails("E2", "department", "");
        assertFalse(updated);
    }

    @Test
    public void testUpdateWithUnknownField() {
        boolean updated = db.updateEmployeeDetails("E1", "unknown", "value");
        assertFalse(updated);
    }

    @Test
    public void testGetEmployeeByIdValid() {
        Employee<String> found = db.getEmployeeById("E2");
        assertNotNull(found);
    }

    @Test
    public void testGetEmployeeByIdInvalid() {
        assertNull(db.getEmployeeById("E999"));
    }

    @Test
    public void testGetEmployeesByDepartment() throws InvalidDepartmentException {
        List<Employee<String>> engineers = db.getEmployeesByDepartment("Engineering");
        assertEquals(2, engineers.size());
    }

    @Test
    public void testGetEmployeesByInvalidDepartment() {
        assertThrows(InvalidDepartmentException.class, () -> db.getEmployeesByDepartment(""));
    }

    @Test
    public void testSearchEmployeesByName() {
        List<Employee<String>> results = db.searchEmployeesByName("Ali");
        assertEquals(1, results.size());
    }

    @Test
    public void testHighPerformers() {
        List<Employee<String>> results = db.getHighPerformers(4.0);
        assertEquals(2, results.size());
    }

    @Test
    public void testSalaryRange() {
        List<Employee<String>> results = db.getEmployeesBySalaryRange(40000, 80000);
        assertEquals(2, results.size());
    }

    @Test
    public void testTop5HighestPaid() {
        List<Employee<String>> topPaid = db.getTop5HighestPaid();
        assertEquals(3, topPaid.size());
        assertEquals("E2", topPaid.get(0).getEmployeeId());
    }

    @Test
    public void testSearchByDepartment() {
        List<Employee<String>> results = db.searchByDepartment("Engineering");
        assertEquals(2, results.size());
    }

    @Test
    public void testSearchByName() {
        List<Employee<String>> results = db.searchByName("char");
        assertEquals(1, results.size());
    }

    @Test
    public void testFilterByPerformance() {
        List<Employee<String>> results = db.filterByPerformance(3.5);
        assertEquals(2, results.size());
    }

    @Test
    public void testFilterBySalaryRange() {
        List<Employee<String>> results = db.filterBySalaryRange(20000, 60000);
        assertEquals(2, results.size());
    }

    @Test
    public void testGetAllEmployees() {
        assertEquals(3, db.getAllEmployees().size());
    }

    @Test
    public void testGetAllEmployeesEmpty() {
        EmployeeDatabase<String> emptyDb = new EmployeeDatabase<>();
        assertEquals(0, emptyDb.getAllEmployees().size());
    }

    @Test
    public void testGetEmployeeIterator() {
        assertTrue(db.getEmployeeIterator().hasNext());
    }

    @Test
    public void testNullInputAdd() {
        assertThrows(IllegalArgumentException.class, () -> db.addEmployee(null));
    }

    @Test
    public void testNullInputRemove() {
        db.removeEmployee(null); // Should log error but not throw
    }

    @Test
    public void testUpdateNonExistentEmployee() {
        boolean result = db.updateEmployeeDetails("NON_EXISTENT", "name", "Ghost");
        assertFalse(result);
    }

    @Test
    public void testAddValidEmployee() {
        Employee<String> newEmp = new Employee<>("E4", "Diana", "Marketing", 40000, 4.0, 4, true);
        assertDoesNotThrow(() -> db.addEmployee(newEmp));
        assertEquals(4, db.getAllEmployees().size());
    }

    @Test
    public void testAddDuplicateEmployeeId() {
        Employee<String> duplicate = new Employee<>("E1", "Eve", "Finance", 60000, 3.5, 2, true);
        assertThrows(IllegalArgumentException.class, () -> db.addEmployee(duplicate));
        assertEquals(3, db.getAllEmployees().size()); // Ensure size didn't change
    }

    @Test
    public void testAddNullEmployee() {
        assertThrows(IllegalArgumentException.class, () -> db.addEmployee(null));
        assertEquals(3, db.getAllEmployees().size()); // No change
    }

    @Test
    public void testAddMultipleValidEmployees() {
        Employee<String> emp4 = new Employee<>("E4", "Gina", "Design", 48000, 4.1, 3, true);
        Employee<String> emp5 = new Employee<>("E5", "Henry", "IT", 51000, 4.6, 4, true);

        assertDoesNotThrow(() -> db.addEmployee(emp4));
        assertDoesNotThrow(() -> db.addEmployee(emp5));
        assertEquals(5, db.getAllEmployees().size());
    }
}
