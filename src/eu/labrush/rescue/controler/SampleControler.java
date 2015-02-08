/**
 * 
 */
package eu.labrush.rescue.controler;

import java.awt.event.KeyEvent;

import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.graphic.KeyboardListener;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Trajectoire;
import eu.labrush.rescue.utils.Listener;

/**
 * @author adrienbocquet
 *
 */
public class SampleControler {

	Personnage model;
	boolean moving;

	public SampleControler(Personnage personnage) {
		super();
		this.model = personnage;

		KeyboardListener keyboard = GraphicCore.getKeyboard();

		keyboard.addObserver(new Listener<KeyEvent>() {

			@Override
			public void update(KeyEvent req) {
				final Trajectoire t = model.getTrajectoire();

				if (req.getID() == KeyEvent.KEY_PRESSED) {
					switch (req.getKeyCode()) {
						case KeyEvent.VK_UP:
							if(t.getVitesse().getY() == 0)
							t.getVitesse().setY(0.9);
							break;
						case KeyEvent.VK_LEFT:
							t.getVitesse().setX(-.1);
							break;
						case KeyEvent.VK_RIGHT:
							t.getVitesse().setX(.1);
							break;
						case KeyEvent.VK_DOWN:
							t.getVitesse().setY(-.1);
					}
				}

				else if (req.getID() == KeyEvent.KEY_RELEASED) {

					switch (req.getKeyCode()) {
						case KeyEvent.VK_UP:
						case KeyEvent.VK_DOWN:
							t.getVitesse().setY(0);
							break;
						case KeyEvent.VK_LEFT:
						case KeyEvent.VK_RIGHT:
							t.getVitesse().setX(0);
							break;
					}
				}

				moving = !(t.getVitesse().norme() == 0 && t.getAcceleration().norme() == 0);
			}

		});
	}

}
