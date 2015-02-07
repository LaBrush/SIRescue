/**
 * 
 */
package eu.labrush.rescue.view;

import java.util.Observable;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.Personnage;

/**
 * @author adrienbocquet
 *
 */
public class PersonnageView extends AbstractView {

	public PersonnageView(Personnage model) {
		super(model);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.segui.view.AbstractView#draw(eu.segui.core.graphic.DrawRequest)
	 */
	@Override
	protected void draw(DrawRequest req) {
		req.rect(this.x, this.y, this.width, this.height);
	}

	@Override
	protected void bindModel(Observable model) {
		Personnage p = (Personnage)model ;
		
		this.x = (int)p.getTrajectoire().getPosition().getX();
		this.y = (int)p.getTrajectoire().getPosition().getY();
		this.width = (int)p.getWidth();
		this.height = (int)p.getHeight();		
	}

}
