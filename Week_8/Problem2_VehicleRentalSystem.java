public class Problem2_VehicleRentalSystem {

    static abstract class Vehicle {

        String name;
        boolean available = true;

        Vehicle(String name) {
            this.name = name;
        }

        abstract double calculateCharge(int days);
    }

    static class StandardCar extends Vehicle {

        StandardCar(String name) {
            super(name);
        }

        @Override
        double calculateCharge(int days) {
            return days * 50;
        }
    }

    static class LuxuryCar extends Vehicle {

        LuxuryCar(String name) {
            super(name);
        }

        @Override
        double calculateCharge(int days) {
            return days * 100;
        }
    }

    static class SUV extends Vehicle {

        SUV(String name) {
            super(name);
        }

        @Override
        double calculateCharge(int days) {
            return days * 80;
        }
    }

    static class Customer {

        String name;

        Customer(String name) {
            this.name = name;
        }
    }

    static class Rental {

        Customer customer;
        Vehicle vehicle;
        int days;
        double totalCharge;

        Rental(
                Customer customer,
                Vehicle vehicle,
                int days) {

            this.customer = customer;
            this.vehicle = vehicle;
            this.days = days;
            this.totalCharge =
                    vehicle.calculateCharge(days);
        }
    }

    static class RentalService {

        Rental rentVehicle(
                Customer customer,
                Vehicle vehicle,
                int days) {

            if (!vehicle.available) {

                System.out.println(
                        vehicle.name
                        + " is already rented.");

                return null;
            }

            vehicle.available = false;

            Rental rental =
                    new Rental(
                            customer,
                            vehicle,
                            days);

            System.out.println(
                    vehicle.name
                    + " rented for "
                    + days
                    + " days.");

            System.out.printf(
                    "Total charge: $%.2f%n",
                    rental.totalCharge);

            return rental;
        }

        void returnVehicle(Vehicle vehicle) {

            vehicle.available = true;

            System.out.println(
                    vehicle.name
                    + " returned. Now available.");
        }
    }

    public static void main(String[] args) {

        Customer customer =
                new Customer("John");

        Vehicle luxury =
                new LuxuryCar("Luxury Car A");

        Vehicle standard =
                new StandardCar("Standard Car B");

        RentalService service =
                new RentalService();

        service.rentVehicle(
                customer,
                luxury,
                3);

        service.rentVehicle(
                customer,
                standard,
                5);

        service.returnVehicle(luxury);

        service.rentVehicle(
                customer,
                luxury,
                2);
    }
}