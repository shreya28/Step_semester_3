public class Problem5_TicketIssuanceSettlement {

    static class EventTicket {

        private static int ticketsIssued = 0;

        protected double balanceDue;

        public final String ticketId;

        static {
            ticketsIssued = 0;
        }

        public EventTicket(double basePrice) {

            if (basePrice <= 0) {
                throw new IllegalArgumentException(
                        "Invalid price");
            }

            ticketsIssued++;

            ticketId =
                    "TCK-" + (1000 + ticketsIssued);

            balanceDue = basePrice;
        }

        public void pay(double amount) {

            if (amount > 0) {

                balanceDue -= amount;

                if (balanceDue < 0) {
                    balanceDue = 0;
                }
            }
        }

        public void pay(
                double amount,
                String mode) {

            System.out.println(
                    "Payment mode: " + mode);

            pay(amount);
        }

        static boolean isValidPromoCode(
                String code) {

            if (code == null
                    || code.length() != 5) {
                return false;
            }

            if (code.charAt(0) != 'F') {
                return false;
            }

            if (!Character.isDigit(code.charAt(1))
                    || !Character.isDigit(code.charAt(2))
                    || !Character.isDigit(code.charAt(3))) {
                return false;
            }

            if (!Character.isUpperCase(code.charAt(4))) {
                return false;
            }

            return true;
        }

        static int getTicketsIssued() {
            return ticketsIssued;
        }

        public double getBalanceDue() {
            return balanceDue;
        }
    }

    static class GroupTicket
            extends EventTicket {

        private int groupSize;

        public GroupTicket(
                double basePrice,
                int groupSize) {

            super(basePrice);

            if (groupSize <= 0) {
                throw new IllegalArgumentException(
                        "Invalid group size");
            }

            this.groupSize = groupSize;
        }
    }

    static String processNightlySettlement(
            EventTicket[] tickets) {

        int processed = 0;
        int nullSkipped = 0;
        int group = 0;
        int individual = 0;

        for (EventTicket ticket : tickets) {

            if (ticket == null) {
                nullSkipped++;
                continue;
            }

            processed++;

            if (ticket instanceof GroupTicket) {
                group++;
            } else {
                individual++;
            }
        }

        return processed + " processed | "
                + nullSkipped + " null skipped | "
                + group + " group | "
                + individual + " individual";
    }

    public static void main(String[] args) {

        EventTicket t1 =
                new EventTicket(500);

        System.out.println(t1.ticketId);

        System.out.println(
                EventTicket.getTicketsIssued());

        System.out.println(
                EventTicket.isValidPromoCode("F123A"));

        System.out.println(
                EventTicket.isValidPromoCode("F12A"));

        System.out.println(
                EventTicket.isValidPromoCode("X123A"));

        t1.pay(200);
        t1.pay(200, "UPI");

        System.out.println(
                t1.getBalanceDue());

        EventTicket[] tickets = {
            new GroupTicket(2000, 5),
            null,
            new EventTicket(500)
        };

        System.out.println(
                processNightlySettlement(tickets));
    }
}