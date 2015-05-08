package eu.labrush.rescue.model;

/**
 * @author adrienbocquet
 *
 */
public class Item extends AbstractObject {

	AbstractModel contenu;

	public Item(double x, double y, int width, int height) {
		super(x, y, width, height);
	}

	public void deliver(Personnage p){};

	public boolean intersect(AbstractObject o) {
		return !(o.getX() + o.getWidth() < this.getX() || o.getY() + o.getHeight() < this.getY() || o.getX() > this.getX() + this.getWidth() || o.getY() > this.getY() + this.getHeight());
	}
}
