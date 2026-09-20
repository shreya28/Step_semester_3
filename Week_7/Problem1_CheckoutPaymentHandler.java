public class Problem1_CheckoutPaymentHandler {

    static abstract class PaymentMethod {

        private static int transactionCounter = 0;

        private final String transactionId;

        public PaymentMethod() {
            transactionCounter++;

            transactionId =
                    "TXN-" + (1000 + transactionCounter);
        }

        public abstract String processPayment(
                double amount);

        public String processPayment(
                double amount,
                String note) {

            return processPayment(amount)
                    + " (" + note + ")";
        }

        public String getTransactionId() {
            return transactionId;
        }
    }

    static class CreditCardPayment
            extends PaymentMethod {

        private String cardNumberLastFour;

        public CreditCardPayment(
                String cardNumberLastFour) {

            super();
            this.cardNumberLastFour =
                    cardNumberLastFour;
        }

        @Override
        public String processPayment(
                double amount) {

            return "Charged $" + amount
                    + " to card ending "
                    + cardNumberLastFour
                    + " - Txn "
                    + getTransactionId();
        }
    }

    static class CashPayment
            extends PaymentMethod {

        public CashPayment() {
            super();
        }

        @Override
        public String processPayment(
                double amount) {

            return "Received $" + amount
                    + " in cash - Txn "
                    + getTransactionId();
        }
    }

    static void printConfirmation(
            PaymentMethod payment,
            double amount) {

        System.out.println(
                payment.processPayment(amount));
    }

    static void testUpcasting() {

        CreditCardPayment cc =
                new CreditCardPayment("4471");

        // Upcasting:
        PaymentMethod ref = cc;

        printConfirmation(ref, 250.0);
    }

    public static void main(String[] args) {

        CreditCardPayment cc =
                new CreditCardPayment("4471");

        System.out.println(
                cc.processPayment(250.0));

        CashPayment cash =
                new CashPayment();

        System.out.println(
                cash.processPayment(40.0));

        System.out.println(
                cc.processPayment(
                        250.0,
                        "Birthday gift"));

        testUpcasting();

        // This cannot compile because
        // PaymentMethod is abstract:
        //
        // PaymentMethod p =
        //         new PaymentMethod();
    }
}