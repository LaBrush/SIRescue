package eu.labrush.rescue.model;


/**
 * @author adrienbocquet
 *
 */
public class Bloc extends AbstractObject {

	private int id ;
	
	public Bloc(double x, double y, int w, int h){
		this(x, y, w, h, 0);
	}

	public Bloc(double x, double y, int w, int h, int id) {
		super(x, y, w, h);
		this.setMovable(false);	
		this.setId(id) ;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
}
