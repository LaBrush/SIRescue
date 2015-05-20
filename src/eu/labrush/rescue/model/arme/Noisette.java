package eu.labrush.rescue.model.arme;

import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

/**
 * @author adrienbocquet
 *
 */
public class Noisette extends Transistor {

	public Noisette(Vecteur position, int angle, int degat, int recul, Personnage owner){
		super(position, angle, degat, recul, owner);
		
		setImage("noisette.png");
		getDimension().setSize(20, 20);
		
		update(null, null);
	}
	
}
