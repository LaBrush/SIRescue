package eu.labrush.rescue.model.arme;

import java.awt.Color;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import eu.labrush.rescue.model.AbstractModel;
import eu.labrush.rescue.model.Vecteur;

public class Arme extends AbstractModel {

	private int cartouchesLeft = 0;
	private int degats;

	private int reloadTime; // temps de rechargement
	private long lastShootTime = 0; // Date UNIX du dernier tir

	private String tirClass ;

	Color c ;
	
	/**
	 * 
	 * @param tirClass
	 * 		La classe dont l'instance du Tir va hériter (pattern factory)
	 * @param degats
	 * @param reloadTime
	 */
	public Arme(String tirClass, int degats, int reloadTime, Color c) {
		super();
		this.tirClass = "eu.labrush.rescue.model.arme." + tirClass ;
		this.degats = degats;
		this.reloadTime = reloadTime;
		this.c = c; 
	}

	/**
	 * TODO: tmp
	 * 
	 * @return the c
	 */
	public Color getC() {
		return c;
	}

	public Tir shoot(Vecteur position, int angle) {
		if(System.currentTimeMillis() - lastShootTime < reloadTime){
			return null ;
		}
		
		Tir tir = null;

		try {
			// On crée un objet Class
			Class<?> cl = Class.forName(tirClass);

			// On crée les paramètres du constructeur
			Class<?>[] types = new Class[] { Vecteur.class, int.class, int.class };
			
			// On récupère le constructeur avec les deux paramètres
			Constructor<?> ct = cl.getConstructor(types);

			// On instancie l'objet avec le constructeur surchargé !
			tir = (Tir) ct.newInstance(new Object[] { position, angle, degats});

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

		lastShootTime = System.currentTimeMillis();
		return tir;
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
	 * @param cartouches
	 *            le nombre de cartouches restantes
	 */
	public void setCartouchesLeft(int nb) {
		this.cartouchesLeft = nb;
		throwUpdate();
	}

	/**
	 * @return the degats
	 */
	public int getDegats() {
		return degats;
	}

	/**
	 * @return the tirClass
	 */
	public String getTirClass() {
		return tirClass;
	}
	
	/**
	 * @param degats
	 *            the degats to set
	 */
	public void setDegats(int degats) {
		this.degats = degats;
		throwUpdate();
	}
}
