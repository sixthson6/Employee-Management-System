// package org.employees;

// import java.util.Scanner;

// public class  Main{
//     public static void main(String[] args) {
//         try (Scanner scanner = new Scanner(System.in)) {
//             System.out.println("=== Employee Creation Console ===");
            
//             System.out.print("Enter Employee ID (int): ");
//             int id = Integer.parseInt(scanner.nextLine());
            
//             System.out.print("Enter Full Name: ");
//             String name = scanner.nextLine();
            
//             System.out.print("Enter Department (e.g., HR, IT, Finance): ");
//             String department = scanner.nextLine();
            
//             System.out.print("Enter Salary: ");
//             double salary = Double.parseDouble(scanner.nextLine());
            
//             System.out.print("Enter Performance Rating (0-5): ");
//             double rating = Double.parseDouble(scanner.nextLine());
            
//             System.out.print("Enter Years of Experience: ");
//             int experience = Integer.parseInt(scanner.nextLine());
            
//             System.out.print("Is the employee active? (true/false): ");
//             boolean isActive = Boolean.parseBoolean(scanner.nextLine());
            
//             // Create the Employee
//             Employee<Integer> employee = new Employee<>(
//                     id, name, department, salary, rating, experience, isActive
//             );
            
//             System.out.println("\n=== Employee Created ===");
//             System.out.println(employee);
//         }
//     }
// }
