import java.util.Scanner;

public class MovieManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DoublyLinkedList movieList = new DoublyLinkedList();

        while (true) {
            System.out.println("\n1. Add Movie at Beginning");
            System.out.println("2. Add Movie at End");
            System.out.println("3. Add Movie at Specific Position");
            System.out.println("4. Remove Movie by Title");
            System.out.println("5. Search Movie by Director");
            System.out.println("6. Search Movie by Rating");
            System.out.println("7. Display All Movies Forward");
            System.out.println("8. Display All Movies Reverse");
            System.out.println("9. Update Movie Rating");
            System.out.println("10. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Title, Director, Year, Rating: ");
                    movieList.addAtBeginning(scanner.nextLine(), scanner.nextLine(), scanner.nextInt(), scanner.nextDouble());
                    break;
                case 2:
                    System.out.print("Enter Title, Director, Year, Rating: ");
                    movieList.addAtEnd(scanner.nextLine(), scanner.nextLine(), scanner.nextInt(), scanner.nextDouble());
                    break;
                case 3:
                    System.out.print("Enter Position, Title, Director, Year, Rating: ");
                    movieList.addAtPosition(scanner.nextInt(), scanner.nextLine().trim(), scanner.nextLine(), scanner.nextInt(), scanner.nextDouble());
                    break;
                case 4:
                    System.out.print("Enter Movie Title to remove: ");
                    movieList.removeByTitle(scanner.nextLine());
                    break;
                case 5:
                    System.out.print("Enter Director to search: ");
                    movieList.searchByDirector(scanner.nextLine());
                    break;
                case 6:
                    System.out.print("Enter Rating to search: ");
                    movieList.searchByRating(scanner.nextDouble());
                    break;
                case 7:
                    movieList.displayForward();
                    break;
                case 8:
                    movieList.displayReverse();
                    break;
                case 9:
                    System.out.print("Enter Movie Title and new Rating: ");
                    movieList.updateRating(scanner.nextLine(), scanner.nextDouble());
                    break;
                case 10:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

// Node structure for Movie
class MovieNode {
    String title;
    String director;
    int year;
    double rating;
    MovieNode next;
    MovieNode prev;

    public MovieNode(String title, String director, int year, double rating) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.next = null;
        this.prev = null;
    }
}

// Doubly Linked List to manage movies
class DoublyLinkedList {
    private MovieNode head;
    private MovieNode tail;

    // Add movie at the beginning
    public void addAtBeginning(String title, String director, int year, double rating) {
        MovieNode newNode = new MovieNode(title, director, year, rating);
        if (head == null) {
            head = tail = newNode;
        } else {
            newNode.next = head;
            head.prev = newNode;
            head = newNode;
        }
    }

    // Add movie at the end
    public void addAtEnd(String title, String director, int year, double rating) {
        MovieNode newNode = new MovieNode(title, director, year, rating);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
    }

    // Add movie at a specific position
    public void addAtPosition(int position, String title, String director, int year, double rating) {
        if (position == 1) {
            addAtBeginning(title, director, year, rating);
            return;
        }
        MovieNode newNode = new MovieNode(title, director, year, rating);
        MovieNode current = head;
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

    // Remove movie by title
    public void removeByTitle(String title) {
        MovieNode current = head;
        while (current != null && !current.title.equalsIgnoreCase(title)) {
            current = current.next;
        }
        if (current == null) {
            System.out.println("Movie not found!");
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
        System.out.println("Movie removed successfully!");
    }

    // Search movie by director
    public void searchByDirector(String director) {
        MovieNode current = head;
        boolean found = false;
        while (current != null) {
            if (current.director.equalsIgnoreCase(director)) {
                System.out.println(current.title + ", " + current.director + ", " + current.year + ", " + current.rating);
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No movies found by the given director!");
        }
    }

    // Search movie by rating
    public void searchByRating(double rating) {
        MovieNode current = head;
        boolean found = false;
        while (current != null) {
            if (current.rating == rating) {
                System.out.println(current.title + ", " + current.director + ", " + current.year + ", " + current.rating);
                found = true;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("No movies found with the given rating!");
        }
    }

    // Display all movies forward
    public void displayForward() {
        MovieNode current = head;
        if (current == null) {
            System.out.println("No movies to display!");
            return;
        }
        while (current != null) {
            System.out.println(current.title + ", " + current.director + ", " + current.year + ", " + current.rating);
            current = current.next;
        }
    }

    // Display all movies in reverse
    public void displayReverse() {
        MovieNode current = tail;
        if (current == null) {
            System.out.println("No movies to display!");
            return;
        }
        while (current != null) {
            System.out.println(current.title + ", " + current.director + ", " + current.year + ", " + current.rating);
            current = current.prev;
        }
    }

    // Update movie rating by title
    public void updateRating(String title, double newRating) {
        MovieNode current = head;
        while (current != null) {
            if (current.title.equalsIgnoreCase(title)) {
                current.rating = newRating;
                System.out.println("Rating updated successfully!");
                return;
            }
            current = current.next;
        }
        System.out.println("Movie not found!");
    }
}

/*
Input:
1. Add Movie at Beginning
Enter Title, Director, Year, Rating: Inception Christopher Nolan 2010 9.0
2. Add Movie at End
Enter Title, Director, Year, Rating: Avatar James Cameron 2009 8.0
3. Add Movie at Specific Position
Enter Position, Title, Director, Year, Rating: 2 Titanic James Cameron 1997 8.2
5. Search Movie by Director
Enter Director to search: James Cameron
7. Display All Movies Forward
8. Display All Movies Reverse
9. Update Movie Rating
Enter Movie Title and new Rating: Avatar 8.0

Output:
Titanic, James Cameron, 1997, 8.2
Avatar, James Cameron, 2009, 8.0
Inception, Christopher Nolan, 2010, 9.0
Avatar, James Cameron, 2009, 8.0
*/
