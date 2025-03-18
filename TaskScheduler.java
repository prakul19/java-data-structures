import java.util.Scanner;

public class TaskScheduler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CircularLinkedList taskList = new CircularLinkedList();

        while (true) {
            System.out.println("\n1. Add Task at Beginning");
            System.out.println("2. Add Task at End");
            System.out.println("3. Add Task at Specific Position");
            System.out.println("4. Remove Task by Task ID");
            System.out.println("5. View Current Task and Move to Next");
            System.out.println("6. Display All Tasks");
            System.out.println("7. Search Task by Priority");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Task ID, Task Name, Priority, Due Date: ");
                    taskList.addAtBeginning(scanner.nextInt(), scanner.next(), scanner.nextInt(), scanner.next());
                    break;
                case 2:
                    System.out.print("Enter Task ID, Task Name, Priority, Due Date: ");
                    taskList.addAtEnd(scanner.nextInt(), scanner.next(), scanner.nextInt(), scanner.next());
                    break;
                case 3:
                    System.out.print("Enter Position, Task ID, Task Name, Priority, Due Date: ");
                    taskList.addAtPosition(scanner.nextInt(), scanner.nextInt(), scanner.next(), scanner.nextInt(), scanner.next());
                    break;
                case 4:
                    System.out.print("Enter Task ID to remove: ");
                    taskList.removeByTaskId(scanner.nextInt());
                    break;
                case 5:
                    taskList.viewCurrentTaskAndMoveToNext();
                    break;
                case 6:
                    taskList.displayAllTasks();
                    break;
                case 7:
                    System.out.print("Enter Priority to search: ");
                    taskList.searchByPriority(scanner.nextInt());
                    break;
                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

// Node representing a task
class TaskNode {
    int taskId;
    String taskName;
    int priority;
    String dueDate;
    TaskNode next;

    public TaskNode(int taskId, String taskName, int priority, String dueDate) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.priority = priority;
        this.dueDate = dueDate;
        this.next = null;
    }
}

// Circular Linked List to manage tasks
class CircularLinkedList {
    private TaskNode head = null;
    private TaskNode tail = null;
    private TaskNode current = null;

    // Add task at the beginning
    public void addAtBeginning(int taskId, String taskName, int priority, String dueDate) {
        TaskNode newNode = new TaskNode(taskId, taskName, priority, dueDate);
        if (head == null) {
            head = tail = newNode;
            newNode.next = head;
        } else {
            newNode.next = head;
            head = newNode;
            tail.next = head;
        }
    }

    // Add task at the end
    public void addAtEnd(int taskId, String taskName, int priority, String dueDate) {
        TaskNode newNode = new TaskNode(taskId, taskName, priority, dueDate);
        if (head == null) {
            head = tail = newNode;
            newNode.next = head;
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head;
        }
    }

    // Add task at a specific position
    public void addAtPosition(int position, int taskId, String taskName, int priority, String dueDate) {
        if (position == 1) {
            addAtBeginning(taskId, taskName, priority, dueDate);
            return;
        }
        TaskNode newNode = new TaskNode(taskId, taskName, priority, dueDate);
        TaskNode current = head;
        for (int i = 1; i < position - 1 && current.next != head; i++) {
            current = current.next;
        }
        if (current.next == head) {
            System.out.println("Position out of bounds!");
            return;
        }
        newNode.next = current.next;
        current.next = newNode;
    }

    // Remove task by Task ID
    public void removeByTaskId(int taskId) {
        if (head == null) {
            System.out.println("Task list is empty!");
            return;
        }
        TaskNode current = head, prev = tail;
        do {
            if (current.taskId == taskId) {
                if (current == head) {
                    head = head.next;
                    tail.next = head;
                } else {
                    prev.next = current.next;
                }
                if (current == tail) {
                    tail = prev;
                }
                System.out.println("Task removed successfully!");
                return;
            }
            prev = current;
            current = current.next;
        } while (current != head);
        System.out.println("Task ID not found!");
    }

    // View current task and move to the next
    public void viewCurrentTaskAndMoveToNext() {
        if (current == null) {
            current = head;
        }
        if (current == null) {
            System.out.println("No tasks available!");
            return;
        }
        System.out.println("Current Task: " + current.taskId + ", " + current.taskName + ", " + current.priority + ", " + current.dueDate);
        current = current.next;
    }

    // Display all tasks starting from the head
    public void displayAllTasks() {
        if (head == null) {
            System.out.println("No tasks to display!");
            return;
        }
        TaskNode temp = head;
        do {
            System.out.println(temp.taskId + ", " + temp.taskName + ", " + temp.priority + ", " + temp.dueDate);
            temp = temp.next;
        } while (temp != head);
    }

    // Search tasks by priority
    public void searchByPriority(int priority) {
        if (head == null) {
            System.out.println("Task list is empty!");
            return;
        }
        TaskNode temp = head;
        boolean found = false;
        do {
            if (temp.priority == priority) {
                System.out.println(temp.taskId + ", " + temp.taskName + ", " + temp.priority + ", " + temp.dueDate);
                found = true;
            }
            temp = temp.next;
        } while (temp != head);
        if (!found) {
            System.out.println("No tasks found with the given priority!");
        }
    }
}

/*
Input:
1. Add Task at Beginning
Enter Task ID, Task Name, Priority, Due Date: 1 Task1 1 2025-01-01
2. Add Task at End
Enter Task ID, Task Name, Priority, Due Date: 2 Task2 2 2023-01-10
3. Add Task at Specific Position
Enter Position, Task ID, Task Name, Priority, Due Date: 2 3 Task3 1 2025-01-05
5. View Current Task and Move to Next
6. Display All Tasks
4. Remove Task by Task ID
Enter Task ID to remove: 102

Output:
Current Task: 101, Task1, 1, 2023-12-01
1, Task1, 1, 2025-01-01
3, Task3, 1, 2025-01-10
2, Task2, 2, 2025-01-05
Task removed successfully!
*/
