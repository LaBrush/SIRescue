/**
 * 
 */
package eu.labrush.rescue.model;

import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.utils.Listener;

/**
 * @author adrienbocquet
 *
 */
public class Trajectoire extends AbstractModel implements Listener<PhysicCore> {

	Vecteur acceleration, vitesse, position ;
	
	public Trajectoire(Vecteur a, Vecteur v, Vecteur p){
		this.setAcceleration(a) ;
		this.setVitesse(v) ;
		this.setPosition(p) ;
	}
	
	public Trajectoire(double x, double y){
		this(new Vecteur(), new Vecteur(), new Vecteur(x, y));
	}
	
	public Trajectoire(){
		this(0, 0);
	}
		
	/**
	 * Met à jour la position et la vitesse du composant en fonction du temps 
	 */
	@Override
	public void update(PhysicCore core) {
		//Si la vitesse et l'acceleration sont nulles, il n'est pas la peine de recalculer la position
		if(this.getAcceleration().norme() != 0 || this.getVitesse().norme() != 0){
			
			/* On chercher à obternir la valeur de la vitesse en fonction de l'accélération
			 * On applique la relation v = a . ∆t puisque v est la dérivée de l'accélération par rapport au temps
			 * http://www.kartable.fr/terminale-s/physique/1109/cours/cinematique-vecteurs-et-mouvements,TS05029
			 */
			setVitesse(vitesse.add(acceleration.k(core.getDelta())));
			
			//Idem pour la position
			setPosition(position.add(acceleration.k(1/2 * Math.pow(core.getDelta(), 2))).add(vitesse.k(core.getDelta())));
			notifyObservers();
			
		}
	}

	/**
	 * @param acceleration the acceleration to set
	 */
	public void setAcceleration(Vecteur a) {
		if(this.acceleration != null){
			this.acceleration.deleteObserver(this);
		}
		
		this.acceleration = a;
		this.acceleration.addObserver(this);
		
		setChanged();
	}

	/**
	 * @param vitesse the vitesse to set
	 */
	public void setVitesse(Vecteur v) {
		if(this.vitesse != null){
			this.vitesse.deleteObserver(this);
		}
		
		this.vitesse = v;
		this.vitesse.addObserver(this);
		
		setChanged();
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Vecteur p) {
		if(this.position != null){
			this.position.deleteObserver(this);
		}
		
		this.position = p;
		this.position.addObserver(this);
		
		setChanged();
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
