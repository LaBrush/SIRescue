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
	
	double  vitesse;
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
		attack = false;		
		vitesse = 0.02;
		
	}
	
	public void update(Personnage hero) {

		Vecteur v = b.getTrajectoire().getVitesse();

		double botX = b.getX();
		double botY = b.getY();

		double heroX = hero.getX();
		double heroY = hero.getY();

		if ((Math.pow(heroX - botX, 2) + Math.pow(heroY - botY, 2)) > 10000 && attack == false) {
			if (v.getX() == 0) {
				v.setX(vitesse);
				
			} else if ((b.getX() >= (bloc.getX()+bloc.getWidth() - b.getWidth()) && v.getX() > 0) || (b.getX() <= (bloc.getX()) && v.getX() < 0)) {
				v.setX(-v.getX());
			}
			
		} else if ((Math.pow(heroX - botX, 2) + Math.pow(heroY - botY, 2)) < 10000 && attack == false) {			

			attack = true;
			
			
		}else if((Math.pow(heroX - botX, 2) + Math.pow(heroY - botY, 2)) < 90000 && attack == true){
			vitesse = 0.05;
			
			if (heroX < botX) {
				v.setX(-vitesse);
			}
			if (heroX > botX) {
				v.setX(vitesse);
			}
			
			
		}else if ((Math.pow(heroX - botX, 2) + Math.pow(heroY - botY, 2)) > 90000 && attack == true){
			
			
			vitesse = 0;			
			v.setX(vitesse);			
			
			
		}
		
	}
}
