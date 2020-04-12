import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class SimpleClient {
	public static void main(String[] args) {

		Point[] points = {
				new Point(30000, 0), new Point(20000, 10000), new Point(10000, 20000), new Point(0, 30000), new Point(10, 10),
//				new Point(3, 2), new Point(6, 4), new Point(12, 8),
//				new Point(3, 2), new Point(6, 4), new Point(12, 8),
				new Point(14543, 3593), new Point(2501, 14368), new Point(12, 8),
		};


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
