package eu.labrush.rescue.view;

import java.util.Observable;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.arme.Arme;

/**
 * @author adrienbocquet
 *
 */
public class PersonnageView extends AbstractView {

	ArmeView armeView;
	Arme arme;
	
	public PersonnageView(Personnage model) {
		super(model);
		width = (int) model.getWidth();
		height = (int) model.getHeight();
	}

	@Override
	protected void bindModel(Observable model) {
		Personnage p = (Personnage) model;

		x = (int) p.getTrajectoire().getPosition().getX();
		y = (int) p.getTrajectoire().getPosition().getY();
		
		Arme previous = arme;
		
		arme = p.getCurrentArme();
		if (arme != null && !arme.equals(previous)) {
			armeView = new ArmeView(model, p);
		}

	}

	@Override
	public void draw(DrawRequest req) {
		req.rect(this.x, this.y, this.width, this.height);
		
		if (armeView != null)
			armeView.draw(req);
	}
}
