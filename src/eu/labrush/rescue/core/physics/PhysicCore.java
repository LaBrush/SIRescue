/**
 * 
 */
package eu.labrush.rescue.core.physics;

import java.util.ArrayList;

import eu.labrush.rescue.utils.Listenable;
import eu.labrush.rescue.utils.Listener;

/**
 * @author adrienbocquet
 *
 *         Le coeur physique permet de réaliser le lien entre le modèle physique
 *         et les objets et assure la conversion des unités (m/s en
 *         pixels/cycles) afin de permettre aux animations d'être humainement
 *         comprehensibles.
 *
 */
final public class PhysicCore implements Listenable<PhysicCore> {

	int framerate;
	int delta_t ; // Le temps écoulé entre deux cycles

	static int ECHELLE = 100; // ECHELLE = PIXELS / METRES
	static double GRAVITY = -.003 ;

	private ArrayList<Listener<PhysicCore>> observers = new ArrayList<Listener<PhysicCore>>();
	private boolean running = false;

	public PhysicCore(int framerate) throws IllegalArgumentException{
		if(framerate <= 0){
			throw new IllegalArgumentException("Cannot set a null framerate value");
		}
		
		this.framerate = framerate;
		this.delta_t = 1000 / framerate;
	}

	public void start() {
		if (!running) {
			new Thread(new Play()).start();
			running = true;
		}
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

	public int getDelta() {
		return this.delta_t;
	}

	@Override
	public void addObserver(Listener<PhysicCore> obs) {
		this.observers.add(obs);
	}

	@Override
	public void delObserver(Listener<PhysicCore> obs) {
		this.observers.remove(obs);
	}

	@Override
	public void notifyObservers() {
		for (Listener<PhysicCore> obs : this.observers) {
			obs.update(this);
		}
	}

}
