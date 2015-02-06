/**
 * 
 */
package eu.labrush.rescue.core;

import eu.labrush.rescue.controler.SampleControler;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.view.AbstractView;
import eu.labrush.rescue.view.PersonnageView;

/**
 * @author adrienbocquet
 *
 */
public class SIRescue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphicCore graphics = GraphicCore.getInstance();
		
		Personnage personnage = new Personnage(200, 200);
		SampleControler controler = new SampleControler(personnage);
		AbstractView vPersonnage = new PersonnageView(personnage);
		
		graphics.suscribe(vPersonnage.getGraphicsListener());
		
		graphics.start();
	}

}
