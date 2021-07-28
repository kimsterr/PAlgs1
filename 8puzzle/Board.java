/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import java.util.Arrays;
import java.util.ArrayDeque;

public class Board {

    // board dimension (board is N x N)
    private int N;
    private int[][] tiles;
    private int hammingDist = -1;
    private int manhattanDist = -1;
    private int zeroRow = -1;
    private int zeroCol = -1;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        N = tiles.length;
        this.tiles = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];
                if (this.tiles[i][j] == 0) {
                    zeroRow = i;
                    zeroCol = j;
                }
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
        if (manhattanDist > -1) return manhattanDist;

        // If computation actually needed...
        manhattanDist = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                manhattanDist += manhattanHelper(tiles[i][j], i, j);
            }
        }

        return manhattanDist;
    }

    private int manhattanHelper(int num, int i, int j) {
        if (num == 0) return 0; // Need to have a numbered tile for any impact

        // Find the actual place that "num" should be
        int goalRow = (num-1) / N;
        int goalCol = (num-1) % N;

        return Math.abs(i-goalRow) + Math.abs(j-goalCol);
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
        ArrayDeque<Board> neighbors = new ArrayDeque<Board>();

        // What is a "neighbor"?
        // Take the tile where 0 is, swap it with all possible tiles...keyword is POSSIBLE
        // use zeroRow and zeroCol to aid this function

        // To the left
        if (zeroCol > 0) {
            int[][] leftArr = deepCopy(tiles);
            swap(leftArr, zeroRow, zeroCol-1, zeroRow, zeroCol);
            neighbors.add(new Board(leftArr));
        }

        // To the right
        if (zeroCol < N-1) {
            int[][] rightArr = deepCopy(tiles);
            swap(rightArr, zeroRow, zeroCol+1, zeroRow, zeroCol);
            neighbors.add(new Board(rightArr));
        }

        // Above
        if (zeroRow > 0) {
            int[][] aboveArr = deepCopy(tiles);
            swap(aboveArr, zeroRow-1, zeroCol, zeroRow, zeroCol);
            neighbors.add(new Board(aboveArr));
        }

        // Below
        if (zeroRow < N-1) {
            int[][] belowArr = deepCopy(tiles);
            swap(belowArr, zeroRow+1, zeroCol, zeroRow, zeroCol);
            neighbors.add(new Board(belowArr));
        }

        // Remember to generate completely NEW board and NEW underlying array for the board

        return neighbors;
    }

    private int[][] deepCopy(int[][] arr) {
        int[][] result = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                result[i][j] = arr[i][j];
            }
        }

        return result;
    }

    private void swap(int[][] arr, int i1, int j1, int i2, int j2) {
        int temp = arr[i1][j1];
        arr[i1][j1] = arr[i2][j2];
        arr[i2][j2] = temp;
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