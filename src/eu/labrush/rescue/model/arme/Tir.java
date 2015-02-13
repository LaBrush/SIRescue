package eu.labrush.rescue.model.arme;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Vecteur;

public class Tir extends AbstractObject {

	int degat = 0;
	int angle;
	double vitesse = .1;

	public Tir(Vecteur position, int degat, int angle) {
		this.getTrajectoire().setPosition(position);
		this.getTrajectoire().getVitesse().setPolar(this.vitesse, angle);

		this.degat = degat;
		this.angle = angle;

		this.width = 5;
		this.height = 5;
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
