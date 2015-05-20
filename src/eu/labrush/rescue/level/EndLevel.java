package eu.labrush.rescue.level;

import java.awt.Color;

import eu.labrush.rescue.controler.AudioControler;
import eu.labrush.rescue.controler.BlocControler;
import eu.labrush.rescue.controler.PersonnageControler;
import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.core.physics.RebondPhysicBehaviour;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.utils.Listener;

/**
 *
 * Niveau de fin du jeu
 *
 * @author adrienbocquet
 */
public class EndLevel extends Level {

	public EndLevel(GraphicCore graphics, PhysicCore physics) {
		this.graphics = graphics;
		this.physics = physics;
		
		this.audioControler = new AudioControler();
		
		graphics.addObserver(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				req.fillRect(0, req.getHeight(), req.getWidth(), req.getHeight(), Color.BLACK);
			}
		});
		
		blocControler = new BlocControler(this);
		personnageControler = new PersonnageControler(this, blocControler);
		
		graphics.addObserver(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				req.drawString("Vous avez fini SIRescue !", 20, 350, Color.WHITE, 30, true);
				req.drawString("C'est négligeable", 20, 300, Color.WHITE, 20, true);
			}
		});
		
		
		Personnage p = new Personnage(50, 150);
		p.setPhysicBehaviour(new RebondPhysicBehaviour(p.getTrajectoire(), p.getDimension()));
		p.getDimension().setSize(100, 200);
		p.setImage("tonneau.png");
		
		personnageControler.add(p);

		addBorders();
	}
	
	private void addBorders() {

		int width = this.graphics.getPan().getWidth(), height = this.graphics.getPan().getHeight();
		
		blocControler.add(new Bloc(0, height + 1, width, 10)); // mur en haut
		blocControler.add(new Bloc(-10, 0, 10, height + 100)); // mur à gauche
		blocControler.add(new Bloc(width, 0, 10, height + 100)); // mur à droite
		blocControler.add(new Bloc(0, -10, width + 1, 10)); // mur en bas
	}
}
