/**
 * 
 */
package eu.labrush.rescue.model;

/**
 * @author adrienbocquet
 *
 */
public class Vecteur extends AbstractModel {

	private double x;
	private double y;

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

	public String toString() {
		return "Vecteur.@" + Integer.toHexString(hashCode()) + " x: " + this.x + " y:" + this.y + " ";
	}

	/**
	 * Fait correspondre les coordonées cartésiennes du vecteur aux coordonées
	 * polaires données en argument
	 * 
	 * @param norme
	 * @param angle
	 *            (en degrès)
	 */
	public void setPolar(double norme, int angle) {
		this.x = Math.cos(Math.toRadians(angle)) * norme;
		this.y = Math.sin(Math.toRadians(angle)) * norme;

		throwUpdate();
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
		return new Vecteur(this.x + other.x, this.y + other.y);
	}

	public Vecteur k(double k) {
		return new Vecteur(this.x * k, this.y * k);
	}

}
