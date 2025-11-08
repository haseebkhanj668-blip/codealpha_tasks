import java.io.*;
import java.util.*;

class Room {
    int roomNumber;
    String category;
    boolean isBooked;

    public Room(int roomNumber, String category) {
        this.roomNumber = roomNumber;
        this.category = category;
        this.isBooked = false;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " [" + category + "] - " + (isBooked ? "Booked" : "Available");
    }
}

class Booking {
    String customerName;
    int roomNumber;
    String category;
    String paymentStatus;

    public Booking(String customerName, int roomNumber, String category, String paymentStatus) {
        this.customerName = customerName;
        this.roomNumber = roomNumber;
        this.category = category;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String toString() {
        return "Customer: " + customerName + " | Room: " + roomNumber + " [" + category + "] | Payment: " + paymentStatus;
    }
}

public class Hotelroomeservation {
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static final String ROOMS_FILE = "rooms.dat";
    static final String BOOKINGS_FILE = "bookings.dat";

    public static void main(String[] args) {
        loadRooms();
        loadBookings();
        mainMenu();
        saveRooms();
        saveBookings();
    }

    static void mainMenu() {
        boolean running = true;
        while (running) {
            System.out.println("\n=== Hotel Reservation System ===");
            System.out.println("1. View Available Rooms");
            System.out.println("2. Book a Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. View All Bookings");
            System.out.println("5. Exit");

            System.out.print("Choose an option: ");
            int choice = getIntInput();

            switch (choice) {
                case 1 -> viewAvailableRooms();
                case 2 -> bookRoom();
                case 3 -> cancelBooking();
                case 4 -> viewAllBookings();
                case 5 -> {
                    System.out.println("Thank you for using the system!");
                    running = false;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void loadRooms() {
        File file = new File(ROOMS_FILE);
        if (!file.exists()) {
            rooms.add(new Room(101, "Standard"));
            rooms.add(new Room(102, "Standard"));
            rooms.add(new Room(201, "Deluxe"));
            rooms.add(new Room(202, "Deluxe"));
            rooms.add(new Room(301, "Suite"));
            rooms.add(new Room(302, "Suite"));
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ROOMS_FILE))) {
            rooms = (ArrayList<Room>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Failed to load room data.");
        }
    }

    static void saveRooms() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ROOMS_FILE))) {
            oos.writeObject(rooms);
        } catch (IOException e) {
            System.out.println("Failed to save room data.");
        }
    }

    static void loadBookings() {
        File file = new File(BOOKINGS_FILE);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(BOOKINGS_FILE))) {
            bookings = (ArrayList<Booking>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Failed to load booking data.");
        }
    }

    static void saveBookings() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(BOOKINGS_FILE))) {
            oos.writeObject(bookings);
        } catch (IOException e) {
            System.out.println("Failed to save booking data.");
        }
    }

    static void viewAvailableRooms() {
        System.out.println("\nAvailable Rooms:");
        boolean found = false;
        for (Room room : rooms) {
            if (!room.isBooked) {
                System.out.println(room);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No available rooms.");
        }
    }

    static void bookRoom() {
        viewAvailableRooms();
        System.out.print("\nEnter your name: ");
        String name = scanner.nextLine();

        System.out.print("Enter room number to book: ");
        int roomNumber = getIntInput();

        Room selectedRoom = null;
        for (Room room : rooms) {
            if (room.roomNumber == roomNumber && !room.isBooked) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room is not available or does not exist.");
            return;
        }

        // Simulate Payment
        System.out.print("Proceed to payment (yes/no)? ");
        String pay = scanner.nextLine();
        if (!pay.equalsIgnoreCase("yes")) {
            System.out.println("Booking cancelled.");
            return;
        }

        selectedRoom.isBooked = true;
        Booking booking = new Booking(name, roomNumber, selectedRoom.category, "Paid");
        bookings.add(booking);
        System.out.println("Booking successful!");
    }

    static void cancelBooking() {
        System.out.print("Enter your name to cancel booking: ");
        String name = scanner.nextLine();

        Booking toRemove = null;
        for (Booking booking : bookings) {
            if (booking.customerName.equalsIgnoreCase(name)) {
                toRemove = booking;
                break;
            }
        }

        if (toRemove != null) {
            bookings.remove(toRemove);
            for (Room room : rooms) {
                if (room.roomNumber == toRemove.roomNumber) {
                    room.isBooked = false;
                    break;
                }
            }
            System.out.println("Booking cancelled successfully.");
        } else {
            System.out.println("No booking found for the name: " + name);
        }
    }

    static void viewAllBookings() {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
            return;
        }

        System.out.println("\nAll Bookings:");
        for (Booking booking : bookings) {
            System.out.println(booking);
        }
    }

    static int getIntInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("Invalid input. Enter a number: ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine(); // consume newline
        return input;
    }
}
