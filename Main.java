import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private String name;
    private int rollNumber;
    private double marks;

    public Student(String name, int rollNumber, double marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
    }

    public String getName() { return name; }
    public int getRollNumber() { return rollNumber; }
    public double getMarks() { return marks; }
    public void setName(String name) { this.name = name; }
    public void setMarks(double marks) { this.marks = marks; }

    public String getGrade() {
        if (marks >= 90) return "A";
        if (marks >= 75) return "B";
        if (marks >= 60) return "C";
        if (marks >= 40) return "D";
        return "Fail";
    }

    public void display() {
        System.out.printf("%-12d %-22s %-10.2f %-12.2f %-8s%n",
                rollNumber, name, marks, marks, getGrade());
    }
}

public class Main {
    private static final Scanner sc = new Scanner(System.in);
    private static final ArrayList<Student> students = new ArrayList<>();

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("======================================");
        System.out.println(" STUDENT RECORD MANAGEMENT SYSTEM");
        System.out.println("======================================");

        while (running) {
            System.out.println("\n1. Add student");
            System.out.println("2. View all students");
            System.out.println("3. Search student by roll number");
            System.out.println("4. Update student");
            System.out.println("5. Delete student");
            System.out.println("6. Exit");

            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1: addStudent(); break;
                case 2: viewStudents(); break;
                case 3: searchStudent(); break;
                case 4: updateStudent(); break;
                case 5: deleteStudent(); break;
                case 6:
                    running = false;
                    System.out.println("Thank you for using the system!");
                    break;
                default: System.out.println("Invalid option. Choose 1 to 6.");
            }
        }
        sc.close();
    }

    private static void addStudent() {
        System.out.print("Enter student name: ");
        String name = sc.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
            return;
        }

        int roll = readInt("Enter roll number: ");
        if (findStudent(roll) != null) {
            System.out.println("This roll number already exists.");
            return;
        }

        double marks = readMarks();
        students.add(new Student(name, roll, marks));
        System.out.println("Student added successfully.");
    }

    private static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No records yet. Add a student first.");
            return;
        }
        System.out.printf("%-12s %-22s %-10s %-12s %-8s%n",
                "Roll No.", "Name", "Marks", "Percentage", "Grade");
        System.out.println("----------------------------------------------------------------");
        for (Student student : students) student.display();
    }

    private static void searchStudent() {
        int roll = readInt("Enter roll number to search: ");
        Student student = findStudent(roll);
        if (student == null) {
            System.out.println("Student not found.");
        } else {
            System.out.printf("%-12s %-22s %-10s %-12s %-8s%n",
                    "Roll No.", "Name", "Marks", "Percentage", "Grade");
            student.display();
        }
    }

    private static void updateStudent() {
        int roll = readInt("Enter roll number to update: ");
        Student student = findStudent(roll);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }

        System.out.print("New name (press Enter to keep current): ");
        String name = sc.nextLine().trim();
        if (!name.isEmpty()) student.setName(name);

        System.out.print("Update marks? (y/n): ");
        if (sc.nextLine().trim().equalsIgnoreCase("y")) {
            student.setMarks(readMarks());
        }
        System.out.println("Student record updated.");
    }

    private static void deleteStudent() {
        int roll = readInt("Enter roll number to delete: ");
        Student student = findStudent(roll);
        if (student == null) {
            System.out.println("Student not found.");
            return;
        }
        students.remove(student);
        System.out.println("Student record deleted.");
    }

    private static Student findStudent(int roll) {
        for (Student student : students) {
            if (student.getRollNumber() == roll) return student;
        }
        return null;
    }

    private static int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid whole number.");
            }
        }
    }

    private static double readMarks() {
        while (true) {
            System.out.print("Enter marks (0 to 100): ");
            try {
                double marks = Double.parseDouble(sc.nextLine().trim());
                if (marks >= 0 && marks <= 100) return marks;
            } catch (NumberFormatException ignored) {}
            System.out.println("Marks must be a number between 0 and 100.");
        }
    }
}
