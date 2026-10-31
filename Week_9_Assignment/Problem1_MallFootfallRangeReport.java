import java.util.Arrays;

public class Problem1_MallFootfallRangeReport {

    // Naive: re-add each range -> O(n) per query, O(n * q) for q queries.
    // Optimized: build prefix sums once in O(n), then each query is O(1).
    // Total time O(n + q), extra space O(n).
    // prefix[i] = sum of visitors[0..i-1], so start = 0 needs no special case.
    // Preprocessing is worth it whenever the same data gets more than a few queries.
    static long[] footfallReport(int[] visitors, int[][] queries) {

        long[] prefix = new long[visitors.length + 1];

        for (int i = 0; i < visitors.length; i++) {
            prefix[i + 1] = prefix[i] + visitors[i];
        }

        long[] result = new long[queries.length];

        for (int i = 0; i < queries.length; i++) {
            int start = queries[i][0];
            int end = queries[i][1];
            result[i] = prefix[end + 1] - prefix[start];
        }

        return result;
    }

    public static void main(String[] args) {

        int[] visitors = {12, 7, 3, 9, 15, 4, 8};
        int[][] queries = {{0, 2}, {2, 5}, {4, 6}, {3, 3}};

        System.out.println(Arrays.toString(footfallReport(visitors, queries)));
    }
}
