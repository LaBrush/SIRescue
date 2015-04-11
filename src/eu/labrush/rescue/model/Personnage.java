package eu.labrush.rescue.model;

import java.util.ArrayList;

import eu.labrush.rescue.model.arme.Arme;

/**
 * @author adrienbocquet
 *
 */
public class Personnage extends AbstractObject {

	protected int life = 100, maxLife = 100;
	private ArrayList<Arme> armes = new ArrayList<Arme>();

	private int currentArme = -1;
	private Vecteur vitesse_nominale = new Vecteur(5, 5);

	public Personnage(double x, double y) {
		super(x, y, 20, 20);
	}

	/**
	 * notifie les observeurs (les controlleurs) si le personnage est mort
	 */
	public void checkIfDead() {
		if (this.life <= 0) {
			setChanged();
			this.notifyObservers("dead");
		}
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
	 *            Les degats infligés
	 */
	public void prendreDegats(int degats) {
		this.life -= degats;
		checkIfDead();
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

	public void nextArme() {
		currentArme = currentArme < armes.size() - 1 ? currentArme + 1 : 0;
		throwUpdate();
	}

	/**
	 * @return the currentArme
	 */
	public Arme getCurrentArme() {
		if (currentArme < 0) {
			return null;
		}
		return this.armes.get(currentArme);
	}

	public void setCurrentArme(Arme arme) {
		this.currentArme = this.armes.indexOf(arme);

		if (currentArme < 0) {
			addArme(arme);
			setCurrentArme(arme);
		}

		throwUpdate();
	}

	public void addArme(Arme arme) {

		boolean owned = false;
		for (Arme a : this.armes) {
			if (a.getTirClass().equals(arme.getTirClass()) && a.getCartouchesLeft() > 0) {
				a.addCartouches(arme.getCartouchesLeft());
				owned = true;
				break;
			}
		}
		if (!owned) {
			arme.addObserver(this);
			arme.setOwner(this);
			armes.add(arme);
		}

		if (currentArme < 0) {
			setCurrentArme(arme);
		}

		throwUpdate();
	}

	public void removeArme(Arme arme) {
		arme.deleteObserver(this);
		this.armes.remove(arme);

		if (this.armes.size() == 0) {
			this.currentArme = -1;
		}
		else {
			this.currentArme = 0;
		}

		throwUpdate();
	}

}
