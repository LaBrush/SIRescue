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

	boolean hurting ;
	
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
		Bloc b = (Bloc) model;

		this.x = (int) b.getTrajectoire().getPosition().getX();
		this.y = (int) b.getTrajectoire().getPosition().getY();
		this.width = (int) b.getWidth();
		this.height = (int) b.getHeight();
		this.hurting = b.isHurting();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#draw(eu.labrush.rescue.core.graphic.DrawRequest)
	 */
	@Override
	public void draw(DrawRequest req) {
		req.rect(this.x, this.y, this.width, this.height, hurting ? Color.RED : Color.BLUE);
	}

}
