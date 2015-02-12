package eu.labrush.rescue.controler;

import java.util.HashSet;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.physics.AbstractPhysicBehaviour;
import eu.labrush.rescue.core.physics.PersonnagePhysicBehaviour;
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
	HashSet<AbstractPhysicBehaviour> behaviours = new  HashSet<AbstractPhysicBehaviour>();
	
	BlocControler blocControler ;
	
	/**
	 * @param level
	 */
	public PersonnageControler(Level level, BlocControler blocControler) {
		super(level);

		this.blocControler = blocControler ;
		
		this.graphics.suscribe(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				for(PersonnageView v: views){
					v.draw(req);
				}
			}

		});
		
		this.physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				for(AbstractPhysicBehaviour b: behaviours){
					b.updateTrajectoire(blocControler.getBlocs(), req.getDelta());
				}
			}

		});
	}

	/**
	 * Ajoute un modèle de type pesonnage et la vue qui lui est associée
	 * 
	 * @param personnage
	 */
	public void add(Personnage personnage) {
		add(personnage, new PersonnagePhysicBehaviour(personnage));
	}
	
	/**
	 * @param personnage
	 * @param behaviour
	 */
	public void add(Personnage personnage, AbstractPhysicBehaviour behaviour) {
		PersonnageView v = new PersonnageView(personnage);
		behaviour.setTarget(personnage);
		
		this.personnages.add(personnage);
		this.views.add(v);
		this.behaviours.add(behaviour);
	}

}
