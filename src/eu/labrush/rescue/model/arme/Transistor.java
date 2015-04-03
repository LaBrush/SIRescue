package eu.labrush.rescue.model.arme;

import java.awt.Dimension;

import eu.labrush.rescue.core.physics.TirPhysicBehaviour;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Vecteur;

public class Transistor extends Tir {

	public Transistor(Vecteur position, int angle, int degat) {	
		super(position, angle, degat);
		
		this.dim = new Dimension(5, 5);
		
		this.getTrajectoire().setPosition(position);
		this.getTrajectoire().getVitesse().setPolar(this.vitesse, angle);
		
		this.behaviour = new TirPhysicBehaviour(this.getTrajectoire(), this.dim);

		update(null, null);
	}

	@Override
	public boolean cross(AbstractObject o) {

		Vecteur position = this.getTrajectoire().getPosition();
		int width = (int) this.dim.getWidth(), height = (int) this.dim.getHeight();
		
		return pointIn(o, position) || pointIn(o, position.add(new Vecteur(0, height))) || pointIn(o, position.add(new Vecteur(width, height))) || pointIn(o, position.add(new Vecteur(width, 0)));
	}
	
	private boolean pointIn(AbstractObject o, Vecteur p){
		return o.getX() <= p.getX() && o.getX()+o.getWidth() >= p.getX() && o.getY() <= p.getY() && o.getY()+o.getHeight() >= p.getY() ;
	}


}
