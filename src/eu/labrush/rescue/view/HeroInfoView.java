package eu.labrush.rescue.view;

import java.awt.Color;
import java.util.Observable;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.Personnage;

/**
 * @author adrienbocquet
 *
 */
public class HeroInfoView extends AbstractView {

	int maxLife, life, cartouchesLeft ;
	String label;

	public HeroInfoView(Observable model) {
		super(model);
		maxLife = ((Personnage) model).getMaxLife();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#bindModel(java.util.Observable)
	 */
	@Override
	protected void bindModel(Observable model) {
		Personnage p = (Personnage) model;

		life = p.getLife();
		cartouchesLeft = p.getCurrentArme().getCartouchesLeft();
		
		String s = p.getCurrentArme().getTirClass();
		label = s.substring(s.lastIndexOf(".") + 1);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#draw(eu.labrush.rescue.core.graphic.DrawRequest)
	 */
	@Override
	public void draw(DrawRequest req) {
		int width = 200 * life / maxLife;

		int r = 255 * (maxLife - life) / maxLife;
		int v = 255 * life / maxLife;

		r = r > 0 && r <= 255 ? r : 0;
		v = v > 0 && v <= 255 ? v : 0;

		req.fillRect(10, req.getHeight() - 15, width, 20, new Color(r, v, 0));
		req.drawString("Arme: " + label + " (" + cartouchesLeft + ")", 11, req.getHeight() - 60);
	}

}
