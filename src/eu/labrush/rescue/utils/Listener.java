/**
 * 
 */
package eu.labrush.rescue.utils;

/**
 * Interface générique d'EventListener utilisant la génétricité
 * Cette interface est à utliser conjointement avec Listenable
 * 
 * @author adrienbocquet
 */
public interface Listener<T> {

	public void update(T req);
	
}
