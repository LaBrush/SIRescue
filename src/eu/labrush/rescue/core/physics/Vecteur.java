/**
 * 
 */
package eu.labrush.rescue.core.physics;

/**
 * @author adrienbocquet
 *
 */
public class Vecteur {

	private int x ;
	private int y ;

	
	/**
	 * Default constructor
	 */
	public Vecteur() {
		this.x = 0 ;
		this.y = 0 ;
	}

	/**
	 * @param x
	 * @param y
	 */
	public Vecteur(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Vecteur add(Vecteur other){
		return new Vecteur(this.x + other.getX(), this.y + other.getY());
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	
	
}
