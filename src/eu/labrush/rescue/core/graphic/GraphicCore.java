package eu.labrush.rescue.core.graphic;

import javax.swing.JFrame;

import eu.labrush.rescue.utils.Observer;

/**
 * @author adrienbocquet
 *
 */

@SuppressWarnings("serial")
public class GraphicCore extends JFrame {
	public static final int FRAMERATE = 120;
	
	private Panel pan = new Panel();
	private KeyboardListener keyboard = new KeyboardListener();
	
	private GraphicCore() {
		this.setTitle("Segui Rescue");
		this.setSize(600, 400);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setContentPane(this.pan);
		this.setVisible(true);
		
		this.setFocusable(false);
		pan.add(this.keyboard);
		
		//this.setContentPane(this.keyboard);
		this.keyboard.setFocusable(true);
		this.keyboard.addKeyListener(this.keyboard);
		this.keyboard.requestFocus();
		
	}

	/** Holder 
	 *  
	 *  on utilise un holder afin de permettre le multithreading
	 *  http://thecodersbreakfast.net/index.php?post/2008/02/25/26-de-la-bonne-implementation-du-singleton-en-java
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
		new Thread(new Play()).start();
	}

	class Play implements Runnable {
		public void run() {
			go();
		}
	}

	private void go() {
		while (true) {
			// On redessine notre Panneau
			pan.repaint();

			try {
				Thread.sleep(1000 / FRAMERATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void suscribe(Observer<DrawRequest> obs){
		this.getPan().addObserver(obs);
	}
	
	public Panel getPan() {
		return pan;
	}

	public KeyboardListener getKeyboard() {
		return keyboard;
	}

}