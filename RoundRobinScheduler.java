import java.util.Scanner;

public class RoundRobinScheduler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CircularLinkedList processList = new CircularLinkedList();

        System.out.print("Enter the time quantum: ");
        int timeQuantum = scanner.nextInt();

        while (true) {
            System.out.println("\n1. Add Process");
            System.out.println("2. Remove Process by Process ID");
            System.out.println("3. Simulate Round Robin Scheduling");
            System.out.println("4. Display Processes");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Process ID, Burst Time, Priority: ");
                    processList.addProcess(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
                    break;
                case 2:
                    System.out.print("Enter Process ID to remove: ");
                    processList.removeProcess(scanner.nextInt());
                    break;
                case 3:
                    processList.simulateRoundRobin(timeQuantum);
                    break;
                case 4:
                    processList.displayProcesses();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

// Node representing a process
class ProcessNode {
    int processId;
    int burstTime;
    int priority;
    ProcessNode next;

    public ProcessNode(int processId, int burstTime, int priority) {
        this.processId = processId;
        this.burstTime = burstTime;
        this.priority = priority;
        this.next = null;
    }
}

// Circular Linked List to manage processes
class CircularLinkedList {
    private ProcessNode head = null;
    private ProcessNode tail = null;

    public void addProcess(int processId, int burstTime, int priority) {
        ProcessNode newNode = new ProcessNode(processId, burstTime, priority);
        if (head == null) {
            head = tail = newNode;
            newNode.next = head;
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head;
        }
    }

    public void removeProcess(int processId) {
        if (head == null) {
            System.out.println("No processes to remove!");
            return;
        }
        ProcessNode current = head, prev = tail;
        do {
            if (current.processId == processId) {
                if (current == head) {
                    head = head.next;
                    tail.next = head;
                } else {
                    prev.next = current.next;
                }
                if (current == tail) {
                    tail = prev;
                }
                System.out.println("Process removed successfully!");
                return;
            }
            prev = current;
            current = current.next;
        } while (current != head);
        System.out.println("Process ID not found!");
    }

    public void simulateRoundRobin(int timeQuantum) {
        if (head == null) {
            System.out.println("No processes to simulate!");
            return;
        }

        int totalWaitingTime = 0, totalTurnAroundTime = 0, processCount = 0;
        ProcessNode current = head;
        while (true) {
            processCount++;
            boolean processExecuted = false;
            do {
                if (current.burstTime > 0) {
                    int executionTime = Math.min(current.burstTime, timeQuantum);
                    current.burstTime -= executionTime;
                    System.out.println("Process ID: " + current.processId + " executed for " + executionTime + " units.");
                    processExecuted = true;
                }
                current = current.next;
            } while (current != head);

            displayProcesses();

            // Check if all processes have completed
            ProcessNode temp = head;
            boolean allCompleted = true;
            do {
                if (temp.burstTime > 0) {
                    allCompleted = false;
                    break;
                }
                temp = temp.next;
            } while (temp != head);

            if (allCompleted) {
                break;
            }
        }

        System.out.println("Average Waiting Time: " + (double) totalWaitingTime / processCount);
        System.out.println("Average Turn-Around Time: " + (double) totalTurnAroundTime / processCount);
    }

    public void displayProcesses() {
        if (head == null) {
            System.out.println("No processes to display!");
            return;
        }
        ProcessNode current = head;
        do {
            System.out.println("Process ID: " + current.processId + ", Burst Time: " + current.burstTime + ", Priority: " + current.priority);
            current = current.next;
        } while (current != head);
    }
}

/*
Input:
Enter the time quantum: 4
1. Add Process
Enter Process ID, Burst Time, Priority: 1 10 1
1. Add Process
Enter Process ID, Burst Time, Priority: 2 5 2
1. Add Process
Enter Process ID, Burst Time, Priority: 3 8 3
3. Simulate Round Robin Scheduling
4. Display Processes

Output:
Process ID: 1, Burst Time: 0, Priority: 1
Process ID: 2, Burst Time: 0, Priority: 2
Process ID: 3, Burst Time: 0, Priority: 3
*/
