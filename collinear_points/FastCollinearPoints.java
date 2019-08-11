package collinear_points;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FastCollinearPoints {

    final private LineSegment[] lineSegments;

    public FastCollinearPoints(Point[] points) {
        Point[] sortedPoints;
        final int numberOfPoints = points.length;
        List<Point> pointsWithSameSlope;
        List<LineSegment> maxLineSegments = new ArrayList<>();
        int currentPointIndex;
        double slopeToCompareWith;
        Point currentPoint, maxPoint;
        Point[] pointsSortedBySlope;

        checkForNullPoints(points);
        // sort points array
        sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        // now sorted, check for duplicates
        checkForDuplicatePoints(sortedPoints);

        // for each point: determine slope all other points make with it, sort by this slope
        for (int pointIndex = 0; pointIndex < numberOfPoints; pointIndex++) {
            currentPoint = sortedPoints[pointIndex];
            pointsSortedBySlope = sortedPoints.clone();
            // sort each point by the slope they make with current point
            Arrays.sort(pointsSortedBySlope, currentPoint.slopeOrder());

            // check if any 3 (or more) adjacent points in the sorted order have equal slopes with respect to p
            // if so, these points, together with p, are collinear
            currentPointIndex = 1; // start at index 1 as index 0 is position of current point
            while (currentPointIndex < numberOfPoints) {
                pointsWithSameSlope = new ArrayList<>();
                slopeToCompareWith = currentPoint.slopeTo(pointsSortedBySlope[currentPointIndex]);
                do {
                    pointsWithSameSlope.add(pointsSortedBySlope[currentPointIndex]);
                    currentPointIndex++;
                } while ((currentPointIndex < numberOfPoints) &&
                        (currentPoint.slopeTo(pointsSortedBySlope[currentPointIndex]) == slopeToCompareWith));

                // collinear => least 3 points without current point on same line
                // max line segment created using current point and last point added
                if ((pointsWithSameSlope.size() >= 3) && (currentPoint.compareTo(pointsWithSameSlope.get(0)) < 0)) {
                    maxPoint = pointsWithSameSlope.get(pointsWithSameSlope.size() - 1); // get point added last (largest)
                    maxLineSegments.add(new LineSegment(currentPoint, maxPoint));
                }
            }
        }

        lineSegments = maxLineSegments.toArray(new LineSegment[maxLineSegments.size()]);
    }

    public int numberOfSegments() { // the number of line segments
        return lineSegments.length;
    }

    public LineSegment[] segments() { // the line segments
        return lineSegments.clone();
    }

    private void checkForDuplicatePoints(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) // as list is sorted we only have to check the next point in the array
            if (points[i].compareTo(points[i + 1]) == 0)
                throw new IllegalArgumentException("duplicate point(s) found in points array");
    }

    private void checkForNullPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("points array is null");
        // loop through each point in the array looking for null elements
        for (Point point : points)
            if (point == null)
                throw new IllegalArgumentException("points array contains a null element");
    }

    // test client provided with
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}