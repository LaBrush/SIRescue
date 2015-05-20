package eu.labrush.rescue.IA.behaviour;

import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

/**
 * @author adrienbocquet
 * @author solineducousso
 * 
 *         Définit le comportement d'un bot assigné à un bloc et attaquant en allant au contact du
 *         héro
 * 
 */
public class FlyBotBehaviour implements BotBehaviour {

	private Bot b;

	double extremiteG, extremiteD;
	boolean attack;
	long attente;

	public FlyBotBehaviour(Bot b) {
		setBot(b);
	}

	public FlyBotBehaviour() {
	}

	@Override
	public void setBot(Bot bot) {
		this.b = bot;
		b.getPhysicBehaviour().setGravity(0);
		//extremiteG = 100;
		//extremiteD = 400;
	}

	public void update(Personnage hero) {

		double heroY = hero.getY();
		double heroX = hero.getX();

		double botX = b.getX();
		double botY = b.getY();

		Vecteur v = b.getTrajectoire().getVitesse();
		v.setX(0);
		
		if (v.getX() != 0 && attack == false) {

			v.setX(0);

		}
		
		if ((Math.pow(heroX - botX, 2) < 10000) && attack == false) {

			attack = true;
			
		}
		else if ((Math.pow(heroX - botX, 2) < 122500) && attack == true) {

			double angle, alpha;			
			alpha = Math.atan((botX - heroX) / (botY - heroY));		
			angle = -Math.toDegrees(alpha)-90;			
			b.shoot((int) angle);
			
			extremiteG = heroX - 50;
			extremiteD = heroX + 50;

			
			if (botX <= extremiteG) {

				v.setX(60);

			}
			else if (botX >= extremiteD) {

				v.setX(-60);
			}

		}

	}

}
