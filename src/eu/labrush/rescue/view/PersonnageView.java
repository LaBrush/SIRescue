package eu.labrush.rescue.view;

import java.awt.Color;
import java.util.Observable;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.arme.Arme;

/**
 * @author adrienbocquet
 *
 */
public class PersonnageView extends AbstractObjectView {

	ArmeView armeView;
	Arme arme;

	int life, maxLife;
	int sens = 1 ;
	boolean bot = false;

	public PersonnageView(Personnage model) {
		super(model);
		height = (int) model.getHeight();
		width = (int) model.getWidth();

		life = model.getLife();
		maxLife = model.getMaxLife();

	}

	@Override
	protected void bindModel(Observable model) {
		Personnage p = (Personnage) model;

		x = (int) p.getTrajectoire().getPosition().getX();
		y = (int) p.getTrajectoire().getPosition().getY();

		life = p.getLife();
		bot = model instanceof Bot;

		int vx = (int)p.getTrajectoire().getVitesse().getX();
		if(vx != 0){
			sens = vx > 0 ? 1 : -1 ;
		}
		
		/*Arme previous = arme;

		arme = p.getCurrentArme();
		if (arme != null && !arme.equals(previous)) {
			armeView = new ArmeView(model, p);
		}*/
	}

	@Override
	public void draw(DrawRequest req) {
		
		if (image == null) {
			req.rect(x, y, width, height);
		} else {
			req.image(image, x, y, width * sens, height, 0);
		}

		/*if (armeView != null)
			armeView.draw(req);*/

		if (bot) {
			int width = this.width * life / maxLife;
			
			int r = 255 * (maxLife - life) / maxLife;
			int v = 255 * life / maxLife;

			r = r >= 0 && r <= 255 ? r : 0;
			v = v >= 0 && v <= 255 ? v : 0;

			req.fillRect(this.x, this.y + this.height + 5, width, 2, new Color(r, v, 0));
		}
	}
}
