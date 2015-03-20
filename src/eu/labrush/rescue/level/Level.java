package eu.labrush.rescue.level;

<<<<<<< HEAD
=======
import eu.labrush.rescue.IA.behaviour.FlyBotBehaviour;
import eu.labrush.rescue.IA.behaviour.ToucherBotBehaviour;
>>>>>>> origin/master
import eu.labrush.rescue.controler.BlocControler;
import eu.labrush.rescue.controler.BotControler;
import eu.labrush.rescue.controler.HeroControler;
import eu.labrush.rescue.controler.PersonnageControler;
import eu.labrush.rescue.controler.TirControler;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;

/**
 *
 * Gère tous les controlleurs, vues et modèles d'un niveau
 * 
 * TODO: ajouter un destructeur pour enlever les listener des coeurs graphique et physique
 * 
 * TODO: créer un loader XML pour les niveaux
 *
 * @author adrienbocquet
 */
public class Level {

	GraphicCore graphics;
	PhysicCore physics;

	PersonnageControler personnageControler;
	BlocControler blocControler;
	public HeroControler heroControler;
	TirControler tirControler;
	BotControler botControler;

	/**
	 * @param graphics
	 * @param physics
	 */
	public Level(GraphicCore graphics, PhysicCore physics) {
		super();
		this.graphics = graphics;
		this.physics = physics;

		blocControler = new BlocControler(this);
		personnageControler = new PersonnageControler(this, blocControler);
		tirControler = new TirControler(this, blocControler, personnageControler);
		heroControler = new HeroControler(this, tirControler, personnageControler);
		botControler = new BotControler(this, personnageControler, tirControler, heroControler);
<<<<<<< HEAD
=======
		
		botControler.add(new Bot(300,300), new FlyBotBehaviour());
		botControler.add(new Bot(260,175), new ToucherBotBehaviour(blocControler.getBloc(2)));
		botControler.add(new Bot(320,215), new ToucherBotBehaviour(blocControler.getBloc(3)));	
	}

	/**
	 * Ajoute des blocs aux quatres coins de l'écran afin d'éviter tout débordement du panneau
	 */
	private void addBorders() {

		int width = this.graphics.getPan().getWidth(), height = this.graphics.getPan().getHeight();

		blocControler.add(new Bloc(0, height - 10, width, 10)); // mur en haut
		blocControler.add(new Bloc(0, 0, 10, height + 100)); // mur à gauche
		blocControler.add(new Bloc(width - 10, 0, 10, height + 100)); // mur à droite
		blocControler.add(new Bloc(0, 0, width, 10)); // mur en bas
>>>>>>> origin/master
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
