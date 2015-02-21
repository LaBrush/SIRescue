package eu.labrush.rescue.model.arme;

import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Personnage;

public interface Tir {

	public int getDegat();
	public void cross(Personnage p);
	public void cross(Bloc b);
	
}
