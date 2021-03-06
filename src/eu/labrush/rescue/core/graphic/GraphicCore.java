package eu.labrush.rescue.core.graphic;

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

	private Thread t = null;

	private GraphicCore() {
		this.setTitle("Segui Rescue");
		this.setSize(1080, 720);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setContentPane(this.pan);
		this.setVisible(true);
		this.setResizable(false);

		// On donne le focus au panel principal pour intercepter les appuis sur les touches du
		// clavier
		setFocusable(false);
		pan.add(keyboard);
		keyboard.requestFocus();
		keyboard.setFocusTraversalKeysEnabled(false); // touche tabulation
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

	/**
	 * Point d'accès pour l'instance unique du singleton
	 * 
	 * @return The graphic core instance
	 * */
	public static GraphicCore getInstance() {
		return GraphicCoreHolder.instance;
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
			t = null ;
		}
	}

	class Play implements Runnable {
		public void run() {
			while (true) {
				// On redessine notre Panneau
				pan.repaint();

				try {
					Thread.sleep(1000 / FRAMERATE);
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				}
			}
		}
	}

	public void addObserver(Listener<DrawRequest> obs) {
		this.getPan().addObserver(obs);
	}

	public synchronized void clearObservers() {
		this.getPan().clearObservers();
	}

	public Panel getPan() {
		return pan;
	}

	public static KeyboardListener getKeyboard() {
		return keyboard;
	}
}