/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

public class Board {

    // board dimension (board is N x N)
    private int N;
    private int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        if (N == 0) return String.valueOf(N)+"\n";
        String result;
        StringBuilder sb = new StringBuilder(String.valueOf(N));
        sb.append("\n");

        for (int i = 0; i < N; i++) {
            for (int j = 1; j < N; j++) {
                sb.append(String.format("%2d ", tiles[i][j]));
            }
            sb.append("\n"); // end of the row
        }
        result = sb.toString();
        return result;
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        return 0;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return true;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}