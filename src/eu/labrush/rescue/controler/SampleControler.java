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
		this.moving = false;

		KeyboardListener keyboard = GraphicCore.getKeyboard();

		keyboard.addObserver(new Listener<KeyEvent>() {

			@Override
			public void update(KeyEvent req) {
				final Trajectoire t = model.getTrajectoire() ;
				
				if (req.getID() == KeyEvent.KEY_PRESSED) {
					if (!moving) {
						moving = true;

						new Thread(new Runnable() {
							public void run() {
								while (moving) {
									
									// Traitement sur x
									if ((t.getVitesse().getX() < 0 && t.getPosition().getX() < 0)
									||  (t.getVitesse().getX() > 0 && t.getPosition().getX() + personnage.getWidth() > 600)) {
										t.getVitesse().setX(-t.getVitesse().getX());
									}

									// traitement sur Y
									if (t.getPosition().getY() > 10 && t.getAcceleration().getY() == 0) {
										t.getAcceleration().setY(-0.003);
									}

									if (t.getPosition().getY() < 10) {
										t.getAcceleration().setY(0);
										t.getPosition().setY(10);
										t.getVitesse().setY(0);
									}

									try {
										Thread.sleep(20);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
								}
						}
						}).start();
					}

					switch (req.getKeyCode()) {
						case KeyEvent.VK_UP:
							if (t.getVitesse().getY() == 0)
								t.getVitesse().setY(1);
							break;
						case KeyEvent.VK_LEFT:
							t.getAcceleration().setX(-0.0005);
							break;
						case KeyEvent.VK_RIGHT:
							t.getAcceleration().setX(0.0005);
							break;
						case KeyEvent.VK_DOWN:
							t.getVitesse().setX(0);
					}
				}

				else if (req.getID() == KeyEvent.KEY_RELEASED) {

					switch (req.getKeyCode()) {
						case KeyEvent.VK_LEFT:
						case KeyEvent.VK_RIGHT:
							t.getAcceleration().setX(0);
							break;
					}
				}

				moving = !(t.getVitesse().norme() == 0 && t.getAcceleration().norme() == 0);
			}

		});
	}

}
