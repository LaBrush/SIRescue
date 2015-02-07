package eu.labrush.rescue.model;

import java.util.Observable;
import java.util.Observer;

/**
 * Défini un modèle abstrait de donnée et fournit les outils nécessaire pour 
 * utiliser le pattern observer à tous les niveaux de chaque modèle
 * 
 * TODO: Expliciter la description
 * 
 * NB: Chaque setter d'objets héritants d'AbstractModel dans des AbstractModel
 * faire souscrire l'objet au nouvel arrivants
 * 
 * @author adrienbocquet
 */
 abstract class AbstractModel extends Observable implements Observer {	
	
	AbstractModel() {
		//TODO: Try to add AbstractModel instance suscribe automation
		/*for(Field field : this.getClass().getFields()){
			System.out.println(field);
			try {
				if (field.get(this) instanceof AbstractModel) {
					((Observable) field.get(this)).addObserver(this);
				}
			} catch (IllegalArgumentException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}*/
		setChanged();
	}
	
	public void update(Observable obs, Object obj) {
		this.setChanged();
		this.notifyObservers();
	}
}
