package eu.labrush.rescue.model.arme;

import eu.labrush.rescue.model.AbstractObject;

public interface Tir {

	public int getAngle();
	public int getDegat();
	public boolean cross(AbstractObject o);
	
}
