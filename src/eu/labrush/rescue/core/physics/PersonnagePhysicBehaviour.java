/**
 * 
 */
package eu.labrush.rescue.core.physics;

import java.util.HashSet;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Trajectoire;

/**
 * @author adrienbocquet
 *
 */
public class PersonnagePhysicBehaviour extends AbstractPhysicBehaviour {

	public PersonnagePhysicBehaviour(AbstractObject obj) {
		super(obj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.labrush.rescue.core.physics.AbstractPhysicBehaviour#updateTrajectoire(java.util.HashSet)
	 */
	@Override
	public LibertyDegree calcDegrees(HashSet<? extends AbstractObject> obstacles) {

		LibertyDegree moves = new LibertyDegree();

		for (AbstractObject obstacle : obstacles) {
			if (obstacle instanceof Bloc) {
				this.meetBloc(obstacle, moves);
			}
		}
		
		return moves ;
	}
		
	public void adaptToDegrees(){
		
		Trajectoire t = this.getTarget().getTrajectoire();
		
		if (!this.moves.bottom) {
			if (t.getAcceleration().getY() < 0) {
				t.getAcceleration().setY(0);
			}

			if (t.getVitesse().getY() < 0) {
				t.getVitesse().setY(0);
			}
		}
		else if (t.hasGravity() && t.getAcceleration().getY() == 0) {
			t.getAcceleration().setY(-.0015);
		}

		if (!this.moves.top) {
			if (t.getAcceleration().getY() > 0) {
				t.getAcceleration().setY(0);
			}

			if (t.getVitesse().getY() > 0) {
				t.getVitesse().setY(0);
			}
		}

		if (!this.moves.left) {
			if (t.getAcceleration().getX() < 0) {
				t.getAcceleration().setX(0);
			}

			if (t.getVitesse().getX() < 0) {
				t.getVitesse().setX(0);
			}
		}

		if (!this.moves.right) {
			if (t.getAcceleration().getX() > 0) {
				t.getAcceleration().setX(0);
			}

			if (t.getVitesse().getX() > 0) {
				t.getVitesse().setX(0);
			}
		}
	}

	private void meetBloc(AbstractObject bloc, LibertyDegree moves) {

		double tw = this.getTarget().getWidth();
		double th = this.getTarget().getHeight();

		double tx = this.getTarget().getTrajectoire().getPosition().getX();
		double ty = this.getTarget().getTrajectoire().getPosition().getY();

		double bw = bloc.getWidth();
		double bh = bloc.getHeight();

		double bx = bloc.getTrajectoire().getPosition().getX();
		double by = bloc.getTrajectoire().getPosition().getY();

		// On regarde d'abord en haut...
		if (moves.top && isBetween(ty + th, by, by + bh) && (isBetween(tx + 1, bx, bx + bw) || isBetween(tx - 1 + th, bx, bx + bw))) {
			moves.top = false;
		}

		// ... en bas ...
		if (moves.bottom && isBetween(ty, by, by + bh) && (isBetween(tx + 1, bx, bx + bw) || isBetween(tx + th - 1, bx, bx + bw))) {
			moves.bottom = false;
		}

		// ... à gauche ...
		if (moves.left && isBetween(tx, bx, bx + bw) && (isBetween(ty + 1, by, by + bh) || isBetween(ty + th - 1, by, by + bh))) {
			moves.left = false;
		}

		// et enfin à droite
		if (moves.right && isBetween(tx + tw, bx, bx + bw) && (isBetween(ty + 1, by, by + bh) || isBetween(ty + th - 1, by, by + bh))) {
			moves.right = false;
		}
	}

	private static boolean isBetween(double value, double inf, double sup) {
		return value > inf && value < sup;
	}
}
