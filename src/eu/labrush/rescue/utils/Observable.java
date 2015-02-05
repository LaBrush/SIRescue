package eu.labrush.rescue.utils;

/**
 * Interface générique d'EventListener utilisant la génétricité
 * Cette interface est à utliser conjointement avec Observer<T>
 *
 * @author adrienbocquet
 */

public interface Observable<T> {

	public void addObserver(Observer<T> obs);
	public void delObserver(Observer<T> obs);
	
	public void notifyObservers();
}
