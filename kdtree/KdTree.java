/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {

    private class Node {
        private Point2D point;
        private Node left;
        private Node right;
        private int size;

        public Node(double x, double y, Node left, Node right, int size) {
            this.point = new Point2D(x, y);
            this.left = left;
            this.right = right;
            this.size = size;
        }
    }

    private Node root;

    // construct an empty set of points
    public KdTree()  {
        root = null;
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        if (isEmpty()) {
            return 0;
        }
        else {
            return root.size;
        }
    }

    private int size(Node nd) {
        if (nd == null) return 0;
        return nd.size;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        root = insert(p, root, true);
    }
    //  public Node(double x, double y, Node left, Node right, int size) {
    private Node insert(Point2D p, Node nd, boolean useX) {
        if (nd == null) {
            Node newNd = new Node(p.x(), p.y(), null, null, 1);
            return newNd;
        }
        else if (p.equals(nd.point)) { // What if equal Node already exists in the tree?
            return nd;
        }
        else if (useX) {
            if (p.x() <= nd.point.x()) {
                nd.left = insert(p, nd.left, false);
            }
            else {
                assert p.x() > nd.point.x();
                nd.right = insert(p, nd.right, false);
            }
        }
        else { // use y coordinate
            if (p.y() <= nd.point.y()) {
                nd.left = insert(p, nd.left, true);
            }
            else {
                assert p.y() > nd.point.y();
                nd.right = insert(p, nd.right, true);
            }
        }
        // Update the size of the current node
        nd.size = 1 + size(nd.left) + size(nd.right);
        return nd;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return contains(p, root, true);
    }
    private boolean contains(Point2D p, Node nd, boolean useX) {
        if (nd == null) {
            return false;
        }
        else if (p.equals(nd.point)) {
            return true;
        }
        else if (useX) {
            if (p.x() <= nd.point.x()) {
                return contains(p, nd.left, false);
            }
            else {
                assert p.x() > nd.point.x();
                return contains(p, nd.right, false);
            }
        }
        else { // use y coordinate
            if (p.y() <= nd.point.y()) {
                return contains(p, nd.left, true);
            }
            else {
                assert p.y() > nd.point.y();
                return contains(p, nd.right, true);
            }
        }
    }

    // draw all points to standard draw
    public void draw() {

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();
        return null;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return null;
    }

    public static void main(String[] args) {

    }
}