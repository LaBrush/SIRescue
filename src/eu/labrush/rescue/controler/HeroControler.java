package eu.labrush.rescue.controler;

import java.awt.event.KeyEvent;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.graphic.KeyboardListener;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Trajectoire;
import eu.labrush.rescue.model.Vecteur;
import eu.labrush.rescue.utils.Listener;
import eu.labrush.rescue.view.HeroInfoView;

/**
 * @author adrienbocquet
 *
 */
public final class HeroControler extends AbstractControler {

	TirControler tirControler;
	PersonnageControler personnageControler;

	private HeroInfoView heroView;

	private Thread shootThread;
	private boolean shooting = false;

	private Personnage model;

	private double vx, vy;

	KeyboardListener keyboard = GraphicCore.getKeyboard();
	Listener<KeyEvent> listener = new Listener<KeyEvent>() {

		@Override
		public void update(KeyEvent req) {
			Trajectoire t = model.getTrajectoire();

			if (req.getID() == KeyEvent.KEY_PRESSED) {
				switch (req.getKeyCode()) {
					case KeyEvent.VK_UP:
						if (t.getVitesse().getY() == 0 && t.getAcceleration().getY() == 0)
							t.getVitesse().setY(vy);
						break;
					case KeyEvent.VK_LEFT:
						t.getVitesse().setX(-vx);
						break;
					case KeyEvent.VK_RIGHT:
						t.getVitesse().setX(vx);
						break;
					case KeyEvent.VK_TAB:
						model.nextArme();
						break;
				}

				switch (req.getKeyChar()) {
					case 'd':
						startShoot(0);
						break;
					case 'e':
						startShoot(45);
						break;
					case 'z':
						startShoot(90);
						break;
					case 'a':
						startShoot(135);
						break;
					case 'q':
						startShoot(180);
						break;
				}
			}

			else if (req.getID() == KeyEvent.KEY_RELEASED) {

				switch (req.getKeyChar()) {
					case 'd':
					case 'e':
					case 'z':
					case 'a':
					case 'q':
						shooting = false;
						break;
				}
				;

				switch (req.getKeyCode()) {
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_RIGHT:
						t.getVitesse().setX(0);
						break;
				}
			}

		}

	};

	private void startShoot(int angle) {
		shootThread = new Thread(new Runnable() {
			@Override
			public void run() {
				shooting = true;
				while (shooting) {
					tirControler.shoot(model, angle);

					try {
						Thread.sleep((int) model.getCurrentArme().getReloadTime() / 2);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});

		shootThread.start();
	}

	public HeroControler(Level level, TirControler tirContoler, PersonnageControler personnageControler) {
		super(level);

		this.tirControler = tirContoler;
		this.personnageControler = personnageControler;

	}

	public void setPersonnage(Personnage personnage) {
		if (personnage != null) {
			personnage.setVitesseNominale(new Vecteur(300, 900));

			personnageControler.removePersonnage(personnage);
			keyboard.delObserver(listener);

			this.model = personnage;

			keyboard.addObserver(listener);

			this.vx = model.getVitesseNominale().getX();
			this.vy = model.getVitesseNominale().getY();

			personnageControler.add(model);
			heroView = new HeroInfoView(personnage);

			this.graphics.addObserver(new Listener<DrawRequest>() {
				@Override
				public void update(DrawRequest req) {
					heroView.draw(req);
				}
			});
			
		}

		throwUpdate();
	}

	public Personnage getPersonnage() {
		return model;
	}
}
