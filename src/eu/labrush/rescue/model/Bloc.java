package eu.labrush.rescue.model;


/**
 * @author adrienbocquet
 *
 */
public class Bloc extends AbstractObject {
	
	private boolean hurting = false ;
	
	public Bloc(){
		this(0, 0, 0, 0);
	}

	public Bloc(double x, double y, int w, int h) {
		super(x, y, w, h);
	}

	public Bloc(double x, double y, int w, int h, boolean hurting) {
		super(x, y, w, h);
		this.hurting = hurting ;
	}
	
	/**
	 * @return the hurting
	 */
	public boolean isHurting() {
		return hurting;
	}

	/**
	 * @param hurting the hurting to set
	 */
	public void setHurting(boolean hurting) {
		this.hurting = hurting;
		throwUpdate();
	}
	
	public boolean touch(AbstractObject o){
		return !(o.getX() + o.getWidth() < this.getX() || o.getY() + o.getHeight() < this.getY() || o.getX() > this.getX() + this.getWidth() || o.getY() > this.getY() + this.getHeight());
	}
}
