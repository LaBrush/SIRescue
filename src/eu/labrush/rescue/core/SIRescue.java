package eu.labrush.rescue.core;

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

		GraphicCore graphics = GraphicCore.getInstance();
		PhysicCore physics = new PhysicCore(GraphicCore.FRAMERATE * 2);


		/*LevelManager manager = new LevelManager(graphics, physics);
		manager.load("game.xml");
		manager.loadLevel();*/
		
		Level level = new SampleLevel(graphics, physics);
		
		graphics.start();
		physics.start();
	}
}
