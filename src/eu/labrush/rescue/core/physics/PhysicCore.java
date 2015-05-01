package eu.labrush.rescue.core.physics;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import eu.labrush.rescue.utils.Listenable;
import eu.labrush.rescue.utils.Listener;

/**
 * @author adrienbocquet
 *
 *         Le coeur physique permet de réaliser le lien entre le modèle physique et les objets
 *
 */
final public class PhysicCore implements Listenable<PhysicCore> {

	int framerate;
	int delta_t; // Le temps écoulé entre deux cycles (en ms)
	double delta_t_ms; // Le temps écoulé entre deux cycles (en s)

	public static double GRAVITY = -3000;

	private List<Listener<PhysicCore>> observers = new CopyOnWriteArrayList<Listener<PhysicCore>>();
	private Thread t = null;

	public PhysicCore(int framerate) throws IllegalArgumentException {
		if (framerate <= 0) {
			throw new IllegalArgumentException("Cannot set a null framerate value");
		}

		this.framerate = framerate;
		this.delta_t = 1000 / framerate;
		this.delta_t_ms = 1 / (double) framerate;
	}

	public void start() {
		if (t == null) {
			t = new Thread(new Play());
			t.start();
		}
	}

	public void stop() {
		if (t != null) {
			t.interrupt();
			t = null;
		}
	}

	class Play implements Runnable {
		public void run() {
			while (true) {

				notifyObservers();

				try {
					Thread.sleep(delta_t);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		}
	}

	/**
	 * @return le temps écoulé entre deux cycles (en SECONDES)
	 */
	public double getDelta() {
		return delta_t_ms;
	}

	@Override
	public synchronized void addObserver(Listener<PhysicCore> obs) {
		this.observers.add(obs);
	}

	@Override
	public synchronized void delObserver(Listener<PhysicCore> obs) {
		this.observers.remove(obs);
	}

	public synchronized void clearObservers() {
		this.observers.clear();
	}

	@Override
	public synchronized void notifyObservers() {

		Iterator<Listener<PhysicCore>> it = observers.iterator();
		while (it.hasNext()) {
			it.next().update(this);
		}

	}

}
