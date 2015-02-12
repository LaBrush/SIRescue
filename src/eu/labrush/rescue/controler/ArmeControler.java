package eu.labrush.rescue.controler;

import java.util.HashSet;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.arme.Arme;
import eu.labrush.rescue.utils.Listener;
import eu.labrush.rescue.view.ArmeView;

/**
 * Cette classes est inutile tant que Arme est dépendante de Personnages et ArmesView dépendantes de
 * PersonnagesView
 * 
 * @author adrienbocquet
 */
public class ArmeControler extends AbstractControler {

	HashSet<Arme> armes = new HashSet<Arme>();
	HashSet<ArmeView> views = new HashSet<ArmeView>();

	/**
	 * @param level
	 */
	public ArmeControler(Level level) {
		super(level);

		this.graphics.suscribe(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				for (ArmeView v : views) {
					v.draw(req);
				}
			}
		});
	}

	/**
	 * Ajoute un modèle de type pesonnage et la vue qui lui est associée
	 * 
	 * @param arme
	 */
	public void add(Arme arme, Personnage owner) {
		ArmeView v = new ArmeView(arme, owner);

		this.armes.add(arme);
		this.views.add(v);
	}

}
