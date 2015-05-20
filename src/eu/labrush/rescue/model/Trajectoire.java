package eu.labrush.rescue.model;


/**
 * @author adrienbocquet
 * 
 *         Stocke les composantes de la trajectoire d'un objet (accélération, vitesse, position) et
 *         met à jour leur valeurs en fonction du temps
 *
 */
public class Trajectoire extends AbstractModel implements Cloneable {

	private Vecteur acceleration, vitesse, position;

	public Trajectoire(Vecteur a, Vecteur v, Vecteur p) {
		this.setAcceleration(a);
		this.setVitesse(v);
		this.setPosition(p);
	}

	public Trajectoire(double x, double y) {
		this(new Vecteur(), new Vecteur(), new Vecteur(x, y));
	}

	public Trajectoire() {
		this(0, 0);
	}
	
	/**
	 * Met à jour la position et la vitesse du composant en fonction du temps
	 * 
	 * @param delta_t
	 *            la durée (en ms) séparant deux mises à jour de la position
	 * 
	 */
	public void update(double delta_t) {
		// Si la vitesse et l'acceleration sont nulles, il n'est pas la peine de recalculer la
		// position
		if (this.getAcceleration().norme() != 0 || this.getVitesse().norme() != 0) {
			
			/*
			 * On chercher à obternir la valeur de la vitesse en fonction de l'accélération On
			 * applique la relation v = a . ∆t puisque v est la dérivée de l'accélération par
			 * rapport au temps
			 * http://www.kartable.fr/terminale-s/physique/1109/cours/cinematique-vecteurs
			 * -et-mouvements,TS05029
			 */			
			getVitesse().add(acceleration.k(delta_t));
			
			// Idem pour la position
			getPosition().add(acceleration.k(1 / 2 * Math.pow(delta_t, 2))).add(vitesse.k(delta_t));

			throwUpdate();
		}
	}

	/**
	 * Retourne la distance parcourue lors du prochain déplacement sans modifier l'objet
	 * 
	 * @param delta_t l'intervalle de temps séparant deux calculs
	 * @return la distance parcourue durant l'intervalle de temps donné
	 */
	public Vecteur distanceParcourue(double delta_t) {
		Vecteur p = new Vecteur();
		if (this.getAcceleration().norme() != 0 || this.getVitesse().norme() != 0) {
			Vecteur v = this.getVitesse().clone().add(acceleration.k(delta_t));
			p.add(this.getAcceleration().k(1 / 2 * Math.pow(delta_t, 2))).add(v.k(delta_t));
		}
		return p;
	}
	
	public Trajectoire clone() {		
		return new Trajectoire(this.getAcceleration().clone(), this.getVitesse().clone(), this.getPosition().clone());
	}

	public void setAcceleration(Vecteur acceleration) {
		if (this.acceleration != null) {
			this.acceleration.deleteObserver(this);
		}

		this.acceleration = acceleration;
		this.acceleration.addObserver(this);

		throwUpdate();
	}

	public void setVitesse(Vecteur vitesse) {
		if (this.vitesse != null) {
			this.vitesse.deleteObserver(this);
		}

		this.vitesse = vitesse;
		this.vitesse.addObserver(this);

		throwUpdate();
	}

	public void setPosition(Vecteur position) {
		if (this.position != null) {
			this.position.deleteObserver(this);
		}

		this.position = position;
		this.position.addObserver(this);

		throwUpdate();
	}

	/**
	 * @return the acceleration
	 */
	public Vecteur getAcceleration() {
		return acceleration;
	}

	/**
	 * @return the vitesse
	 */
	public Vecteur getVitesse() {
		return vitesse;
	}

	/**
	 * @return the position
	 */
	public Vecteur getPosition() {
		return position;
	}
}
