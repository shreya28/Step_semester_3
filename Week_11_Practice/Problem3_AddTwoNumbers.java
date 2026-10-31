public class Problem3_AddTwoNumbers {

    static class Node {

        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Digits are stored in reverse order, so we add from the head like column addition.
    // Time O(max(m, n)), space O(max(m, n)) for the result list.
    static Node addNumbers(Node a, Node b) {

        Node dummy = new Node(0);
        Node tail = dummy;
        int carry = 0;

        while (a != null || b != null || carry != 0) {

            int sum = carry;

            if (a != null) {
                sum += a.data;
                a = a.next;
            }
            if (b != null) {
                sum += b.data;
                b = b.next;
            }

            tail.next = new Node(sum % 10);
            tail = tail.next;
            carry = sum / 10;
        }

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

        print(addNumbers(build(new int[]{2, 4, 3}), build(new int[]{5, 6, 4})));
        print(addNumbers(build(new int[]{9, 9}), build(new int[]{1})));
    }
}
