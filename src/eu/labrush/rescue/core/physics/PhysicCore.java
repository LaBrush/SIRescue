package eu.labrush.rescue.core.physics;

import java.util.Vector;

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

	private Vector<Listener<PhysicCore>> observers = new Vector<Listener<PhysicCore>>();
	private boolean running = false;

	public PhysicCore(int framerate) throws IllegalArgumentException {
		if (framerate <= 0) {
			throw new IllegalArgumentException("Cannot set a null framerate value");
		}

		this.framerate = framerate;
		this.delta_t = 1000 / framerate;
		this.delta_t_ms = 1 / (double) framerate;
	}

	public void start() {
		if (!running) {
			new Thread(new Play()).start();
			running = true;
		}
	}

	public void stop() {
		this.running = false;
	}

	class Play implements Runnable {
		public void run() {
			while (running) {

				notifyObservers();

				try {
					Thread.sleep(delta_t);
				} catch (InterruptedException e) {
					e.printStackTrace();
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
		for (Listener<PhysicCore> obs : this.observers) {
			obs.update(this);
		}
	}

}
