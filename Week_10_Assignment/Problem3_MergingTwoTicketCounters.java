public class Problem3_MergingTwoTicketCounters {

    static class Node {

        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Time O(m + n): each node is attached exactly once.
    // Space O(1): existing nodes are relinked; only one dummy node is created.
    // Copying into an array and sorting would cost O((m + n) log (m + n)) time
    // and O(m + n) extra space, and it ignores that both lists are already sorted.
    static Node mergeSorted(Node a, Node b) {

        Node dummy = new Node(0);
        Node tail = dummy;

        while (a != null && b != null) {
            // <= keeps duplicates and takes from a first on ties.
            if (a.data <= b.data) {
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }

        // One list has run out; attach the rest of the other.
        tail.next = (a != null) ? a : b;

        return dummy.next;
    }

    static Node build(int[] values) {

        Node dummy = new Node(0);
        Node tail = dummy;

        for (int value : values) {
            tail.next = new Node(value);
            tail = tail.next;
        }

        return dummy.next;
    }

    static void print(Node head) {

        StringBuilder sb = new StringBuilder();

        for (Node cur = head; cur != null; cur = cur.next) {
            if (sb.length() > 0) {
                sb.append(" -> ");
            }
            sb.append(cur.data);
        }

        System.out.println(sb.length() == 0 ? "(empty)" : sb);
    }

    public static void main(String[] args) {

        print(mergeSorted(build(new int[]{3, 8, 15}), build(new int[]{5, 8, 12, 20})));
        print(mergeSorted(build(new int[]{}), build(new int[]{4})));
    }
}
