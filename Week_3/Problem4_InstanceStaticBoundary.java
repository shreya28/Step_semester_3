public class Problem4_InstanceStaticBoundary {

    // Broken version: all fields are static,
    // so all students share the same data.
    static class BrokenSrmStudent {

        static String name;
        static String regNo;
        static int attendance;

        BrokenSrmStudent(String name, String regNo, int attendance) {
            BrokenSrmStudent.name = name;
            BrokenSrmStudent.regNo = regNo;
            BrokenSrmStudent.attendance = attendance;
        }
    }

    // Fixed version
    static class SrmStudent {

        String name;
        String regNo;
        int attendance;

        static String university = "SRM";
        static int admissionCount = 0;

        SrmStudent(String name, int attendance) {
            this.name = name;
            this.attendance = attendance;

            admissionCount++;
            this.regNo = "RA2311003010" + admissionCount;
        }

        void printIdCard() {
            System.out.println(name + " | " + regNo);
        }

        static void printTotalAdmissions() {
            System.out.println("Students admitted so far: "
                    + admissionCount);
        }
    }

    public static void main(String[] args) {

        // Broken version
        System.out.println("Broken version:");

        BrokenSrmStudent ravi =
                new BrokenSrmStudent("Ravi", "RA101", 82);

        BrokenSrmStudent meera =
                new BrokenSrmStudent("Meera", "RA102", 74);

        System.out.println(ravi.name);
        System.out.println(meera.name);

        // Fixed version
        System.out.println("\nFixed version:");

        SrmStudent student1 =
                new SrmStudent("Ravi", 82);

        SrmStudent student2 =
                new SrmStudent("Meera", 74);

        student1.printIdCard();
        student2.printIdCard();

        SrmStudent.printTotalAdmissions();
    }
}