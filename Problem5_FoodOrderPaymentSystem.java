import java.util.ArrayList;

public class Problem5_FoodOrderPaymentSystem {

    interface IPaymentMethod {

        boolean pay(double amount);
    }

    static class CreditCardPayment
            implements IPaymentMethod {

        @Override
        public boolean pay(double amount) {

            System.out.println(
                    "Payment via Credit Card successful.");

            return true;
        }
    }

    static class DigitalWalletPayment
            implements IPaymentMethod {

        private boolean shouldSucceed;

        DigitalWalletPayment(
                boolean shouldSucceed) {

            this.shouldSucceed =
                    shouldSucceed;
        }

        @Override
        public boolean pay(double amount) {

            if (shouldSucceed) {

                System.out.println(
                        "Payment via Digital Wallet successful.");

                return true;
            }

            System.out.println(
                    "Payment via Digital Wallet failed.");

            return false;
        }
    }

    static class FoodItem {

        String name;
        double price;

        FoodItem(
                String name,
                double price) {

            this.name = name;
            this.price = price;
        }
    }

    static class LineItem {

        FoodItem item;
        int quantity;

        LineItem(
                FoodItem item,
                int quantity) {

            this.item = item;
            this.quantity = quantity;
        }

        double getTotal() {
            return item.price * quantity;
        }
    }

    static class Customer {

        String name;

        Customer(String name) {
            this.name = name;
        }
    }

    static class Order {

        static int counter = 122;

        int orderId;

        Customer customer;

        ArrayList<LineItem> items =
                new ArrayList<>();

        String status =
                "Created";

        Order(Customer customer) {

            this.customer = customer;

            counter++;
            orderId = counter;

            System.out.println(
                    "Order created.");
        }

        void addItem(
                FoodItem item,
                int quantity) {

            items.add(
                    new LineItem(
                            item,
                            quantity));

            System.out.println(
                    "Added "
                    + item.name
                    + " (Qty "
                    + quantity
                    + ")");
        }

        boolean placeOrder(
                IPaymentMethod payment) {

            if (items.isEmpty()) {

                System.out.println(
                        "Cannot place order: "
                        + "Order must contain at least one item.");

                return false;
            }

            System.out.println(
                    "Order placed successfully.");

            double total = 0;

            for (LineItem item : items) {
                total += item.getTotal();
            }

            boolean success =
                    payment.pay(total);

            if (success) {

                status = "Paid";

                System.out.println(
                        "Order status: Paid");

                System.out.println(
                        "Notification: Order #"
                        + orderId
                        + " placed and paid.");

            } else {

                status = "Pending Payment";

                System.out.println(
                        "Order status: Pending Payment");

                System.out.println(
                        "Notification: Order #"
                        + orderId
                        + " placed, awaiting payment.");
            }

            return success;
        }
    }

    public static void main(String[] args) {

        Customer customer =
                new Customer("John");

        FoodItem pizza =
                new FoodItem(
                        "Pizza",
                        10);

        FoodItem soda =
                new FoodItem(
                        "Soda",
                        3);

        Order order1 =
                new Order(customer);

        order1.addItem(pizza, 2);
        order1.addItem(soda, 1);

        // Empty cart test
        Order emptyOrder =
                new Order(customer);

        emptyOrder.placeOrder(
                new CreditCardPayment());

        order1.placeOrder(
                new CreditCardPayment());

        FoodItem burger =
                new FoodItem(
                        "Burger",
                        8);

        Order order2 =
                new Order(customer);

        order2.addItem(
                burger,
                1);

        order2.placeOrder(
                new DigitalWalletPayment(false));
    }
}