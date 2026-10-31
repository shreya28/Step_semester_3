import java.util.ArrayList;
import java.util.List;

public class Problem5_SpiralStockAuditRoute {

    // Every bin is visited once -> O(m * n) time.
    // Only four boundary variables -> O(1) extra space, excluding the output.
    // The checks before the bottom row and left column stop a single
    // leftover row or column from being visited twice.
    static List<Integer> auditRoute(int[][] grid) {

        List<Integer> route = new ArrayList<>();

        if (grid.length == 0 || grid[0].length == 0) {
            return route;
        }

        int top = 0;
        int bottom = grid.length - 1;
        int left = 0;
        int right = grid[0].length - 1;

        while (top <= bottom && left <= right) {

            for (int col = left; col <= right; col++) {
                route.add(grid[top][col]);
            }
            top++;

            for (int row = top; row <= bottom; row++) {
                route.add(grid[row][right]);
            }
            right--;

            if (top <= bottom) {
                for (int col = right; col >= left; col--) {
                    route.add(grid[bottom][col]);
                }
                bottom--;
            }

            if (left <= right) {
                for (int row = bottom; row >= top; row--) {
                    route.add(grid[row][left]);
                }
                left++;
            }
        }

        return route;
    }

    public static void main(String[] args) {

        int[][] grid = {
            {1, 2, 3, 4},
            {5, 6, 7, 8},
            {9, 10, 11, 12}
        };

        System.out.println(auditRoute(grid));
    }
}
