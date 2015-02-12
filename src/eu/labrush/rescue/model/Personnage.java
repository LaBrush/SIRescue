package eu.labrush.rescue.model;

import java.util.HashSet;

import eu.labrush.rescue.model.arme.Arme;

/**
 * @author adrienbocquet
 *
 */
public class Personnage extends AbstractObject {

	protected int life = 100, maxLife = 100;
	HashSet<Arme> armes = new HashSet<Arme>();
	Arme currentArme = null ;
	
	public Personnage(double x, double y) {
		super(x, y);
		this.getTrajectoire().setGravity(true);
	}

	/**
	 * @return si le personnage est mort
	 */
	public boolean isDead(){
		return this.life <= 0 ;
	}
	
	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @param life
	 *            the life to set
	 */
	public void setLife(int life) {
		this.life = life;
		throwUpdate();
	}

	/**
	 * @return the maxLife
	 */
	public int getMaxLife() {
		return maxLife;
	}

	/**
	 * @param maxLife
	 *            the maxLife to set
	 */
	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
		throwUpdate();
	}

	/**
	 * @return the armes
	 */
	public HashSet<Arme> getArmes() {
		return armes;
	}

	/**
	 * @param armes the armes to set
	 */
	public void setArmes(HashSet<Arme> armes) {
		this.armes = armes;
	}

	/**
	 * @return the currentArme
	 */
	public Arme getCurrentArme() {
		return currentArme;
	}

	/**
	 * @param currentArme the currentArme to set
	 */
	public void setCurrentArme(Arme currentArme) {
		this.currentArme = currentArme;
	}

	/**
	 * @param e
	 * @return
	 * @see java.util.HashSet#add(java.lang.Object)
	 */
	public boolean addArme(Arme e) {
		setCurrentArme(e);
		return armes.add(e);
	}

}
