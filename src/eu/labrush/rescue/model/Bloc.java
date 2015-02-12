/**
 * 
 */
package eu.labrush.rescue.model;

/**
 * @author adrienbocquet
 *
 */
public class Bloc extends AbstractObject {

	public Bloc(double x, double y, int width, int height){
		super(x, y);
		this.setWidth(width);
		this.setHeight(height);
		this.setMovable(false);		
	}
	
}
