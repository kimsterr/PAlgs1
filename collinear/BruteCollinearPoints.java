import java.util.Arrays;
import java.util.Comparator;

/******************************************************************************
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

public class BruteCollinearPoints {
    private int numberOfSegments;
    private LineSegment[] theSegments;

    public BruteCollinearPoints(Point[] points) {
        // Go thru corner cases for input
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        for (int i = 0; i < pointsCopy.length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        if (pointsCopy.length < 4) {
            numberOfSegments = 0;
            theSegments = new LineSegment[0];
            return;
        }

        // Passed corner cases for input
        // Now compile all the segments
        numberOfSegments = 0;
        LineSegment[] segmentsRaw = new LineSegment[1];

        for (int i = 0; i < pointsCopy.length - 3; i++) {
            for (int j = i + 1; j < pointsCopy.length - 2; j++) {
                for (int k = j + 1; k < pointsCopy.length - 1; k++) {
                    for (int l = k + 1; l < pointsCopy.length; l++) {
                        Comparator<Point> ptComparator = pointsCopy[i].slopeOrder();
                        if (ptComparator.compare(pointsCopy[j], pointsCopy[k]) == 0 && ptComparator
                                .compare(pointsCopy[j], pointsCopy[l]) == 0
                                && ptComparator.compare(pointsCopy[k], pointsCopy[l]) == 0) {
                            numberOfSegments++;
                            if (numberOfSegments > segmentsRaw.length) {
                                segmentsRaw = reallocate(segmentsRaw);
                            }
                            segmentsRaw[numberOfSegments-1] = new LineSegment(pointsCopy[i], pointsCopy[l]);
                        }
                    }
                }
            }
        }

        // Formulate the final segments array
        theSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            theSegments[i] = segmentsRaw[i];
        }
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return theSegments.clone();
    }

    private LineSegment[] reallocate(LineSegment[] segmentsRaw) {
        LineSegment[] newSegmentsRaw = new LineSegment[segmentsRaw.length * 2];
        for (int i = 0; i < segmentsRaw.length; i++) {
            newSegmentsRaw[i] = segmentsRaw[i];
        }
        return newSegmentsRaw;
    }
}
