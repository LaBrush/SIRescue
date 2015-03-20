/**
 * 
 */
package eu.labrush.rescue.controler;

import java.util.Observable;

import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;

/**
 * @author adrienbocquet
 *
 */
public abstract class AbstractControler extends Observable {

	Level level ;
	
	GraphicCore graphics ;
	PhysicCore physics ;
	
	AbstractControler(Level level){
		this.level = level ;
		
		this.graphics = level.getGraphics() ;
		this.physics = level.getPhysics() ;
	}
	
	protected void throwUpdate() {
		setChanged();
		notifyObservers();
	}
	
}
