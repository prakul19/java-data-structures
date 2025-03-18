import java.util.Scanner;

public class StudentRecordManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SinglyLinkedList list = new SinglyLinkedList();

        while (true) {
            System.out.println("\n1. Add Student at Beginning");
            System.out.println("2. Add Student at End");
            System.out.println("3. Add Student at Specific Position");
            System.out.println("4. Delete Student by Roll Number");
            System.out.println("5. Search Student by Roll Number");
            System.out.println("6. Display All Students");
            System.out.println("7. Update Student's Grade");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    // Add at the beginning
                    System.out.print("Enter Roll Number, Name, Age, Grade: ");
                    list.addAtBeginning(scanner.nextInt(), scanner.next(), scanner.nextInt(), scanner.next());
                    break;
                case 2:
                    // Add at the end
                    System.out.print("Enter Roll Number, Name, Age, Grade: ");
                    list.addAtEnd(scanner.nextInt(), scanner.next(), scanner.nextInt(), scanner.next());
                    break;
                case 3:
                    // Add at a specific position
                    System.out.print("Enter Position, Roll Number, Name, Age, Grade: ");
                    list.addAtPosition(scanner.nextInt(), scanner.nextInt(), scanner.next(), scanner.nextInt(), scanner.next());
                    break;
                case 4:
                    // Delete by Roll Number
                    System.out.print("Enter Roll Number to delete: ");
                    list.deleteByRollNumber(scanner.nextInt());
                    break;
                case 5:
                    // Search by Roll Number
                    System.out.print("Enter Roll Number to search: ");
                    list.searchByRollNumber(scanner.nextInt());
                    break;
                case 6:
                    // Display all records
                    list.displayAll();
                    break;
                case 7:
                    // Update grade
                    System.out.print("Enter Roll Number and new Grade: ");
                    list.updateGrade(scanner.nextInt(), scanner.next());
                    break;
                case 8:
                    // Exit the program
                    System.out.println("Exiting");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

// Node structure for student record
class Node {
    int rollNumber;
    String name;
    int age;
    String grade;
    Node next;

    public Node(int rollNumber, String name, int age, String grade) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.age = age;
        this.grade = grade;
        this.next = null;
    }
}

// Singly linked list to manage student records
class SinglyLinkedList {
    private Node head;

    // Add record at the beginning
    public void addAtBeginning(int rollNumber, String name, int age, String grade) {
        Node newNode = new Node(rollNumber, name, age, grade);
        newNode.next = head;
        head = newNode;
    }

    // Add record at the end
    public void addAtEnd(int rollNumber, String name, int age, String grade) {
        Node newNode = new Node(rollNumber, name, age, grade);
        if (head == null) {
            head = newNode;
            return;
        }
        Node current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    // Add record at a specific position
    public void addAtPosition(int position, int rollNumber, String name, int age, String grade) {
        if (position == 1) {
            addAtBeginning(rollNumber, name, age, grade);
            return;
        }
        Node newNode = new Node(rollNumber, name, age, grade);
        Node current = head;
        for (int i = 1; i < position - 1 && current != null; i++) {
            current = current.next;
        }
        if (current == null) {
            System.out.println("Position out of bounds!");
            return;
        }
        newNode.next = current.next;
        current.next = newNode;
    }

    // Delete record by Roll Number
    public void deleteByRollNumber(int rollNumber) {
        if (head == null) {
            System.out.println("List is empty!");
            return;
        }
        if (head.rollNumber == rollNumber) {
            head = head.next;
            return;
        }
        Node current = head;
        while (current.next != null && current.next.rollNumber != rollNumber) {
            current = current.next;
        }
        if (current.next == null) {
            System.out.println("Roll Number not found!");
            return;
        }
        current.next = current.next.next;
    }

    // Search record by Roll Number
    public void searchByRollNumber(int rollNumber) {
        Node current = head;
        while (current != null) {
            if (current.rollNumber == rollNumber) {
                System.out.println("Found: " + current.rollNumber + ", " + current.name + ", " + current.age + ", " + current.grade);
                return;
            }
            current = current.next;
        }
        System.out.println("Roll Number not found!");
    }

    // Display all records
    public void displayAll() {
        if (head == null) {
            System.out.println("No records to display!");
            return;
        }
        Node current = head;
        while (current != null) {
            System.out.println(current.rollNumber + ", " + current.name + ", " + current.age + ", " + current.grade);
            current = current.next;
        }
    }

    // Update grade by Roll Number
    public void updateGrade(int rollNumber, String newGrade) {
        Node current = head;
        while (current != null) {
            if (current.rollNumber == rollNumber) {
                current.grade = newGrade;
                System.out.println("Grade updated successfully!");
                return;
            }
            current = current.next;
        }
        System.out.println("Roll Number not found!");
    }
}

/*
Input:
1. Add Student at Beginning
Enter Roll Number, Name, Age, Grade: 1 prakul 21 A
2. Add Student at End
Enter Roll Number, Name, Age, Grade: 2 parth 13 B
3. Add Student at Specific Position
Enter Position, Roll Number, Name, Age, Grade: 2 3 parag 22 B+
4. Delete Student by Roll Number
Enter Roll Number to delete: 2
5. Search Student by Roll Number
Enter Roll Number to search: 3
6. Display All Students

Output:
Found: 3, parag, 21, B+
1, prakul, 21, A
3, parag, 22, B+
*/
