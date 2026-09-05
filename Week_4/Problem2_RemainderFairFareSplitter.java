public class Problem2_RemainderFairFareSplitter {

    static class FareSplitter {

        String tripId;
        double totalFare;
        int passengerCount;

        // Full constructor
        FareSplitter(String tripId, double totalFare, int passengerCount) {

            if (totalFare < 0) {
                throw new IllegalArgumentException("Fare cannot be negative");
            }

            if (passengerCount <= 0) {
                throw new IllegalArgumentException(
                        "Passenger count must be positive");
            }

            this.tripId = tripId;
            this.totalFare = totalFare;
            this.passengerCount = passengerCount;
        }

        // Constructor chaining
        FareSplitter(String tripId, double totalFare) {
            this(tripId, totalFare, 1);
        }

        // Provisional constructor
        FareSplitter(String tripId) {
            this(tripId, 0, 2);
        }

        double[] fareBreakdown() {

            double[] result = new double[passengerCount];

            double share =
                    Math.floor((totalFare / passengerCount) * 100) / 100;

            double total = 0;

            for (int i = 0; i < passengerCount - 1; i++) {
                result[i] = share;
                total += share;
            }

            result[passengerCount - 1] =
                    Math.round((totalFare - total) * 100) / 100.0;

            return result;
        }

        boolean isConfirmationOverdue(int confirmed, int expected) {
            return confirmed < expected;
        }
    }

    public static void main(String[] args) {

        FareSplitter fare =
                new FareSplitter("TRIP001", 100000, 3);

        double[] breakdown = fare.fareBreakdown();

        System.out.print("Fare breakdown: ");

        for (double amount : breakdown) {
            System.out.print(amount + " ");
        }

        System.out.println();

        FareSplitter provisional =
                new FareSplitter("TRIP003");

        double[] result = provisional.fareBreakdown();

        System.out.print("Provisional breakdown: ");

        for (double amount : result) {
            System.out.print(amount + " ");
        }

        System.out.println();

        System.out.println(
                "Overdue: " +
                fare.isConfirmationOverdue(2, 3)
        );
    }
}