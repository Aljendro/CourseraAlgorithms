public class Percolation {

  private boolean[][] grid;
  private UF unionFind;
  private int GRID_SIZE;
  private int UF_BOTTOM;
  private int UF_TOP;

  public Percolation( int N ) {

    if ( N < 0 ) throw new IllegalArgumentException();

    // Intialize a Union Find with 2 extra spots
    // for a virtual top and virtual bottom
    // Nth element of unionFind will be the bottom
    // Nth + 1 element of unionFind will be the top
    unionFind = new UF( N * N + 2 );
    UF_BOTTOM = N * N;
    UF_TOP = ( N * N ) + 1;

    // Initialize a grid where false
    // shows the site is blocked and true
    // shows the sight is open.
    grid = new boolean[N][N];
    GRID_SIZE = N;

    // Initialize grid array.
    // false signifies that the item is blocked
    for ( int i = 0; i < N; i++ ) {
      for ( int j = 0; j < N; j++ ) {
        grid[i][j] = false;
      }
    }
  }

  // open site (row i, column j) if it is not open already
  public void open( int i, int j )  {

    int row = i - 1;
    int col = j - 1;

    checkBounds( row, col );

    // Check if its already open
    if ( isOpen( i, j ) ) {
      return;
    }

    // Change the value to true to open the blocked site
    grid[row][col] = true;

    // Connect the surround open elements to this site

    // Any open site in the top row will be connected to
    // the virtual top site
    if ( row == 0 ) {
      unionFind.union( convert2DimTo1DimArrayIndices( row, col ), UF_TOP );
      bottomUnion( row, col );
    }
    // Any open site in the bottom row will be connected to the
    // virtual bottom site
    else if ( row == GRID_SIZE - 1 ) {
      unionFind.union( convert2DimTo1DimArrayIndices( row, col ), UF_BOTTOM );
      topUnion( row, col );
    }
    // Union the left column
    else if ( col == 0 ) {
      rightUnion( row, col );
      topUnion( row, col );
      bottomUnion( row, col );
    }
    // Union the right column
    else if ( col == GRID_SIZE - 1 ) {
      leftUnion( row, col );
      topUnion( row, col );
      bottomUnion( row, col );
    }
    // Union everything else
    else {
      leftUnion( row, col );
      rightUnion( row, col );
      topUnion( row, col );
      bottomUnion( row, col );
    }

  }

  private int convert2DimTo1DimArrayIndices( int row, int col ) {
    return row * GRID_SIZE + col;
  }

  // is site (row i, column j) open?
  public boolean isOpen( int i, int j ) {

    int row = i - 1;
    int col = j - 1;

    checkBounds( row, col );

    return grid[row][col];
  }

  // is site (row i, column j) full?
  public boolean isFull( int i, int j )  {

    int row = i - 1;
    int col = j - 1;

    checkBounds( row, col );

    return unionFind.connected( convert2DimTo1DimArrayIndices( row, col ), UF_TOP);
  }

  // does the system percolate?
  public boolean percolates()  {

    // Are the virtual TOP and BOTTOM sites connected?
    return unionFind.connected( UF_TOP, UF_BOTTOM );
  }

  private void checkBounds( int row, int col ) {

    if ( row < 0 || row >= GRID_SIZE  ) {
      throw new IndexOutOfBoundsException();
    } else if ( col < 0 || col >= GRID_SIZE ) {
      throw new IndexOutOfBoundsException();
    }
  }

  // The following Unions will use the isOpen call,
  // requires that its arguments be:
  // i: 1 to N
  // j: 1 to N
  private void rightUnion( int row , int col ) {

    // Check the right side
    if ( isOpen( row + 1, ( col + 1 ) + 1 ) ) {
      unionFind.union( convert2DimTo1DimArrayIndices( row, col ), convert2DimTo1DimArrayIndices( row, col + 1 ) );
    }
  }

  private void leftUnion( int row, int col ) {

    // Check the left side
    if ( isOpen( row + 1 , ( col + 1 ) - 1 ) ) {
      unionFind.union( convert2DimTo1DimArrayIndices( row, col ), convert2DimTo1DimArrayIndices( row, col - 1 ) );
    }
  }

  private void topUnion( int row, int col ) {

    // Check the top of the site
    if ( isOpen( ( row + 1 ) - 1, col + 1 ) ) {
      unionFind.union( convert2DimTo1DimArrayIndices( row, col ), convert2DimTo1DimArrayIndices( row - 1, col ) );
    }
  }

  private void bottomUnion( int row, int col ) {

    // check the bottom of the site
    if ( isOpen( ( row + 1) + 1, col + 1 ) ) {
      unionFind.union( convert2DimTo1DimArrayIndices( row, col ), convert2DimTo1DimArrayIndices( row + 1, col ) );
    }
  }


  public static void main(String[] args) {
      System.out.println("Hello, World!");
  }
}
