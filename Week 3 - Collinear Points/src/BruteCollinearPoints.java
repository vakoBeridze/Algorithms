import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private final LineSegment[] segments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {

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

        ArrayList<LineSegment> foundSegments = new ArrayList<>();
        for (int i = 0; i < length - 3; i++) {
            for (int j = i + 1; j < length - 2; j++) {
                for (int k = j + 1; k < length - 1; k++) {
                    for (int m = k + 1; m < length; m++) {
                        Point point1 = pointsCopy[i];
                        Point point2 = pointsCopy[j];
                        Point point3 = pointsCopy[k];
                        Point point4 = pointsCopy[m];
                        if (Double.compare(point1.slopeTo(point2), point1.slopeTo(point3)) == 0 &&
                                Double.compare(point1.slopeTo(point2), point1.slopeTo(point4)) == 0) {
                            foundSegments.add(new LineSegment(point1, point4));
                        }
                    }
                }
            }
        }

        this.segments = foundSegments.toArray(new LineSegment[0]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segments.clone();
    }
}
