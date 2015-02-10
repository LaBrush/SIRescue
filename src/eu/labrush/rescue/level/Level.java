package eu.labrush.rescue.level;

import java.util.HashSet;

import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.AbstractPhysicBehaviour;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.core.physics.RebondPhysicBehaviour;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.utils.Listener;
import eu.labrush.rescue.view.BlocView;
import eu.labrush.rescue.view.PersonnageView;

/**
 *
 * Gère tous les controlleurs, vues et modèles d'un niveau
 * 
 * TODO: ajouter un destructeur pour enlever les listener des coeurs graphique et physique
 * 
 * TODO: créer un loader XML pour les niveaux
 *
 * @author adrienbocquet
 */
public class Level {

	GraphicCore graphics;
	PhysicCore physics;

	HashSet<Personnage> personnages = new HashSet<Personnage>();
	HashSet<Bloc> blocs = new HashSet<Bloc>();

	HashSet<AbstractPhysicBehaviour> behaviours = new HashSet<AbstractPhysicBehaviour>();

	HashSet<PersonnageView> personnagesView = new HashSet<PersonnageView>();
	HashSet<BlocView> blocsView = new HashSet<BlocView>();

	/**
	 * @param graphics
	 * @param physics
	 */
	public Level(GraphicCore graphics, PhysicCore physics) {
		super();
		this.graphics = graphics;
		this.physics = physics;

		for (int i = 0; i < 3; i++) {
			Personnage p = new Personnage(10 + (Math.random() * 500), 10 + (Math.random() * (300)));
			this.addPersonnage(p);
			behaviours.add(new RebondPhysicBehaviour(p));
		}

		this.addBorders();
		this.addBloc(new Bloc(100, 110, 100, 155));
		this.addBloc(new Bloc(300, 250, 200, 10));
		this.addBloc(new Bloc(300, 50, 10, 80));

		// SampleControler controler = new SampleControler(p);
		// AbstractPhysicBehaviour pb = new PersonnagePhysicBehaviour(p);

		physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				for (AbstractPhysicBehaviour b : behaviours) {
					b.updateTrajectoire(blocs, req.getDelta());
				}
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
