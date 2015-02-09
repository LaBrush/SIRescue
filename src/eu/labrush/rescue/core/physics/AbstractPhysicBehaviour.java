package eu.labrush.rescue.core.physics;

import java.util.HashSet;

import eu.labrush.rescue.model.AbstractObject;

/**
 * @author adrienbocquet
 *
 */
public abstract class AbstractPhysicBehaviour {

	public class LibertyDegree {
		public double top = Double.POSITIVE_INFINITY, bottom = Double.POSITIVE_INFINITY, left = Double.POSITIVE_INFINITY,
				right = Double.POSITIVE_INFINITY;
	}

	private AbstractObject target;
	protected LibertyDegree moves;

	AbstractPhysicBehaviour(AbstractObject obj) {
		this.setTarget(obj);
		this.moves = new LibertyDegree();
	}

	public abstract void updateTrajectoire(HashSet<? extends AbstractObject> obstacles, int delta_t);

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
