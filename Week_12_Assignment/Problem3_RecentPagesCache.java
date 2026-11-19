import java.util.HashMap;

public class Problem3_RecentPagesCache {

    // LRU cache: HashMap for O(1) lookup + own doubly linked list for O(1) reordering.
    //
    // Time:  get O(1), put O(1) - one map operation plus a constant number of
    //        pointer changes (remove a node, add it at the front).
    // Space: O(capacity) - at most capacity nodes and map entries.
    //
    // Why a singly linked list can't remove an arbitrary node in O(1):
    //   removing a node means changing the next of the node BEFORE it, and with
    //   only next pointers we have to walk from the head to find it - O(n).
    //   The prev pointer gives us that node directly.
    static class RecentCache {

        private class Node {
            int key;
            String page;
            Node prev;
            Node next;

            Node(int key, String page) {
                this.key = key;
                this.page = page;
            }
        }

        private final int capacity;
        private final HashMap<Integer, Node> map = new HashMap<>();
        private final Node head = new Node(0, null); // dummy: most recent side
        private final Node tail = new Node(0, null); // dummy: least recent side

        RecentCache(int capacity) {
            this.capacity = capacity;
            head.next = tail;
            tail.prev = head;
        }

        private void remove(Node node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void addToFront(Node node) {
            node.next = head.next;
            node.prev = head;
            head.next.prev = node;
            head.next = node;
        }

        String get(int key) {
            Node node = map.get(key);
            if (node == null) {
                System.out.println("get(" + key + ") -> -1");
                return "-1";
            }
            remove(node);
            addToFront(node); // reading makes it most recent
            System.out.println("get(" + key + ") -> " + node.page);
            return node.page;
        }

        void put(int key, String page) {
            String label = "put(" + key + "," + page + ")";
            Node node = map.get(key);

            if (node != null) {
                node.page = page;
                remove(node);
                addToFront(node);
                System.out.println(label + " updated");
                return;
            }

            String evicted = "";
            if (map.size() == capacity) {
                Node lru = tail.prev; // node just before tail is least recent
                remove(lru);
                map.remove(lru.key);
                evicted = " evicted " + lru.key;
            }

            Node fresh = new Node(key, page);
            addToFront(fresh);
            map.put(key, fresh);
            System.out.println(label + evicted);
        }

        void printCache() {
            StringBuilder sb = new StringBuilder("Cache (most recent first): ");
            for (Node n = head.next; n != tail; n = n.next) {
                sb.append(n.key).append("=").append(n.page);
                if (n.next != tail) {
                    sb.append(", ");
                }
            }
            System.out.println(sb);
        }
    }

    public static void main(String[] args) {

        RecentCache cache = new RecentCache(2);

        cache.put(1, "Home");
        cache.put(2, "Notes");
        cache.get(1);
        cache.put(3, "Quiz");
        cache.get(2);
        cache.put(1, "Home2");
        cache.put(4, "Mail");
        cache.get(3);
        cache.get(1);
        cache.printCache();
    }
}
