/**
 * 
 */
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
public class PersonnagePhysicBehaviour extends AbstractPhysicBehaviour {

	public PersonnagePhysicBehaviour(AbstractObject obj) {
		super(obj);
	}

	@Override
	public void updateTrajectoire(HashSet<? extends AbstractObject> obstacles, int delta_t) {

		this.moves = new LibertyDegree();
		Trajectoire t = this.getTarget().getTrajectoire();
		Vecteur deplacement = t.distanceParcourue(delta_t);
		
		// On calcul la distance de chaque coté à chaque obstacle potentiel
		for (AbstractObject obstacle : obstacles) {
			if (obstacle instanceof Bloc) {
				this.meetBloc(obstacle, deplacement);
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
		else if (this.moves.bottom > 0 && t.hasGravity()) { // Si on est en l'air, on rajoute la gravité
			t.getAcceleration().setY(PhysicCore.GRAVITY);
		}

		// Enfin on met à jour la trajectoire
		t.update(delta_t);
	}

	private void meetBloc(AbstractObject bloc, Vecteur next) {

		double tw = this.getTarget().getWidth();
		double th = this.getTarget().getHeight();

		double tx = this.getTarget().getTrajectoire().getPosition().getX();
		double ty = this.getTarget().getTrajectoire().getPosition().getY();


		double bw = bloc.getWidth();
		double bh = bloc.getHeight();

		double bx = bloc.getTrajectoire().getPosition().getX();
		double by = bloc.getTrajectoire().getPosition().getY();

		// TODO: Prendre en compte le déplacement gauche-droite dans isBetween 
		// On regarde d'abord en haut...
		if (moves.top > by + bh && (isBetween(tx + 1, bx, bx + bw) || isBetween(tx - 1 + tw, bx, bx + bw))) {
			double d = by - (ty + th);

			if (0 <= d && d < moves.top)
				moves.top = d;
		}

		// ... en bas ...
		if (moves.bottom > by && (isBetween(tx + 1, bx, bx + bw) || isBetween(tx + tw - 1, bx, bx + bw))) {
			double d = ty - (by + bh);
			
			if (0 <= d && d < moves.bottom)
				moves.bottom = d;
		}

		// ... à gauche ...
		if (moves.left > bx && (isBetween(ty + 1, by, by + bh) || isBetween(ty + th - 1, by, by + bh))) {
			double d = tx - (bx + bw);

			if (0 <= d && d < moves.left)
				moves.left = d;
		}

		// et enfin à droite
		if (moves.right > bx + bw && (isBetween(ty + 1, by, by + bh) || isBetween(ty + th - 1, by, by + bh))) {
			double d = bx - (tx + tw);

			if (0 <= d && d < moves.right)
				moves.right = d;
		}
	}

	private static boolean isBetween(double value, double inf, double sup) {
		return value > inf && value < sup;
	}
}
