package eu.labrush.rescue.model.arme;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Vecteur;

public abstract class Tir extends AbstractObject {

	int degat = 0;
	int angle;
	double vitesse = .1;
	
	abstract public boolean cross(AbstractObject o);
	
	public Tir(Vecteur position, int angle, int degat){
		super();
		this.degat = degat;
		this.angle = angle;
	}

	

	/**
	 * @return the degat
	 */
	public int getDegat() {
		return degat;
	}

	/**
	 * @param degat
	 *            the degat to set
	 */
	public void setDegat(int degat) {
		this.degat = degat;
	}

	/**
	 * @return the angle
	 */
	public int getAngle() {
		return angle;
	}

	/**
	 * @param angle
	 *            the angle to set
	 */
	public void setAngle(int angle) {
		this.angle = angle;
	}
}
