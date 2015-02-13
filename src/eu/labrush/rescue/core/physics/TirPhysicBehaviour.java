package eu.labrush.rescue.core.physics;

import java.util.HashSet;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Trajectoire;
import eu.labrush.rescue.model.arme.Tir;

/**
 * @author adrienbocquet
 *
 */
public class TirPhysicBehaviour extends AbstractPhysicBehaviour {

	public TirPhysicBehaviour(Tir obj) {
		super(obj);
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
		Trajectoire t = this.getTarget().getTrajectoire();
		t.update(delta_t);
	}

}
