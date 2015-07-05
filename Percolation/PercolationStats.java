public class PercolationStats {

  private int TEST_SIZE;
  private double[] openSites;

  public PercolationStats(int N, int T) {

    if (N <= 0 || T <= 0) throw new IllegalArgumentException();

    openSites = new double[T];

    TEST_SIZE = T; 

    int site = 0;
    int openSiteCount = 0;
    int TOTAL_GRIDS = N * N;
    Percolation tests = null;

    // Run T amount of tests
    for (int i = 0; i < T; i++) {
      site = 0;
      openSiteCount = 0;

      tests = new Percolation(N);

      while (!tests.percolates() && site < TOTAL_GRIDS) {

        int x = StdRandom.uniform(1, N + 1);
        int y = StdRandom.uniform(1, N + 1);

        if (!tests.isOpen(x, y)) {
          site += 1;
          tests.open(x, y);
          openSiteCount += 1;
        }
      }

      openSites[i] = (double) openSiteCount / TOTAL_GRIDS;
      tests = null;
    }

  }

  public double mean() {
    return StdStats.mean(openSites);
  }

  public double stddev() {
    return StdStats.stddev(openSites);
  }

  public double confidenceLo() {
    return (StdStats.mean(openSites)
      - ((1.96 * StdStats.stddev(openSites)) / Math.sqrt((double) TEST_SIZE)));
  }

  public double confidenceHi() {

    return (StdStats.mean(openSites)
      + ((1.96 * StdStats.stddev(openSites)) / Math.sqrt((double) TEST_SIZE)));
  }

  public static void main(String[] args) {

    PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]),
                                                  Integer.parseInt(args[1]));

    System.out.println("mean                    = " + stats.mean());
    System.out.println("stddev                  = " + stats.stddev());
    System.out.println("95% confidence interval = "
                        + stats.confidenceLo() + ", " + stats.confidenceHi());
  }

}
