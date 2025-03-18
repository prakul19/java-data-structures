import java.util.Scanner;

public class TextEditorUndoRedo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UndoRedoManager manager = new UndoRedoManager(10);

        while (true) {
            System.out.println("\n1. Add Text");
            System.out.println("2. Undo");
            System.out.println("3. Redo");
            System.out.println("4. Display Current State");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter new text state: ");
                    String newText = scanner.nextLine();
                    manager.addState(newText);
                    break;
                case 2:
                    manager.undo();
                    break;
                case 3:
                    manager.redo();
                    break;
                case 4:
                    manager.displayCurrentState();
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

// Node representing a text state
class TextNode {
    String textContent;
    TextNode prev;
    TextNode next;

    public TextNode(String textContent) {
        this.textContent = textContent;
        this.prev = null;
        this.next = null;
    }
}

// Undo/Redo manager using a doubly linked list
class UndoRedoManager {
    private TextNode head;
    private TextNode current;
    private int maxSize;
    private int size;

    public UndoRedoManager(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
    }

    // Add a new text state
    public void addState(String text) {
        TextNode newNode = new TextNode(text);

        // Remove all redo states
        if (current != null && current.next != null) {
            current.next = null;
        }

        // Add the new state
        if (head == null) {
            head = current = newNode;
        } else {
            current.next = newNode;
            newNode.prev = current;
            current = newNode;
        }

        // Manage size and remove the oldest state if necessary
        size++;
        if (size > maxSize) {
            head = head.next;
            head.prev = null;
            size--;
        }

        System.out.println("New state added: " + text);
    }

    // Undo functionality
    public void undo() {
        if (current == null || current.prev == null) {
            System.out.println("Nothing to undo!");
            return;
        }
        current = current.prev;
        System.out.println("Undo performed. Current state: " + current.textContent);
    }

    // Redo functionality
    public void redo() {
        if (current == null || current.next == null) {
            System.out.println("Nothing to redo!");
            return;
        }
        current = current.next;
        System.out.println("Redo performed. Current state: " + current.textContent);
    }

    // Display the current text state
    public void displayCurrentState() {
        if (current == null) {
            System.out.println("No states available!");
        } else {
            System.out.println("Current state: " + current.textContent);
        }
    }
}

/*
Input:
1. Add Text
Enter new text state: Hello
1. Add Text
Enter new text state: Hello World
1. Add Text
Enter new text state: Hello World!
2. Undo
3. Redo
4. Display Current State

Output:
New state added: Hello
New state added: Hello World
New state added: Hello World!
Undo performed. Current state: Hello World
Redo performed. Current state: Hello World!
Current state: Hello World!
*/
