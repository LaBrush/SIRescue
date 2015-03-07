
package eu.labrush.rescue.view;

import java.util.Observable;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.Vecteur;
import eu.labrush.rescue.model.arme.Resistance;

/**
 * @author adrienbocquet
 *
 */
public class TirView extends AbstractView {

	int angle;
	private Vecteur hg, hd, bg, bd;

	/**
	 * @param model
	 */
	public TirView(Observable model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#bindModel(java.util.Observable)
	 */
	@Override
	protected void bindModel(Observable model) {
		if (model instanceof Resistance) {
			Resistance t = (Resistance) model;

			width = (int) t.getWidth();
			height = (int) t.getHeight();

			x = (int) (t.getTrajectoire().getPosition().getX());
			y = (int) (t.getTrajectoire().getPosition().getY());
			
			hg = t.hg ;
			hd = t.hd ;
			bg = t.bg ;
			bd = t.bd ;
						
			angle = t.getAngle() % 180;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#draw(eu.labrush.rescue.core.graphic.DrawRequest)
	 */
	@Override
	public void draw(DrawRequest req) {
		//req.rect(this.x, this.y, this.width, this.height, this.angle);
		
		req.rect((int)hg.getX(), (int)hg.getY(), 1, 1);
		req.rect((int)hd.getX(), (int)hd.getY(), 1, 1);
		req.rect((int)bg.getX(), (int)bg.getY(), 1, 1);
		req.rect((int)bd.getX(), (int)bd.getY(), 1, 1);
	}

}
