/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;

public class Board {

    // board dimension (board is N x N)
    private int N;
    private int[][] tiles;
    private int hammingDist = -1;

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
            for (int j = 0; j < N; j++) {
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
        if (hammingDist > -1) return hammingDist;

        // If computation actually needed...
        hammingDist = 0;
        int target = 1;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tiles[i][j] == 0) {
                    target++;
                    continue; // Tile-less area cannot contribute to hammingDist
                }
                else if (i == N-1 && j == N-1) {
                    if (tiles[i][j] != 0) {
                        hammingDist++;
                    }
                }
                else if (tiles[i][j] != target) {
                    hammingDist++;
                }
                target++;
            }
        }

        return hammingDist;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        return 0;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null) {
            return false;
        }
        if (this.getClass() != y.getClass()) {
            return false;
        }

        // Have established that these are both Board objects occupying different addresses
        Board that = (Board) y;
        if (this.N != that.dimension()) {
            return false;
        }
        return Arrays.deepEquals(this.tiles, that.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        return null;
    }

    // a board that is obtained by exchanging any pair of tiles
    // REMEMBER THAT ZERO IS **NOT** a tile!!!!!!!!!!!!
    public Board twin() {
        // Make the new inner array
        int[][] twinArray = new int[N][N];

        int i1 = -1, j1 = -1;
        int i2 = -1, j2 = -1;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                twinArray[i][j] = tiles[i][j];
                if (i1 == -1) {
                    if (twinArray[i][j] != 0) {
                        i1 = i;
                        j1 = j;
                    }
                }
                else if (i2 == -1) {
                    if (twinArray[i][j] != 0) {
                        i2 = i;
                        j2 = j;
                    }
                }
            }
        }

        // Modify the array
        int temp = twinArray[i1][j1];
        twinArray[i1][j1] = twinArray[i2][j2];
        twinArray[i2][j2] = temp;

        Board twinBoard = new Board(twinArray);
        return twinBoard;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}