public class Problem2_LongestBudgetFriendlyStreak {

    // Checking every run: O(n^2) runs.
    // Sliding window: each index enters and leaves the window once -> O(n) time, O(1) space.
    // Costs are non-negative, so growing the window only raises the sum and
    // shrinking only lowers it. That is why moving left forward is always safe.
    // With negative values, a later day could bring the sum back under budget.
    static int[] longestStreak(int[] costs, long budget) {

        int bestLen = 0;
        int bestStart = -1;
        int left = 0;
        long windowSum = 0;

        for (int right = 0; right < costs.length; right++) {

            windowSum += costs[right];

            while (windowSum > budget && left <= right) {
                windowSum -= costs[left];
                left++;
            }

            int length = right - left + 1;

            // Strictly greater keeps the earliest start on ties.
            if (length > bestLen) {
                bestLen = length;
                bestStart = left;
            }
        }

        return new int[]{bestLen, bestStart};
    }

    public static void main(String[] args) {

        int[] r1 = longestStreak(new int[]{4, 2, 1, 7, 3, 1, 2, 1, 5}, 8);
        System.out.println("(" + r1[0] + ", " + r1[1] + ")");

        int[] r2 = longestStreak(new int[]{9, 10}, 8);
        System.out.println("(" + r2[0] + ", " + r2[1] + ")");
    }
}
