package eu.labrush.rescue.model.arme;

import java.awt.Dimension;

import eu.labrush.rescue.core.physics.TirPhysicBehaviour;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Vecteur;

public class Resistance extends AbstractObject implements Tir {

	int degat = 0;
	int angle;
	double vitesse = .1;

	// ces trois vecteurs représentent les points en haut, gauche et droite, et en bas gauche et
	// droite du rectangle
	public Vecteur hg, hd, bg, bd;

	public Resistance(Vecteur position, int degat, int angle) {

		this.getTrajectoire().setPosition(position);
		this.getTrajectoire().getVitesse().setPolar(this.vitesse, angle);

		this.dim = new Dimension(5, 5);

		this.degat = degat;
		this.angle = angle;

		this.behaviour = new TirPhysicBehaviour(this.getTrajectoire(), this.dim);
		
		throwUpdate();
	}

	//on met à jour coordonées des points à tout changment de position
	protected void throwUpdate() {
		this.bg = this.getTrajectoire().getPosition().clone();
		this.bd = new Vecteur(bg.getX() + this.getWidth() * Math.cos(angle), bg.getY() + this.getHeight() * Math.sin(angle));
		this.hg = new Vecteur(bg.getX() + this.getWidth() * Math.sin(angle), bg.getY() + this.getHeight() * Math.cos(angle));
		
		double angle_hd = Math.atan(this.getHeight() / this.getWidth());
		
		this.hd = new Vecteur(bg.getX() + Math.cos(angle_hd) * this.getWidth(), bg.getY() + Math.sin(angle_hd) *  this.getWidth());
		
		setChanged();
		notifyObservers();
	}

	@Override
	public boolean cross(AbstractObject o) {

		return false;
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
