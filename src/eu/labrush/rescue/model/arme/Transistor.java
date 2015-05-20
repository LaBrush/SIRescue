package eu.labrush.rescue.model.arme;

import java.awt.Dimension;

import eu.labrush.rescue.core.physics.TirPhysicBehaviour;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Trajectoire;
import eu.labrush.rescue.model.Vecteur;

public class Transistor extends Tir {

	private int tempsExplosion = 600 ;
	private long usedAt = 0 ;
	
	private Vecteur explosion_pos ;
	
	public Transistor(Vecteur position, int angle, int degat, int recul, Personnage owner) {	
		super(position, angle, degat, recul, owner);
		this.angle = 0 ;
		this.dim = new Dimension(17, 32);

		this.vitesse = 200 ;
		
		this.getTrajectoire().setPosition(position);
		this.getTrajectoire().getVitesse().setPolar(this.vitesse, angle);

		this.behaviour = new TirPhysicBehaviour(this.getTrajectoire(), this.dim);
		this.behaviour.setGravity(-300);
		
		setImage("transistor.png");
		
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
			
			explosion_pos = this.getTrajectoire().getPosition().clone();
			setTrajectoire(new Trajectoire(this.getX(), this.getY()));
			
			setImage("explosion.png");
		}
		else if(System.currentTimeMillis() - usedAt < tempsExplosion){
			double radius = 5 + 30 * (double)((System.currentTimeMillis() - usedAt) / (double)tempsExplosion) ;
			this.dim.setSize(radius, radius);
			
			this.getTrajectoire().setPosition(new Vecteur(explosion_pos.getX() - radius/2, explosion_pos.getY() - radius/2));
			
			this.throwUpdate();
		}
		else {
			setChanged();
			notifyObservers("done");
		}
	}
}
