/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayDeque;

public class Solver {

    private int moves = 0;
    ArrayDeque<Board> solution = new ArrayDeque<Board>();

    private static class Node implements Comparable<Node> {
        private Board board;
        private int currMoves;
        private Node predecessor;
        private int estMoves;

        public Node(Board board, int currMoves, Node predecessor) {
            this.board = board;
            this.currMoves = currMoves;
            this.predecessor = predecessor;
            this.estMoves = currMoves + board.manhattan();
        }

        public int compareTo(Node y) {
            if (y.estMoves == this.estMoves) {
                return 0;
            }
            else if (y.estMoves > this.estMoves) {
                return -1;
            }
            else {
                return 1;
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }

        // Construct initial nodes
        Node initialNode1 = new Node(initial, 0, null);
        Node initialNode2 = new Node(initial.twin(), 0, null);

        MinPQ<Node> heap1 = new MinPQ<Node>();
        MinPQ<Node> heap2 = new MinPQ<Node>();

        heap1.insert(initialNode1);
        heap2.insert(initialNode2);

        while (true) {
            Node currNode1 = heap1.delMin();
            Node currNode2 = heap2.delMin();

            for (Board n1: currNode1.board.neighbors()) {

            }

            for (Board n2: currNode2.board.neighbors()) {

            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return moves == -1;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        return null;
    }

    // test client (see below)
    public static void main(String[] args) {

    }
}
