package eu.labrush.rescue.controler;

import java.util.HashMap;
import java.util.Set;

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

	HashMap<Personnage, PersonnageView> personnages = new HashMap<Personnage, PersonnageView>();

	BlocControler blocControler;

	/**
	 * @param level
	 */
	public PersonnageControler(Level level, BlocControler blocControler) {
		super(level);

		this.blocControler = blocControler;

		this.graphics.addObserver(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				for (PersonnageView v : personnages.values()) {
					v.draw(req);
				}
			}
		});

		this.physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				for (Personnage p : personnages.keySet()) {
					p.getPhysicBehaviour().updateTrajectoire(blocControler.getBlocs(), req.getDelta());
				}
			}
		});
	}


	/**
	 * @param personnage
	 */
	public void add(Personnage personnage) {
		PersonnageView v = new PersonnageView(personnage);

		this.personnages.put(personnage, v);
	}

	/**
	 * @return the personnages
	 */
	public Set<Personnage> getPersonnages() {
		return personnages.keySet();
	}
	
	public void removePersonnage(Personnage p){
		personnages.remove(p);
	}

}
