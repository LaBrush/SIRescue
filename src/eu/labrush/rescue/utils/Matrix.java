package eu.labrush.rescue.utils;

/**
 * @author adrienbocquet
 *
 */
public class Matrix implements Cloneable {

	double[][] matrix;
	int width, height;

	public Matrix(int width, int height) {
		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("The matrix's dimensions cannot be null");
		}

		this.width = width;
		this.height = height;

		this.matrix = new double[width][height];
	}

	public Matrix(double[][] i) {
		this.width = i.length;
		this.height = i[0].length;

		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("The matrix's dimensions cannot be null");
		}

		this.matrix = i;
	}

	public Matrix(int[][] i) {
		this.width = i.length;
		this.height = i[0].length;

		if (width <= 0 || height <= 0) {
			throw new IllegalArgumentException("The matrix's dimensions cannot be null");
		}

		this.matrix = new double[width][height];

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				this.matrix[x][y] = (double) i[x][y];
			}
		}

	}

	public static Matrix add(Matrix m1, Matrix m2) {
		try {
			return ((Matrix) m1.clone()).add(m2);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @param the
	 *            matrix to multiply with
	 * @return the product of both matrix
	 */
	public static Matrix multiply(Matrix m1, Matrix m2) {
		if (m1.getWidth() != m2.getHeight()) {
			throw new IllegalArgumentException("The first matrix column number must be the same as the second matrix row number");
		}

		Matrix m = new Matrix(m1.width, m2.height);
		
		for (int x = 0; x < m.width; x++) {
			for (int y = 0; y < m.height; y++) {
				double sum = 0.0;

				for (int i = 0; i < m.width; i++) {
					sum += m1.get(x, i) * m2.get(i, y);
				}

				m.matrix[x][y] = sum ;
			}
		}

		return m;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "";
		for (int x = 0; x < width; x++) {
			s += "|";
			for (int y = 0; y < height; y++) {
				s += " " + matrix[x][y];
			}
			s += " |\n";
		}

		return s;
	}

	/**
	 * 
	 * @param the
	 *            matrix to add with
	 * @return the sum of both matrix
	 */
	Matrix add(Matrix other) {
		if (other.getWidth() != this.getWidth() || other.getHeight() != this.getHeight()) {
			throw new IllegalArgumentException("Both matrix doesn't have the same dimension");
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				this.matrix[x][y] = this.get(x, y) + other.get(x, y);
			}
		}

		return this;
	}

	/**
	 * 
	 * @param the
	 *            scalar factor
	 * @return
	 */
	Matrix scalar(double k) {
		Matrix m = new Matrix(width, height);

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				m.matrix[x][y] *= k;
			}
		}

		return m;
	}

	public double set(int x, int y, double value) {
		matrix[x][y] = value;
		return value;
	}

	public double get(int x, int y) {
		return matrix[x][y];
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

}
