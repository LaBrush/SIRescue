package eu.labrush.rescue.core.physics;

import java.util.HashSet;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Trajectoire;

public class FollowPhysicBehaviour extends AbstractPhysicBehaviour {

	public FollowPhysicBehaviour(Trajectoire trajectoire, AbstractObject master){
		this.trajectoire = trajectoire ;
		trajectoire.setPosition(master.getTrajectoire().getPosition());				
	}
	
	@Override
	public void updateTrajectoire(double delta_t, HashSet<? extends AbstractObject> obstacles) {}

}
