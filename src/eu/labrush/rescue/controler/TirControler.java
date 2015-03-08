package eu.labrush.rescue.controler;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;
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
	ConcurrentHashMap<Tir, TirView> tirs = new ConcurrentHashMap<Tir, TirView>();

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
					if (t instanceof Resistance) {
						//On met à jour la position des tirs
						((AbstractObject) t).getPhysicBehaviour().updateTrajectoire(obstacles, req.getDelta());

						//Puis on regarde s'ils entre en collision avec d'autres objets
						Resistance tir = (Resistance) t ;
						
						for(AbstractObject o: obstacles){
							if(tir.cross(o)){
								if(o instanceof Personnage){
									Personnage p = (Personnage)o;
									p.prendreDegats(tir.getDegat());
									if(p.isDead()){
										personnageControler.removePersonnage(p);
									}
								}
								
								deleteTir(tir);
							}
						}
					}
				}
			}
		});

	}

	public void shoot(Personnage personnage, int angle) {
		Vecteur position = new Vecteur();
		
		position.setX(30 * Math.cos(angle) + personnage.getX());
		position.setY(30 * Math.sin(angle % 90) + personnage.getY() + personnage.getHeight()/2) ;
		
		Resistance tir = new Resistance(position, 10, angle);
		TirView v = new TirView(tir);

		this.tirs.put(tir, v);
	}

	private void deleteTir(Tir tir){
		tirs.remove(tir);
	}
	
}
