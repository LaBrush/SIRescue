package eu.labrush.rescue.level;

import eu.labrush.rescue.controler.AchievementControler;
import eu.labrush.rescue.controler.BlocControler;
import eu.labrush.rescue.controler.BotControler;
import eu.labrush.rescue.controler.HeroControler;
import eu.labrush.rescue.controler.ItemControler;
import eu.labrush.rescue.controler.PersonnageControler;
import eu.labrush.rescue.controler.TirControler;
import eu.labrush.rescue.core.audio.AudioCore;
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

	AudioCore audio;
	GraphicCore graphics;
	PhysicCore physics;

	PersonnageControler personnageControler;
	BlocControler blocControler;
	HeroControler heroControler;
	TirControler tirControler;
	BotControler botControler;
	ItemControler itemControler;
	AchievementControler achievementControler;

	/**
	 * @param graphics
	 *            le coeur graphique
	 * @param physics
	 *            le coeur physique
	 * @param audio 
	 */
	public Level(GraphicCore graphics, PhysicCore physics, AudioCore audio) {
		super();
		this.graphics = graphics;
		this.physics = physics;
		this.audio = audio;

		blocControler = new BlocControler(this);

		personnageControler = new PersonnageControler(this, blocControler);
		tirControler = new TirControler(this, blocControler, personnageControler);
		heroControler = new HeroControler(this, tirControler, personnageControler);
		botControler = new BotControler(this, personnageControler, tirControler, heroControler);

		itemControler = new ItemControler(this, heroControler);

		achievementControler = new AchievementControler(this, heroControler, blocControler);
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

	/**
	 * @return the achievementControler
	 */
	public AchievementControler getAchievementControler() {
		return achievementControler;
	}

}
