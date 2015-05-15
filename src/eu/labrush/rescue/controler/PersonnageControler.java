package eu.labrush.rescue.controler;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.utils.Listener;
import eu.labrush.rescue.view.PersonnageView;

/**
 * @author adrienbocquet
 *
 */
public class PersonnageControler extends AbstractControler {

	ConcurrentHashMap<Personnage, PersonnageView> personnages = new ConcurrentHashMap<Personnage, PersonnageView>(
			new HashMap<Personnage, PersonnageView>());

	BlocControler blocControler;

	Observer deadObserver = new Observer() {

		@Override
		public void update(Observable o, Object arg) {
			if ("dead".equals(arg)) {
				removePersonnage((Personnage) o);
			}
		}
	};

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
					
					p.getPhysicBehaviour().updateTrajectoire(req.getDelta(), blocControler.getBlocs());
					
					for (Bloc b : blocControler.getBlocs()) {
						if (b.touch(p) && b.isHurting()) {
							p.prendreDegats(1000);
						}
					}
					
					if(p.getTrajectoire().getAcceleration().getY() == 0 && p.getTrajectoire().getVitesse().getY() == 0 && p.isHurted()){
						p.setHurted(false);
					}
				}				
			}
		});
	}

	public void add(Personnage personnage) {
		PersonnageView v = new PersonnageView(personnage);
		personnage.addObserver(this.deadObserver);

		this.personnages.put(personnage, v);
		throwUpdate();
	}

	/**
	 * @return the personnages
	 */
	public Set<Personnage> getPersonnages() {
		return personnages.keySet();
	}

	public void removePersonnage(Personnage p) {
		personnages.remove(p);
		throwUpdate();
	}

}
