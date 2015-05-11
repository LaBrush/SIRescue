package eu.labrush.rescue.core.physics;

import java.awt.Dimension;
import java.util.HashSet;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Trajectoire;
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

	protected Trajectoire trajectoire;
	protected LibertyDegree moves;
	protected Dimension dim;

	protected double gravity = 0;

	AbstractPhysicBehaviour() {
	}

	AbstractPhysicBehaviour(Trajectoire t, Dimension dim) {
		this.trajectoire = t;
		this.dim = dim;
	}

	public abstract void updateTrajectoire(double delta_t, HashSet<? extends AbstractObject> obstacles);

	/**
	 * @param bloc
	 *            le bloc par rapport auquel on se situe
	 * @param next_pos
	 *            le prochaine posisition de l'objet
	 * 
	 * Calcul les degrès de liberté du solide par rapport à un bloc donné
	 */
	protected final void calcMargin(AbstractObject bloc, Vecteur next_pos) {
		
		double th = dim.getHeight();
		double tw = dim.getWidth();

		double tx = this.trajectoire.getPosition().getX();
		double ty = this.trajectoire.getPosition().getY();

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

	public void setTrajectoire(Trajectoire trajectoire) {
		this.trajectoire = trajectoire;
	}

	public void setDimension(Dimension dim) {
		this.dim = dim;
	}

	public void setGravity(double g) {
		this.gravity = g;
	}

	public double getGravity() {
		return gravity;
	}

	/**
	 * @return the moves
	 */
	public LibertyDegree getMoves() {
		return moves;
	}

}
