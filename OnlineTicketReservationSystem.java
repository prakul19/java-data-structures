import java.util.Scanner;

public class OnlineTicketReservationSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CircularLinkedList ticketList = new CircularLinkedList();

        while (true) {
            System.out.println("\n1. Add Ticket Reservation");
            System.out.println("2. Remove Ticket by Ticket ID");
            System.out.println("3. Display All Tickets");
            System.out.println("4. Search for Ticket by Customer Name");
            System.out.println("5. Search for Ticket by Movie Name");
            System.out.println("6. Calculate Total Number of Tickets");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Ticket ID, Customer Name, Movie Name, Seat Number, Booking Time: ");
                    ticketList.addTicket(scanner.nextInt(), scanner.next(), scanner.next(), scanner.next(), scanner.next());
                    break;
                case 2:
                    System.out.print("Enter Ticket ID to remove: ");
                    ticketList.removeTicket(scanner.nextInt());
                    break;
                case 3:
                    ticketList.displayTickets();
                    break;
                case 4:
                    System.out.print("Enter Customer Name to search: ");
                    ticketList.searchByCustomerName(scanner.nextLine());
                    break;
                case 5:
                    System.out.print("Enter Movie Name to search: ");
                    ticketList.searchByMovieName(scanner.nextLine());
                    break;
                case 6:
                    ticketList.calculateTotalTickets();
                    break;
                case 7:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}

// Node representing a booked ticket
class TicketNode {
    int ticketId;
    String customerName;
    String movieName;
    String seatNumber;
    String bookingTime;
    TicketNode next;

    public TicketNode(int ticketId, String customerName, String movieName, String seatNumber, String bookingTime) {
        this.ticketId = ticketId;
        this.customerName = customerName;
        this.movieName = movieName;
        this.seatNumber = seatNumber;
        this.bookingTime = bookingTime;
        this.next = null;
    }
}

// Circular Linked List to manage tickets
class CircularLinkedList {
    private TicketNode head = null;
    private TicketNode tail = null;

    // Add a new ticket at the end
    public void addTicket(int ticketId, String customerName, String movieName, String seatNumber, String bookingTime) {
        TicketNode newNode = new TicketNode(ticketId, customerName, movieName, seatNumber, bookingTime);
        if (head == null) {
            head = tail = newNode;
            newNode.next = head;
        } else {
            tail.next = newNode;
            tail = newNode;
            tail.next = head;
        }
        System.out.println("Ticket added successfully!");
    }

    // Remove a ticket by Ticket ID
    public void removeTicket(int ticketId) {
        if (head == null) {
            System.out.println("No tickets to remove!");
            return;
        }
        TicketNode current = head, prev = tail;
        do {
            if (current.ticketId == ticketId) {
                if (current == head) {
                    head = head.next;
                    tail.next = head;
                } else {
                    prev.next = current.next;
                }
                if (current == tail) {
                    tail = prev;
                }
                System.out.println("Ticket removed successfully!");
                return;
            }
            prev = current;
            current = current.next;
        } while (current != head);
        System.out.println("Ticket ID not found!");
    }

    // Display all tickets in the list
    public void displayTickets() {
        if (head == null) {
            System.out.println("No tickets to display!");
            return;
        }
        TicketNode current = head;
        do {
            System.out.println("Ticket ID: " + current.ticketId + ", Customer Name: " + current.customerName
                    + ", Movie Name: " + current.movieName + ", Seat Number: " + current.seatNumber
                    + ", Booking Time: " + current.bookingTime);
            current = current.next;
        } while (current != head);
    }

    // Search for a ticket by Customer Name
    public void searchByCustomerName(String customerName) {
        if (head == null) {
            System.out.println("No tickets to search!");
            return;
        }
        TicketNode current = head;
        boolean found = false;
        do {
            if (current.customerName.equalsIgnoreCase(customerName)) {
                System.out.println("Found Ticket: Ticket ID: " + current.ticketId + ", Movie Name: " + current.movieName
                        + ", Seat Number: " + current.seatNumber + ", Booking Time: " + current.bookingTime);
                found = true;
            }
            current = current.next;
        } while (current != head);
        if (!found) {
            System.out.println("No tickets found for the given customer name!");
        }
    }

    // Search for a ticket by Movie Name
    public void searchByMovieName(String movieName) {
        if (head == null) {
            System.out.println("No tickets to search!");
            return;
        }
        TicketNode current = head;
        boolean found = false;
        do {
            if (current.movieName.equalsIgnoreCase(movieName)) {
                System.out.println("Found Ticket: Ticket ID: " + current.ticketId + ", Customer Name: " + current.customerName
                        + ", Seat Number: " + current.seatNumber + ", Booking Time: " + current.bookingTime);
                found = true;
            }
            current = current.next;
        } while (current != head);
        if (!found) {
            System.out.println("No tickets found for the given movie name!");
        }
    }

    // Calculate the total number of booked tickets
    public void calculateTotalTickets() {
        if (head == null) {
            System.out.println("Total Tickets: 0");
            return;
        }
        int count = 0;
        TicketNode current = head;
        do {
            count++;
            current = current.next;
        } while (current != head);
        System.out.println("Total Tickets: " + count);
    }
}

/*
Input:
1. Add Ticket Reservation
Enter Ticket ID, Customer Name, Movie Name, Seat Number, Booking Time: 1 prakul Avengers A1 10:00AM
1. Add Ticket Reservation
Enter Ticket ID, Customer Name, Movie Name, Seat Number, Booking Time: 2 parth Batman B2 12:00PM
3. Display All Tickets
4. Search for Ticket by Customer Name
Enter Customer Name to search: prakul
5. Search for Ticket by Movie Name
Enter Movie Name to search: Batman
2. Remove Ticket by Ticket ID
Enter Ticket ID to remove: 1
6. Calculate Total Number of Tickets
3. Display All Tickets

Output:
Ticket added successfully!
Ticket added successfully!
Ticket ID: 1, Customer Name: prakul, Movie Name: Avengers, Seat Number: A1, Booking Time: 10:00AM
Ticket ID: 2, Customer Name: parth, Movie Name: Batman, Seat Number: B2, Booking Time: 12:00PM
Found Ticket: Ticket ID: 1, Movie Name: Avengers, Seat Number: A1, Booking Time: 10:00AM
Found Ticket: Ticket ID: 2, Customer Name: parth, Seat Number: B2, Booking Time: 12:00PM
Ticket removed successfully!
Total Tickets: 1
Ticket ID: 2, Customer Name: Bob, Movie Name: Batman, Seat Number: B2, Booking Time: 12:00PM
*/
