package eu.labrush.rescue.core;

import eu.labrush.rescue.core.audio.AudioCore;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.level.SampleLevel;

/**
 * @author solineducousso
 * @author nicolasballas
 * @author daveanhnguyenkhac
 * @author adrienbocquet
 */
public class SIRescue {

	public static void main(String[] args) {

		AudioCore audio = new AudioCore();
		GraphicCore graphics = GraphicCore.getInstance();
		PhysicCore physics = new PhysicCore(GraphicCore.FRAMERATE * 2);

		//audio.add(new Bloc(), "resources/audio/explosions_transistors.wav");

		/*
		 * @SuppressWarnings("unused") SampleLevel level = new SampleLevel(graphics, physics);
		 * graphics.start(); physics.start();
		 */

		/*LevelManager manager = new LevelManager(graphics, physics, audio);
		manager.load("game.xml");
		manager.loadLevel();
		*/
		
		Level level = new SampleLevel(graphics, physics, audio);
		
		graphics.start();
		physics.start();
		
		//audio.play(new Bloc());

	}
}
