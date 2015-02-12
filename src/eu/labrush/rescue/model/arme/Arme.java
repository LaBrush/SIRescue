package eu.labrush.rescue.model.arme;

import java.util.HashSet;

import eu.labrush.rescue.model.AbstractModel;

public class Arme extends AbstractModel {

	protected int cartouches = 0;
	protected int degats;

	protected int recharge; // temps de rechargement

	private HashSet<Tir> tirs = new HashSet<Tir>();

	/**
	 * 
	 * @param degats
	 * @param recharge
	 */
	public Arme(int degats, int recharge) {
		super();
		this.degats = degats;
		this.recharge = recharge;
	}

	/**
	 * @return the cartouches
	 */
	public int getCartouches() {
		return cartouches;
	}

	/**
	 * @param cartouches
	 *            le nombre de cartouches
	 */
	public void setCartouches(int cartouches) {
		this.cartouches = cartouches;
		throwUpdate();
	}

	/**
	 * @return the degats
	 */
	public int getDegats() {
		return degats;
	}

	/**
	 * @param degats
	 *            the degats to set
	 */
	public void setDegats(int degats) {
		this.degats = degats;
		throwUpdate();
	}

	/**
	 * @param recharge
	 *            : le temps de rechargement, en millisecondes
	 */
	public void setRecharge(int recharge) {
		this.recharge = recharge;
		throwUpdate();
	}

	/**
	 * @return the recharge
	 */
	public int getRecharge() {
		return recharge;
	}

	/**
	 * @return the tirs
	 */
	public HashSet<Tir> getTirs() {
		return tirs;
	}
}
