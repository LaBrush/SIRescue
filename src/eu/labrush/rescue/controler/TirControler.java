package eu.labrush.rescue.controler;

import java.util.HashSet;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;
import eu.labrush.rescue.model.arme.Arme;
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

	private Observer usedTirObserver = new Observer() {
		@Override
		public void update(Observable o, Object arg) {
			if ("done".equals(arg))
				deleteTir((Tir) o);
		}
	};

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
					tir.getPhysicBehaviour().updateTrajectoire(req.getDelta(), obstacles);

					// Puis on regarde s'ils entre en collision avec d'autres objets
					for (AbstractObject o : obstacles) {
						if (tir.cross(o) && tir.getOwner() != o) {
							if (o instanceof Personnage) {
								Personnage p = (Personnage) o;
								
								int recul = 100 ;
								if(p.getX()+p.getWidth()/2 < tir.getX()+tir.getWidth()/2){
									recul *= -1 ;
								}
								
								p.prendreDegats(tir.getDamage(), recul);
							}
							tir.use();
						}
						else if(tir.isActivated())
						{
							tir.use();
						}
					}

				}
			}
		});

	}

	public void shoot(Personnage personnage, int angle) {
		Vecteur position = new Vecteur();

		double rad = Math.toRadians(angle);

		position.setX(30 * Math.cos(rad) + personnage.getX() + personnage.getWidth() / 2);
		position.setY(30 * Math.sin(rad) + personnage.getY() + personnage.getHeight() / 2);

		Tir tir = null;

		Arme currentArme = personnage.getCurrentArme();
		if (currentArme != null)
			tir = currentArme.shoot(position, angle);

		if (tir != null) {
			if (currentArme.getCartouchesLeft() == 0) {
				personnage.removeArme(currentArme);
			}

			TirView v = new TirView(tir);
			this.tirs.put(tir, v);

			tir.addObserver(usedTirObserver);
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
