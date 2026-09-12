public class Problem5_NightlyMultiKitchenReconciliation {

    static class DeliveryAccount {

        String studentId;
        double orderValue;

        static double surgeRate;

        // One-time class-level setup
        static {
            surgeRate = 1.0;
        }

        // Full constructor
        DeliveryAccount(String studentId, double orderValue) {
            this.studentId = studentId;
            this.orderValue = orderValue;
        }

        // Provisional constructor
        DeliveryAccount(String studentId) {
            this(studentId, 0);
        }

        final double calculateSurgeFee(int delayMinutes) {

            if (delayMinutes < 0) {
                throw new IllegalArgumentException("Invalid delay");
            }

            return orderValue * surgeRate / 100 * delayMinutes;
        }

        void processAccount(double amount, int delayMinutes) {
            System.out.println(
                    studentId + " regular | Amount: Rs " + amount
            );
        }
    }

    static class Premium extends DeliveryAccount {

        Premium(String studentId, double orderValue) {
            super(studentId, orderValue);
        }

        @Override
        void processAccount(double amount, int delayMinutes) {
            System.out.println(
                    studentId + " premium | Amount: Rs " + amount
            );
        }
    }

    static void processBatch(
            DeliveryAccount[] accounts,
            double[] amounts,
            int[] delayMinutesArray) {

        // Reject the entire batch if arrays do not match.
        if (accounts.length != amounts.length
                || accounts.length != delayMinutesArray.length) {

            System.out.println(
                    "Invalid batch: array lengths do not match"
            );
            return;
        }

        int processed = 0;
        int nullSkipped = 0;
        int premiumCount = 0;
        int regularCount = 0;
        double grandTotalSurgeFee = 0;

        for (int i = 0; i < accounts.length; i++) {

            DeliveryAccount account = accounts[i];

            // Safely skip null entries
            if (account == null) {
                nullSkipped++;
                continue;
            }

            try {

                if (account instanceof Premium) {
                    premiumCount++;
                } else {
                    regularCount++;
                }

                account.processAccount(
                        amounts[i],
                        delayMinutesArray[i]
                );

                grandTotalSurgeFee +=
                        account.calculateSurgeFee(
                                delayMinutesArray[i]
                        );

                processed++;

            } catch (Exception e) {

                System.out.println(
                        account.studentId
                        + " skipped due to invalid data"
                );
            }
        }

        System.out.println();

        System.out.println("Processed: " + processed);
        System.out.println("Null skipped: " + nullSkipped);
        System.out.println("Premium: " + premiumCount);
        System.out.println("Regular: " + regularCount);

        System.out.println(
                "Grand total surge fees = Rs "
                + grandTotalSurgeFee
        );
    }

    public static void main(String[] args) {

        DeliveryAccount[] accounts = {
            new Premium("STU001", 500),
            null,
            new DeliveryAccount("STU002", 300)
        };

        double[] amounts = {
            500,
            400,
            300
        };

        int[] delayMinutesArray = {
            10,
            5,
            0
        };

        processBatch(
                accounts,
                amounts,
                delayMinutesArray
        );
    }
}