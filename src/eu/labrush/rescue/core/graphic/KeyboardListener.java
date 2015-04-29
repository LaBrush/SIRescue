package eu.labrush.rescue.core.graphic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import eu.labrush.rescue.utils.Listenable;
import eu.labrush.rescue.utils.Listener;

/**
 * Cette classe a pour but de propager les événements liés au clavier en les
 * centralisant sur un seul JPanel ayant le focus tout le temps
 *
 * @author adrienbocquet
 */

@SuppressWarnings("serial")
public class KeyboardListener extends JPanel implements Listenable<KeyEvent>,
		KeyListener {

	private ArrayList<Listener<KeyEvent>> observers = new ArrayList<Listener<KeyEvent>>();
	private KeyEvent keyEvent = null;

	// Ne pas mettre le constructeur public pour éviter d'instancier la classe
	// dans un autre package
	KeyboardListener() {
		super();
		
		this.setFocusable(true);
		this.addKeyListener(this);
	}

	@Override
	public void addObserver(Listener<KeyEvent> obs) {
		this.observers.add((Listener<KeyEvent>) obs);
	}

	@Override
	public void delObserver(Listener<KeyEvent> obs) {
		this.observers.remove(obs);
	}

	public void clearObservers() {
		this.observers.clear();
	}

	@Override
	public void notifyObservers() {
		for (Listener<KeyEvent> obs : this.observers) {
			obs.update(this.keyEvent);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		this.keyEvent = e;
		this.notifyObservers();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		this.keyEvent = e;
		this.notifyObservers();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		this.keyEvent = e;
		this.notifyObservers();
	}

}
