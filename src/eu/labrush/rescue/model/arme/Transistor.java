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

		this.vitesse = 200 ;
		
		this.getTrajectoire().setPosition(position);
		this.getTrajectoire().getVitesse().setPolar(this.vitesse, angle);

		this.behaviour = new TirPhysicBehaviour(this.getTrajectoire(), this.dim);
		this.behaviour.setGravity(-300);
		
		update(null, null);
	}

	@Override
	public boolean cross(AbstractObject o) {
		return !(o.getX() + o.getWidth() < this.getX() || o.getY() + o.getHeight() < this.getY() || o.getX() > this.getX() + this.getWidth() || o.getY() > this.getY() + this.getHeight());
	}
}
