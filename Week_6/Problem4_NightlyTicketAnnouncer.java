public class Problem4_NightlyTicketAnnouncer {

    static class EventTicket {

        protected double balanceDue;

        EventTicket(double basePrice) {
            balanceDue = basePrice;
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        public void printTicket(StringBuilder result) {

            result.append(
                    "Standard | Balance: ")
                    .append(balanceDue)
                    .append(" | ");
        }
    }

    static class WorkshopTicket
            extends EventTicket {

        private String track;

        WorkshopTicket(
                double basePrice,
                String track) {

            super(basePrice);
            this.track = track;
        }

        @Override
        public void printTicket(StringBuilder result) {

            result.append(
                    "Workshop | Track: ")
                    .append(track)
                    .append(" | Balance: ")
                    .append(balanceDue)
                    .append(" | ");
        }

        public String getTrack() {
            return track;
        }
    }

    static String batchPrint(
            EventTicket[] tickets) {

        StringBuilder result =
                new StringBuilder();

        for (EventTicket ticket : tickets) {

            ticket.printTicket(result);

            if (ticket instanceof WorkshopTicket) {

                WorkshopTicket workshop =
                        (WorkshopTicket) ticket;

                result.append(
                        "[Track via downcast: ")
                        .append(workshop.getTrack())
                        .append("] | ");
            }
        }

        return result.toString();
    }

    public static void main(String[] args) {

        EventTicket plain =
                new EventTicket(500);

        WorkshopTicket workshop =
                new WorkshopTicket(
                        1200, "AI/ML");

        EventTicket[] tickets = {
            plain, workshop
        };

        System.out.println(
                batchPrint(tickets));
    }
}