package eu.labrush.rescue.controler;

import java.util.HashSet;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.physics.AbstractPhysicBehaviour;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.core.physics.TirPhysicBehaviour;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.arme.Tir;
import eu.labrush.rescue.utils.Listener;
import eu.labrush.rescue.view.TirView;

/**
 * TODO: supprimer les tirs lorsqu'ils percutent un bloc ou personnage
 * 
 * TODO: implémenter les dégats aux personnages
 * 
 * TODO: régler le problème d'ajout asynchrone (Exception in thread "Thread-1"
 * java.util.ConcurrentModificationException) voir <i>synchronized</i>
 * 
 * @author adrienbocquet
 */
public class TirControler extends AbstractControler {

	HashSet<Tir> tirs = new HashSet<Tir>();
	HashSet<TirView> views = new HashSet<TirView>();
	HashSet<TirPhysicBehaviour> behaviours = new HashSet<TirPhysicBehaviour>();

	BlocControler blocControler;
	PersonnageControler personnageControler;

	/**
	 * @param level
	 * @param personnageControler
	 * @param blocControler2
	 */
	public TirControler(Level level, BlocControler blocControler, PersonnageControler personnageControler) {
		super(level);
		this.blocControler = blocControler;
		this.personnageControler = personnageControler;

		this.graphics.suscribe(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				for (TirView v : views) {
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

				for (AbstractPhysicBehaviour b : behaviours) {
					b.updateTrajectoire(obstacles, req.getDelta());
				}
			}
		});

	}

	public void shoot(Personnage personnage, int angle) {
		Tir tir = new Tir(personnage.getTrajectoire().getPosition(), 10, angle);
		TirView v = new TirView(tir);
		TirPhysicBehaviour behaviour = new TirPhysicBehaviour(tir);

		this.tirs.add(tir);
		this.views.add(v);
		this.behaviours.add(behaviour);
	}

}
