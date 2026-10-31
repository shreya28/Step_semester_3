public class Problem4_PalindromeTrainCode {

    static class Node {

        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Time O(n): find the middle, reverse half, compare, reverse back.
    // Space O(1): only a few pointers, no array or stack.
    // Copying values into an array is also O(n) time, but needs O(n) extra space.
    static boolean isPalindrome(Node head) {

        if (head == null || head.next == null) {
            return true;
        }

        // slow ends at the last node of the first half.
        Node slow = head;
        Node fast = head;

        while (fast.next != null && fast.next.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        Node secondHead = reverse(slow.next);

        boolean result = true;
        Node p1 = head;
        Node p2 = secondHead;

        while (p2 != null) {
            if (p1.data != p2.data) {
                result = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        // Reverse the second half again and reattach it, so the list is unchanged.
        slow.next = reverse(secondHead);

        return result;
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

    static String format(Node head) {

        StringBuilder sb = new StringBuilder();

        for (Node cur = head; cur != null; cur = cur.next) {
            if (sb.length() > 0) {
                sb.append(" -> ");
            }
            sb.append(cur.data);
        }

        return sb.toString();
    }

    public static void main(String[] args) {

        int[][] codes = {
            {1, 2, 3, 2, 1},
            {1, 2, 3, 2},
            {1, 2, 2, 1}
        };

        for (int[] code : codes) {
            Node head = build(code);
            boolean result = isPalindrome(head);
            System.out.println(result + " (list afterwards: " + format(head) + ")");
        }
    }
}
