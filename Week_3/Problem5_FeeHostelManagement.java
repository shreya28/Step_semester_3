public class Problem5_FeeHostelManagement {

    static class FeeAccount {

        private double totalFee;
        private double amountPaid;

        FeeAccount(double totalFee) {
            this.totalFee = totalFee;
        }

        void pay(double amount) {
            if (amount > 0) {
                amountPaid += amount;
            } else {
                System.out.println("Payment rejected");
            }
        }

        double getDue() {
            return totalFee - amountPaid;
        }
    }

    static class HostelFeeAccount extends FeeAccount {

        HostelFeeAccount(double totalFee) {
            super(totalFee);
        }
    }

    static class HostelRoom {

        String roomNo;
        int beds;
        int occupied;

        HostelRoom(String roomNo, int beds) {
            this.roomNo = roomNo;
            this.beds = beds;
        }

        void allot(String name) {
            if (occupied < beds) {
                occupied++;
            }
        }
    }

    static class SrmStudent {

        String name;
        String regNo;
        HostelFeeAccount feeAccount;
        HostelRoom room;

        static int totalStudents = 0;

        SrmStudent(String name, String regNo,
                   HostelFeeAccount feeAccount) {
            this.name = name;
            this.regNo = regNo;
            this.feeAccount = feeAccount;
            totalStudents++;
        }

        String fullStatus() {

            String roomNumber;

            if (room == null) {
                roomNumber = "unallotted";
            } else {
                roomNumber = room.roomNo;
            }

            return name + " | Due: Rs "
                    + feeAccount.getDue()
                    + " | Room: " + roomNumber;
        }
    }

    static HostelRoom findAvailableRoom(HostelRoom[] rooms) {

        for (HostelRoom room : rooms) {
            if (room.occupied < room.beds) {
                return room;
            }
        }

        return null;
    }

    static void safeAllot(HostelRoom[] rooms, SrmStudent student) {

        HostelRoom room = findAvailableRoom(rooms);

        if (room != null) {
            room.allot(student.name);
            student.room = room;
        }
    }

    public static void main(String[] args) {

        SrmStudent ravi = new SrmStudent(
                "Ravi", "RA101",
                new HostelFeeAccount(200000));

        SrmStudent anitha = new SrmStudent(
                "Anitha", "RA102",
                new HostelFeeAccount(200000));

        SrmStudent karthik = new SrmStudent(
                "Karthik", "RA103",
                new HostelFeeAccount(200000));

        HostelRoom[] rooms = {
            new HostelRoom("C-214", 1),
            new HostelRoom("C-507", 1)
        };

        safeAllot(rooms, ravi);
        safeAllot(rooms, anitha);

        ravi.feeAccount.pay(60000);
        anitha.feeAccount.pay(20000);

        karthik.feeAccount.pay(-5000);

        System.out.println(ravi.fullStatus());
        System.out.println(anitha.fullStatus());
        System.out.println(karthik.fullStatus());

        System.out.println("Total students: "
                + SrmStudent.totalStudents);
    }
}