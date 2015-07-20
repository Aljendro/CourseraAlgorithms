import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // Sort by Slope Order
    private class SlopeOrder implements Comparator<Point> {
      public int compare(Point first, Point second) {
        double slopeToFirst = Point.this.slopeTo(first);
        double slopeToSecond = Point.this.slopeTo(second);

        if (slopeToFirst < slopeToSecond) return -1;
        if (slopeToFirst > slopeToSecond) return 1;
        return 0;
      }
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        // Check for a vertical line
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        // Check if the points are equal
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        // Check if they the slope is horizontal
        if (this.y == that.y) return 0.0;

        return (this.y - that.y) / (this.x - that.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {

      if (this.y < that.y) return -1;
      if (this.y > that.y) return 1;

      // The y-coordinates are both equal
      // try to break ties with the x-coordinate
      if (this.x < that.x) return -1;
      if (this.x > that.x) return 1;

      // Both the x and the y coordinates are equal
      return 0;
    }

    // return string representation of this point
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
      // TODO: Implement this
    }
}
