/**
 * 
 */
package eu.labrush.rescue.controler;

import java.awt.event.KeyEvent;

import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.graphic.KeyboardListener;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.utils.Observer;

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

		keyboard.addObserver(new Observer<KeyEvent>() {

			@Override
			public void update(KeyEvent req) {
				if (req.getID() == KeyEvent.KEY_PRESSED) {

					if (!moving) {
						moving = true ;
												
						new Thread(new Runnable() {
							public void run() {
								while (moving) {
									
									//Traitement sur x
									if((model.getX() < 0 && model.getVX() < 0)|| ( model.getX() + model.getWidth() > 600 && model.getVY() > 0))
									{
										model.setVX(0);
									}
									
									model.setX(model.getX()
											+ model.getVX());
									
									// traitement sur Y
									if(model.getY() > 10){
										model.setVY( model.getVY() - 0.3 );
									}
									model.setY(model.getY() + model.getVY());
									
									if(model.getY() < 10){
										model.setY(10);
										model.setVY(0);
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
							model.setVY(10);
							break;
						case KeyEvent.VK_LEFT:
							model.setVX(-5);
							break;
						case KeyEvent.VK_RIGHT:
							model.setVX(5);
							break;
					}
				}

				else if (req.getID() == KeyEvent.KEY_RELEASED) {

					switch (req.getKeyCode()) {
						case KeyEvent.VK_LEFT:
						case KeyEvent.VK_RIGHT:
							model.setVX(0);
							break;
					}
				}

				moving = model.normeVitesse() != 0;
			}

		});
	}

}
