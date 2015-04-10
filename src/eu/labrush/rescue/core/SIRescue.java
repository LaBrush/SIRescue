package eu.labrush.rescue.core;

import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.SampleLevel;

/**
 * @author adrienbocquet
 *
 */
public class SIRescue {

	public static void main(String[] args) {
		GraphicCore graphics = GraphicCore.getInstance();
		PhysicCore physics = new PhysicCore(GraphicCore.FRAMERATE * 2);
		
		
		@SuppressWarnings("unused")
		SampleLevel level = new SampleLevel(graphics, physics);
		
		/*Level level = new Level(graphics, physics);
		LevelLoader loader = new LevelLoader();
		loader.load(level, "level.xml");*/	
		
		graphics.start();
		physics.start();
	}
}
