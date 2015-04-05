package eu.labrush.rescue.model.arme;

import java.awt.Dimension;

import eu.labrush.rescue.core.physics.TirPhysicBehaviour;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

public class Transistor extends Tir {

	public Transistor(Vecteur position, int angle, int degat, Personnage owner) {	
		super(position, angle, degat, owner);

		this.dim = new Dimension(5, 5);

		this.vitesse = .6 ;
		
		this.getTrajectoire().setPosition(position);
		this.getTrajectoire().getVitesse().setPolar(this.vitesse, angle);

		this.behaviour = new TirPhysicBehaviour(this.getTrajectoire(), this.dim);
		this.behaviour.setGravity(-.002);
		
		update(null, null);
	}

	@Override
	public boolean cross(AbstractObject o) {

		Vecteur position = this.getTrajectoire().getPosition();
		int width = (int) this.dim.getWidth(), height = (int) this.dim.getHeight();

		return pointIn(o, position) || pointIn(o, new Vecteur(width, height).add(position)) || pointIn(o, new Vecteur(0, height).add(position)) || pointIn(o, new Vecteur(width, 0).add(position));
	}

	private boolean pointIn(AbstractObject o, Vecteur p) {
		return o.getX() <= p.getX() && o.getX() + o.getWidth() >= p.getX() && o.getY() <= p.getY() && o.getY() + o.getHeight() >= p.getY();
	}

}
