package eu.labrush.rescue.view;

import java.util.Observable;
import java.util.Observer;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.utils.Listener;

/**
 * @author adrienbocquet
 *
 */
public abstract class AbstractView extends Observable {

	protected int x = 0;
	protected int y = 0;
	protected int width = 0 ;
	protected int height = 0 ;
	
	public AbstractView(Observable model){
		model.addObserver(binder);
		this.bindModel(model);
	}
	
	//Hydratation depuis le modèle
	
	protected Observer binder = new Observer(){
		@Override
		public void update(Observable o, Object arg) {
			bindModel(o);
		}
	};
	
	protected abstract void bindModel(Observable model);
	
	//Ecoute du coeur graphique pour être dessiné
	
	private Listener<DrawRequest> graphicsListener = new Listener<DrawRequest>() {
		@Override
		public void update(DrawRequest req) {
			draw(req);
		}
	};

	public abstract void draw(DrawRequest req);

	public Listener<DrawRequest> getGraphicsListener() {
		return graphicsListener;
	}

	public void setGraphicsListener(Listener<DrawRequest> graphicsListener) {
		this.graphicsListener = graphicsListener;
	}
}
