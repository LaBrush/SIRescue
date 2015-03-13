package eu.labrush.rescue.IA.behaviour;

import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

/**
 * @author adrienbocquet
 *
 */
public class AbstractBotBehaviour {

	public void update(Bot b, Personnage hero) {
		
		Vecteur v = b.getTrajectoire().getVitesse() ;
		
		double botX = b.getX();
		double botY = b.getY();
		
		double heroX = hero.getX();
		double heroY = hero.getY();
		
		if ((Math.pow(heroX-botX,2) + Math.pow(heroY-botY,2))> 10000){
			if(v.getX() == 0)
			{			
				v.setX(.02);
			}
			else if((b.getX() < 200 && v.getX() < 0) || (b.getX() > 250  && v.getX() > 0)){
				v.setX(-v.getX());
			}
		}
		else {
			v.setX(0);
			
			if (heroX < botX){
				v.setX(-0.05);
			}
			if (heroX > botX){
				v.setX(0.05);
				
			}
		}
	}

}
