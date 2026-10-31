public class Problem5_ReorderList {

    static class Node {

        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Combines three standard techniques, O(n) time, O(1) space, no new nodes:
    // 1. Fast and slow pointers to find the middle.
    // 2. Split there and reverse the second half in place.
    // 3. Merge the two halves by alternately taking one node from each.
    // For odd lengths the first half is one node longer, so the middle node ends up last.
    static void reorderList(Node head) {

        if (head == null || head.next == null) {
            return;
        }

        Node slow = head;
        Node fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node second = reverse(slow.next);
        slow.next = null;    // split into two lists

        Node first = head;

        while (second != null) {
            Node firstNext = first.next;
            Node secondNext = second.next;

            first.next = second;
            second.next = firstNext;

            first = firstNext;
            second = secondNext;
        }
    }

    static Node reverse(Node head) {

        Node prev = null;
        Node cur = head;

        while (cur != null) {
            Node next = cur.next;
            cur.next = prev;
            prev = cur;
            cur = next;
        }

        return prev;
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

        System.out.println(sb);
    }

    public static void main(String[] args) {

        Node list1 = build(new int[]{1, 2, 3, 4});
        reorderList(list1);
        print(list1);

        Node list2 = build(new int[]{1, 2, 3, 4, 5});
        reorderList(list2);
        print(list2);
    }
}
