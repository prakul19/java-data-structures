import java.util.ArrayList;
import java.util.Scanner;

// Main class
public class SocialMediaFriendConnections {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SinglyLinkedList userList = new SinglyLinkedList();

        while (true) {
            System.out.println("\n1. Add a User");
            System.out.println("2. Add a Friend Connection");
            System.out.println("3. Remove a Friend Connection");
            System.out.println("4. Find Mutual Friends");
            System.out.println("5. Display All Friends of a User");
            System.out.println("6. Search for a User by Name or User ID");
            System.out.println("7. Count the Number of Friends for Each User");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter User ID, Name, Age: ");
                    userList.addUser(scanner.nextInt(), scanner.next(), scanner.nextInt());
                    break;
                case 2:
                    System.out.print("Enter User ID and Friend ID: ");
                    userList.addFriendConnection(scanner.nextInt(), scanner.nextInt());
                    break;
                case 3:
                    System.out.print("Enter User ID and Friend ID to remove connection: ");
                    userList.removeFriendConnection(scanner.nextInt(), scanner.nextInt());
                    break;
                case 4:
                    System.out.print("Enter User ID 1 and User ID 2 to find mutual friends: ");
                    userList.findMutualFriends(scanner.nextInt(), scanner.nextInt());
                    break;
                case 5:
                    System.out.print("Enter User ID to display friends: ");
                    userList.displayFriends(scanner.nextInt());
                    break;
                case 6:
                    System.out.print("Enter User ID or Name to search: ");
                    String input = scanner.nextLine();
                    if (input.matches("\\d+")) {
                        userList.searchUserById(Integer.parseInt(input));
                    } else {
                        userList.searchUserByName(input);
                    }
                    break;
                case 7:
                    userList.countFriends();
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

// Node representing a User
class UserNode {
    int userId;
    String name;
    int age;
    ArrayList<Integer> friendIds; // List of Friend IDs
    UserNode next;

    public UserNode(int userId, String name, int age) {
        this.userId = userId;
        this.name = name;
        this.age = age;
        this.friendIds = new ArrayList<>();
        this.next = null;
    }
}

// Singly Linked List to manage users
class SinglyLinkedList {
    private UserNode head;

    // Add a new user
    public void addUser(int userId, String name, int age) {
        UserNode newNode = new UserNode(userId, name, age);
        if (head == null) {
            head = newNode;
            return;
        }
        UserNode current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = newNode;
    }

    // Add a friend connection
    public void addFriendConnection(int userId, int friendId) {
        UserNode user = findUser(userId);
        UserNode friend = findUser(friendId);
        if (user == null || friend == null) {
            System.out.println("One or both users not found!");
            return;
        }
        if (!user.friendIds.contains(friendId)) {
            user.friendIds.add(friendId);
        }
        if (!friend.friendIds.contains(userId)) {
            friend.friendIds.add(userId);
        }
        System.out.println("Friend connection added successfully!");
    }

    // Remove a friend connection
    public void removeFriendConnection(int userId, int friendId) {
        UserNode user = findUser(userId);
        UserNode friend = findUser(friendId);
        if (user == null || friend == null) {
            System.out.println("One or both users not found!");
            return;
        }
        user.friendIds.remove((Integer) friendId);
        friend.friendIds.remove((Integer) userId);
        System.out.println("Friend connection removed successfully!");
    }

    // Find mutual friends
    public void findMutualFriends(int userId1, int userId2) {
        UserNode user1 = findUser(userId1);
        UserNode user2 = findUser(userId2);
        if (user1 == null || user2 == null) {
            System.out.println("One or both users not found!");
            return;
        }
        ArrayList<Integer> mutualFriends = new ArrayList<>(user1.friendIds);
        mutualFriends.retainAll(user2.friendIds);
        System.out.println("Mutual Friends: " + mutualFriends);
    }

    // Display all friends of a user
    public void displayFriends(int userId) {
        UserNode user = findUser(userId);
        if (user == null) {
            System.out.println("User not found!");
            return;
        }
        System.out.println("Friends of " + user.name + ": " + user.friendIds);
    }

    // Search for a user by User ID
    public void searchUserById(int userId) {
        UserNode user = findUser(userId);
        if (user != null) {
            System.out.println("User Found: " + user.userId + ", " + user.name + ", " + user.age);
        } else {
            System.out.println("User not found!");
        }
    }

    // Search for a user by Name
    public void searchUserByName(String name) {
        UserNode current = head;
        while (current != null) {
            if (current.name.equalsIgnoreCase(name)) {
                System.out.println("User Found: " + current.userId + ", " + current.name + ", " + current.age);
                return;
            }
            current = current.next;
        }
        System.out.println("User not found!");
    }

    // Count the number of friends for each user
    public void countFriends() {
        UserNode current = head;
        while (current != null) {
            System.out.println(current.name + " has " + current.friendIds.size() + " friends.");
            current = current.next;
        }
    }

    // Find a user by User ID
    private UserNode findUser(int userId) {
        UserNode current = head;
        while (current != null) {
            if (current.userId == userId) {
                return current;
            }
            current = current.next;
        }
        return null;
    }
}

/*
Input:
1. Add a User
Enter User ID, Name, Age: 1 Parth 23
Enter User ID, Name, Age: 2 Prakul 21
Enter User ID, Name, Age: 3 Parag 22
2. Add a Friend Connection
Enter User ID and Friend ID: 1 2
Enter User ID and Friend ID: 2 3
4. Find Mutual Friends
Enter User ID 1 and User ID 2 to find mutual friends: 1 3
5. Display All Friends of a User
Enter User ID to display friends: 2
7. Count the Number of Friends for Each User

Output:
Friend connection added successfully!
Mutual Friends: [2]
Friends of Bob: [1, 3]
Parth has 1 friends.
Prakul has 2 friends.
Parag has 1 friends.
*/
