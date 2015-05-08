package eu.labrush.rescue.IA.path;

import eu.labrush.rescue.model.AbstractObject;


public class Point implements Cloneable {

	public int x, y;
	
	public Point(int x, int y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Point(AbstractObject o){
		this((o.getX()+o.getWidth()/2)/10, (o.getY()+o.getHeight()/2)/10);
	}
	
	public Point(double x, double y) {
		this((int)x, (int)y);
	}

	public Point(int x, int y, int cineticReserve) {
		this(x,y);
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
