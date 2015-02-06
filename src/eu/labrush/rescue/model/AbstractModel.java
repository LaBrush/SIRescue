/**
 * 
 */
package eu.labrush.rescue.model;

import java.util.ArrayList;

import eu.labrush.rescue.utils.Observable;
import eu.labrush.rescue.utils.Observer;

/**
 * @author adrienbocquet
 *
 */
public abstract class AbstractModel implements Observable<AbstractModel> {
	
	ArrayList<Observer<AbstractModel>> observers = new ArrayList<Observer<AbstractModel>>();
	
	@Override
	public void addObserver(Observer<AbstractModel> obs) {
		this.observers.add(obs);
	}
	@Override
	public void delObserver(Observer<AbstractModel> obs) {
		this.observers.remove(obs);
	}
	@Override
	public void notifyObservers() {
		for(Observer<AbstractModel> obs: this.observers){
			obs.update(this);
		}
	}
	
}
