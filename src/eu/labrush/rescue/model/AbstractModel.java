package eu.labrush.rescue.model;

import java.util.Observable;
import java.util.Observer;

/**
 * Défini un modèle abstrait de donnée et fournit les outils nécessaire pour utiliser le pattern
 * observer à tous les niveaux de chaque modèle
 * 
 * TODO: Expliciter la description
 * 
 * NB: Chaque setter d'objets héritants d'AbstractModel dans des AbstractModel faire souscrire
 * l'objet au nouvel arrivants
 * 
 * NB2: Tout setter modifiant un type primitif doit appeler la méthode <i>throwUpdate</i> afin de
 * faire remonter la modification aux objets parents (sauf pour les consctructeurs puisque les
 * objets n'ont pas encore de listener)
 * 
 * @author adrienbocquet
 */
abstract public class AbstractModel extends Observable implements Observer {

	protected void throwUpdate() {
		setChanged();
		notifyObservers();
	}

	public void update(Observable obs, Object obj) {
		this.setChanged();
		this.notifyObservers();
	}
}
