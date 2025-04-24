package org.employees;

public class EmployeeExceptions {
    public static class EmployeeNotFoundException extends Exception {
        public EmployeeNotFoundException(String message) {
            super(message);
        }
    }

    public static class InvalidEmployeeIdException extends Exception {
        public InvalidEmployeeIdException(String message) {
            super(message);
        }
    }


    public static class InvalidSalaryException extends Exception {
        public InvalidSalaryException(String message) {
            super(message);
        }
    }

    public static class InvalidPerformanceRatingException extends Exception {
        public InvalidPerformanceRatingException(String message) {
            super(message);
        }
    }

    public static class InvalidYearsOfExperienceException extends Exception {
        public InvalidYearsOfExperienceException(String message) {
            super(message);
        }
    }
    public static class InvalidDepartmentException extends Exception {
        public InvalidDepartmentException(String message) {
            super(message);
        }
    }
}