import java.time.LocalDate;
import java.util.ArrayList;

public class Problem3_HotelBookingSystem {

    static class Customer {

        String name;

        Customer(String name) {
            this.name = name;
        }
    }

    static class Room {

        String name;
        String category;
        double pricePerNight;

        ArrayList<Reservation> reservations =
                new ArrayList<>();

        Room(
                String name,
                String category,
                double pricePerNight) {

            this.name = name;
            this.category = category;
            this.pricePerNight =
                    pricePerNight;
        }

        boolean isAvailable(
                LocalDate start,
                LocalDate end) {

            for (Reservation r : reservations) {

                if (!r.cancelled
                        && start.isBefore(r.endDate)
                        && end.isAfter(r.startDate)) {

                    return false;
                }
            }

            return true;
        }
    }

    static class Reservation {

        Customer customer;
        Room room;

        LocalDate startDate;
        LocalDate endDate;

        boolean cancelled;

        Reservation(
                Customer customer,
                Room room,
                LocalDate startDate,
                LocalDate endDate) {

            this.customer = customer;
            this.room = room;
            this.startDate = startDate;
            this.endDate = endDate;
        }

        double calculatePrice() {

            long nights =
                    java.time.temporal.ChronoUnit.DAYS
                    .between(startDate, endDate);

            return nights *
                    room.pricePerNight;
        }

        void cancel() {

            cancelled = true;

            System.out.println(
                    "Reservation for "
                    + room.name
                    + " cancelled successfully.");
        }
    }

    static class BookingManager {

        Reservation bookRoom(
                Customer customer,
                Room room,
                LocalDate start,
                LocalDate end) {

            if (!room.isAvailable(start, end)) {

                System.out.println(
                        "Booking failed: "
                        + room.name
                        + " is not available for "
                        + start
                        + " to "
                        + end);

                return null;
            }

            Reservation reservation =
                    new Reservation(
                            customer,
                            room,
                            start,
                            end);

            room.reservations.add(reservation);

            System.out.println(
                    room.name
                    + " booked from "
                    + start
                    + " to "
                    + end);

            System.out.printf(
                    "Total price: $%.2f%n",
                    reservation.calculatePrice());

            return reservation;
        }
    }

    public static void main(String[] args) {

        Customer customer =
                new Customer("John");

        Room deluxe =
                new Room(
                        "Deluxe Room 101",
                        "Deluxe",
                        200);

        Room standard =
                new Room(
                        "Standard Room 205",
                        "Standard",
                        150);

        BookingManager manager =
                new BookingManager();

        Reservation r1 =
                manager.bookRoom(
                        customer,
                        deluxe,
                        LocalDate.of(2024, 12, 1),
                        LocalDate.of(2024, 12, 5));

        manager.bookRoom(
                customer,
                standard,
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7));

        manager.bookRoom(
                customer,
                deluxe,
                LocalDate.of(2024, 12, 3),
                LocalDate.of(2024, 12, 7));

        if (r1 != null) {
            r1.cancel();
        }
    }
}