package eu.labrush.rescue.IA.star;

/**
 * @author adrienbocquet
 *
 */
public class Noeud implements Cloneable {

	public int cout_g, cout_f, cout_h;
	Point parent;

	public Noeud clone() throws CloneNotSupportedException {
		return (Noeud) super.clone();
	}

}
