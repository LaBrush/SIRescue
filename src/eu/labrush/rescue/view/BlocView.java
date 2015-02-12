/**
 * 
 */
package eu.labrush.rescue.view;

import java.awt.Color;
import java.util.Observable;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.Bloc;

/**
 * @author adrienbocquet
 *
 */
public class BlocView extends AbstractView {

	public BlocView(Observable model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#bindModel(java.util.Observable)
	 */
	@Override
	protected void bindModel(Observable model) {
		Bloc p = (Bloc) model;

		this.x = (int) p.getTrajectoire().getPosition().getX();
		this.y = (int) p.getTrajectoire().getPosition().getY();
		this.width = (int) p.getWidth();
		this.height = (int) p.getHeight();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#draw(eu.labrush.rescue.core.graphic.DrawRequest)
	 */
	@Override
	public void draw(DrawRequest req) {
		req.rect(this.x, this.y, this.width, this.height, Color.RED);
	}

}
