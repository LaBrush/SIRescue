package eu.labrush.rescue.controler;

import java.util.HashSet;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.utils.Listener;
import eu.labrush.rescue.view.PersonnageView;

/**
 * @author adrienbocquet
 *
 */
public class PersonnageControler extends AbstractControler {

	HashSet<Personnage> personnages = new HashSet<Personnage>();
	HashSet<PersonnageView> views = new HashSet<PersonnageView>();

	BlocControler blocControler;

	/**
	 * @param level
	 */
	public PersonnageControler(Level level, BlocControler blocControler) {
		super(level);

		this.blocControler = blocControler;

		this.graphics.suscribe(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				for (PersonnageView v : views) {
					v.draw(req);
				}
			}
		});

		this.physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				for (Personnage p : personnages) {
					p.getBehaviour().updateTrajectoire(blocControler.getBlocs(), req.getDelta());
				}
			}
		});
	}


	/**
	 * @param personnage
	 */
	public void add(Personnage personnage) {
		PersonnageView v = new PersonnageView(personnage);

		this.personnages.add(personnage);
		this.views.add(v);
	}

	/**
	 * @return the personnages
	 */
	public HashSet<Personnage> getPersonnages() {
		return personnages;
	}

}
