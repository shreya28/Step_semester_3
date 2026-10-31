import java.util.Arrays;

public class Problem1_CanteenPriceStreak {

    // Monotonic stack of day indices, prices strictly decreasing from bottom to top.
    // Only days with a higher price than everything after them can block a future
    // streak, so a day whose price is <= today's is popped and never needed again.
    // Each index is pushed and popped at most once -> O(n) time, O(n) space.
    static int[] priceStreak(int[] price) {

        int n = price.length;
        int[] streak = new int[n];

        // Array used as a stack; top is the index of the top element.
        int[] stack = new int[n];
        int top = -1;

        for (int i = 0; i < n; i++) {

            while (top >= 0 && price[stack[top]] <= price[i]) {
                top--;
            }

            streak[i] = (top < 0) ? i + 1 : i - stack[top];

            stack[++top] = i;
        }

        return streak;
    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(priceStreak(new int[]{100, 80, 60, 70, 60, 75, 85})));
        System.out.println(Arrays.toString(priceStreak(new int[]{10, 20, 30})));
    }
}
