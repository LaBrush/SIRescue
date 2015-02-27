package eu.labrush.rescue.IA.behaviour;

import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Vecteur;

/**
 * @author adrienbocquet
 *
 */
public class AbstractBotBehaviour {

	public void update(Bot b) {
		
		Vecteur v = b.getTrajectoire().getVitesse() ;
		if(v.getX() == 0)
		{
			System.out.println("bip");
			v.setX(.2);
		}
		else if((b.getX() < 200 && v.getX() < 0) || (b.getX() > 380  && v.getX() > 0)){
			v.setX(-v.getX());
		}
	}

}
