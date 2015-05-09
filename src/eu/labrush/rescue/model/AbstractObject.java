package eu.labrush.rescue.model;

import java.awt.Dimension;

import eu.labrush.rescue.core.physics.AbstractPhysicBehaviour;
import eu.labrush.rescue.core.physics.ClassicPhysicBehaviour;

/**
 * 
 * Défini un modèle abstrait pour les objets physiques (personnage, bloc, tir...) Tout modèle
 * pouvant être dessiné <b>doit</b> hériter de cette classe
 * 
 * @author adrienbocquet
 * 
 */
public abstract class AbstractObject extends AbstractModel {

	protected Trajectoire trajectoire;
	protected AbstractPhysicBehaviour behaviour;
	protected Dimension dim;

	private String image = "" ;
	
	public AbstractObject(double x, double y, int width, int height, AbstractPhysicBehaviour behaviour) {
		super();
		this.setTrajectoire(new Trajectoire(x, y));
		this.dim = new Dimension(width, height);

		this.behaviour = behaviour;
		this.behaviour.setTrajectoire(this.trajectoire);
		this.behaviour.setDimension(this.dim);
	}

	public AbstractObject(double x, double y, int width, int height) {
		this(x, y, width, height, new ClassicPhysicBehaviour());
	}

	public AbstractObject() {
		super();
		this.setTrajectoire(new Trajectoire());
	}

	public double getWidth() {
		return dim.getWidth();
	}

	public double getHeight() {
		return dim.getHeight();
	}

	public double getX(){
		return this.getTrajectoire().getPosition().getX();
	}
	
	public double getY(){
		return this.getTrajectoire().getPosition().getY();
	}

	public Dimension getDimension() {
		return dim;
	}
	
	public void setDimension(Dimension dim) {
		this.dim = dim ;
	}

	/**
	 * @return the trajectoire
	 */
	public Trajectoire getTrajectoire() {
		return trajectoire;
	}

	public void setTrajectoire(Trajectoire t) {
		if (this.trajectoire != null) {
			this.trajectoire.deleteObserver(this);
		}

		this.trajectoire = t;
		this.trajectoire.addObserver(this);

		setChanged();
	}

	/**
	 * @return the behaviour
	 */
	public AbstractPhysicBehaviour getPhysicBehaviour() {
		return behaviour;
	}

	/**
	 * @param behaviour
	 *            the behaviour to set
	 */
	public void setPhysicBehaviour(AbstractPhysicBehaviour behaviour) {
		this.behaviour = behaviour;
		throwUpdate();
	}

	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
		setChanged();
		notifyObservers("image");
	}

}
