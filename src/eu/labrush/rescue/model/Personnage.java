/**
 * 
 */
package eu.labrush.rescue.model;

/**
 * @author adrienbocquet
 *
 */
public class Personnage extends AbstractObject {

	protected int life = 100, maxLife = 100;

	public Personnage(double x, double y) {
		super(x, y);
		this.getTrajectoire().setGravity(true);
	}

	/**
	 * @return the life
	 */
	public int getLife() {
		return life;
	}

	/**
	 * @param life
	 *            the life to set
	 */
	public void setLife(int life) {
		this.life = life;
		throwUpdate();
	}

	/**
	 * @return the maxLife
	 */
	public int getMaxLife() {
		return maxLife;
	}

	/**
	 * @param maxLife
	 *            the maxLife to set
	 */
	public void setMaxLife(int maxLife) {
		this.maxLife = maxLife;
		throwUpdate();
	}

}
