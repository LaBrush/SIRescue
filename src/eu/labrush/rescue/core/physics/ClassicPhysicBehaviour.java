package eu.labrush.rescue.core.physics;

import java.awt.Dimension;
import java.util.HashSet;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Trajectoire;
import eu.labrush.rescue.model.Vecteur;

/**
 * @author adrienbocquet
 *
 *         Décrit la trajectoire d'un objet de facon linéaire ou parabolique (en fonction de sa
 *         réaction à la gravité)
 *
 */
public class ClassicPhysicBehaviour extends AbstractPhysicBehaviour {

	public ClassicPhysicBehaviour(Trajectoire t, Dimension dim) {
		super(t, dim);
		this.setGravity(PhysicCore.GRAVITY);
		this.moves = new LibertyDegree() ;
	}

	public ClassicPhysicBehaviour() {
		this.setGravity(PhysicCore.GRAVITY);
		this.moves = new LibertyDegree() ;
	}

	@Override
	public void updateTrajectoire(double delta_t, HashSet<? extends AbstractObject> obstacles) {
		if (this.moves.bottom > 0) { 
			// Si on est en l'air, on rajoute la gravité
			trajectoire.getAcceleration().setY(this.gravity);
		}
		
		Vecteur deplacement = trajectoire.distanceParcourue(delta_t);
		
		this.moves = new LibertyDegree();	
		
		// On calcul la distance de chaque coté à chaque obstacle potentiel
		for (AbstractObject obstacle : obstacles) {
			if (obstacle instanceof Bloc) {
				this.calcMargin(obstacle, deplacement);
			}
		}
		
		// La distance parcourue pendant la durée delta_t est supérieure à la distance, on adapte la
		// vitesse

		if (deplacement.getX() > 0 && deplacement.getX() >= this.moves.right) {
			trajectoire.getVitesse().setX(this.moves.right / delta_t);
			trajectoire.getAcceleration().setX(0);
		}
		else if (deplacement.getX() < 0 && -deplacement.getX() >= this.moves.left) {
			trajectoire.getVitesse().setX(-this.moves.left / delta_t);
			trajectoire.getAcceleration().setX(0);
		}

		if (deplacement.getY() > 0 && deplacement.getY() >= this.moves.top) {
			trajectoire.getVitesse().setY(this.moves.top / delta_t);
			trajectoire.getAcceleration().setX(0);
		}
		else if (deplacement.getY() < 0 && -deplacement.getY() >= this.moves.bottom) {
			trajectoire.getVitesse().setY(-this.moves.bottom / delta_t);
			trajectoire.getAcceleration().setY(0);
		}

		// Enfin on met à jour la trajectoire
		trajectoire.update(delta_t);
	}

}
