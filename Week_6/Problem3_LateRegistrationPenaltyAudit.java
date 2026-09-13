public class Problem3_LateRegistrationPenaltyAudit {

    static class EventTicket {

        protected double balanceDue;

        private double[] lateFeeHistory;
        private int historyCount;

        EventTicket(double basePrice) {

            if (basePrice <= 0) {
                throw new IllegalArgumentException("Invalid price");
            }

            balanceDue = basePrice;
            lateFeeHistory = new double[10];
        }

        public void pay(double amount) {

            if (amount > 0) {
                balanceDue -= amount;

                if (balanceDue < 0) {
                    balanceDue = 0;
                }
            }
        }

        public double getBalanceDue() {
            return balanceDue;
        }

        protected void applyLateFee(double amount) {

            if (amount <= 0) {
                return;
            }

            balanceDue += amount;

            if (historyCount < lateFeeHistory.length) {
                lateFeeHistory[historyCount] = amount;
                historyCount++;
            }
        }

        public double[] getLateFeeHistory() {

            double[] copy =
                    new double[historyCount];

            for (int i = 0; i < historyCount; i++) {
                copy[i] = lateFeeHistory[i];
            }

            return copy;
        }
    }

    static class WorkshopTicket
            extends EventTicket {

        WorkshopTicket(double basePrice) {
            super(basePrice);
        }

        @Override
        protected void applyLateFee(double amount) {
            super.applyLateFee(amount * 2);
        }
    }

    public static void main(String[] args) {

        WorkshopTicket w =
                new WorkshopTicket(1200);

        w.pay(1200);

        w.applyLateFee(100);

        System.out.println(
                w.getBalanceDue());

        double[] history =
                w.getLateFeeHistory();

        history[0] = 999;

        System.out.println(
                w.getLateFeeHistory()[0]);
    }
}