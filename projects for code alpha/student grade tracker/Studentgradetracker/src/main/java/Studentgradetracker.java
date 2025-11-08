import java.util.ArrayList;
import java.util.Scanner;

class Student {
    String name;
    ArrayList<Integer> grades;

    public Student(String name) {
        this.name = name;
        this.grades = new ArrayList<>();
    }

    public void addGrade(int grade) {
        grades.add(grade);
    }

    public double getAverage() {
        if (grades.isEmpty()) return 0;
        int sum = 0;
        for (int grade : grades) {
            sum += grade;
        }
        return (double) sum / grades.size();
    }

    public int getHighest() {
        if (grades.isEmpty()) return 0;
        int highest = grades.get(0);
        for (int grade : grades) {
            if (grade > highest) highest = grade;
        }
        return highest;
    }

    public int getLowest() {
        if (grades.isEmpty()) return 0;
        int lowest = grades.get(0);
        for (int grade : grades) {
            if (grade < lowest) lowest = grade;
        }
        return lowest;
    }

    public void printReport() {
        System.out.println("Student: " + name);
        System.out.println("Grades: " + grades);
        System.out.printf("Average: %.2f\n", getAverage());
        System.out.println("Highest: " + getHighest());
        System.out.println("Lowest: " + getLowest());
        System.out.println("----------------------------");
    }
}

public class Studentgradetracker {

    static ArrayList<Student> students = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            printMenu();
            int choice = getIntegerInput("Enter your choice: ");

            switch (choice) {
                case 1 -> addStudent();
                case 2 -> addGrades();
                case 3 -> displayAllReports();
                case 4 -> {
                    System.out.println("Exiting program. Goodbye!");
                    running = false;
                }
                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }

    static void printMenu() {
        System.out.println("\n=== Student Grade Tracker ===");
        System.out.println("1. Add Student");
        System.out.println("2. Add Grades to Student");
        System.out.println("3. Display Summary Report");
        System.out.println("4. Exit");
    }

    static void addStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        students.add(new Student(name));
        System.out.println("Student added successfully!");
    }

    static void addGrades() {
        if (students.isEmpty()) {
            System.out.println("No students available. Please add a student first.");
            return;
        }

        displayStudentList();
        int index = getIntegerInput("Select student index to add grades: ");

        if (index < 0 || index >= students.size()) {
            System.out.println("Invalid index!");
            return;
        }

        Student student = students.get(index);
        boolean addingGrades = true;

        while (addingGrades) {
            int grade = getIntegerInput("Enter grade (0-100): ");
            if (grade < 0 || grade > 100) {
                System.out.println("Invalid grade. Please enter a number between 0 and 100.");
            } else {
                student.addGrade(grade);
                System.out.println("Grade added.");
            }

            System.out.print("Add another grade? (y/n): ");
            String more = scanner.nextLine();
            addingGrades = more.equalsIgnoreCase("y");
        }
    }

    static void displayAllReports() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        System.out.println("\n=== Student Summary Reports ===");
        for (Student student : students) {
            student.printReport();
        }
    }

    static void displayStudentList() {
        System.out.println("\nAvailable Students:");
        for (int i = 0; i < students.size(); i++) {
            System.out.println(i + ". " + students.get(i).name);
        }
    }

    static int getIntegerInput(String prompt) {
        System.out.print(prompt);
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Try again: ");
            scanner.next();
        }
        int value = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return value;
    }
}

