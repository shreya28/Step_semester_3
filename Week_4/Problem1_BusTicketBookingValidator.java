public class Problem1_BusTicketBookingValidator {

    static class BusTicket {

        String passengerName;
        String destination;
        boolean checkedIn;

        BusTicket(String passengerName, String destination) {

            if (passengerName == null || passengerName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid passenger name");
            }

            if (destination == null || destination.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid destination");
            }

            if (!passengerName.matches("[A-Za-z ]+")) {
                throw new IllegalArgumentException("Invalid passenger name");
            }

            this.passengerName = passengerName;
            this.destination = destination;
        }

        void markCheckedIn() {

            if (!checkedIn) {
                checkedIn = true;
            }
        }
    }

    static void processBatch(String[][] rawBookings) {

        int valid = 0;
        int rejected = 0;
        int duplicates = 0;

        String[][] accepted = new String[rawBookings.length][2];

        for (String[] booking : rawBookings) {

            try {

                BusTicket ticket =
                        new BusTicket(booking[0], booking[1]);

                boolean duplicate = false;

                for (int i = 0; i < valid; i++) {

                    if (accepted[i][0].equals(ticket.passengerName)
                            && accepted[i][1].equals(ticket.destination)) {

                        duplicate = true;
                        break;
                    }
                }

                if (duplicate) {
                    duplicates++;
                } else {
                    accepted[valid][0] = ticket.passengerName;
                    accepted[valid][1] = ticket.destination;
                    valid++;
                }

            } catch (Exception e) {
                rejected++;
            }
        }

        System.out.println(
                "Valid: " + valid
                + " | Rejected: " + rejected
                + " | Duplicates skipped: " + duplicates
        );
    }

    public static void main(String[] args) {

        String[][] bookings = {
            {"Divya", "Chennai"},
            {"", "Bangalore"},
            {"Ravi123", "Pune"},
            {"Divya", "Chennai"},
            {" ", " "}
        };

        processBatch(bookings);
    }
}