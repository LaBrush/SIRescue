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

	int maxLife, life ;
	
	public HeroInfoView(Observable model) {
		super(model);
		maxLife = ((Personnage)model).getMaxLife();
	}

	/* (non-Javadoc)
	 * @see eu.labrush.rescue.view.AbstractView#bindModel(java.util.Observable)
	 */
	@Override
	protected void bindModel(Observable model) {
		life = ((Personnage)model).getLife();
	}

	/* (non-Javadoc)
	 * @see eu.labrush.rescue.view.AbstractView#draw(eu.labrush.rescue.core.graphic.DrawRequest)
	 */
	@Override
	public void draw(DrawRequest req) {
		int width = 200 * life / maxLife;
		
		int r = 255 * (maxLife - life) / maxLife ;
		int v = 255 * life / maxLife;
		
		r = r >= 0 || r <= 255 ? r : 0 ;
		v = v >= 0 || v <= 255 ? v : 0 ;
		
		req.fillRect(11, req.getHeight()-10, width, 20, new Color(r, v, 0));
	}

}
