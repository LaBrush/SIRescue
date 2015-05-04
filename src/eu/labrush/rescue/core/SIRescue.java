package eu.labrush.rescue.core;

import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.LevelManager;

/**
 * @author solineducousso
 * @author nicolasballas
 * @author daveanhnguyenkhac
 * @author adrienbocquet
 */
public class SIRescue {

	public static void main(String[] args) {

		GraphicCore graphics = GraphicCore.getInstance();
		PhysicCore physics = new PhysicCore(GraphicCore.FRAMERATE * 2);

		//audio.add(new Bloc(), "resources/audio/explosions_transistors.wav");

		/*
		 * @SuppressWarnings("unused") SampleLevel level = new SampleLevel(graphics, physics);
		 * graphics.start(); physics.start();
		 */

		LevelManager manager = new LevelManager(graphics, physics);
		manager.load("game.xml");
		manager.loadLevel();

		//audio.play(new Bloc());

	}
}
