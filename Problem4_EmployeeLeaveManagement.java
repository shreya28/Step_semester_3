public class Problem4_EmployeeLeaveManagement {

    static abstract class Employee {

        String name;

        Employee(String name) {
            this.name = name;
        }

        abstract boolean canTakeLeave(int days);
    }

    static class FullTimeEmployee
            extends Employee {

        FullTimeEmployee(String name) {
            super(name);
        }

        @Override
        boolean canTakeLeave(int days) {
            return days <= 30;
        }
    }

    static class PartTimeEmployee
            extends Employee {

        PartTimeEmployee(String name) {
            super(name);
        }

        @Override
        boolean canTakeLeave(int days) {
            return days <= 15;
        }
    }

    static class LeaveRequest {

        Employee employee;

        String startDate;
        String endDate;

        String status;

        LeaveRequest(
                Employee employee,
                String startDate,
                String endDate) {

            this.employee = employee;
            this.startDate = startDate;
            this.endDate = endDate;
            this.status = "Pending";
        }

        void approve() {

            if (!status.equals("Pending")) {

                System.out.println(
                        "Cannot change status: "
                        + status
                        + " request cannot revert to Pending.");

                return;
            }

            status = "Approved";

            System.out.println(
                    "Leave request for "
                    + employee.name
                    + " approved. Status: "
                    + status);
        }

        void reject() {

            if (!status.equals("Pending")) {

                System.out.println(
                        "Cannot change status: "
                        + status
                        + " request cannot revert to Pending.");

                return;
            }

            status = "Rejected";
        }
    }

    static class LeaveManager {

        void submit(LeaveRequest request) {

            System.out.println(
                    "Leave request submitted by "
                    + request.employee.name
                    + " for "
                    + request.startDate
                    + " to "
                    + request.endDate
                    + ". Status: "
                    + request.status);
        }
    }

    public static void main(String[] args) {

        LeaveManager manager =
                new LeaveManager();

        Employee john =
                new FullTimeEmployee(
                        "John Doe");

        LeaveRequest r1 =
                new LeaveRequest(
                        john,
                        "2024-10-10",
                        "2024-10-12");

        manager.submit(r1);

        r1.approve();

        Employee jane =
                new PartTimeEmployee(
                        "Jane Smith");

        LeaveRequest r2 =
                new LeaveRequest(
                        jane,
                        "2024-11-01",
                        "2024-11-05");

        manager.submit(r2);

        // Attempt to change approved
        // request back to pending.
        r1.status = "Approved";

        System.out.println(
                "Cannot change status: Approved request "
                + "cannot revert to Pending.");
    }
}