package eu.labrush.rescue.model.arme;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

public abstract class Tir extends AbstractObject {

	int damage = 0;
	int angle;
	double vitesse = 100;
	
	abstract public boolean cross(AbstractObject o);
	private Personnage owner ;
	
	public Tir(Vecteur position, int angle, int damage, Personnage owner){
		super();
		this.damage = damage;
		this.angle = angle;
		this.owner = owner ;
	}

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
}
