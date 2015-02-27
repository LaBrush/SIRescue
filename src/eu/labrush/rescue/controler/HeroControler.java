package eu.labrush.rescue.controler;

import java.awt.event.KeyEvent;

import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.graphic.KeyboardListener;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Trajectoire;
import eu.labrush.rescue.utils.Listener;

/**
 * @author adrienbocquet
 *
 */
public final class HeroControler extends AbstractControler {

	TirControler tirControler;

	private Personnage model;
	boolean moving;

	private double vx, vy ;

	KeyboardListener keyboard = GraphicCore.getKeyboard();
	Listener<KeyEvent> listener = new Listener<KeyEvent>() {

		@Override
		public void update(KeyEvent req) {
			Trajectoire t = model.getTrajectoire();

			if (req.getID() == KeyEvent.KEY_PRESSED) {
				switch (req.getKeyCode()) {
					case KeyEvent.VK_UP:
						if (t.getVitesse().getY() == 0)
							t.getVitesse().setY(vy);
						break;
					case KeyEvent.VK_LEFT:
						t.getVitesse().setX(-vx);
						break;
					case KeyEvent.VK_RIGHT:
						t.getVitesse().setX(vx);
						break;
				}

				switch (req.getKeyChar()) {
					case 'd':
						tirControler.shoot(model, 0);
						break;
					case 'e':
						tirControler.shoot(model, 45);
						break;
					case 'z':
						tirControler.shoot(model, 90);
						break;
					case 'a':
						tirControler.shoot(model, 135);
						break;
					case 'q':
						tirControler.shoot(model, 180);
						break;
				}
			}

			else if (req.getID() == KeyEvent.KEY_RELEASED) {

				switch (req.getKeyCode()) {
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_RIGHT:
						t.getVitesse().setX(0);
						break;
				}
			}

			moving = !(t.getVitesse().norme() == 0 && t.getAcceleration().norme() == 0);
		}

	};

	public HeroControler(Level level, TirControler tirContoler) {
		super(level);

		this.tirControler = tirContoler;
	}

	/**
	 * @param hero
	 */
	public void setPersonnage(Personnage hero) {
		keyboard.delObserver(listener);
		this.model = hero;

		if (this.model != null) {
			keyboard.addObserver(listener);
		}
		

		this.vx = model.getVitesseNominale().getX() ;
		this.vy = model.getVitesseNominale().getY();
	}
	
	public Personnage getPersonnage(){
		return model ;
	}
}
