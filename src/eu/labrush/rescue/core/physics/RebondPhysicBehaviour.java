package eu.labrush.rescue.core.physics;

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
	
	public RebondPhysicBehaviour(AbstractObject obj) {
		super(obj);
		obj.getTrajectoire().getVitesse().setY(speed);
		obj.getTrajectoire().getVitesse().setX(speed);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.labrush.rescue.core.physics.AbstractPhysicBehaviour#updateTrajectoire(java.util.HashSet,
	 * int)
	 */
	@Override
	public void updateTrajectoire(HashSet<? extends AbstractObject> obstacles, int delta_t) {
		this.moves = new LibertyDegree();
		Trajectoire t = this.getTarget().getTrajectoire();
		Vecteur deplacement = t.distanceParcourue(delta_t);

		// On calcul la distance de chaque coté à chaque obstacle potentiel
		for (AbstractObject obstacle : obstacles) {
			if (obstacle instanceof Bloc) {
				this.calcMargin(obstacle, deplacement);
			}
		}

		// La distance parcourue pendant la durée delta_t est supérieure à la distance, on adapte la
		// vitesse

		if (deplacement.getX() > 0 && deplacement.getX() >= this.moves.right) {
			t.getVitesse().setX(this.moves.right / delta_t);
			t.getAcceleration().setX(0);
		}
		else if (deplacement.getX() < 0 && -deplacement.getX() >= this.moves.left) {
			t.getVitesse().setX(-this.moves.left / delta_t);
			t.getAcceleration().setX(0);
		}

		if (deplacement.getY() > 0 && deplacement.getY() >= this.moves.top) {
			t.getVitesse().setY(this.moves.top / delta_t);
			t.getAcceleration().setX(0);
		}
		else if (deplacement.getY() < 0 && -deplacement.getY() >= this.moves.bottom) {
			t.getVitesse().setY(-this.moves.bottom / delta_t);
			t.getAcceleration().setY(0);
		}

		if(this.moves.top == 0 && t.getVitesse().getY() == 0){
			t.getVitesse().setY(-speed);
		}
		else if(this.moves.bottom == 0 && t.getVitesse().getY() == 0){
			t.getVitesse().setY(speed);
		}
		
		if(this.moves.right == 0 && t.getVitesse().getX() == 0){
			t.getVitesse().setX(-speed);
		}
		else if(this.moves.left == 0 && t.getVitesse().getX() == 0){
			t.getVitesse().setX(speed);
		}
		
		// Enfin on met à jour la trajectoire
		t.update(delta_t);
	}

}
