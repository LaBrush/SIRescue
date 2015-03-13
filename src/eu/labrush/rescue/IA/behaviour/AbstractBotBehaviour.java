package eu.labrush.rescue.IA.behaviour;

import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

/**
 * @author adrienbocquet
 *
 */
public class AbstractBotBehaviour {

	private Bot b;

	double botInitialX, botInitialY;
	boolean attack ;

	public AbstractBotBehaviour(Bot b) {
		this.b = b;
		botInitialX = b.getX();
		botInitialY = b.getY();
		attack = false;
	}

	public void update(Personnage hero) {

		Vecteur v = b.getTrajectoire().getVitesse();

		double botX = b.getX();
		double botY = b.getY();

		double heroX = hero.getX();
		double heroY = hero.getY();

		if ((Math.pow(heroX - botX, 2) + Math.pow(heroY - botY, 2)) > 10000 && attack == false) {
			if (v.getX() == 0) {
				v.setX(.02);
				
			} else if ((b.getX() >= (botInitialX + 20) && v.getX() > 0) || (b.getX() <= (botInitialX - 40) && v.getX() < 0)) {
				v.setX(-v.getX());
			}
		} else if (attack == false){
			v.setX(0);

			if (heroX < botX) {
				v.setX(-0.05);
			}
			if (heroX > botX) {
				v.setX(0.05);
			}
			
		} else if (attack == true){
			
		}
		
	}

}
