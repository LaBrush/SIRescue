package eu.labrush.rescue.model.arme;

import java.awt.Dimension;
import java.util.Observable;

import eu.labrush.rescue.core.physics.TirPhysicBehaviour;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

public class Resistance extends Tir {

	// ces quatre vecteurs représentent les points en haut, gauche et droite, et en bas gauche et
	// droite du rectangle
	public Vecteur hg, hd, bg, bd;

	public Resistance(Vecteur position, int angle, int degat, int recul, Personnage owner) {	
		super(angle, degat, recul, owner);
		
		this.dim = new Dimension(30, 25);
		setImage("resistance.png");
		
		this.getTrajectoire().setPosition(position.add(new Vecteur(0, - this.getHeight() / 2 * Math.cos(angle))));
		this.getTrajectoire().getVitesse().setPolar(this.vitesse, angle);

		this.behaviour = new TirPhysicBehaviour(this.getTrajectoire(), this.dim);
		
		update(null, null);
	}

	//on met à jour coordonées des points à tout changment de position
	public void update(Observable obs, Object obj) {
	
		double angle = Math.toRadians(this.angle);
		
		bg = this.getTrajectoire().getPosition().clone();
		bd = new Vecteur(this.getWidth() * Math.cos(angle), this.getWidth() * Math.sin(angle));
		hg = new Vecteur(this.getHeight() * Math.cos(angle + Math.PI / 2), this.getHeight() * Math.sin(angle + Math.PI / 2));	
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

	@Override
	public void use() {
		setChanged();
		notifyObservers("done");
	}
}
