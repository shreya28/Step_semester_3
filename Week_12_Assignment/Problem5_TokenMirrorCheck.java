public class Problem5_TokenMirrorCheck {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Palindrome check in O(1) extra space; the list is restored before returning.
    //
    // Time:  O(n) - find middle, reverse half, compare, reverse back: each O(n).
    // Space: O(1) - only pointers.
    //
    // Why an array or a stack doesn't meet the requirement:
    //   copying the values stores all n (or n/2) of them, which is O(n) extra space.
    //   Reversing the second half in place reuses the existing nodes instead.
    static boolean isPalindrome(Node head) {

        if (head == null || head.next == null) {
            return true;
        }

        // End of the first half (for odd length the middle stays in the first half).
        Node firstEnd = head;
        Node fast = head;
        while (fast.next != null && fast.next.next != null) {
            firstEnd = firstEnd.next;
            fast = fast.next.next;
        }

        Node secondStart = reverse(firstEnd.next);

        // Compare without returning early, so the list is always restored.
        boolean result = true;
        Node p1 = head;
        Node p2 = secondStart;
        while (p2 != null) {
            if (p1.data != p2.data) {
                result = false;
                break;
            }
            p1 = p1.next;
            p2 = p2.next;
        }

        firstEnd.next = reverse(secondStart); // put the list back exactly as it was
        return result;
    }

    static Node reverse(Node head) {
        Node prev = null;
        Node curr = head;
        while (curr != null) {
            Node next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    static Node build(int... values) {
        Node dummy = new Node(0);
        Node tail = dummy;
        for (int v : values) {
            tail.next = new Node(v);
            tail = tail.next;
        }
        return dummy.next;
    }

    static String format(Node head) {
        StringBuilder sb = new StringBuilder();
        for (Node n = head; n != null; n = n.next) {
            sb.append(n.data).append(" -> ");
        }
        return sb.append("null").toString();
    }

    static void check(Node head) {
        System.out.println("Palindrome: " + isPalindrome(head));
        System.out.println("List after check: " + format(head));
    }

    public static void main(String[] args) {

        // Sample 1 -> Palindrome: true / 4 -> 7 -> 9 -> 7 -> 4 -> null
        check(build(4, 7, 9, 7, 4));

        // Sample 2 -> Palindrome: false / 4 -> 7 -> 7 -> 9 -> null
        check(build(4, 7, 7, 9));
    }
}
