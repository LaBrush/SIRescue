package eu.labrush.rescue.controler;

import java.util.HashSet;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.utils.Listener;
import eu.labrush.rescue.view.BlocView;

/**
 * @author adrienbocquet
 *
 */
public class BlocControler extends AbstractControler {

	HashSet<Bloc> blocs = new HashSet<Bloc>();
	HashSet<BlocView> views = new HashSet<BlocView>();

	public BlocControler(Level level) {
		super(level);
		
		this.graphics.addObserver(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				for(BlocView v: views){
					v.draw(req);
				}
			}
		});
	}

	/**
	 * Ajoute un modèle de type bloc à l'objet et la vue qui lui est associée
	 * 
	 * @param bloc le bloc à ajouter
	 */
	public void add(Bloc bloc) {
		BlocView v = new BlocView(bloc);

		this.blocs.add(bloc);
		this.views.add(v);
	}


	/**
	 * @return the blocs
	 */
	public HashSet<Bloc> getBlocs() {
		return blocs;
	}
}
