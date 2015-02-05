/**
 * 
 */
package eu.labrush.rescue.utils;

/**
 * Interface générique d'EventListener utilisant la génétricité
 * Cette interface est à utliser conjointement avec Observable<T>
 * 
 * @author adrienbocquet
 */
public interface Observer<T> {

	public void update(T req);
	
}
