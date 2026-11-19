public class Problem4_LibraryReturnsByDueDay {

    static class Node {
        String title;
        int day;
        Node next;

        Node(String title, int day) {
            this.title = title;
            this.day = day;
        }
    }

    // Stable merge sort on the linked list, relinking nodes only.
    //
    // Time:  O(n log n) - log n levels of splitting, O(n) merging per level.
    // Space: O(log n)   - recursion depth of the splits; no arrays, no new data nodes.
    //
    // Why merge sort suits linked lists better than quick sort:
    //   merge sort only walks forward and relinks, which lists do cheaply, and it is
    //   always O(n log n) and stable. Quick sort relies on random access and swapping,
    //   which lists don't have, and degrades to O(n^2) with bad pivots.
    static Node sortByDueDay(Node head) {

        if (head == null || head.next == null) {
            return head;
        }

        // Find the middle; fast starts one ahead so 2 nodes split into 1 + 1.
        Node slow = head;
        Node fast = head.next;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node right = slow.next;
        slow.next = null; // cut the list into two halves

        return merge(sortByDueDay(head), sortByDueDay(right));
    }

    static Node merge(Node a, Node b) {

        Node dummy = new Node("", 0);
        Node tail = dummy;

        while (a != null && b != null) {
            if (a.day <= b.day) { // <= takes the left node on ties -> stable
                tail.next = a;
                a = a.next;
            } else {
                tail.next = b;
                b = b.next;
            }
            tail = tail.next;
        }

        tail.next = (a != null) ? a : b;
        return dummy.next;
    }

    // Builds the list from "title:day" pairs.
    static Node build(String... pairs) {
        Node dummy = new Node("", 0);
        Node tail = dummy;
        for (String p : pairs) {
            String[] parts = p.split(":");
            tail.next = new Node(parts[0], Integer.parseInt(parts[1]));
            tail = tail.next;
        }
        return dummy.next;
    }

    static String format(Node head) {
        StringBuilder sb = new StringBuilder();
        for (Node n = head; n != null; n = n.next) {
            sb.append(n.title).append(":").append(n.day).append(" -> ");
        }
        return sb.append("null").toString();
    }

    public static void main(String[] args) {

        // Sample 1 -> AI:1 -> DSA:3 -> DBMS:3 -> OS:9 -> CN:12 -> Java:15 -> null
        System.out.println(format(sortByDueDay(
                build("Java:15", "DSA:3", "OS:9", "DBMS:3", "CN:12", "AI:1"))));

        // Sample 2 -> null
        System.out.println(format(sortByDueDay(build())));
    }
}
