import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class FastCollinearPoints {

    private final ArrayList<Double> foundSegmentSlopes;
    private final ArrayList<LineSegment> segments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if (points == null)
            throw new IllegalArgumentException();

        for (Point point : points) {
            if (point == null)
                throw new IllegalArgumentException();

        }
        Point[] pointsCopy = Arrays.copyOf(points, points.length);

        Arrays.sort(pointsCopy);
        int length = pointsCopy.length;
        for (int i = 0; i < length - 1; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i + 1]) == 0)
                throw new IllegalArgumentException();
        }

        this.foundSegmentSlopes = new ArrayList<>();
        this.segments = new ArrayList<>();

        for (Point startPoint : pointsCopy) {
            Point[] slopeOrderedPoints = pointsCopy.clone();
            Arrays.sort(slopeOrderedPoints, startPoint.slopeOrder());

            ArrayList<Point> segmentPoints = new ArrayList<>();
            double previousSlope = startPoint.slopeTo(startPoint);

            for (int j = 1; j < slopeOrderedPoints.length; j++) {
                Point currentPoint = slopeOrderedPoints[j];
                double currentSlope = startPoint.slopeTo(currentPoint);

                if (Double.compare(previousSlope, currentSlope) != 0) {
                    if (segmentPoints.size() >= 3) {
                        segmentPoints.add(startPoint);
                        addSegmentIfNotExists(segmentPoints, previousSlope);
                    }
                    segmentPoints.clear();
                }

                segmentPoints.add(currentPoint);
                previousSlope = currentSlope;
            }
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[0]);
    }

    private void addSegmentIfNotExists(ArrayList<Point> slopePoints, double slope) {
        // FIXME change foundSegmentSlopes with different data structure.
        //  (0, 0) -> (1, 1) and (0, 1) -> (1, 2) have same slopes.
        boolean contains = foundSegmentSlopes.contains(slope);
        if (!contains) {
            Collections.sort(slopePoints);
            Point start = slopePoints.get(0);
            Point end = slopePoints.get(slopePoints.size() - 1);
            LineSegment lineSegment = new LineSegment(start, end);
            foundSegmentSlopes.add(slope);
            segments.add(lineSegment);
        }
    }
}

