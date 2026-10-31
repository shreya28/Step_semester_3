public class Problem1_RemoveNthFromEnd {

    static class Node {

        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Single pass, two pointers -> O(n) time, O(1) space.
    // fast starts n + 1 steps ahead of slow, both from a dummy node before head.
    // When fast reaches null, slow is just before the node to remove.
    // Removing the head needs no special case: for n = length, slow stays on
    // the dummy, and dummy.next is moved past the old head.
    static Node removeNthFromEnd(Node head, int n) {

        Node dummy = new Node(0);
        dummy.next = head;

        Node fast = dummy;
        Node slow = dummy;

        for (int i = 0; i <= n; i++) {
            fast = fast.next;
        }

        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;

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

        System.out.println(sb.length() == 0 ? "(empty list)" : sb);
    }

    public static void main(String[] args) {

        print(removeNthFromEnd(build(new int[]{1, 2, 3, 4, 5}), 2));
        print(removeNthFromEnd(build(new int[]{1}), 1));
    }
}
