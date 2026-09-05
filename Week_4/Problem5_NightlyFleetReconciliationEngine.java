public class Problem5_NightlyFleetReconciliationEngine {

    static class BusTicketAccount {

        String bookingId;
        double ticketFare;

        static double penaltyRate;

        // Static block runs once when the class is loaded
        static {
            penaltyRate = 1.0;
        }

        BusTicketAccount(String bookingId, double ticketFare) {
            this.bookingId = bookingId;
            this.ticketFare = ticketFare;
        }

        BusTicketAccount(String bookingId) {
            this(bookingId, 0);
        }

        final double calculatePenalty(int minutesLate) {

            if (minutesLate < 0) {
                throw new IllegalArgumentException("Invalid minutes");
            }

            return ticketFare * penaltyRate / 100 * minutesLate;
        }

        void processAccount(double amount, int minutesLate) {
            System.out.println(
                    bookingId + " processed | Amount: Rs " + amount
            );
        }
    }

    static class Sleeper extends BusTicketAccount {

        Sleeper(String bookingId, double ticketFare) {
            super(bookingId, ticketFare);
        }

        @Override
        void processAccount(double amount, int minutesLate) {
            System.out.println(
                    bookingId + " sleeper processed | Amount: Rs " + amount
            );
        }
    }

    static void processBatch(
            BusTicketAccount[] accounts,
            double[] amounts,
            int[] minutesLateArray) {

        if (accounts.length != amounts.length
                || accounts.length != minutesLateArray.length) {

            System.out.println("Invalid batch: array lengths do not match");
            return;
        }

        int processed = 0;
        int nullSkipped = 0;
        int sleeperCount = 0;
        int regularCount = 0;
        double grandTotalPenalty = 0;

        for (int i = 0; i < accounts.length; i++) {

            BusTicketAccount account = accounts[i];

            if (account == null) {
                nullSkipped++;
                continue;
            }

            try {

                if (account instanceof Sleeper) {
                    sleeperCount++;
                } else {
                    regularCount++;
                }

                account.processAccount(
                        amounts[i],
                        minutesLateArray[i]
                );

                grandTotalPenalty +=
                        account.calculatePenalty(minutesLateArray[i]);

                processed++;

            } catch (Exception e) {
                System.out.println(
                        account.bookingId + " skipped due to invalid data"
                );
            }
        }

        System.out.println();
        System.out.println("Processed: " + processed);
        System.out.println("Null skipped: " + nullSkipped);
        System.out.println("Sleeper: " + sleeperCount);
        System.out.println("Regular: " + regularCount);
        System.out.println(
                "Grand total penalties = Rs " + grandTotalPenalty
        );
    }

    public static void main(String[] args) {

        BusTicketAccount[] accounts = {
            new Sleeper("BK001", 2000),
            null,
            new BusTicketAccount("BK002", 1200)
        };

        double[] amounts = {
            1200,
            900,
            700
        };

        int[] minutesLateArray = {
            10,
            5,
            0
        };

        processBatch(accounts, amounts, minutesLateArray);
    }
}