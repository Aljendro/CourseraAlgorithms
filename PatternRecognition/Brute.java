public class Brute {

  public static void main(String[] args) {

    if (args.length != 1) return;


    In read = new In(args[0]);
    int size = read.readInt();
    Point[] points = new Point[size];

    for(int i = 0; i < size; i++) {
      points[i] = new Point(read.readInt(), read.readInt());
    }

    // Plot the points from the points array
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    StdDraw.show(0);
    StdDraw.setPenRadius(0.01);  // make the points a bit larger

    for (int i = 0; i < size; i++) {
        points[i].draw();
    }
    // display to screen all at once
    StdDraw.show(0);

    // reset the pen radius
    StdDraw.setPenRadius();


    // Now to brute force this problem
    // for (int first = 0; first < size; first++) {
    //   for (int second = first + 1; second < size; second++) {
    //     for (int third = second + 1; third < size; third++) {
    //       for (int fourth = fourth + 1; fourth < size; fourth++) {
    //         if ()
    //       }
    //     }
    //   }
    // }

  }
}
