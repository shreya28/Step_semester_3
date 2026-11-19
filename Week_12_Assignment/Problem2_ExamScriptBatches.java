public class Problem2_ExamScriptBatches {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Reverses the list in groups of k by relinking only; a short last group stays as is.
    //
    // Time:  O(n)  - each node is visited once by the "k nodes exist?" check
    //                and once while its link is reversed.
    // Space: O(1)  - iterative, so no recursion stack. A recursive version would
    //                keep one frame per group, i.e. O(n/k) stack space; the loop
    //                below carries the needed state (groupPrev) in a variable instead.
    static Node reverseInGroups(Node head, int k) {

        Node dummy = new Node(0); // sentinel, not part of the data
        dummy.next = head;
        Node groupPrev = dummy;   // node just before the current group

        while (true) {

            // Check that k nodes exist after groupPrev.
            Node kth = groupPrev;
            for (int i = 0; i < k && kth != null; i++) {
                kth = kth.next;
            }
            if (kth == null) {
                break; // fewer than k left: keep original order
            }

            Node groupNext = kth.next;      // first node after this group
            Node groupStart = groupPrev.next;

            // Reverse exactly k links; the reversed group's tail points to groupNext.
            Node prev = groupNext;
            Node curr = groupStart;
            while (curr != groupNext) {
                Node nextNode = curr.next;
                curr.next = prev;
                prev = curr;
                curr = nextNode;
            }

            // Connect the previous part to the new group head (old kth node).
            groupPrev.next = kth;
            groupPrev = groupStart; // old start is now this group's tail
        }

        return dummy.next;
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

    public static void main(String[] args) {

        // Sample 1 -> 3 -> 2 -> 1 -> 6 -> 5 -> 4 -> 7 -> 8 -> null
        System.out.println(format(reverseInGroups(build(1, 2, 3, 4, 5, 6, 7, 8), 3)));

        // Sample 2 -> 11 -> 12 -> null
        System.out.println(format(reverseInGroups(build(11, 12), 3)));
    }
}
