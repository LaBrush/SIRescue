/**
 * 
 */
package eu.labrush.rescue.level;

import java.util.HashSet;

import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.model.Personnage;

/**
 * @author adrienbocquet
 *
 */
public class Level {

	GraphicCore graphics ;
	PhysicCore physics;
	
	HashSet<Personnage> personnages = new HashSet<Personnage>() ;
	//HashSet<Bloc> blocs = new HashSet<Bloc>() ;
	
	/**
	 * @param graphics
	 * @param physics
	 */
	public Level(GraphicCore graphics, PhysicCore physics) {
		super();
		this.graphics = graphics;
		this.physics = physics;
	}
	
	
	
}
