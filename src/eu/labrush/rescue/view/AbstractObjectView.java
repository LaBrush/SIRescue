package eu.labrush.rescue.view;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.AbstractObject;

/**
 * @author adrienbocquet
 *
 */
public abstract class AbstractObjectView extends AbstractView {

	protected BufferedImage image = null;

	/**
	 * @param model
	 */
	public AbstractObjectView(AbstractObject model) {
		model.addObserver(binder);
		this.bindModel(model);
		
		if (!model.getImage().isEmpty())
			setImage(model.getImage());
	}

	// Hydratation depuis le modèle

	protected Observer binder = new Observer() {
		@Override
		public void update(Observable o, Object arg) {

			if ("image".equals(arg)) {
				setImage(((AbstractObject) o).getImage());
			}

			bindModel(o);
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#bindModel(java.util.Observable)
	 */
	@Override
	protected abstract void bindModel(Observable model);

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.labrush.rescue.view.AbstractView#draw(eu.labrush.rescue.core.graphic.DrawRequest)
	 */
	@Override
	public abstract void draw(DrawRequest req);

	private void setImage(String src) {
		try {
			image = ImageIO.read(new File("resources/images/" + src));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
