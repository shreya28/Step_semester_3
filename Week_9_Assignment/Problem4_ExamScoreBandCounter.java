public class Problem4_ExamScoreBandCounter {

    // Linear scan: O(n) time, O(1) space.
    // Final: two binary searches -> O(log n) time, O(1) space.
    // A plain binary search can stop anywhere inside a run of duplicates, so we
    // find the first index with score >= low and the first with score > high.
    // Both land exactly on the edges of the duplicate runs.

    // First index i with scores[i] >= target.
    static int lowerBound(int[] scores, int target) {

        int lo = 0;
        int hi = scores.length;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (scores[mid] >= target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    // First index i with scores[i] > target.
    static int upperBound(int[] scores, int target) {

        int lo = 0;
        int hi = scores.length;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (scores[mid] > target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    static int countInBand(int[] scores, int low, int high) {
        return Math.max(0, upperBound(scores, high) - lowerBound(scores, low));
    }

    public static void main(String[] args) {

        int[] scores = {35, 42, 42, 50, 58, 58, 58, 63, 71, 88};

        System.out.println(countInBand(scores, 42, 58));
        System.out.println(countInBand(scores, 90, 100));
    }
}
