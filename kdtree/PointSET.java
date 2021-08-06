/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

import java.util.ArrayDeque;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> setOfPoints;

    // construct an empty set of points
    public PointSET() {
        setOfPoints = new TreeSet<Point2D>();
    }

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    // is the set empty?
    public boolean isEmpty() {
        return setOfPoints.size() == 0;
    }

    // number of points in the set
    public int size() {
        return setOfPoints.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        setOfPoints.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return setOfPoints.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : setOfPoints) {
            p.draw();
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new java.lang.IllegalArgumentException();

        ArrayDeque<Point2D> insideRect = new ArrayDeque<Point2D>();

        for (Point2D p : setOfPoints) {
            if (p.x() >= rect.xmin() && p.x() <= rect.xmax() && p.y() >= rect.ymin()
                    && p.y() <= rect.ymax()) {
                insideRect.add(p);
            }
        }

        return insideRect;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        if (setOfPoints.isEmpty()) return null;

        Point2D currentChamp = null;
        double currentBestDist = Double.POSITIVE_INFINITY;

        for (Point2D other : setOfPoints) {
            double currDist = p.distanceSquaredTo(other);
            if (currDist < currentBestDist) {
                currentChamp = other;
                currentBestDist = currDist;
            }
        }

        return currentChamp;
    }
}