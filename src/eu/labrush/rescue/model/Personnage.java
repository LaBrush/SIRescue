package eu.labrush.rescue.model;

import java.util.ArrayList;

import eu.labrush.rescue.model.arme.Arme;

/**
 * @author adrienbocquet
 *
 */
public class Personnage extends AbstractObject {

	protected int life = 100, maxLife = 100;
	ArrayList<Arme> armes = new ArrayList<Arme>();

	int currentArme = -1 ;
	Vecteur vitesse_nominale = new Vecteur(5, 5);

	public Personnage(double x, double y) {
		super(x, y, 20, 20);
	}

	/**
	 * notifie les observeurs (les controlleurs) si le personnage est mort
	 */
	public void checkIfDead(){
		if(this.life <= 0){
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
	public ArrayList<Arme> getArmes() {
		return armes;
	}

	public void nextArme(){
		currentArme = currentArme < armes.size() - 1 ? currentArme+1 : 0   ;
		throwUpdate();
	}
	
	/**
	 * @param armes
	 *            the armes to set
	 */
	public void setArmes(ArrayList<Arme> armes) {
		this.armes = armes;
	}

	/**
	 * @return the currentArme
	 */
	public Arme getCurrentArme() {
		if(currentArme < 0){return null;}
		return this.armes.get(currentArme) ;
	}

	/**
	 * @param currentArme
	 *            the currentArme to set
	 */
	public void setCurrentArme(Arme arme) {
		this.currentArme = this.armes.indexOf(arme);
	}

	/**
	 * @param e
	 *            l'arme à ajouter
	 * @return l'arme passée en argument
	 */
	public void addArme(Arme e) {
		e.setOwner(this);
		armes.add(e);
		setCurrentArme(e);
	}
}
