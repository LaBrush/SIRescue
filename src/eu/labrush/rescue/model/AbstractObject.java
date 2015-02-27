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

	protected boolean movable = true;

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param behaviour
	 */
	public AbstractObject(double x, double y, int width, int height, AbstractPhysicBehaviour behaviour) {
		super();
		this.setTrajectoire(new Trajectoire(x, y));
		this.dim = new Dimension(width, height);

		this.behaviour = behaviour;
		this.behaviour.setTrajectoire(this.trajectoire);
		this.behaviour.setDimension(this.dim);
	}

	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
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

	/**
	 * @return the trajectoire
	 */
	public Trajectoire getTrajectoire() {
		return trajectoire;
	}

	/**
	 * @param trajectoire
	 *            the trajectoire to set
	 */
	public void setTrajectoire(Trajectoire t) {
		if (this.trajectoire != null) {
			this.trajectoire.deleteObserver(this);
		}

		this.trajectoire = t;
		this.trajectoire.addObserver(this);

		setChanged();
	}

	/**
	 * @return the movable
	 */
	public boolean isMovable() {
		return movable;
	}

	/**
	 * @param movable
	 *            the movable to set
	 */
	public void setMovable(boolean movable) {
		this.movable = movable;
		throwUpdate();
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

}
