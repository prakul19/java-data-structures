import java.util.Scanner;

public class InventoryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SinglyLinkedList inventory = new SinglyLinkedList();

        while (true) {
            System.out.println("\n1. Add Item at Beginning");
            System.out.println("2. Add Item at End");
            System.out.println("3. Add Item at Specific Position");
            System.out.println("4. Remove Item by Item ID");
            System.out.println("5. Update Item Quantity by Item ID");
            System.out.println("6. Search Item by Item ID");
            System.out.println("7. Search Item by Item Name");
            System.out.println("8. Calculate Total Inventory Value");
            System.out.println("9. Sort Items by Item Name");
            System.out.println("10. Sort Items by Price");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Item Name, Item ID, Quantity, Price: ");
                    inventory.addAtBeginning(scanner.next(), scanner.nextInt(), scanner.nextInt(), scanner.nextDouble());
                    break;
                case 2:
                    System.out.print("Enter Item Name, Item ID, Quantity, Price: ");
                    inventory.addAtEnd(scanner.next(), scanner.nextInt(), scanner.nextInt(), scanner.nextDouble());
                    break;
                case 3:
                    System.out.print("Enter Position, Item Name, Item ID, Quantity, Price: ");
                    inventory.addAtPosition(scanner.nextInt(), scanner.next(), scanner.nextInt(), scanner.nextInt(), scanner.nextDouble());
                    break;
                case 4:
                    System.out.print("Enter Item ID to remove: ");
                    inventory.removeByItemId(scanner.nextInt());
                    break;
                case 5:
                    System.out.print("Enter Item ID and new Quantity: ");
                    inventory.updateQuantity(scanner.nextInt(), scanner.nextInt());
                    break;
                case 6:
                    System.out.print("Enter Item ID to search: ");
                    inventory.searchByItemId(scanner.nextInt());
                    break;
                case 7:
                    System.out.print("Enter Item Name to search: ");
                    inventory.searchByItemName(scanner.next());
                    break;
                case 8:
                    inventory.calculateTotalValue();
                    break;
                case 9:
                    inventory.sortByItemName();
                    break;
                case 10:
                    inventory.sortByPrice();
                    break;
                case 11:
                    System.out.println("Exiting");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

// Node representing an item in inventory
class Node {
    String itemName;
    int itemId;
    int quantity;
    double price;
    Node next;

    public Node(String itemName, int itemId, int quantity, double price) {
        this.itemName = itemName;
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
        this.next = null;
    }
}

// Singly linked list to manage inventory
class SinglyLinkedList {
    private Node head;

    // Add item at the beginning
    public void addAtBeginning(String itemName, int itemId, int quantity, double price) {
        Node newNode = new Node(itemName, itemId, quantity, price);
        newNode.next = head;
        head = newNode;
    }

    // Add item at the end
    public void addAtEnd(String itemName, int itemId, int quantity, double price) {
        Node newNode = new Node(itemName, itemId, quantity, price);
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

    // Add item at a specific position
    public void addAtPosition(int position, String itemName, int itemId, int quantity, double price) {
        if (position == 1) {
            addAtBeginning(itemName, itemId, quantity, price);
            return;
        }
        Node newNode = new Node(itemName, itemId, quantity, price);
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

    // Remove item by Item ID
    public void removeByItemId(int itemId) {
        if (head == null) {
            System.out.println("Inventory is empty!");
            return;
        }
        if (head.itemId == itemId) {
            head = head.next;
            return;
        }
        Node current = head;
        while (current.next != null && current.next.itemId != itemId) {
            current = current.next;
        }
        if (current.next == null) {
            System.out.println("Item ID not found!");
            return;
        }
        current.next = current.next.next;
        System.out.println("Item removed successfully!");
    }

    // Update quantity by Item ID
    public void updateQuantity(int itemId, int newQuantity) {
        Node current = head;
        while (current != null) {
            if (current.itemId == itemId) {
                current.quantity = newQuantity;
                System.out.println("Quantity updated successfully!");
                return;
            }
            current = current.next;
        }
        System.out.println("Item ID not found!");
    }

    // Search item by Item ID
    public void searchByItemId(int itemId) {
        Node current = head;
        while (current != null) {
            if (current.itemId == itemId) {
                System.out.println(current.itemName + ", " + current.itemId + ", " + current.quantity + ", " + current.price);
                return;
            }
            current = current.next;
        }
        System.out.println("Item ID not found!");
    }

    // Search item by Item Name
    public void searchByItemName(String itemName) {
        Node current = head;
        while (current != null) {
            if (current.itemName.equalsIgnoreCase(itemName)) {
                System.out.println(current.itemName + ", " + current.itemId + ", " + current.quantity + ", " + current.price);
                return;
            }
            current = current.next;
        }
        System.out.println("Item Name not found!");
    }

    // Calculate total inventory value
    public void calculateTotalValue() {
        double totalValue = 0.0;
        Node current = head;
        while (current != null) {
            totalValue += current.quantity * current.price;
            current = current.next;
        }
        System.out.println("Total Inventory Value: $" + totalValue);
    }

    // Sort items by Item Name
    public void sortByItemName() {
        if (head == null || head.next == null) {
            return;
        }
        head = mergeSort(head, true);
        System.out.println("Inventory sorted by Item Name!");
    }

    // Sort items by Price
    public void sortByPrice() {
        if (head == null || head.next == null) {
            return;
        }
        head = mergeSort(head, false);
        System.out.println("Inventory sorted by Price!");
    }

    private Node mergeSort(Node head, boolean sortByName) {
        if (head == null || head.next == null) {
            return head;
        }
        Node middle = getMiddle(head);
        Node nextOfMiddle = middle.next;
        middle.next = null;

        Node left = mergeSort(head, sortByName);
        Node right = mergeSort(nextOfMiddle, sortByName);

        return merge(left, right, sortByName);
    }

    private Node merge(Node left, Node right, boolean sortByName) {
        Node result = null;
        if (left == null) {
            return right;
        }
        if (right == null) {
            return left;
        }
        if (sortByName ? left.itemName.compareToIgnoreCase(right.itemName) <= 0 : left.price <= right.price) {
            result = left;
            result.next = merge(left.next, right, sortByName);
        } else {
            result = right;
            result.next = merge(left, right.next, sortByName);
        }
        return result;
    }

    private Node getMiddle(Node head) {
        if (head == null) {
            return head;
        }
        Node slow = head, fast = head;
        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}

/*
Input:
1. Add Item at Beginning
Enter Item Name, Item ID, Quantity, Price: Laptop 1 5 800
2. Add Item at End
Enter Item Name, Item ID, Quantity, Price: Phone 2 10 500
3. Add Item at Specific Position
Enter Position, Item Name, Item ID, Quantity, Price: 2 Tablet 3 8 300
6. Search Item by Item ID
Enter Item ID to search: 2
8. Calculate Total Inventory Value
9. Sort Items by Item Name
10. Sort Items by Price

Output:
Phone, 102, 10, 500

 */