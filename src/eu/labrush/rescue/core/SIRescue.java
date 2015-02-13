package eu.labrush.rescue.core;

import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;

/**
 * @author adrienbocquet
 *
 */
public class SIRescue {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		GraphicCore graphics = GraphicCore.getInstance();
		PhysicCore physics = new PhysicCore(GraphicCore.FRAMERATE * 2);

		Level level = new Level(graphics, physics);
		
		graphics.start();
		physics.start();
	}

}
