public class Problem5_FireDrillExits {

    // Multi-source BFS: every exit enters the queue with distance 0 before the
    // search starts. BFS reaches cells in order of distance, so the first time a
    // room is reached is its distance to the nearest exit.
    // Each cell is queued at most once -> O(rows * cols) time and space.
    // (A separate BFS from every room would be O((rows * cols)^2).)
    static int[][] exitDistance(char[][] plan) {

        int rows = plan.length;
        int cols = plan[0].length;

        int[][] dist = new int[rows][cols];

        // Array used as a queue of cells, each stored as r * cols + c.
        int[] queue = new int[rows * cols];
        int head = 0;
        int tail = 0;

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (plan[r][c] == 'E') {
                    dist[r][c] = 0;
                    queue[tail++] = r * cols + c;
                } else {
                    dist[r][c] = -1;    // walls stay -1; rooms stay -1 if never reached
                }
            }
        }

        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (head < tail) {

            int cell = queue[head++];
            int r = cell / cols;
            int c = cell % cols;

            for (int d = 0; d < 4; d++) {

                int nr = r + dr[d];
                int nc = c + dc[d];

                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && plan[nr][nc] == '.' && dist[nr][nc] == -1) {
                    dist[nr][nc] = dist[r][c] + 1;
                    queue[tail++] = nr * cols + nc;
                }
            }
        }

        return dist;
    }

    public static void main(String[] args) {

        char[][] plan = {
            {'.', '#', '.', 'E'},
            {'.', '.', '.', '#'},
            {'E', '#', '.', '.'}
        };

        for (int[] row : exitDistance(plan)) {
            StringBuilder sb = new StringBuilder();
            for (int value : row) {
                if (sb.length() > 0) {
                    sb.append(" ");
                }
                sb.append(value);
            }
            System.out.println(sb);
        }
    }
}
