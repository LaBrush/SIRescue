/**
 * 
 */
package eu.labrush.rescue.core.physics;

/**
 * @author adrienbocquet
 *
 */
public class Vecteur{

	private double x ;
	private double y ;
	
	/**
	 * @param x
	 * @param y
	 */
	public Vecteur(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}
	
	public Vecteur() {
		super();
		this.x = 0.0;
		this.y = 0.0;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double norme(){
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}
		
}
