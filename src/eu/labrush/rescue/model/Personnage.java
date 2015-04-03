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

	Arme currentArme = null;
	Vecteur vitesse_nominale = new Vecteur(5, 5);

	public Personnage(double x, double y) {
		super(x, y, 20, 20);
		this.getTrajectoire().setGravity(true);
	}

	/**
	 * @return si le personnage est mort
	 */
	public boolean isDead() {
		return this.life <= 0;
	}

	/**
	 * @return the vitesse_nominale
	 */
	public Vecteur getVitesseNominale() {
		return vitesse_nominale;
	}

	/**
	 * @param vitesse_nominale
	 *            the vitesse_nominale to set
	 */
	public void setVitesseNominale(Vecteur vitesse_nominale) {
		this.vitesse_nominale = vitesse_nominale;
	}

	/**
	 * Retranche à la vie du personnage les dégats infligés
	 * 
	 * @param degats
	 */
	public void prendreDegats(int degats) {
		this.life -= degats;
		throwUpdate();
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
	 * @param armes
	 *            the armes to set
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
	 * @param currentArme
	 *            the currentArme to set
	 */
	public void setCurrentArme(Arme currentArme) {
		this.currentArme = currentArme;
	}

	/**
	 * @param e
	 *            l'arme à ajouter
	 * @return l'arme passée en argument
	 */
	public boolean addArme(Arme e) {
		setCurrentArme(e);
		return armes.add(e);
	}
}
