package eu.labrush.rescue.core.physics;

import java.util.HashSet;

import eu.labrush.rescue.model.AbstractObject;

/**
 * @author adrienbocquet
 *
 */
public abstract class AbstractPhysicBehaviour {

	public class LibertyDegree {
		public boolean top = true, bottom = true, left = true, right = true;

		public boolean equals(LibertyDegree other) {
			// Vérification de l'égalité des références
			if (other == this) {
				return true;
			}

			return this.top == other.top && this.bottom == other.bottom && this.left == other.left && this.right == other.right;
		}
	}

	private AbstractObject target;
	protected LibertyDegree moves, previous;

	AbstractPhysicBehaviour(AbstractObject obj) {
		this.setTarget(obj);
		this.moves = new LibertyDegree();
	}

	abstract LibertyDegree calcDegrees(HashSet<? extends AbstractObject> obstacles);
	abstract public void adaptToDegrees();
	
	public void updateTrajectoire(HashSet<? extends AbstractObject> obstacles, int delta_t) {
		
		//On calcul les degrés de liberté possibles
		this.moves = this.calcDegrees(obstacles);
				
		//On les rapporte sur la trajectoire
		this.adaptToDegrees();
		this.getTarget().getTrajectoire().update(delta_t);

		//TODO: Systeme de détection et de correction des changements de position trop importants entrainant la collsition d'un objet dans un autre
		
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
