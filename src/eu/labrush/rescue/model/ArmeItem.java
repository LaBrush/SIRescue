package eu.labrush.rescue.model;

import eu.labrush.rescue.model.arme.Arme;

/**
 * @author adrienbocquet
 *
 */
public class ArmeItem extends Item {

	Arme arme ;
	
	public ArmeItem(int x, int y, Arme contenu) {
		super(x, y, 20, 20);
		this.arme = contenu ;
	}

	/* (non-Javadoc)
	 * @see eu.labrush.rescue.model.Item#deliver(eu.labrush.rescue.model.Personnage)
	 */
	@Override
	public void deliver(Personnage p) {
		p.addArme(arme);
	}

}
