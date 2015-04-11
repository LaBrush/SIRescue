package eu.labrush.rescue.model.arme;

import java.awt.Dimension;

import eu.labrush.rescue.core.physics.TirPhysicBehaviour;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Trajectoire;
import eu.labrush.rescue.model.Vecteur;

public class Transistor extends Tir {

	private int tempsExplosion = 500 ;
	private long usedAt = 0 ;
	
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
	
	@Override
	public void use() {
		
		if(usedAt == 0){
			usedAt = System.currentTimeMillis();
			setActivated(true);
			setTrajectoire(new Trajectoire(this.getX(), this.getY()));
		}
		else if(System.currentTimeMillis() - usedAt < tempsExplosion){
			double radius = 5 + 20 * (double)((System.currentTimeMillis() - usedAt) / (double)tempsExplosion) ;
			this.dim.setSize(radius, radius);
			this.throwUpdate();
		}
		else {
			setChanged();
			notifyObservers("done");
		}
	}
}
