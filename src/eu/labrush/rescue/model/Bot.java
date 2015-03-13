package eu.labrush.rescue.model;

import eu.labrush.rescue.controler.TirControler.TirInterface;


/**
 * @author adrienbocquet
 *
 */
public class Bot extends Personnage {
	
	private TirInterface tirManager ;
	
	public Bot(double x, double y) {
		super(x, y);
	}
	
	public void shoot(int angle){
		tirManager.commandShoot(this, angle);
	}
	
	/**
	 * @return the tirManager
	 */
	public TirInterface getTirManager() {
		return tirManager;
	}

	/**
	 * @param tirManager the tirManager to set
	 */
	public void setTirManager(TirInterface tirManager) {
		this.tirManager = tirManager;
	}

}
