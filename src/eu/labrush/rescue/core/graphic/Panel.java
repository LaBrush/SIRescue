/**
 * 
 */
package eu.labrush.rescue.core.graphic;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import eu.labrush.rescue.utils.Listenable;
import eu.labrush.rescue.utils.Listener;

/**
 * @author adrienbocquet
 *
 */

@SuppressWarnings("serial")
public class Panel extends JPanel implements Listenable<DrawRequest> {

	private ArrayList<Listener<DrawRequest>> observers = new ArrayList<Listener<DrawRequest>>();
	private Graphics2D g;

	public void paintComponent(Graphics g) {
		this.g = (Graphics2D) g;

		// On décide d'une couleur de fond pour notre rectangle
		this.g.setColor(Color.white);
		// On dessine celui-ci afin qu'il prenne tout la surface
		this.g.fillRect(0, 0, this.getWidth(), this.getHeight());

		this.g.setColor(Color.BLACK);
		this.notifyObservers();
	}

	@Override
	public void addObserver(Listener<DrawRequest> obs) {
		this.observers.add(obs);

	}

	@Override
	public void delObserver(Listener<DrawRequest> obs) {
		this.observers.remove(obs);

	}

	@Override
	public void notifyObservers() {
		for (Listener<DrawRequest> obs : this.observers) {
			obs.update(new DrawRequest(this.g, this.getWidth(), this.getHeight()));
		}
	}

}
