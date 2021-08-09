/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayDeque;

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
    public KdTree() {
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

        ArrayDeque<Point2D> points = new ArrayDeque<Point2D>();
        range(rect, points, root, true, new double[] { 0.0, 0.0, 1.0, 1.0 }); // full search area

        return points;
    }

    private void range(RectHV rect, ArrayDeque<Point2D> points, Node currNode, boolean useX,
                       double[] searchArea) {
        if (currNode == null) {
            return;
        }
        if (isInside(currNode.point, rect)) {
            points.addLast(currNode.point);
        }
        if (useX) {
            double currentX = currNode.point.x(); // Split L/R on vertical line thru here
            RectHV leftRect = new RectHV(searchArea[0], searchArea[1], currentX, searchArea[3]);
            RectHV rightRect = new RectHV(currentX, searchArea[1], searchArea[2], searchArea[3]);

            // only explore if have intersection
            if (rect.intersects(leftRect)) {
                range(rect, points, currNode.left, false, new double[] {
                        leftRect.xmin(), leftRect.ymin(), leftRect.xmax(), leftRect.ymax()
                });
            }
            if (rect.intersects(rightRect)) {
                range(rect, points, currNode.right, false, new double[] {
                        rightRect.xmin(), rightRect.ymin(), rightRect.xmax(), rightRect.ymax()
                });
            }
        }
        else {
            double currentY = currNode.point.y(); // Split B/T on horizontal line thru here
            RectHV bottomRect = new RectHV(searchArea[0], searchArea[1], searchArea[2], currentY);
            RectHV topRect = new RectHV(searchArea[0], currentY, searchArea[2], searchArea[3]);

            // only explore if have intersection
            if (rect.intersects(bottomRect)) {
                range(rect, points, currNode.left, true, new double[] {
                        bottomRect.xmin(), bottomRect.ymin(), bottomRect.xmax(), bottomRect.ymax()
                });
            }
            if (rect.intersects(topRect)) {
                range(rect, points, currNode.right, true, new double[] {
                        topRect.xmin(), topRect.ymin(), topRect.xmax(), topRect.ymax()
                });
            }
        }
    }

    private boolean isInside(Point2D p, RectHV rect) {
        return (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin()
                && p.y() <= rect.ymax());
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return nearest(p, root, true, new double[] { 0.0, 0.0, 1.0, 1.0 }, null);
    }

    private Point2D nearest(Point2D p, Node currNode, boolean useX, double[] searchArea,
                            Point2D champPoint) {
        if (currNode == null) return champPoint;

        if (p.equals(currNode.point)) {
            champPoint = currNode.point;
            return champPoint;
        }

        // Update champ point, if warranted
        if (champPoint == null || p.distanceSquaredTo(currNode.point) < p.distanceSquaredTo(champPoint)) {
            champPoint = currNode.point;
        }

        Point2D candidate;

        if (useX) {
            // Establish the regions
            double currentX = currNode.point.x(); // Split L/R on vertical line thru here
            RectHV leftRect = new RectHV(searchArea[0], searchArea[1], currentX, searchArea[3]);
            RectHV rightRect = new RectHV(currentX, searchArea[1], searchArea[2], searchArea[3]);

            // Figure out which region (L or R) to explore first
            boolean leftFirst = (p.x() <= currNode.point.x());

            if (leftFirst) {
                if (champPoint == null || champPoint.distanceSquaredTo(p) > leftRect.distanceSquaredTo(p)) {
                    candidate = nearest(p, currNode.left, false, new double[]{leftRect.xmin(), leftRect.ymin(), leftRect.xmax(), leftRect.ymax()}, champPoint);
                    if (p.distanceSquaredTo(candidate) < p.distanceSquaredTo(champPoint)) {
                        champPoint = candidate;
                    }
                }
                if (champPoint == null || champPoint.distanceSquaredTo(p) > rightRect.distanceSquaredTo(p)) {
                    candidate = nearest(p, currNode.right, false, new double[]{rightRect.xmin(), rightRect.ymin(), rightRect.xmax(), rightRect.ymax()}, champPoint);
                    if (p.distanceSquaredTo(candidate) < p.distanceSquaredTo(champPoint)) {
                        champPoint = candidate;
                    }
                }
            }
            else { // Identical to above except rightFirst
                if (champPoint == null || champPoint.distanceSquaredTo(p) > rightRect.distanceSquaredTo(p)) {
                    candidate = nearest(p, currNode.right, false, new double[]{rightRect.xmin(), rightRect.ymin(), rightRect.xmax(), rightRect.ymax()}, champPoint);
                    if (p.distanceSquaredTo(candidate) < p.distanceSquaredTo(champPoint)) {
                        champPoint = candidate;
                    }
                }
                if (champPoint == null || champPoint.distanceSquaredTo(p) > leftRect.distanceSquaredTo(p)) {
                    candidate = nearest(p, currNode.left, false, new double[]{leftRect.xmin(), leftRect.ymin(), leftRect.xmax(), leftRect.ymax()}, champPoint);
                    if (p.distanceSquaredTo(candidate) < p.distanceSquaredTo(champPoint)) {
                        champPoint = candidate;
                    }
                }
            }
        }
        // USE Y coordinate for splitting instead
        else {
            // Establish the regions
            double currentY = currNode.point.y(); // Split B/T on horizontal line thru here
            RectHV bottomRect = new RectHV(searchArea[0], searchArea[1], searchArea[2], currentY);
            RectHV topRect = new RectHV(searchArea[0], currentY, searchArea[2], searchArea[3]);

            // Figure out which region (T or B) to explore first
            boolean bottomFirst = (p.y() <= currNode.point.y());

            if (bottomFirst) {
                if (champPoint == null || champPoint.distanceSquaredTo(p) > bottomRect.distanceSquaredTo(p)) {
                    candidate = nearest(p, currNode.left, true, new double[]{bottomRect.xmin(), bottomRect.ymin(), bottomRect.xmax(), bottomRect.ymax()}, champPoint);
                    if (p.distanceSquaredTo(candidate) < p.distanceSquaredTo(champPoint)) {
                        champPoint = candidate;
                    }
                }
                if (champPoint == null || champPoint.distanceSquaredTo(p) > topRect.distanceSquaredTo(p)) {
                    candidate = nearest(p, currNode.right, true, new double[]{topRect.xmin(), topRect.ymin(), topRect.xmax(), topRect.ymax()}, champPoint);
                    if (p.distanceSquaredTo(candidate) < p.distanceSquaredTo(champPoint)) {
                        champPoint = candidate;
                    }
                }
            }
            else { // Identical to above except topFirst
                if (champPoint == null || champPoint.distanceSquaredTo(p) > topRect.distanceSquaredTo(p)) {
                    candidate = nearest(p, currNode.right, true, new double[]{topRect.xmin(), topRect.ymin(), topRect.xmax(), topRect.ymax()}, champPoint);
                    if (p.distanceSquaredTo(candidate) < p.distanceSquaredTo(champPoint)) {
                        champPoint = candidate;
                    }
                }
                if (champPoint == null || champPoint.distanceSquaredTo(p) > bottomRect.distanceSquaredTo(p)) {
                    candidate = nearest(p, currNode.left, true, new double[]{bottomRect.xmin(), bottomRect.ymin(), bottomRect.xmax(), bottomRect.ymax()}, champPoint);
                    if (p.distanceSquaredTo(candidate) < p.distanceSquaredTo(champPoint)) {
                        champPoint = candidate;
                    }
                }
            }
        }

        return champPoint;
    }

    public static void main(String[] args) {
        System.out.println("Goodbye world!");
    }
}