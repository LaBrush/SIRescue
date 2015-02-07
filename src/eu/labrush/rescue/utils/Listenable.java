package eu.labrush.rescue.utils;

/**
 * Interface générique d'EventListener utilisant la génétricité
 * Cette interface est à utliser conjointement avec Listener<T>
 *
 * @author adrienbocquet
 */

public interface Listenable<T> {

	public void addObserver(Listener<T> obs);
	public void delObserver(Listener<T> obs);
	
	public void notifyObservers();
}
