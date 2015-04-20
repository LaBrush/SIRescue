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
 */
public class RebondPhysicBehaviour extends AbstractPhysicBehaviour {

	private double speed = .3 ;

	public RebondPhysicBehaviour(Trajectoire t, Dimension dim) {
		super(t, dim);
		this.trajectoire.setVitesse(new Vecteur(speed, speed));
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.labrush.rescue.core.physics.AbstractPhysicBehaviour#updateTrajectoire(java.util.HashSet,
	 * int)
	 */
	@Override
	public void updateTrajectoire(double delta_t, HashSet<? extends AbstractObject> obstacles) {
		
		this.moves = new LibertyDegree();
		Vecteur deplacement = trajectoire.distanceParcourue(delta_t);

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

		if(this.moves.top == 0 && trajectoire.getVitesse().getY() == 0){
			trajectoire.getVitesse().setY(-speed);
		}
		else if(this.moves.bottom == 0 && trajectoire.getVitesse().getY() == 0){
			trajectoire.getVitesse().setY(speed);
		}
		
		if(this.moves.right == 0 && trajectoire.getVitesse().getX() == 0){
			trajectoire.getVitesse().setX(-speed);
		}
		else if(this.moves.left == 0 && trajectoire.getVitesse().getX() == 0){
			trajectoire.getVitesse().setX(speed);
		}
		
		// Enfin on met à jour la trajectoire
		trajectoire.update(delta_t);
	}
}
