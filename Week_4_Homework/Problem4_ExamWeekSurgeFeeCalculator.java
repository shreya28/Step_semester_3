public class Problem4_ExamWeekSurgeFeeCalculator {

    static final class SurgeFeeCalculator {

        private final double minimumSurgePercent;

        SurgeFeeCalculator(double minimumSurgePercent) {
            this.minimumSurgePercent = minimumSurgePercent;
        }

        final double calculateSurgeFee(double orderValue, int delayMinutes) {

            if (orderValue < 0 || delayMinutes < 0) {
                throw new IllegalArgumentException("Invalid input");
            }

            if (delayMinutes == 0) {
                return 0.0;
            }

            double surgeFee = 0.0;

            // Minutes 1-5: 0.5% per minute
            int firstTier = Math.min(delayMinutes, 5);
            surgeFee += firstTier * orderValue * 0.005;

            // Minutes 6-15: 1% per minute
            if (delayMinutes > 5) {
                int secondTier = Math.min(delayMinutes, 15) - 5;
                surgeFee += secondTier * orderValue * 0.01;
            }

            // Minutes 16 onward: 2% per minute
            if (delayMinutes > 15) {
                int thirdTier = delayMinutes - 15;
                surgeFee += thirdTier * orderValue * 0.02;
            }

            // Minimum surge floor
            double minimumFee =
                    orderValue * minimumSurgePercent / 100;

            return Math.max(surgeFee, minimumFee);
        }
    }

    public static void main(String[] args) {

        SurgeFeeCalculator calculator =
                new SurgeFeeCalculator(1.0);

        System.out.println(
                "0 minutes: Rs "
                + calculator.calculateSurgeFee(500, 0)
        );

        System.out.println(
                "1 minute: Rs "
                + calculator.calculateSurgeFee(500, 1)
        );

        System.out.println(
                "16 minutes: Rs "
                + calculator.calculateSurgeFee(500, 16)
        );
    }
}