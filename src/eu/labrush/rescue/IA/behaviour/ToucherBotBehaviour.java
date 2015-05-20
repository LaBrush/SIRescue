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
 *         Définit le comportement d'un bot assigné à un bloc et attaquant en allant au contact du
 *         héro
 * 
 */
public class ToucherBotBehaviour implements BotBehaviour, Observer {

	private Bot b;
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
				b.getTrajectoire().getVitesse().setX((int) arg1.second);
				b.getTrajectoire().getVitesse().setY(Math.abs((int) arg1.second));
			}
		}
	}

	/**
	 * Make the bot acting
	 */
	public void update(Personnage hero) {

		if (!hasShield) {
			b.shoot(0);
			hasShield = true;
		}

		if (!b.isHurted()) {
			Vecteur v = b.getTrajectoire().getVitesse();

			double botX = b.getX();
			double botY = b.getY();

			double heroX = hero.getX();
			double heroY = hero.getY();
			
			v.setX(vitesse);

			if ((Math.pow(heroX - botX, 2) + Math.pow(heroY - botY, 2)) > 10000 && attack == false) { // fait des allers retours sur son bloc jusqu'� voir le h�ro
				if (v.getX() == 0) {
					v.setX(vitesse);

				}
				else if ((botX >= (bloc.getX() + bloc.getWidth() - b.getWidth()) && v.getX() > 0)
						|| (b.getX() <= (bloc.getX()) && v.getX() < 0)) {
					v.setX(-vitesse);
				}

			}
			else if ((Math.pow(heroX - botX, 2) + Math.pow(heroY - botY, 2)) < 10000 && attack == false) { // d�bute l'attaque
				attack = true;
			}
			else if ((Math.pow(heroX - botX, 2) + Math.pow(heroY - botY, 2)) < 90000 && attack == true) { // attaque
				vitesse = 50;

				if (heroX <= botX && botX >= bloc.getX()) {
					v.setX(-vitesse);
				}				
				else if (heroX > botX && botX < bloc.getX() + bloc.getWidth() - b.getWidth()) {

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
		this.b = bot;
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
