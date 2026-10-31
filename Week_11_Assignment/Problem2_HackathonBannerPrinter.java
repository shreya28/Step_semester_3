import java.util.ArrayDeque;
import java.util.Deque;

public class Problem2_HackathonBannerPrinter {

    // Two stacks replace recursion: one for repeat counts, one for the text built
    // before each '['. Each '[' saves the current state and starts a fresh builder;
    // each ']' restores it and appends the inner text k times.
    // Time O(n + L) where L is the length of the output; space O(L) plus the nesting depth.
    static String expandBanner(String code) {

        Deque<Integer> counts = new ArrayDeque<>();
        Deque<StringBuilder> texts = new ArrayDeque<>();

        StringBuilder current = new StringBuilder();
        int k = 0;

        for (char c : code.toCharArray()) {

            if (Character.isDigit(c)) {
                // k may have more than one digit, e.g. 12[a].
                k = k * 10 + (c - '0');
            } else if (c == '[') {
                counts.push(k);
                texts.push(current);
                current = new StringBuilder();
                k = 0;
            } else if (c == ']') {
                int repeat = counts.pop();
                StringBuilder outer = texts.pop();
                for (int i = 0; i < repeat; i++) {
                    outer.append(current);
                }
                current = outer;
            } else {
                current.append(c);
            }
        }

        return current.toString();
    }

    public static void main(String[] args) {

        System.out.println("\"" + expandBanner("3[ab]2[c]") + "\"");
        System.out.println("\"" + expandBanner("2[x3[y]]z") + "\"");
    }
}
