package eu.labrush.rescue.model;


/**
 * @author adrienbocquet
 *
 */
public class Bloc extends AbstractObject {

	public Bloc(double x, double y, int w, int h){
		super(x, y, w, h);
		this.setMovable(false);		
	}
	
}
