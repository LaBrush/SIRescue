package eu.labrush.rescue.controler;

import java.util.HashMap;
import java.util.HashSet;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.arme.Resistance;
import eu.labrush.rescue.model.arme.Tir;
import eu.labrush.rescue.utils.Listener;
import eu.labrush.rescue.view.TirView;

/**
 * TODO: supprimer les tirs lorsqu'ils percutent un bloc ou personnage
 * 
 * TODO: implémenter les dégats infligés aux personnages
 * 
 * @author adrienbocquet
 */
public class TirControler extends AbstractControler {

	// On associe dans un même Map les modèles et la vue qui leur est associée
	HashMap<Tir, TirView> tirs = new HashMap<Tir, TirView>();

	BlocControler blocControler;
	PersonnageControler personnageControler;

	/**
	 * @param level
	 * @param blocControler
	 * @param personnageControler
	 */
	public TirControler(Level level, BlocControler blocControler, PersonnageControler personnageControler) {
		super(level);
		this.blocControler = blocControler;
		this.personnageControler = personnageControler;

		this.graphics.suscribe(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				for (TirView v : tirs.values()) {
					v.draw(req);
				}
			}
		});

		this.physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				HashSet<AbstractObject> obstacles = new HashSet<AbstractObject>();

				obstacles.addAll(personnageControler.getPersonnages());
				obstacles.addAll(blocControler.getBlocs());

				for (Tir t : tirs.keySet()) {
					if (t instanceof AbstractObject) {
						((AbstractObject) t).getBehaviour().updateTrajectoire(obstacles, req.getDelta());
					}
				}

			}
		});

	}

	public void shoot(Personnage personnage, int angle) {
		Resistance tir = new Resistance(personnage.getTrajectoire().getPosition(), 10, angle);
		TirView v = new TirView(tir);

		this.tirs.put(tir, v);
	}

}
