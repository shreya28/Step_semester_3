public class Problem2_LinkedListPalindrome {

    static class Node {

        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // O(n) time, O(1) extra space.
    // 1. Fast and slow pointers find the end of the first half
    //    (for odd lengths the middle node stays in the first half and is skipped).
    // 2. Reverse the second half in place.
    // 3. Compare the halves, then reverse the second half back to restore the list.
    static boolean isPalindrome(Node head) {

        if (head == null || head.next == null) {
            return true;
        }

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

    public static void main(String[] args) {

        System.out.println(isPalindrome(build(new int[]{1, 2, 2, 1})));
        System.out.println(isPalindrome(build(new int[]{1, 2, 3})));
    }
}
