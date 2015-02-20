package eu.labrush.rescue.model.arme;

import java.awt.Dimension;

import eu.labrush.rescue.core.physics.TirPhysicBehaviour;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

public class Resistance extends AbstractObject implements Tir {

	int degat = 0;
	int angle;
	double vitesse = .1;

	public Resistance(Vecteur position, int degat, int angle) {
		
		this.getTrajectoire().setPosition(position);
		this.getTrajectoire().getVitesse().setPolar(this.vitesse, angle);
		
		this.dim = new Dimension(5,5);
		
		this.degat = degat;
		this.angle = angle;
		
		this.behaviour = new TirPhysicBehaviour(this.getTrajectoire(), this.dim);
	}
	
	@Override
	public void cross(Personnage p) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void cross(Bloc b) {
		// TODO Auto-generated method stub	
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
