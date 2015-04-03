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
		this.b.getTrajectoire().setGravity(false);

		extremiteG = 100;
		extremiteD = 400;
	}

	public void update(Personnage hero) {

		double heroY = hero.getY();
		double heroX = hero.getX();

		double botX = b.getX();
		double botY = b.getY();

		Vecteur v = b.getTrajectoire().getVitesse();

		if (v.getX() == 0) {

			v.setX(0.04);

		}
		else if ((b.getX() <= extremiteG || b.getX() >= extremiteD) && v.getX() != 0) {

			v.setX(-v.getX());
		}

		if ((Math.pow(heroX - botX, 2) < 10000) && (Math.pow(heroY - botY, 2) < 62500)) {

			attack = true;
			extremiteG = heroX - 50;
			extremiteD = heroX + 50;

		}

		if (((Math.pow(heroX - botX, 2) < 100000) && (Math.pow(heroY - botY, 2) < 62500)) && attack == true) {

			double angle, alpha;
			
			alpha = Math.atan((botX - heroX) / (botY - heroY));			

			angle = -Math.toDegrees(alpha)-90;
			
			b.shoot((int) angle);
			
			if (botX <= extremiteG) {

				v.setX(0.06);

			}
			else if (botX >= extremiteD) {

				v.setX(-0.06);
			}

		}

	}
}
