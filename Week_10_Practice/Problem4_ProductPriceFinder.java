public class Problem4_ProductPriceFinder {

    // Two binary searches -> O(log n) time, O(1) space.

    // First index i with prices[i] >= target.
    static int lowerBound(int[] prices, int target) {

        int lo = 0;
        int hi = prices.length;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (prices[mid] >= target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    // First index i with prices[i] > target.
    static int upperBound(int[] prices, int target) {

        int lo = 0;
        int hi = prices.length;

        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (prices[mid] > target) {
                hi = mid;
            } else {
                lo = mid + 1;
            }
        }

        return lo;
    }

    static int countInRange(int[] prices, int low, int high) {

        int lower = lowerBound(prices, low);
        int upper = upperBound(prices, high);

        System.out.println("Lower bound index " + lower);
        System.out.println("Upper bound index " + upper);

        return Math.max(0, upper - lower);
    }

    public static void main(String[] args) {

        int[] prices = {100, 150, 150, 200, 300, 450};

        System.out.println("Count " + countInRange(prices, 150, 300));
    }
}
