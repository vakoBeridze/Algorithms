import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private int trials;
	private double[] thresholds;

	// perform independent trials on an n-by-n grid
	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0) {
			throw new IllegalArgumentException();
		}
		this.trials = trials;
		thresholds = new double[trials];
		for (int i = 0; i < trials; i++) {
			Percolation percolation = new Percolation(n);
			while (!percolation.percolates()) {
				int row, col;
				do {
					row = StdRandom.uniform(n) + 1;
					col = StdRandom.uniform(n) + 1;
				} while (percolation.isOpen(row, col));
				percolation.open(row, col);
			}
			thresholds[i] = percolation.numberOfOpenSites() / (1.0 * n * n);
		}
	}

	// test client (see below)
	public static void main(String[] args) {
		int n = Integer.parseInt(args[0]);
		int trails = Integer.parseInt(args[1]);

		PercolationStats percolationStats = new PercolationStats(n, trails);
		System.out.println("mean                    = " + percolationStats.mean());
		System.out.println("stddev                  = " + percolationStats.stddev());
		System.out.println("95% confidence interval = [" + percolationStats.confidenceLo() + ", " + percolationStats.confidenceHi() + "]");
	}

	// sample mean of percolation threshold
	public double mean() {
		return StdStats.mean(thresholds);
	}

	// sample standard deviation of percolation threshold
	public double stddev() {
		return StdStats.stddev(thresholds);
	}

	// low endpoint of 95% confidence interval
	public double confidenceLo() {
		return mean() - 1.96 * stddev() / Math.sqrt(trials);
	}

	// high endpoint of 95% confidence interval
	public double confidenceHi() {
		return mean() + 1.96 * stddev() / Math.sqrt(trials);
	}

}