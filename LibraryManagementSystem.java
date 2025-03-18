import java.util.Scanner;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DoublyLinkedList library = new DoublyLinkedList();

        while (true) {
            System.out.println("\n1. Add Book at Beginning");
            System.out.println("2. Add Book at End");
            System.out.println("3. Add Book at Specific Position");
            System.out.println("4. Remove Book by Book ID");
            System.out.println("5. Search Book by Title");
            System.out.println("6. Search Book by Author");
            System.out.println("7. Update Book Availability");
            System.out.println("8. Display All Books Forward");
            System.out.println("9. Display All Books Reverse");
            System.out.println("10. Count Total Books");
            System.out.println("11. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Title, Author, Genre, Book ID, Availability: ");
                    library.addAtBeginning(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextInt(), scanner.nextBoolean());
                    break;
                case 2:
                    System.out.print("Enter Title, Author, Genre, Book ID, Availability: ");
                    library.addAtEnd(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextInt(), scanner.nextBoolean());
                    break;
                case 3:
                    System.out.print("Enter Position, Title, Author, Genre, Book ID, Availability: ");
                    library.addAtPosition(scanner.nextInt(), scanner.nextLine().trim(), scanner.nextLine(), scanner.nextLine(), scanner.nextInt(), scanner.nextBoolean());
                    break;
                case 4:
                    System.out.print("Enter Book ID to remove: ");
                    library.removeByBookId(scanner.nextInt());
                    break;
                case 5:
                    System.out.print("Enter Book Title to search: ");
                    library.searchByTitle(scanner.nextLine());
                    break;
                case 6:
                    System.out.print("Enter Author to search: ");
                    library.searchByAuthor(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("Enter Book ID and new Availability Status: ");
                    library.updateAvailability(scanner.nextInt(), scanner.nextBoolean());
                    break;
                case 8:
                    library.displayForward();
                    break;
                case 9:
                    library.displayReverse();
                    break;
                case 10:
                    library.countBooks();
                    break;
                case 11:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

// Node representing a book
class BookNode {
    String title;
    String author;
    String genre;
    int bookId;
    boolean isAvailable;
    BookNode next;
    BookNode prev;

    public BookNode(String title, String author, String genre, int bookId, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.bookId = bookId;
        this.isAvailable = isAvailable;
        this.next = null;
        this.prev = null;
    }
}

// Doubly Linked List to manage books
class DoublyLinkedList {
    private BookNode head;
    private BookNode tail;

    // Add book at the beginning
    public void addAtBeginning(String title, String author, String genre, int bookId, boolean isAvailable) {
        BookNode newNode = new BookNode(title, author, genre, bookId, isAvailable);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    // Add book at the end
    public void addAtEnd(String title, String author, String genre, int bookId, boolean isAvailable) {
        BookNode newNode = new BookNode(title, author, genre, bookId, isAvailable);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Add book at a specific position
    public void addAtPosition(int position, String title, String author, String genre, int bookId, boolean isAvailable) {
        if (position == 1) {
            addAtBeginning(title, author, genre, bookId, isAvailable);
            return;
        }
        BookNode newNode = new BookNode(title, author, genre, bookId, isAvailable);
        BookNode current = head;
        for (int i = 1; i < position - 1 && current != null; i++) {
            current = current.next;
        }
        if (current == null) {
            System.out.println("Position out of bounds!");
            return;
        }
        newNode.next = current.next;
        newNode.prev = current;
        if (current.next != null) {
            current.next.prev = newNode;
        }
        current.next = newNode;
        if (newNode.next == null) {
            tail = newNode;
        }
    }

    // Remove book by Book ID
    public void removeByBookId(int bookId) {
        if (head == null) {
            System.out.println("Library is empty!");
            return;
        }
        BookNode current = head;
        while (current != null && current.bookId != bookId) {
            current = current.next;
        }
        if (current == null) {
            System.out.println("Book ID not found!");
            return;
        }
        if (current.prev != null) {
            current.prev.next = current.next;
        } else {
            head = current.next;
        }
        if (current.next != null) {
            current.next.prev = current.prev;
        } else {
            tail = current.prev;
        }
        System.out.println("Book removed successfully!");
    }

    // Search book by Title
    public void searchByTitle(String title) {
        BookNode current = head;
        while (current != null) {
            if (current.title.equalsIgnoreCase(title)) {
                System.out.println(current.title + ", " + current.author + ", " + current.genre + ", " + current.bookId + ", " + current.isAvailable);
                return;
            }
            current = current.next;
        }
        System.out.println("Book Title not found!");
    }

    // Search book by Author
    public void searchByAuthor(String author) {
        BookNode current = head;
        boolean found = false;
        while (current != null) {
            if (current.author.equalsIgnoreCase(author)) {
                System.out.println(current.title + ", " + current.author + ", " + current.genre + ", " + current.bookId + ", " + current.isAvailable);
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("Author not found!");
        }
    }

    // Update availability by Book ID
    public void updateAvailability(int bookId, boolean isAvailable) {
        BookNode current = head;
        while (current != null) {
            if (current.bookId == bookId) {
                current.isAvailable = isAvailable;
                System.out.println("Availability updated successfully!");
                return;
            }
            current = current.next;
        }
        System.out.println("Book ID not found!");
    }

    // Display books in forward order
    public void displayForward() {
        if (head == null) {
            System.out.println("Library is empty!");
            return;
        }
        BookNode current = head;
        while (current != null) {
            System.out.println(current.title + ", " + current.author + ", " + current.genre + ", " + current.bookId + ", " + current.isAvailable);
            current = current.next;
        }
    }

    // Display books in reverse order
    public void displayReverse() {
        if (tail == null) {
            System.out.println("Library is empty!");
            return;
        }
        BookNode current = tail;
        while (current != null) {
            System.out.println(current.title + ", " + current.author + ", " + current.genre + ", " + current.bookId + ", " + current.isAvailable);
            current = current.prev;
        }
    }

    // Count total books
    public void countBooks() {
        int count = 0;
        BookNode current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        System.out.println("Total Books in Library: " + count);
    }
}

/*
Input:
1. Add Book at Beginning
Enter Title, Author, Genre, Book ID, Availability: Book1 Author1 Fiction 1 true
2. Add Book at End
Enter Title, Author, Genre, Book ID, Availability: Book2 Author2 Non-Fiction 2 true
3. Add Book at Specific Position
Enter Position, Title, Author, Genre, Book ID, Availability: 2 Book3 Author3 Drama 3 false
5. Search Book by Title
Enter Book Title to search: Book2
10. Count Total Books
8. Display All Books Forward

Output:
Book2, Author2, Non-Fiction, 2, true
Total Books in Library: 3
Book1, Author1, Fiction, 1, true
Book3, Author3, Drama, 3, false
Book2, Author2, Non-Fiction, 102, true
 */