package eu.labrush.rescue.controler;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;
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

	/**
	 * @author adrienbocquet
	 * 
	 *         Fourni une interface aux bots pour tirer depuis ce controler
	 */
	public interface TirInterface {

		public void commandShoot(Personnage p, int angle);

	}

	// On associe dans un même Map les modèles et la vue qui leur est associée
	ConcurrentHashMap<Tir, TirView> tirs = new ConcurrentHashMap<Tir, TirView>();

	BlocControler blocControler;
	PersonnageControler personnageControler;

	private TirInterface tirInterface = new TirInterface() {
		public void commandShoot(Personnage p, int angle) {
			shoot(p, angle);
		}
	};

	/**
	 * @param level
	 * @param blocControler
	 * @param personnageControler
	 */
	public TirControler(Level level, BlocControler blocControler, PersonnageControler personnageControler) {
		super(level);
		this.blocControler = blocControler;
		this.personnageControler = personnageControler;

		this.graphics.addObserver(new Listener<DrawRequest>() {
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

				for (Tir tir : tirs.keySet()) {
					// On met à jour la position des tirs
					((AbstractObject) tir).getPhysicBehaviour().updateTrajectoire(obstacles, req.getDelta());

					// Puis on regarde s'ils entre en collision avec d'autres objets

					for (AbstractObject o : obstacles) {
						if (tir.cross(o)) {
							if (o instanceof Personnage) {
								Personnage p = (Personnage) o;
								p.prendreDegats(tir.getDegat());
								p.checkIfDead();
							}

							deleteTir(tir);
						}
					}

				}
			}
		});

	}

	public void shoot(Personnage personnage, int angle) {
		Vecteur position = new Vecteur();

		position.setX(30 * Math.cos(angle) + personnage.getX());
		position.setY(30 * Math.sin(angle % 90) + personnage.getY() + personnage.getHeight() / 2);

		Tir tir = null;

		if (personnage.getCurrentArme() != null)
			tir = personnage.getCurrentArme().shoot(position, angle);

		// Resistance tir = new Resistance(position, 10, angle);
		if (tir != null) {
			TirView v = new TirView(tir);
			this.tirs.put(tir, v);
		}
	}

	private void deleteTir(Tir tir) {
		tirs.remove(tir);
	}

	/**
	 * @return the tirInterface
	 */
	public TirInterface getTirInterface() {
		return tirInterface;
	}

}
