import edu.princeton.cs.algs4.WeightedQuickUnionUF;

class Percolation {

    private boolean[][] open;
    private int openSiteCount;
    private int dimension;
    private int topConnector;
    private int bottomConnector;
    private WeightedQuickUnionUF wquf;

    // create n-by-n open, with all sites blocked
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n cannot be zero or less");

        open = new boolean[n+1][n+1]; // false = full/blocked ; true = open
        openSiteCount = 0;

        dimension = n;

        topConnector = 0;
        bottomConnector = (n * n) + 1;

        // initialise new Weighted Quick Union Union Find object
        wquf = new WeightedQuickUnionUF((n * n) + 2); // +2 to allow for the two connectors
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        int currentSiteWQUFIndex = calcWQUFIndex(row, col);
        // check our row and column are within the grids bounds
        checkIfInBounds(row, col);
        // if not already open, set to open
        if (!open[row][col]) {
            open[row][col] = true;
            openSiteCount++;
        }

        // check if in first row
        if (row == 1)
            wquf.union(currentSiteWQUFIndex, topConnector); // connect to the top connector

        // check if in bottom row
        if (row == dimension)
            wquf.union(currentSiteWQUFIndex, bottomConnector); // connect to the bottom connector

        // check each of the surrounding sites - ensuring not out of bounds

            // check site above
            if ((row > 1) && (isOpen((row - 1), col)))
                wquf.union(currentSiteWQUFIndex, calcWQUFIndex((row - 1), col));

            // check site below
            if ((row < dimension) && (isOpen((row + 1), col)))
                wquf.union(currentSiteWQUFIndex, calcWQUFIndex((row + 1), col));

            // check left hand site
            if ((col > 1) && (isOpen(row, (col - 1))))
                wquf.union(currentSiteWQUFIndex, calcWQUFIndex(row, (col - 1)));

            // check right hand site
            if ((col < dimension) && (isOpen(row, (col + 1))))
                wquf.union(currentSiteWQUFIndex, calcWQUFIndex(row, (col + 1)));

    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkIfInBounds(row, col);
        return open[row][col];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkIfInBounds(row, col);
        return wquf.connected(topConnector, calcWQUFIndex(row, col));
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSiteCount;
    }

    // does the system percolate?
    public boolean percolates() {
        return wquf.connected(topConnector, bottomConnector);
    }

    private void checkIfInBounds(int row, int col) {
        if (row <= 0 || row > dimension) throw new IndexOutOfBoundsException("Row '" + row + "' index out of bounds");
        if (col <= 0 || col > dimension) throw new IndexOutOfBoundsException("Column '" + col + "' index out of bounds");
    }

    private int calcWQUFIndex(int row, int col) {
        return (dimension * (row - 1)) + col; // as we store a row as successive elements
    }

}