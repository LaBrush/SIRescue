package eu.labrush.rescue.core.physics;

import java.util.HashSet;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Vecteur;

/**
 * @author adrienbocquet
 *
 */
public abstract class AbstractPhysicBehaviour {

	public class LibertyDegree {
		public double top = Double.POSITIVE_INFINITY, bottom = Double.POSITIVE_INFINITY, left = Double.POSITIVE_INFINITY,
				right = Double.POSITIVE_INFINITY;

		public String toString() {
			return "top : " + top + " right : " + right + " bottom : " + bottom + " left : " + left;
		}
	}

	private AbstractObject target;
	protected LibertyDegree moves;

	AbstractPhysicBehaviour(AbstractObject obj) {
		this.setTarget(obj);
		this.moves = new LibertyDegree();
	}

	public abstract void updateTrajectoire(HashSet<? extends AbstractObject> obstacles, int delta_t);

	/**
	 * Calcul les degrès de liberté du solide par rapport à un bloc donné
	 * 
	 * @param bloc
	 * @param next_pos
	 */
	protected final void calcMargin(AbstractObject bloc, Vecteur next_pos) {

		double tw = this.getTarget().getWidth();
		double th = this.getTarget().getHeight();

		double tx = this.getTarget().getTrajectoire().getPosition().getX();
		double ty = this.getTarget().getTrajectoire().getPosition().getY();

		double bw = bloc.getWidth();
		double bh = bloc.getHeight();

		double bx = bloc.getTrajectoire().getPosition().getX();
		double by = bloc.getTrajectoire().getPosition().getY();

		double nx = Math.abs(next_pos.getX());
		double ny = Math.abs(next_pos.getY());

		/*
		 * Le calcul de distance se fait si: le bloc est en face de l'autre, verticalement, si on
		 * regarde la trajectoire sur x et horizontalement, si on regarde sur y
		 * 
		 * Pour la première seconde condition, on prend une marge correspondant à la distance
		 * parcourue sur l'axe durant le prochain déplacement afin d'éviter la possibilité aux cubes
		 * de rentrer l'un dans l'autre par un coin
		 * 
		 * Le dernier test est utile si l'épaisseur du bloc à detecter est moins élévée que celle de
		 * du modèle
		 */

		// On regarde d'abord en haut...
		if (isBetween(tx, bx - nx, bx + bw + nx) || isBetween(tx + tw, bx - nx, bx + bw + nx) || isBetween(bx, tx, tx + tw)) {
			double d = by - (ty + th);

			if (0 <= d && d < moves.top)
				moves.top = d;
		}

		// ... en bas ...
		if (isBetween(tx, bx - nx, bx + bw + nx) || isBetween(tx + tw, bx - nx, bx + bw + nx) || isBetween(bx, tx, tx + tw)) {
			double d = ty - (by + bh);

			if (0 <= d && d < moves.bottom)
				moves.bottom = d;
		}

		// ... à gauche ...
		if (isBetween(ty, by - ny, by + bh + ny) || isBetween(ty + th, by - ny, by + bh + ny) || isBetween(by, ty, ty + th)) {
			double d = tx - (bx + bw);

			if (0 <= d && d < moves.left)
				moves.left = d;
		}

		// et enfin à droite
		if (isBetween(ty, by - ny, by + bh + ny) || isBetween(ty + th, by - ny, by + bh + ny) || isBetween(by, ty, ty + th)) {
			double d = bx - (tx + tw);

			if (0 <= d && d < moves.right)
				moves.right = d;
		}

	}

	private static boolean isBetween(double value, double inf, double sup) {
		return inf < value && value < sup;
	}

	/**
	 * @return the target
	 */
	public AbstractObject getTarget() {
		return target;
	}

	/**
	 * @param target
	 *            the target to set
	 */
	public void setTarget(AbstractObject target) {
		this.target = target;
	}

}
