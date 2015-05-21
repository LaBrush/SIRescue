package eu.labrush.rescue.view;

import java.util.Observable;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.arme.Bouclier;
import eu.labrush.rescue.model.arme.Tir;

/**
 * @author adrienbocquet
 *
 */
public class TirView extends AbstractObjectView {

	int angle;
	boolean isShield = false ;

	public TirView(Tir model) {
		super(model);
		isShield = model instanceof Bouclier ;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#bindModel(java.util.Observable)
	 */
	@Override
	protected void bindModel(Observable model) {
		Tir t = (Tir) model;

		width = (int) t.getWidth();
		height = (int) t.getHeight();

		x = (int) (t.getTrajectoire().getPosition().getX());
		y = (int) (t.getTrajectoire().getPosition().getY());

		angle = (int) t.getAngle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#draw(eu.labrush.rescue.core.graphic.DrawRequest)
	 */
	@Override
	public void draw(DrawRequest req) {
		
		if(isShield){
			return ;
		}
		
		if(this.image == null){
			req.rect(x, y, width, height, angle);
		} else {
			req.image(image, x, y, width, height, angle);
		}
	}

}
