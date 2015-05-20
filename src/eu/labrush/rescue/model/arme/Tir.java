package eu.labrush.rescue.model.arme;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;

public abstract class Tir extends AbstractObject {

	int damage = 0;
	int angle ;
	int recul = 0 ;
	double vitesse = 100;
	
	boolean activated = false ;
	
	abstract public boolean cross(AbstractObject o);
	protected Personnage owner ;
	
	public Tir(int angle, int damage, int recul, Personnage owner){
		super();
		this.damage = damage;
		this.angle = angle;
		this.recul = recul ;
		this.owner = owner ;
	}
	
	abstract public void use();
	/**
	 * @return the damage
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
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

	/**
	 * @return the owner
	 */
	public Personnage getOwner() {
		return owner;
	}

	/**
	 * @return the activated
	 */
	public boolean isActivated() {
		return activated;
	}

	/**
	 * @param activated the activated to set
	 */
	public void setActivated(boolean activated) {
		this.activated = activated;
	}

	/**
	 * @return the recul
	 */
	public int getRecul() {
		return recul;
	}

	/**
	 * @param recul the recul to set
	 */
	public void setRecul(int recul) {
		this.recul = recul;
	}
}
