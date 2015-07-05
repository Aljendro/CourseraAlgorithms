public class Percolation {

  private boolean[][] grid;
  private UF unionFind;
  private int GRID_SIZE;
  private int UF_BOTTOM;
  private int UF_TOP;

  public Percolation(int N) {

    if (N < 0) throw new IllegalArgumentException();

    // Intialize a Union Find with 2 extra spots
    // for a virtual top and virtual bottom
    // Nth element of unionFind will be the bottom
    // Nth + 1 element of unionFind will be the top
    unionFind = new UF(N * N + 2);
    UF_BOTTOM = N * N;
    UF_TOP = (N * N) + 1;

    // Initialize a grid where false
    // shows the site is blocked and true
    // shows the sight is open.
    grid = new boolean[N + 1][N + 1];
    GRID_SIZE = N;

    // Initialize grid array.
    // false signifies that the item is blocked
    for (int i = 1; i <= N; i++) {
      for (int j = 1; j <= N; j++) {
        grid[i][j] = false;
      }
    }
  }

  // open site (row i, column j) if it is not open already
  public void open(int i, int j)  {

    checkBounds(i, j);

    // Check if its already open
    if (isOpen(i, j)) {
      return;
    }

    // Change the value to true to open the blocked site
    grid[i][j] = true;

    // Connect the surround open elements to this site

    // Any open site in the top row will be connected to
    // the virtual top site
    if (i == 1) {
      unionFind.union(convert2DimTo1DimArrayIndices(i, j), UF_TOP);
      if (GRID_SIZE == 1) {
        unionFind.union(convert2DimTo1DimArrayIndices(i, j), UF_BOTTOM);
      } else {
        bottomUnion(i, j);
      }
    }
    // Any open site in the bottom row will not be connected to virtual
    // bottom until it
    else if (i == GRID_SIZE) {
      unionFind.union(convert2DimTo1DimArrayIndices(i, j), UF_BOTTOM);
      if (j != 1) leftUnion(i, j);
      if (j != GRID_SIZE) rightUnion(i, j);
      topUnion(i, j);
    }
    // Union the left column
    else if (j == 1) {
      rightUnion(i, j);
      topUnion(i, j);
      bottomUnion(i, j);
    }
    // Union the right column
    else if (j == GRID_SIZE) {
      leftUnion(i, j);
      topUnion(i, j);
      bottomUnion(i, j);
    }
    // Union everything else
    else {
      leftUnion(i, j);
      rightUnion(i, j);
      topUnion(i, j);
      bottomUnion(i, j);
    }

  }

  private int convert2DimTo1DimArrayIndices(int i, int j) {
    return (i * GRID_SIZE + j) - GRID_SIZE - 1;
  }

  // is site (row i, column j) open?
  public boolean isOpen(int i, int j) {

    checkBounds(i, j);

    return grid[i][j];
  }

  // is site (row i, column j) full?
  public boolean isFull(int i, int j)  {

    checkBounds(i, j);

    return unionFind.connected(convert2DimTo1DimArrayIndices(i, j), UF_TOP);
  }

  private boolean isEmpty(int i, int j) {

    checkBounds(i, j);

    return unionFind.connected(convert2DimTo1DimArrayIndices(i, j), UF_BOTTOM);
  }

  // does the system percolate?
  public boolean percolates()  {

    // Are the virtual TOP and BOTTOM sites connected?
    return unionFind.connected(UF_TOP, UF_BOTTOM);
  }

  private void checkBounds(int i, int j) {

    if (i < 1 || i > GRID_SIZE) {
      throw new IndexOutOfBoundsException();
    } else if (j < 1 || j > GRID_SIZE) {
      throw new IndexOutOfBoundsException();
    }
  }

  // The following Unions will use the isOpen call,
  // requires that its arguments be:
  // i: 1 to N
  // j: 1 to N
  private void rightUnion(int i , int j) {

    // Check the right side
    if (isOpen(i , j + 1)) {
      unionFind.union(convert2DimTo1DimArrayIndices(i, j),
                      convert2DimTo1DimArrayIndices(i, j + 1));
    }
  }

  private void leftUnion(int i, int j) {

    // Check the left side
    if (isOpen(i, j - 1)) {
      unionFind.union(convert2DimTo1DimArrayIndices(i, j),
                      convert2DimTo1DimArrayIndices(i, j - 1));
    }
  }

  private void topUnion(int i, int j) {

    // Check the top of the site
    if (isOpen(i - 1, j)) {
      unionFind.union(convert2DimTo1DimArrayIndices(i, j),
                      convert2DimTo1DimArrayIndices(i - 1, j));

      // if (isFull(i - 1 , j) && i == GRID_SIZE) {
      //   unionFind.union(convert2DimTo1DimArrayIndices(i, j), UF_BOTTOM);
      // }
    }
  }

  private void bottomUnion(int i, int j) {

    // check the bottom of the site
    if (isOpen(i + 1, j)) {
      unionFind.union(convert2DimTo1DimArrayIndices(i, j),
                      convert2DimTo1DimArrayIndices(i + 1, j));

      // if (isFull(i, j) && i + 1 == GRID_SIZE) {
      //   unionFind.union(convert2DimTo1DimArrayIndices(i + 1, j), UF_BOTTOM);
      // }
    }
  }

  public static void main(String[] args) {
      System.out.println("Hello, World!");
  }
}
