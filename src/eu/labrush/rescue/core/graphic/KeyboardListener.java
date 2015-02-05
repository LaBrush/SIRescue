package eu.labrush.rescue.core.graphic;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JPanel;

import eu.labrush.rescue.utils.Observable;
import eu.labrush.rescue.utils.Observer;

/**
 * Cette classe a pour but de propager les événements liés au clavier en les
 * centralisant sur un seul JPanel ayant le focus tout le temps
 *
 * @author adrienbocquet
 */

@SuppressWarnings("serial")
public class KeyboardListener extends JPanel implements Observable<KeyEvent>,
		KeyListener {

	private ArrayList<Observer<KeyEvent>> observers = new ArrayList<Observer<KeyEvent>>();
	private KeyEvent keyEvent = null;

	// Ne pas mettre le constructeur public pour éviter d'instancier la classe
	// dans un autre package
	KeyboardListener() {
		super();
	}

	@Override
	public void addObserver(Observer<KeyEvent> obs) {
		this.observers.add(obs);
	}

	@Override
	public void delObserver(Observer<KeyEvent> obs) {
		this.observers.remove(obs);
	}

	@Override
	public void notifyObservers() {
		for (Observer<KeyEvent> obs : this.observers) {
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
