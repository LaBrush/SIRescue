package eu.labrush.rescue.view;

import java.util.Observable;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.arme.Tir;

/**
 * @author adrienbocquet
 *
 */
public class TirView extends AbstractView {

	int angle;

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
		if (model instanceof Tir) {
			Tir t = (Tir) model;

			width = (int) t.getWidth();
			height = (int) t.getHeight();

			x = (int) (t.getTrajectoire().getPosition().getX());
			y = (int) (t.getTrajectoire().getPosition().getY());

			angle = t.getAngle();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#draw(eu.labrush.rescue.core.graphic.DrawRequest)
	 */
	@Override
	public void draw(DrawRequest req) {
		req.rect(this.x, this.y, this.width, this.height, this.angle);
	}

}
