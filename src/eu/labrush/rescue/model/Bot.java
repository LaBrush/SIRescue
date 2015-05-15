package eu.labrush.rescue.model;

import java.util.ArrayList;

import eu.labrush.rescue.controler.TirControler.TirInterface;
import eu.labrush.rescue.model.arme.Arme;

/**
 * @author adrienbocquet
 *
 */
public class Bot extends Personnage {

	private TirInterface tirManager;

	public Bot(double x, double y) {
		super(x, y);
	}

	public void shoot(int angle) {
		tirManager.commandShoot(this, angle);
	}

	/**
	 * @return the tirManager
	 */
	public TirInterface getTirManager() {
		return tirManager;
	}

	/**
	 * @param tirManager
	 *            the tirManager to set
	 */
	public void setTirManager(TirInterface tirManager) {
		this.tirManager = tirManager;
	}
	
	public Bot clone(){
		Bot b = new Bot(this.getX(), this.getY());
		
		b.setLife(this.getLife());
		
		b.getDimension().setSize(this.getWidth(), this.getHeight());;
		b.getTrajectoire().setPosition(this.getTrajectoire().getPosition().clone());
		
		try {
			b.setPhysicBehaviour(this.getPhysicBehaviour().getClass().newInstance());
			b.getPhysicBehaviour().setTrajectoire(b.getTrajectoire());
			b.getPhysicBehaviour().setDimension(b.getDimension());
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
				
		b.armes = new ArrayList<Arme>();
		for (Arme a : this.getArmes()){
			b.addArme(a.clone());
		}
		
		return b ;
	}

}
