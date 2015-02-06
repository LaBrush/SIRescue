/**
 * 
 */
package eu.labrush.rescue.model;

import eu.labrush.rescue.core.physics.Vecteur;

/**
 * @author adrienbocquet
 *
 */
public class Personnage extends AbstractModel {

	Vecteur position ;
	Vecteur vitesse ;
	
	int width = 50 ;
	int height = 50 ;
	
	/**
	 * @param x
	 * @param y
	 * @param vx
	 * @param vy
	 */
	public Personnage(double x, double y, double vx, double vy) {
		super();
		this.position = new Vecteur(x, y);
		this.vitesse = new Vecteur(vx, vy);
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public Personnage(double x, double y) {
		super();
		this.position = new Vecteur(x, y);
		this.vitesse = new Vecteur();
	}
	
	public Personnage() {
		super();
		this.position = new Vecteur();
		this.vitesse = new Vecteur();
	}
	
	public double getX() {
		return this.position.getX();
	}
	
	public void setX(double x) {
		this.position.setX(x);
		this.notifyObservers();
	}
	
	public double getY() {
		return this.position.getY();
	}
	
	public void setY(double y) {
		this.position.setY(y);
		this.notifyObservers();
	}

	public double getVX() {
		return this.vitesse.getX();
	}
	
	public void setVX(double x) {
		this.vitesse.setX(x);
		this.notifyObservers();
	}
	
	public double getVY() {
		return this.vitesse.getY();
	}
	
	public void setVY(double y) {
		this.vitesse.setY(y);
		this.notifyObservers();
	}
	
	public double normeVitesse(){
		return this.vitesse.norme();
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
}
