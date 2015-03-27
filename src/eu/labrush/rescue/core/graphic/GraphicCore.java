package eu.labrush.rescue.core.graphic;

import java.awt.Rectangle;
import java.awt.event.ComponentEvent;

import javax.swing.JFrame;

import eu.labrush.rescue.utils.Listener;

/**
 * @author adrienbocquet
 *
 */

@SuppressWarnings("serial")
final public class GraphicCore extends JFrame {
	public static int FRAMERATE = 60;

	private Panel pan = new Panel();
	private static KeyboardListener keyboard = new KeyboardListener();

	private boolean running = false;

	private GraphicCore() {
		this.setTitle("Segui Rescue");
		this.setSize(600, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setContentPane(this.pan);
		this.setVisible(true);
		this.setResizable(false);

		// On donne le focus au panel principal pour intercepter les appuis sur les touches du
		// clavier
		this.setFocusable(false);
		pan.add(keyboard);
		keyboard.requestFocus();

	}
	
	/**
	 * Holder
	 * 
	 * on utilise un holder afin de permettre le multithreading
	 * http://thecodersbreakfast.net/index.php
	 * ?post/2008/02/25/26-de-la-bonne-implementation-du-singleton-en-java
	 * 
	 */
	private static class GraphicCoreHolder {
		/** Instance unique non préinitialisée */
		private final static GraphicCore instance = new GraphicCore();
	}

	/** Point d'accès pour l'instance unique du singleton */
	public static GraphicCore getInstance() {
		return GraphicCoreHolder.instance;
	}

	public void start() {
		if (!running) {
			new Thread(new Play()).start();
			running = true;
		}
	}

	class Play implements Runnable {
		public void run() {
			go();
		}
	}

	private void go() {
		while (running) {
			// On redessine notre Panneau
			pan.repaint();

			try {
				Thread.sleep(1000 / FRAMERATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void addObserver(Listener<DrawRequest> obs) {
		this.getPan().addObserver(obs);
	}

	public Panel getPan() {
		return pan;
	}

	public static KeyboardListener getKeyboard() {
		return keyboard;
	}

}