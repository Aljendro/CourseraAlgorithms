public class PercolationStats {

  private Percolation perc;
  private Site[] sites;
  private int GRID_SIZE;
  private int TEST_SIZE;
  private double[] openSites;

  private class Site {

    private int x;
    private int y;

    public Site(int i, int j) {
      x = i;
      y = j;
    }

    public int getX() {
      return x;
    }
    public int getY() {
      return y;
    }
  }

  public PercolationStats(int N, int T) {

    if (N < 0 || T < 0) throw new IllegalArgumentException();

    sites = new Site[N * N];
    openSites = new double[T];

    GRID_SIZE = N;
    TEST_SIZE = T;

    for( int i = 0; i < N; i++) {
      for( int j = 0; j < N; j++) {
        sites[i * N + j] = new Site(i + 1, j + 1);
      }
    }
  }

  private void runStats() {

    int site = 0;
    int openSiteCount = 0;
    int TOTAL_GRIDS = GRID_SIZE * GRID_SIZE;

    // Run T amount of tests
    for (int i = 0; i < TEST_SIZE; i++) {
      StdRandom.shuffle(sites);

      perc = new Percolation(GRID_SIZE);

      site = 0;
      openSiteCount = 0;

      while ( !perc.percolates() && site < TOTAL_GRIDS) {

        perc.open(sites[site].getX(), sites[site].getY());
        site += 1;
        openSiteCount += 1;
      }

      openSites[i] = (double) openSiteCount / TOTAL_GRIDS;
    }
  }

  public double mean() {
    return StdStats.mean(openSites);
  }

  public double stddev() {
    return StdStats.stddev(openSites);
  }

  public double confidenceLo() {
    return ( StdStats.mean(openSites) - ((1.96 * StdStats.stddev(openSites)) / Math.sqrt((double) TEST_SIZE)));
  }

  public double confidenceHi() {

    return ( StdStats.mean(openSites) + ((1.96 * StdStats.stddev(openSites)) / Math.sqrt((double) TEST_SIZE)));
  }

  public static void main(String[] args) {

    PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

    stats.runStats();

    System.out.println("mean                    = " + stats.mean());
    System.out.println("stddev                  = " + stats.stddev());
    System.out.println("95% confidence interval = " +
                      stats.confidenceLo() + ", " + stats.confidenceHi());
  }

}
