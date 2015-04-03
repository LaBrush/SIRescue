package eu.labrush.rescue.level;

import eu.labrush.rescue.IA.behaviour.BossBehaviour;
import eu.labrush.rescue.IA.behaviour.FlyBotBehaviour;
import eu.labrush.rescue.IA.behaviour.ToucherBotBehaviour;
import eu.labrush.rescue.controler.BotControler;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.arme.Arme;

/**
 *
 * Gère tous les controlleurs, vues et modèles d'un niveau
 * 
 * TODO: ajouter un destructeur pour enlever les listener des coeurs graphique et physique
 *
 * @author adrienbocquet
 */
public class SampleLevel extends Level {

	/**
	 * @param graphics
	 * @param physics
	 */
	public SampleLevel(GraphicCore graphics, PhysicCore physics) {
		super(graphics, physics);
		
		Personnage hero = new Personnage(15, 15);
		hero.addArme(new Arme("Transistor", 10, 200));
		
		heroControler.setPersonnage(hero);
		personnageControler.add(hero);
		
		this.addBorders();
		blocControler.add(new Bloc(60, 100, 80, 20, 1));
		blocControler.add(new Bloc(200, 140, 80, 20, 2));
		blocControler.add(new Bloc(300, 180, 80, 20, 3));
		blocControler.add(new Bloc(430, 80, 20, 20));
		
		botControler = new BotControler(this, personnageControler, tirControler, heroControler);
		
		Bot aerien = new Bot(300,300);
		aerien.addArme(new Arme("Resistance", 10, 750));
		botControler.add(aerien, new FlyBotBehaviour());
		
		
		botControler.add(new Bot(260,175), new BossBehaviour(blocControler.getBlocs()));
		botControler.add(new Bot(260,50), new ToucherBotBehaviour(blocControler.getBloc(2)));
		botControler.add(new Bot(320,215), new ToucherBotBehaviour(blocControler.getBloc(3)));	
	}

	/**
	 * Ajoute des blocs aux quatres coins de l'écran afin d'éviter tout débordement du panneau
	 */
	private void addBorders() {

		int width = this.graphics.getPan().getWidth(), height = this.graphics.getPan().getHeight();

		blocControler.add(new Bloc(0, height+1, width, 10)); // mur en haut
		blocControler.add(new Bloc(-1, 0, 0, height + 100)); // mur à gauche
		blocControler.add(new Bloc(width, 0, 10, height + 100)); // mur à droite
		blocControler.add(new Bloc(-1, 0, width+1, 10)); // mur en bas
	}

	/**
	 * @return the graphic core
	 */
	public GraphicCore getGraphics() {
		return graphics;
	}

	/**
	 * @return the physic core
	 */
	public PhysicCore getPhysics() {
		return physics;
	}

}
