package eu.labrush.rescue.level;

import java.util.ArrayList;

import eu.labrush.rescue.controler.BlocControler;
import eu.labrush.rescue.controler.BotControler;
import eu.labrush.rescue.controler.HeroControler;
import eu.labrush.rescue.controler.PersonnageControler;
import eu.labrush.rescue.controler.TirControler;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;
import eu.labrush.rescue.model.arme.Arme;

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

	private PersonnageControler personnageControler;
	private BlocControler blocControler;
	private HeroControler heroControler;
	private TirControler tirControler;
	private BotControler botControler;

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
		heroControler = new HeroControler(this, tirControler);

		Personnage hero = new Personnage(15, 15);
		hero.setVitesseNominale(new Vecteur(.3, .9));
		hero.addArme(new Arme(10, 10));
		
		heroControler.setPersonnage(hero);
		personnageControler.add(hero);
		
		this.addBorders();
		blocControler.add(new Bloc(60, 100, 80, 20));
		blocControler.add(new Bloc(200, 140, 80, 20));
		blocControler.add(new Bloc(300, 180, 80, 20));
		blocControler.add(new Bloc(430, 80, 20, 20));
		
		botControler = new BotControler(this, personnageControler, tirControler, heroControler);
		ArrayList<Bot> ennemis = new ArrayList<Bot>();
		
		ennemis.add(new Bot(50,15));
		ennemis.add(new Bot(350,215));
		
		for(Bot ennemi : ennemis){
			botControler.add(ennemi);
			personnageControler.add(ennemi);
		}
		
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
