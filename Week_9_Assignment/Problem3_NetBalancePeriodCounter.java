import java.util.HashMap;

public class Problem3_NetBalancePeriodCounter {

    // Brute force: sum every subarray -> O(n^2) with running sums.
    // Optimized: if the running total up to day j is S, a period ending at j
    // sums to k when an earlier running total was S - k.
    // O(n) time, O(n) extra space for the map.
    // seen[0] = 1 counts periods that start at day 0.
    static long countPeriods(int[] transactions, long k) {

        HashMap<Long, Integer> seen = new HashMap<>();
        seen.put(0L, 1);

        long running = 0;
        long count = 0;

        for (int t : transactions) {
            running += t;
            count += seen.getOrDefault(running - k, 0);
            seen.put(running, seen.getOrDefault(running, 0) + 1);
        }

        return count;
    }

    public static void main(String[] args) {

        System.out.println(countPeriods(new int[]{3, 4, -7, 1, 3, 3, 1, -4}, 7));
        System.out.println(countPeriods(new int[]{1, 2, 3}, 10));
    }
}
