package eu.labrush.rescue.core;

import java.awt.Color;
import java.util.ArrayList;

import eu.labrush.rescue.IA.star.AStar;
import eu.labrush.rescue.IA.star.Point;
import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.utils.Listener;

/**
 * @author adrienbocquet
 *
 */
public class SIRescue {

	public static void main(String[] args) {
		GraphicCore graphics = GraphicCore.getInstance();
		PhysicCore physics = new PhysicCore(GraphicCore.FRAMERATE * 2);
		
		Level level = new Level(graphics, physics);

		graphics.start();
		physics.start();

		AStar star = new AStar(level.getBlocControler().getBlocs(), graphics.getWidth(), graphics.getHeight(), 10);
		ArrayList<Point> trajet = star.findPath(new Point(5, 5), new Point(44,30));

		for (Point p : trajet) {
			System.out.println(p);
		}

		
		graphics.suscribe(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				for (Point p : trajet) {
					req.rect(p.x * 10, p.y * 10, 1, 1, Color.BLACK);
				}
			}
		});

	}
}
