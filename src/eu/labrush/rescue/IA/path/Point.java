package eu.labrush.rescue.IA.path;

public class Point implements Cloneable {

	public int x, y;

	/**
	 * @param x
	 * @param y
	 */
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Point[] getVoisins() {
		Point[] voisins = new Point[8];
		int c = 0 ;
		
		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {

				// si le point considéré est hors de la grille ou qu'il s'agit du point lui-même, on
				// saute
				if (i < 0 || j < 0 || (i == x && j == y))
					continue;
				
				voisins[c] = new Point(i, j);
				c++;
			}
		}
				
		return voisins ;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Point other = (Point) obj;
		if (x != other.x || y != other.y)
			return false;
		return true;
	}

	public String toString() {
		return this.x + " " + this.y;
	}

	public Point clone() throws CloneNotSupportedException {
		return (Point) super.clone();
	}
}
