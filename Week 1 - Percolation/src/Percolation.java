import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

	private int size;
	private int openCount;
	private boolean[][] grid;
	private WeightedQuickUnionUF uf;

	// creates n-by-n grid, with all sites initially blocked
	public Percolation(int n) {
		if (n <= 0) {
			throw new IllegalArgumentException();
		}
		size = n;
		grid = new boolean[n][n];
		uf = new WeightedQuickUnionUF(n * n + 2);
	}

	// test client (optional)
	public static void main(String[] args) {

		Percolation percolation = new Percolation(4);
		percolation.print();
		percolation.open(1, 2);
		percolation.open(2, 2);
		percolation.open(3, 2);
		percolation.open(3, 3);
		percolation.open(3, 4);
		percolation.open(4, 4);
//		percolation.open(5, 4);
		percolation.print();
		System.out.println(percolation.percolates());

	}

	private void print() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(grid[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
		System.out.println();
	}

	// opens the site (row, col) if it is not open already
	public void open(int row, int col) {
		if (!isOpen(row, col)) {
			grid[row - 1][col - 1] = true;
			openCount++;
		}
		int indexInUF = getUnionIndex(row, col);

		// left neighbor
		unionIfOpen(indexInUF, row, col - 1);

		// right neighbor
		unionIfOpen(indexInUF, row, col + 1);

		// top neighbor
		unionIfOpen(indexInUF, row + 1, col);

		// bottom neighbor
		unionIfOpen(indexInUF, row - 1, col);

		if (row == 1) {
			// union to virtual top index
			uf.union(0, indexInUF);
		}

		if (row == size) {
			// union to virtual bottom index
			uf.union(size * size + 1, indexInUF);
		}
	}

	// is the site (row, col) open?
	public boolean isOpen(int row, int col) {
		if (row < 1 || row > size || col < 1 || col > size) {
			throw new IllegalArgumentException();
		}
		return grid[row - 1][col - 1];
	}

	// is the site (row, col) full?
	public boolean isFull(int row, int col) {
		if (isOpen(row, col)) {
			int top = uf.find(0);
			int target = uf.find(getUnionIndex(row, col));
			return top == target;
		}
		return false;
	}

	// returns the number of open sites
	public int numberOfOpenSites() {
		return openCount;
	}

	// does the system percolate?
	public boolean percolates() {
		int top = uf.find(0);
		int bottom = uf.find(size * size + 1);
		return top == bottom;
	}


	private void unionIfOpen(int sourceIndex, int targetRow, int targetCol) {
		try {
			if (isOpen(targetRow, targetCol)) {
				int targetIndex = getUnionIndex(targetRow, targetCol);
				uf.union(sourceIndex, targetIndex);
			}
		} catch (IllegalArgumentException e) {
//			System.err.println("ignore if out of range");
		}
	}

	private int getUnionIndex(int row, int col) {
		return (row - 1) * size + col;
	}
}