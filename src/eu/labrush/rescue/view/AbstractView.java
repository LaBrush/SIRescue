package eu.labrush.rescue.view;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.utils.Observer;

/**
 * @author adrienbocquet
 *
 */
public abstract class AbstractView {

	@SuppressWarnings({ "rawtypes" })
	protected Observer binder ;

	private Observer<DrawRequest> graphicsListener = new Observer<DrawRequest>() {
		@Override
		public void update(DrawRequest req) {
			draw(req);
		}
	};

	protected abstract void draw(DrawRequest req);

	public Observer getBinder() {
		return binder;
	}

	public void setBinder(Observer binder) {
		this.binder = binder;
	}

	public Observer<DrawRequest> getGraphicsListener() {
		return graphicsListener;
	}

	public void setGraphicsListener(Observer<DrawRequest> graphicsListener) {
		this.graphicsListener = graphicsListener;
	}

}
