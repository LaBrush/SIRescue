package eu.labrush.rescue.level;

import java.util.HashSet;

import eu.labrush.rescue.controler.SampleControler;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PersonnagePhysicBehaviour;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.utils.Listener;
import eu.labrush.rescue.view.BlocView;
import eu.labrush.rescue.view.PersonnageView;

/**
 *
 * Gère tous les controlleurs, vues et modèles d'un niveau
 * 
 * TODO: ajouter un destructeur
 * 
 * TODO: créer un loader XML
 *
 * @author adrienbocquet
 */
public class Level {

	GraphicCore graphics;
	PhysicCore physics;

	HashSet<Personnage> personnages = new HashSet<Personnage>();
	HashSet<Bloc> blocs = new HashSet<Bloc>();

	HashSet<PersonnageView> personnagesView = new HashSet<PersonnageView>();
	HashSet<BlocView> blocsView = new HashSet<BlocView>();

	/**
	 * @param graphics
	 * @param physics
	 */
	@SuppressWarnings("unused")
	public Level(GraphicCore graphics, PhysicCore physics) {
		super();
		this.graphics = graphics;
		this.physics = physics;

		Personnage p = new Personnage(200, 150);
		this.addPersonnage(p);
		
		this.addBorders();
		this.addBloc(new Bloc(300, 100, 200, 100));
		
		SampleControler controler = new SampleControler(p);
		PersonnagePhysicBehaviour pb = new PersonnagePhysicBehaviour(p);

		physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				pb.updateTrajectoire(blocs, req.getDelta());
			}
		});
	}

	/**
	 * Ajoute des blocs aux quatres coins de l'écran afin d'éviter tout débordement du panneau
	 */
	private void addBorders() {

		int width = this.graphics.getPan().getWidth(), height = this.graphics.getPan().getHeight();

		this.addBloc(new Bloc(0, height - 10, width, 10)); // mur en haut
		this.addBloc(new Bloc(0, 0, 10, height)); // mur à gauche
		this.addBloc(new Bloc(width - 10, 0, 10, height)); // mur à droite
		this.addBloc(new Bloc(0, 0, width, 10)); // mur en bas
	}

	/**
	 * Ajoute un modèle de type pesonnage et la vue qui lui est associée
	 * 
	 * @param personnage
	 */
	private void addPersonnage(Personnage personnage) {
		PersonnageView v = new PersonnageView(personnage);
		this.graphics.suscribe(v.getGraphicsListener());

		this.personnages.add(personnage);
		this.personnagesView.add(v);
	}

	/**
	 * Ajoute un modèle de type bloc à l'objet et la vue qui lui est associée
	 * 
	 * @param bloc
	 */
	private void addBloc(Bloc bloc) {
		BlocView v = new BlocView(bloc);
		this.graphics.suscribe(v.getGraphicsListener());

		this.blocs.add(bloc);
		this.blocsView.add(v);
	}

}
