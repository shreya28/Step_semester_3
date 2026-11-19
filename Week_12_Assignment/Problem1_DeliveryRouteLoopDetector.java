public class Problem1_DeliveryRouteLoopDetector {

    static class Node {
        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    // Floyd's cycle detection: O(1) extra space, nodes are never modified.
    //
    // Time:  O(n)  - slow enters the loop within n steps, fast catches it within
    //                one lap, counting the loop and finding its start are O(n) each.
    // Space: O(1)  - only a few pointers and a counter.
    //
    // Why slow and fast must meet inside the loop:
    //   Once both are in the loop, fast gains exactly one node on slow per step,
    //   so the gap shrinks by 1 each time and reaches 0 - fast can never jump over slow.
    static String detectLoop(Node head) {

        Node slow = head;
        Node fast = head;
        boolean hasLoop = false;

        // Phase 1: find a meeting point (compare nodes, not values).
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                hasLoop = true;
                break;
            }
        }

        if (!hasLoop) {
            return "Loop: no";
        }

        // Phase 2: walk once round the loop from the meeting point to count it.
        int length = 1;
        Node walker = slow.next;
        while (walker != slow) {
            walker = walker.next;
            length++;
        }

        // Phase 3: head and meeting point are the same distance from the loop start.
        slow = head;
        while (slow != fast) {
            slow = slow.next;
            fast = fast.next;
        }

        return "Loop: yes, starts at stop " + slow.data + ", length " + length;
    }

    // Builds the list from stops; links the last node back to index pos (-1 = no loop).
    static Node buildRoute(int[] stops, int pos) {

        Node head = new Node(stops[0]);
        Node tail = head;
        Node loopTarget = (pos == 0) ? head : null;

        for (int i = 1; i < stops.length; i++) {
            tail.next = new Node(stops[i]);
            tail = tail.next;
            if (i == pos) {
                loopTarget = tail;
            }
        }

        tail.next = loopTarget; // stays null when pos == -1
        return head;
    }

    public static void main(String[] args) {

        // Sample 1 -> Loop: yes, starts at stop 30, length 3
        System.out.println(detectLoop(buildRoute(new int[]{10, 20, 30, 40, 50}, 2)));

        // Sample 2 -> Loop: no
        System.out.println(detectLoop(buildRoute(new int[]{10, 20, 30}, -1)));

        // Sample 3 -> Loop: yes, starts at stop 7, length 1
        System.out.println(detectLoop(buildRoute(new int[]{7}, 0)));
    }
}
