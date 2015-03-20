package eu.labrush.rescue.IA.behaviour;

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
public class FlyBotBehaviour implements BotBehaviour {

	private Bot b;
	
	double  extremiteG, extremiteD;
	boolean attack ;
	

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
		
		Vecteur v = b.getTrajectoire().getVitesse();
		
		if (v.getX() == 0){
			
			v.setX(0.04);
			
		}else if ((b.getX() <= extremiteG || b.getX() >= extremiteD) && v.getX() != 0){
			
			v.setX(-v.getX());
		}
		
		
		
		
		
		
	}
}
