package eu.labrush.rescue.model.arme;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import eu.labrush.rescue.model.AbstractModel;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

public class Arme extends AbstractModel implements Cloneable {

	private int cartouchesLeft;
	private int damage;

	private int reloadTime; // temps de rechargement
	private long lastShootTime = 0; // Date UNIX du dernier tir

	private String tirClass;
	private Personnage owner;

	/**
	 * 
	 * @param tirClass
	 *            La classe dont l'instance du Tir va hériter (pattern factory)
	 * @param damage
	 *            Les dommages inflgés pour le tir
	 * @param reloadTime
	 *            L'intervalle entre deux tirs
	 * @param cartouchesLeft
	 *            Le nombre de cartoucges restantes (un nombre négatif de cartouches revient à en
	 *            avoir une infinité)
	 */
	public Arme(String tirClass, int damage, int reloadTime, int cartouchesLeft) {
		super();
		this.tirClass = "eu.labrush.rescue.model.arme." + tirClass;
		this.damage = damage;
		this.reloadTime = reloadTime;
		this.cartouchesLeft = cartouchesLeft;
	}

	public Arme(String tirClass, int damage, int reloadTime) {
		super();
		this.tirClass = "eu.labrush.rescue.model.arme." + tirClass;
		this.damage = damage;
		this.reloadTime = reloadTime;
		this.cartouchesLeft = -1;
	}

	public Tir shoot(Vecteur position, int angle) {
		if (System.currentTimeMillis() - lastShootTime < reloadTime) {
			return null;
		}

		Tir tir = null;

		try {
			// On crée un objet Class
			Class<?> cl = Class.forName(tirClass);

			// On crée les paramètres du constructeur
			Class<?>[] types = new Class[] { Vecteur.class, int.class, int.class, Personnage.class };

			// On récupère le constructeur avec les deux paramètres
			Constructor<?> ct = cl.getConstructor(types);

			// On instancie l'objet avec le constructeur surchargé !
			tir = (Tir) ct.newInstance(new Object[] { position, angle, damage, owner });

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

		if (cartouchesLeft > 0){
			cartouchesLeft--;
		}
			
		lastShootTime = System.currentTimeMillis();
		
		throwUpdate();
		return tir;
	}

	public Arme clone() {
		Object o = null;
		try {
			// On récupère l'instance à renvoyer par l'appel de la
			// méthode super.clone()
			o = super.clone();
		} catch (CloneNotSupportedException cnse) {
			// Ne devrait jamais arriver car nous implémentons
			// l'interface Cloneable
			cnse.printStackTrace(System.err);
		}
		// on renvoie le clone
		return (Arme) o;
	}

	/**
	 * @return the reloadTime
	 */
	public long getReloadTime() {
		return reloadTime;
	}

	/**
	 * @return the lastShootTime
	 */
	public long getLastShootTime() {
		return lastShootTime;
	}

	/**
	 * @return le nombre de cartouches restantes
	 */
	public int getCartouchesLeft() {
		return cartouchesLeft;
	}

	/**
	 * @param nb
	 *            le nombre de cartouches restantes
	 */
	public void setCartouchesLeft(int nb) {
		this.cartouchesLeft = nb;
		throwUpdate();
	}

	/**
	 * @return the damage inflicted by the weapon
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @return the tirClass
	 */
	public String getTirClass() {
		return tirClass;
	}

	/**
	 * @param damage
	 *            the degats to set
	 */
	public void setDamage(int damage) {
		this.damage = damage;
		throwUpdate();
	}

	/**
	 * @return the owner
	 */
	public Personnage getOwner() {
		return owner;
	}

	/**
	 * @param owner
	 *            the owner to set
	 */
	public void setOwner(Personnage owner) {
		this.owner = owner;
	}
}
