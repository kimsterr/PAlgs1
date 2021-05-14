/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private WeightedQuickUnionUF ufTop; //
    private WeightedQuickUnionUF ufAll; //
    private final int n; //
    private int openCount; //

    private boolean[][] open; //
    private final int TOP_IDX; //
    private final int BOTTOM_IDX; //

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }

        // The very last array entry will represent "outside of the grid"
        ufTop = new WeightedQuickUnionUF(n*n+1);
        // The very last two array entries will represent "outside of the grid"
        ufAll = new WeightedQuickUnionUF(n*n+2);

        open = new boolean[n][n]; // By default, false
        openCount = 0;

        TOP_IDX = n*n;
        BOTTOM_IDX = n*n + 1;

        this.n = n;
    }

    private int map_toUFIdx(int row, int col) {
        int adjRow = row-1;
        int adjCol = col-1;

        return adjRow * n + adjCol;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }

        if (isOpen(row, col)) {
            return;
        }

        int adjRow = row-1;
        int adjCol = col-1;

        // Flag as open
        open[adjRow][adjCol] = true;
        openCount++;

        // Treat an entry in the top row differently
        if (adjRow == 0) {
            ufTop.union(map_toUFIdx(row,col), TOP_IDX);
            ufAll.union(map_toUFIdx(row,col), TOP_IDX);
        }

        // Treat an entry in the bottom row differently
        if (adjRow == n-1) {
            ufAll.union(map_toUFIdx(row,col), BOTTOM_IDX);
        }

        // Connect with each adjacent *open* site
        if (adjCol > 0 && isOpen(row, col-1)) { // Not on left edge
            ufTop.union(map_toUFIdx(row,col), map_toUFIdx(row,col-1));
            ufAll.union(map_toUFIdx(row,col), map_toUFIdx(row,col-1));
        }
        if (adjCol < n-1 && isOpen(row, col+1)) { // Not on right edge
            ufTop.union(map_toUFIdx(row,col), map_toUFIdx(row,col+1));
            ufAll.union(map_toUFIdx(row,col), map_toUFIdx(row,col+1));
        }
        if (adjRow > 0 && isOpen(row-1, col)) { // Not on top edge
            ufTop.union(map_toUFIdx(row,col), map_toUFIdx(row-1,col));
            ufAll.union(map_toUFIdx(row,col), map_toUFIdx(row-1,col));
        }
        if (adjRow < n-1 && isOpen(row+1, col)) { // Not on bottom edge
            ufTop.union(map_toUFIdx(row,col), map_toUFIdx(row+1,col));
            ufAll.union(map_toUFIdx(row,col), map_toUFIdx(row+1,col));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }

        return open[row-1][col-1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > n || col < 1 || col > n) {
            throw new IllegalArgumentException();
        }

        return ufTop.find(TOP_IDX) == ufTop.find(map_toUFIdx(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return ufAll.find(TOP_IDX) == ufAll.find(BOTTOM_IDX);
    }

    // test client (optional)
    public static void main(String[] args) {

    }
}
