package eu.labrush.rescue.view;

import java.util.Observable;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.Personnage;

/**
 * @author adrienbocquet
 *
 */
public class ArmeView extends AbstractView {

	/**
	 * @param model
	 * @param owner
	 */
	public ArmeView(Observable model, Personnage owner) {
		super(model);

		this.width = 20;
		this.height = 10;

		owner.addObserver(binder);
		this.bindModel(owner);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#bindModel(java.util.Observable)
	 */
	@Override
	protected void bindModel(Observable model) {
		if (model instanceof Personnage) {
			Personnage p = (Personnage) model;
			x = (int) (p.getTrajectoire().getPosition().getX() + (p.getWidth() - width) / 2);
			y = (int) (p.getTrajectoire().getPosition().getY() + (p.getHeight() - height) / 2);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#draw(eu.labrush.rescue.core.graphic.DrawRequest)
	 */
	@Override
	public void draw(DrawRequest req) {
		req.rect(this.x, this.y, this.width, this.height);
	}

}
