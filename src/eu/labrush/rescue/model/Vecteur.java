/**
 * 
 */
package eu.labrush.rescue.model;

/**
 * @author adrienbocquet
 *
 */
public class Vecteur extends AbstractModel implements Cloneable {

	private double x;
	private double y;

	public Vecteur(double x, double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * @param other
	 * 		A vecteur to clone
	 */
	public Vecteur(Vecteur other) {
		this.x = other.getX();
		this.y = other.getY();
	}

	public Vecteur() {
		super();
		this.x = .0;
		this.y = .0;
	}

	public String toString() {
		return "Vecteur.@" + Integer.toHexString(hashCode()) + " x: " + this.x + " y:" + this.y + " ";
	}

	/**
	 * Fait correspondre les coordonées cartésiennes du vecteur aux coordonées polaires données en
	 * argument
	 * 
	 * @param norme
	 * 		la norme du vecteur
	 * @param angle
	 *      (en degrès)
	 */
	public void setPolar(double norme, int angle) {
		this.x = Math.cos(Math.toRadians(angle)) * norme;
		this.y = Math.sin(Math.toRadians(angle)) * norme;

		throwUpdate();
	}

	public Vecteur set(double x, double y){
		this.x = x ;
		this.y = y ;
		
		return this ;
	}
	
	public Vecteur clone() {
		return new Vecteur(x, y);
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
		throwUpdate();
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
		throwUpdate();
	}

	public double norme() {
		return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
	}

	public Vecteur add(Vecteur other) {
		this.x += other.x ;
		this.y += other.y ;
		
		throwUpdate();
		
		return this ;
	}

	public Vecteur k(double k) {
		return new Vecteur(this.x * k, this.y * k);
	}

}
