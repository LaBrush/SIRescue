package eu.labrush.rescue.model;


/**
 * 
 * Défini un modèle abstrait pour les objets physiques (personnage, bloc, tir...)
 * Tout modèle pouvant être dessiné <b>doit</b> hériter de cette classe
 * 
 * @author adrienbocquet
 * 
 */
public abstract class AbstractObject extends AbstractModel {

	
	protected Trajectoire trajectoire ;
	
	protected int width = 50 ;
	protected int height = 50 ;
	
	/**
	 * @param x
	 * @param y
	 */
	public AbstractObject(double x, double y) {
		super();
		this.setTrajectoire(new Trajectoire(x, y));
	}
	
	public AbstractObject() {
		super();
		this.setTrajectoire(new Trajectoire());
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
		setChanged();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
		setChanged();
	}

	/**
	 * @return the trajectoire
	 */
	public Trajectoire getTrajectoire() {
		return trajectoire;
	}

	/**
	 * @param trajectoire the trajectoire to set
	 */
	public void setTrajectoire(Trajectoire t) {
		if(this.trajectoire != null){
			this.trajectoire.deleteObserver(this);
		}
		
		this.trajectoire = t;
		this.trajectoire.addObserver(this);
		
		setChanged();
	}
	
}
