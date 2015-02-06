/**
 * 
 */
package eu.labrush.rescue.view;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.utils.Observer;

/**
 * @author adrienbocquet
 *
 */
public class PersonnageView extends AbstractView {

	int x = 0;
	int y = 0;
	int width = 0 ;
	int height = 0 ;

	public PersonnageView(Personnage model) {
		binder = new Observer<Personnage>() {
			@Override
			public void update(Personnage model) {
				bindModel(model);
			}
		};
		
		model.addObserver(binder);
		model.notifyObservers();
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

	protected void bindModel(Personnage model) {
		this.x = (int)model.getX();
		this.y = (int)model.getY();
		this.width = (int)model.getWidth();
		this.height = (int)model.getHeight();
	}

}
