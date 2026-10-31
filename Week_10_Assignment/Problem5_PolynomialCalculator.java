public class Problem5_PolynomialCalculator {

    static class Node {

        int coeff;
        int exp;
        Node next;

        Node(int coeff, int exp) {
            this.coeff = coeff;
            this.exp = exp;
        }
    }

    // Merge of two lists sorted by decreasing exponent.
    // Time O(m + n): each term of p and q is visited once.
    // Space O(m + n) for the new result list (at most m + n terms);
    // O(1) extra besides the output.
    static Node addPolynomials(Node p, Node q) {

        Node dummy = new Node(0, 0);
        Node tail = dummy;

        while (p != null || q != null) {

            int coeff;
            int exp;

            if (q == null || (p != null && p.exp > q.exp)) {
                coeff = p.coeff;
                exp = p.exp;
                p = p.next;
            } else if (p == null || q.exp > p.exp) {
                coeff = q.coeff;
                exp = q.exp;
                q = q.next;
            } else {
                coeff = p.coeff + q.coeff;
                exp = p.exp;
                p = p.next;
                q = q.next;
            }

            // Terms that cancel out are left out.
            if (coeff != 0) {
                tail.next = new Node(coeff, exp);
                tail = tail.next;
            }
        }

        return dummy.next;
    }

    // Builds a list from {coeff, exp} pairs given in decreasing exponent order.
    static Node build(int[][] terms) {

        Node dummy = new Node(0, 0);
        Node tail = dummy;

        for (int[] term : terms) {
            tail.next = new Node(term[0], term[1]);
            tail = tail.next;
        }

        return dummy.next;
    }

    // Standard form: 5x^3 + 9x^2 - 3x + 3
    static String format(Node head) {

        if (head == null) {
            return "0";
        }

        StringBuilder sb = new StringBuilder();

        for (Node cur = head; cur != null; cur = cur.next) {

            int abs = Math.abs(cur.coeff);

            if (cur == head) {
                if (cur.coeff < 0) {
                    sb.append("-");
                }
            } else {
                sb.append(cur.coeff < 0 ? " - " : " + ");
            }

            // Hide a coefficient of 1 unless it is the constant term.
            if (abs != 1 || cur.exp == 0) {
                sb.append(abs);
            }

            if (cur.exp == 1) {
                sb.append("x");
            } else if (cur.exp > 1) {
                sb.append("x^").append(cur.exp);
            }
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        Node p1 = build(new int[][]{{5, 3}, {4, 2}, {2, 0}});
        Node q1 = build(new int[][]{{5, 2}, {-3, 1}, {1, 0}});
        System.out.println(format(addPolynomials(p1, q1)));

        Node p2 = build(new int[][]{{3, 2}, {1, 0}});
        Node q2 = build(new int[][]{{-3, 2}, {4, 1}});
        System.out.println(format(addPolynomials(p2, q2)));
    }
}
