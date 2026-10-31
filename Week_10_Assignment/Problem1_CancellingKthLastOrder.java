public class Problem1_CancellingKthLastOrder {

    static class Node {

        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Single pass with two pointers -> O(n) time, O(1) extra space.
    // The lead pointer starts k + 1 steps ahead of the trailing pointer, so when
    // lead falls off the end, trail is just before the node to remove.
    // Removing the head needs no special case: trail starts at a dummy node
    // placed before head, so for k = length, trail stays on the dummy and
    // dummy.next is simply moved past the old head.
    static Node removeKthFromEnd(Node head, int k) {

        Node dummy = new Node(0);
        dummy.next = head;

        Node lead = dummy;
        Node trail = dummy;

        for (int i = 0; i <= k; i++) {
            lead = lead.next;
        }

        while (lead != null) {
            lead = lead.next;
            trail = trail.next;
        }

        trail.next = trail.next.next;

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

        System.out.println(sb);
    }

    public static void main(String[] args) {

        int[] orders = {10, 20, 30, 40, 50};

        print(removeKthFromEnd(build(orders), 2));
        print(removeKthFromEnd(build(orders), 5));
    }
}
