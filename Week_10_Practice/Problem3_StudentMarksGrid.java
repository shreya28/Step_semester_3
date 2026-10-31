public class Problem3_StudentMarksGrid {

    // A jagged 2D array stores only the marks each student actually has,
    // so a missing subject is simply a shorter row.
    static void report(String[] subjects, String[] students, int[][] marks) {

        StringBuilder totals = new StringBuilder("Totals");

        for (int i = 0; i < students.length; i++) {

            int total = 0;
            for (int mark : marks[i]) {
                total += mark;
            }

            totals.append(i == 0 ? " " : ", ").append(students[i]).append(" ").append(total);
        }

        System.out.println(totals);

        StringBuilder toppers = new StringBuilder("Toppers");

        for (int s = 0; s < subjects.length; s++) {

            int best = -1;
            int bestStudent = -1;

            for (int i = 0; i < students.length; i++) {
                // Skip students who have no mark for this subject.
                if (s < marks[i].length && marks[i][s] > best) {
                    best = marks[i][s];
                    bestStudent = i;
                }
            }

            toppers.append(s == 0 ? " " : ", ").append(subjects[s]).append(" ");
            toppers.append(bestStudent == -1 ? "none" : students[bestStudent] + " " + best);
        }

        System.out.println(toppers);
    }

    public static void main(String[] args) {

        String[] subjects = {"Math", "Sci", "Eng"};
        String[] students = {"Anu", "Ravi", "Meena"};

        int[][] marks = {
            {80, 90, 70},
            {60, 85},
            {95, 75, 88}
        };

        report(subjects, students, marks);
    }
}
