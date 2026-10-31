public class Problem2_CampusPortalBrowserHistory {

    static class Node {

        String url;
        Node prev;
        Node next;

        Node(String url) {
            this.url = url;
        }
    }

    // visit: O(1). back / forward: O(steps). Space: O(n) for the n pages kept.
    // A visit links the new node right after current and sets its next to null,
    // so every page that was ahead of current is no longer reachable.
    // Java's garbage collector then frees those dropped nodes automatically.
    static class BrowserHistory {

        private Node current;

        BrowserHistory(String homepage) {
            current = new Node(homepage);
        }

        void visit(String url) {

            Node page = new Node(url);

            page.prev = current;
            current.next = page;    // drops the old forward history
            current = page;
        }

        String back(int steps) {

            while (steps > 0 && current.prev != null) {
                current = current.prev;
                steps--;
            }

            return current.url;
        }

        String forward(int steps) {

            while (steps > 0 && current.next != null) {
                current = current.next;
                steps--;
            }

            return current.url;
        }
    }

    public static void main(String[] args) {

        BrowserHistory browser = new BrowserHistory("srm.edu");

        browser.visit("a.com");
        browser.visit("b.com");
        browser.visit("c.com");

        System.out.println(browser.back(1));
        System.out.println(browser.back(1));
        System.out.println(browser.forward(1));

        browser.visit("d.com");

        System.out.println(browser.forward(2));
        System.out.println(browser.back(2));
        System.out.println(browser.back(7));
    }
}
