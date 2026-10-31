import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Problem2_CustomerRegistry {

    static class Customer {

        private final int id;
        private final String name;

        Customer(int id, String name) {
            this.id = id;
            this.name = name;
        }

        // Content equality: same ID and name means the same real-world customer.
        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Customer)) {
                return false;
            }
            Customer other = (Customer) o;
            return id == other.id && name.equals(other.name);
        }

        // Equal objects must produce equal hash codes, or HashSet will miss duplicates.
        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return "(" + id + ", " + name + ")";
        }
    }

    static Set<Customer> registry = new HashSet<>();

    static boolean add(Customer customer, String branch) {
        return registry.add(customer);
    }

    public static void main(String[] args) {

        System.out.println(add(new Customer(101, "Asha"), "Branch A"));

        boolean added = add(new Customer(101, "Asha"), "Branch B");
        System.out.println(added ? "true" : "false (duplicate rejected)");

        System.out.println(add(new Customer(102, "Ravi"), "Branch A"));

        System.out.println("unique count " + registry.size());
        System.out.println("contains: " + registry.contains(new Customer(101, "Asha")));
    }
}
