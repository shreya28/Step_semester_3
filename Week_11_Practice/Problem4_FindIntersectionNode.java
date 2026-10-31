public class Problem4_FindIntersectionNode {

    static class Node {

        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Time O(m + n), space O(1).
    // Advance the longer list by the length difference so both pointers are the
    // same distance from the end, then walk together comparing references (==),
    // not values.
    static Node findIntersection(Node a, Node b) {

        int lenA = length(a);
        int lenB = length(b);

        while (lenA > lenB) {
            a = a.next;
            lenA--;
        }
        while (lenB > lenA) {
            b = b.next;
            lenB--;
        }

        while (a != b) {
            a = a.next;
            b = b.next;
        }

        return a;    // the shared node, or null if both reached the end
    }

    static int length(Node head) {

        int count = 0;

        for (Node cur = head; cur != null; cur = cur.next) {
            count++;
        }

        return count;
    }

    static Node build(int[] values, Node tailList) {

        Node dummy = new Node(0);
        Node tail = dummy;

        for (int value : values) {
            tail.next = new Node(value);
            tail = tail.next;
        }

        tail.next = tailList;

        return dummy.next;
    }

    static void report(Node node) {
        System.out.println(node == null ? "null" : "Node with value " + node.data);
    }

    public static void main(String[] args) {

        // Shared part 8 -> 4 -> 5 is the same node objects in both lists.
        Node shared = build(new int[]{8, 4, 5}, null);
        Node a1 = build(new int[]{4, 1}, shared);
        Node b1 = build(new int[]{5, 6, 1}, shared);
        report(findIntersection(a1, b1));

        Node a2 = build(new int[]{2, 6, 4}, null);
        Node b2 = build(new int[]{1, 5}, null);
        report(findIntersection(a2, b2));
    }
}
