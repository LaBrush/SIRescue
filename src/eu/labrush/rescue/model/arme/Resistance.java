package eu.labrush.rescue.model.arme;

import java.awt.Dimension;
import java.util.Observable;

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
		super();
		
		this.dim = new Dimension(5, 5);
		
		this.getTrajectoire().setPosition(position);
		this.getTrajectoire().getVitesse().setPolar(this.vitesse, angle);
		
		this.degat = degat;
		this.angle = angle;

		this.behaviour = new TirPhysicBehaviour(this.getTrajectoire(), this.dim);
		
		update(null, null);
	}

	//on met à jour coordonées des points à tout changment de position
	public void update(Observable obs, Object obj) {
		
		double angle = Math.toRadians(this.angle);
		
		bg = this.getTrajectoire().getPosition().clone();
		bd = new Vecteur(this.getWidth() * Math.cos(angle), this.getWidth() * Math.sin(angle));
		hg = new Vecteur(this.getHeight() * Math.cos(angle + Math.PI/2), this.getWidth() * Math.sin(angle + Math.PI/2));		
		hd = new Vecteur().add(bd).add(hg);
		
		bd.add(bg);
		hg.add(bg);
		hd.add(bg);
		
		setChanged();
		notifyObservers();
	}

	@Override
	public boolean cross(AbstractObject o) {
		return pointIn(o, bg) || pointIn(o, bd) || pointIn(o, hg) || pointIn(o, hd);
	}
	
	private boolean pointIn(AbstractObject o, Vecteur p){
		return o.getX() <= p.getX() && o.getX()+o.getWidth() >= p.getX() && o.getY() <= p.getY() && o.getY()+o.getHeight() >= p.getY() ;
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
