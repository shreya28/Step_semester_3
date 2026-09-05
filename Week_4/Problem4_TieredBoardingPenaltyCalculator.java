public class Problem4_TieredBoardingPenaltyCalculator {

    static final class BoardingPenaltyCalculator {

        private final double minimumPenaltyPercent;

        BoardingPenaltyCalculator(double minimumPenaltyPercent) {
            this.minimumPenaltyPercent = minimumPenaltyPercent;
        }

        final double calculatePenalty(double ticketFare, int minutesLate) {

            if (ticketFare < 0 || minutesLate < 0) {
                throw new IllegalArgumentException("Invalid input");
            }

            if (minutesLate == 0) {
                return 0.0;
            }

            double penalty = 0.0;

            // First 5 minutes = 0.5% per minute
            int firstTier = Math.min(minutesLate, 5);
            penalty += firstTier * ticketFare * 0.005;

            // Minutes 6 to 15 = 1% per minute
            if (minutesLate > 5) {
                int secondTier = Math.min(minutesLate, 15) - 5;
                penalty += secondTier * ticketFare * 0.01;
            }

            // Minutes 16 onwards = 2% per minute
            if (minutesLate > 15) {
                int thirdTier = minutesLate - 15;
                penalty += thirdTier * ticketFare * 0.02;
            }

            // Apply minimum penalty floor
            double minimumPenalty =
                    ticketFare * minimumPenaltyPercent / 100;

            return Math.max(penalty, minimumPenalty);
        }
    }

    public static void main(String[] args) {

        BoardingPenaltyCalculator calculator =
                new BoardingPenaltyCalculator(1.0);

        System.out.println(
                "0 minutes: Rs "
                + calculator.calculatePenalty(1000, 0)
        );

        System.out.println(
                "1 minute: Rs "
                + calculator.calculatePenalty(1000, 1)
        );

        System.out.println(
                "16 minutes: Rs "
                + calculator.calculatePenalty(1000, 16)
        );
    }
}