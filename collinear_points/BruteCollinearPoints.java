package collinear_points;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BruteCollinearPoints {

    final private LineSegment[] lineSegments;

    public BruteCollinearPoints(Point[] points) { // finds all line segments containing 4 points
        Point[] sortedPoints;
        final int numberOfPoints = points.length;
        List<LineSegment> fourPointLineSegments = new ArrayList<>();
        Point pointA, pointB, pointC, pointD;
        double slopeAB, slopeBC, slopeCD;

        checkForNullPoints(points);
        // sort points array
        sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        // now sorted, check for duplicates
        checkForDuplicatePoints(sortedPoints);

        // check for segments with 4 points
        for (int pointAIndex = 0; pointAIndex < numberOfPoints - 3; pointAIndex++) {
            pointA = sortedPoints[pointAIndex];
            for (int pointBIndex = pointAIndex + 1; pointBIndex < numberOfPoints - 2; pointBIndex++) {
                pointB = sortedPoints[pointBIndex];
                slopeAB = pointA.slopeTo(pointB);

                for (int pointCIndex = pointBIndex + 1; pointCIndex < numberOfPoints - 1; pointCIndex++) {
                    pointC = sortedPoints[pointCIndex];
                    slopeBC = pointB.slopeTo(pointC);

                    if (slopeAB == slopeBC) { // A B C all on same line segment
                        for (int pointDIndex = pointCIndex + 1; pointDIndex < numberOfPoints; pointDIndex++) {
                            pointD = sortedPoints[pointDIndex];
                            slopeCD = pointC.slopeTo(pointD);
                            if (slopeBC == slopeCD) // A B C D all on same line segment
                                fourPointLineSegments.add(new LineSegment(pointA, pointD));
                        }
                    }
                }
            }
        }

        lineSegments = fourPointLineSegments.toArray(new LineSegment[fourPointLineSegments.size()]);
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

}