package eu.labrush.rescue.model.arme;

import java.util.Observable;
import java.util.Observer;

import eu.labrush.rescue.core.physics.InertPhysicBehaviour;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

/**
 * @author adrienbocquet
 *
 *         Type de tir "eternel" cad ne disparaissant jamais et suivant les déplacements du
 *         personnage auquel il est attaché (les binos)
 *
 */
public class Bouclier extends Tir {

	public Bouclier(Vecteur position, int angle, int damage, int recul, Personnage owner) {
		super(position, angle, damage, recul, owner);
		
		this.dim = owner.getDimension();
		this.behaviour = new InertPhysicBehaviour(this.getTrajectoire(), owner);
		
		setActivated(true);
		
		owner.addObserver(new Observer(){
			@Override
			public void update(Observable o, Object arg) {
				if("dead".equals(arg)){
					setChanged();
					notifyObservers("done");
				}
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.model.arme.Tir#cross(eu.labrush.rescue.model.AbstractObject)
	 */
	@Override
	public boolean cross(AbstractObject o) {
		return !(o.getX() + o.getWidth() < this.getX() || o.getY() + o.getHeight() < this.getY() || o.getX() > this.getX() + this.getWidth() || o.getY() > this.getY() + this.getHeight());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.model.arme.Tir#use()
	 */
	@Override
	public void use() {}

}
