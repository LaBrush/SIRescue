package eu.labrush.rescue.IA.behaviour;

import java.util.Observable;
import java.util.Observer;

import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;
import eu.labrush.rescue.utils.Tuple;

/**
 * @author adrienbocquet
 * @author solineducousso
 * 
 *         D√©finit le comportement d'un bot assign√© √† un bloc et attaquant en allant au contact du
 *         h√©ro
 * 
 */
public class ToucherBotBehaviour implements BotBehaviour, Observer {

	private Bot bot;
	private Bloc bloc;

	double vitesse;
	boolean attack, hasShield = false;

	public ToucherBotBehaviour(Bot bot, Bloc bloc) {
		setBot(bot);
		setBloc(bloc);
	}

	public ToucherBotBehaviour(Bloc bloc) {
		super();
		this.bloc = bloc;
	}

	public ToucherBotBehaviour() {
		super();
	}

	/**
	 * Updates as a model observer
	 */
	public void update(Observable o, Object arg) {
		if (arg instanceof Tuple) {
			Tuple<?, ?> arg1 = (Tuple<?, ?>) arg;
			if (arg1.first.equals("hurted") && (int)arg1.second != 0) {
				bot.getTrajectoire().getVitesse().setX((int) arg1.second);
				bot.getTrajectoire().getVitesse().setY(Math.abs((int) arg1.second));
			}
		}
	}

	/**
	 * Make the bot acting
	 */
	public void update(Personnage hero) {

		if (!hasShield) {
			bot.shoot(0);
			hasShield = true;
		}

		if (!bot.isHurted()) {
			Vecteur v = bot.getTrajectoire().getVitesse();

			double botX = bot.getX();
			double botY = bot.getY();

			double heroX = hero.getX();
			double heroY = hero.getY();
			
			v.setX(vitesse);

			if ((Math.pow(heroX - botX, 2) + Math.pow(heroY - botY, 2)) > 10000 && attack == false) { // fait des allers retours sur son bloc jusqu'‡ voir le hÈro
				if (botX >= (bloc.getX() + bloc.getWidth() - bot.getWidth()) && v.getX() > 0) {
					v.setX(-vitesse);

				}
				else if ((botX <= bloc.getX() && v.getX() < 0)) {
					v.setX(vitesse);
				}

			}
			else if ((Math.pow(heroX - botX, 2) + Math.pow(heroY - botY, 2)) < 10000 && attack == false) { // dÈbute l'attaque
				attack = true;
			}
			else if ((Math.pow(heroX - botX, 2) + Math.pow(heroY - botY, 2)) < 90000 && attack == true) { // attaque
				vitesse = 50;

				if (heroX <= botX && botX >= bloc.getX()) {
					v.setX(-vitesse);
				}				
				else if (heroX > botX && botX <= bloc.getX() + bloc.getWidth() - bot.getWidth()) {
					v.setX(vitesse);
				}
				
			}
			else {
					v.setX(0);
				}
							
		}

	}

	@Override
	public void setBot(Bot bot) {
		this.bot = bot;
		attack = false;
		vitesse = 20;

		bot.addObserver(this);
	}

	/**
	 * @return the bloc
	 */
	public Bloc getBloc() {
		return bloc;
	}

	/**
	 * @param bloc
	 *            the bloc to set
	 */
	public void setBloc(Bloc bloc) {
		this.bloc = bloc;
	}

}
