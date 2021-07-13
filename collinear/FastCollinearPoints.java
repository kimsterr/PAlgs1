import java.util.Arrays;
import java.util.Comparator;

/******************************************************************************
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

public class FastCollinearPoints {
    private int numberOfSegments;
    private LineSegment[] theSegments;

    public FastCollinearPoints(Point[] points) {
        // Go thru corner cases for input
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException();
            }
        }

        // Check for duplicates
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
        Point[] pointRanker = pointsCopy.clone();

        // IMPORTANT:  pointsCopy contains the "ground truth" ordering

        for (int i = 0; i < pointsCopy.length; i++) {
            Point p = pointsCopy[i]; // Current "origin point" forming start of line segment
            Comparator<Point> myComparator = p.slopeOrder();
            Arrays.sort(pointRanker, myComparator);

            int currStreak = 0;
            Point currPointInStreak = p;
            Point minPointInStreak = p;
            Point maxPointInStreak = p;

            for (int j = 0; j < pointRanker.length; j++) {
                Point pCandidate = pointRanker[j];

                // Deal with encountering "origin point"
                if (pCandidate.compareTo(p) == 0) {
                    continue;
                }

                // If we don't have a streak going, take any point as starting the streak
                else if (currStreak == 0) {
                    currStreak++;
                    currPointInStreak = pCandidate;

                    if (currPointInStreak.compareTo(minPointInStreak) < 0) {
                        minPointInStreak = currPointInStreak;
                    }
                    else if (currPointInStreak.compareTo(maxPointInStreak) > 0) {
                        maxPointInStreak = currPointInStreak;
                    }
                }

                // If we have a streak going and the slope matches what is in the streak...
                else if (myComparator.compare(pCandidate, currPointInStreak) == 0) {
                    if (pCandidate.compareTo(currPointInStreak) != 0) {
                        currStreak++;
                        currPointInStreak = pCandidate;
                    }

                    if (currPointInStreak.compareTo(minPointInStreak) < 0) {
                        minPointInStreak = currPointInStreak;
                    }
                    else if (currPointInStreak.compareTo(maxPointInStreak) > 0) {
                        maxPointInStreak = currPointInStreak;
                    }
                }

                else { // We had a streak going and it was broken...
                    // Only add the segment if it has enough points in it...AND it has never been seen before
                    if (currStreak >= 3 && p.compareTo(minPointInStreak) == 0) {
                        numberOfSegments++;
                        if (numberOfSegments > segmentsRaw.length) {
                            segmentsRaw = reallocate(segmentsRaw);
                        }
                        segmentsRaw[numberOfSegments-1] = new LineSegment(minPointInStreak, maxPointInStreak);
                    }
                    // Perform the streak reset
                    currStreak = 1;
                    currPointInStreak = pCandidate;

                    if (currPointInStreak.compareTo(p) < 0) {
                        minPointInStreak = currPointInStreak;
                        maxPointInStreak = p;
                    }
                    else {
                        minPointInStreak = p;
                        maxPointInStreak = currPointInStreak;
                    }
                }
            } // End of for loop for fixed "origin point"

            // Exited the for loop for a fixed "origin point" with an unbroken streak
            if (currStreak >= 3 && p.compareTo(minPointInStreak) == 0) {
                numberOfSegments++;
                if (numberOfSegments > segmentsRaw.length) {
                    segmentsRaw = reallocate(segmentsRaw);
                }
                segmentsRaw[numberOfSegments-1] = new LineSegment(minPointInStreak, maxPointInStreak);
            }

            // Proceed to the next fixed "origin point" in the next loop iteration
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
