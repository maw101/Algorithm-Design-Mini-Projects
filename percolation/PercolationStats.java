package percolation;

import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdRandom;

class PercolationStats {

    private final int totalTrials;
    private final double[] fractionOpenSites;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        Percolation percolation;

        totalTrials = trials;
        fractionOpenSites = new double[totalTrials];

        if (n <= 0) throw new IllegalArgumentException("n cannot be zero or less");
        if (trials <= 0) throw new IllegalArgumentException("trials cannot be zero or less");

        for (int i = 0; i < totalTrials; i++) {
            percolation = new Percolation(n);
            while (!percolation.percolates()) {
                // add new random site
                percolation.open(StdRandom.uniform(1, (n + 1)), StdRandom.uniform(1, (n + 1)));
            }
            fractionOpenSites[i] = (double) percolation.numberOfOpenSites() / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractionOpenSites);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractionOpenSites);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - ((1.96 * stddev()) / Math.sqrt(totalTrials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + ((1.96 * stddev()) / Math.sqrt(totalTrials));
    }

    // test client
    public static void main(String[] args) {
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        System.out.println("mean = " + stats.mean());
        System.out.println("stddev = " + stats.stddev());
        System.out.println(
                "95% confidence interval = " +
                        stats.confidenceLo() +
                        ", " +
                        stats.confidenceHi()
        );
    }

}