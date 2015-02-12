/**
 * 
 */
package eu.labrush.rescue.controler;

import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;

/**
 * @author adrienbocquet
 *
 */
public abstract class AbstractControler {

	Level level ;
	
	GraphicCore graphics ;
	PhysicCore physics ;
	
	AbstractControler(Level level){
		this.level = level ;
		
		this.graphics = level.getGraphics() ;
		this.physics = level.getPhysics() ;
	}
	
}
