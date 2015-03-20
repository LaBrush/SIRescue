package eu.labrush.rescue.IA.behaviour;

import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

/**
 * @author adrienbocquet
 * @author solineducousso
 * 
 * Définit le comportement d'un bot assigné à un bloc et attaquant
 * en allant au contact du héro
 * 
 */
public class ToucherBotBehaviour implements BotBehaviour {

	private Bot b;
	private Bloc bloc ;
	
	double botInitialX, botInitialY;
	boolean attack ;

	public ToucherBotBehaviour(Bot b) {
		setBot(b);
	}

	
	public ToucherBotBehaviour(Bloc bloc) {
		this.bloc = bloc ;
	}
	
	@Override
	public void setBot(Bot bot) {
		this.b = bot;
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
