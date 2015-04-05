package eu.labrush.rescue.model;

/**
 * @author adrienbocquet
 *
 */
public abstract class Item extends AbstractObject {

	AbstractModel contenu;

	public Item(double x, double y, int width, int height, AbstractModel contenu) {
		super(x, y, width, height);
		this.contenu = contenu;
	}

	abstract public void deliver(Personnage p);

	public boolean intersect(AbstractObject o) {
		return !(o.getX() + o.getWidth() < this.getX() || o.getY() + o.getHeight() < this.getY() || o.getX() > this.getX() + this.getWidth() || o.getY() > this.getY() + this.getHeight());
	}
}
