/**
 * 
 */
package eu.labrush.rescue.core;

import eu.labrush.rescue.controler.SampleControler;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.view.PersonnageView;

/**
 * @author adrienbocquet
 *
 */
public class SIRescue {

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		GraphicCore graphics = GraphicCore.getInstance();
		PhysicCore physics = new PhysicCore(GraphicCore.FRAMERATE);

		Personnage personnage = new Personnage(200, 200);
		SampleControler controler = new SampleControler(personnage);
		PersonnageView vPersonnage = new PersonnageView(personnage);

		physics.addObserver(personnage.getTrajectoire());
		graphics.suscribe(vPersonnage.getGraphicsListener());

		personnage.notifyObservers();
		
		graphics.start();
		physics.start();
	}

}
