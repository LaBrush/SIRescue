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

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.segui.view.AbstractView#draw(eu.segui.core.graphic.DrawRequest)
	 */
	@Override
	protected void draw(DrawRequest req) {
		// TODO Auto-generated method stub

	}

	PersonnageView() {
		binder = new Observer<Personnage>() {
			@Override
			public void update(Personnage model) {
				bindModel(model);
			}
		};
	}

	protected void bindModel(Personnage model) {

		this.x = model.getX();
		this.y = model.getY();
	}

}
