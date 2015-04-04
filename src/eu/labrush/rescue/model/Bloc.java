package eu.labrush.rescue.model;


/**
 * @author adrienbocquet
 *
 */
public class Bloc extends AbstractObject {
	
	public Bloc(){
		this(0, 0, 0, 0);
	}

	public Bloc(double x, double y, int w, int h) {
		super(x, y, w, h);
		this.setMovable(false);	
	}
}
