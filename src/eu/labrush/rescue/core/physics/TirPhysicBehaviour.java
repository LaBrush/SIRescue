package eu.labrush.rescue.core.physics;

import java.awt.Dimension;
import java.util.HashSet;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Trajectoire;

/**
 * @author adrienbocquet
 *
 */
public class TirPhysicBehaviour extends AbstractPhysicBehaviour {

	public TirPhysicBehaviour(Trajectoire t, Dimension dim) {
		super(t, dim);
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
		trajectoire.update(delta_t);
	}
	
	@Override
	public void setGravity(double g){
		this.gravity = g ;
		trajectoire.getAcceleration().setY(this.gravity);
	}

}
