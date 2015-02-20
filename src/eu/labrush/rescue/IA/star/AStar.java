package eu.labrush.rescue.IA.star;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;

import eu.labrush.rescue.model.Bloc;

/**
 * @author adrienbocquet
 *
 */
public class AStar {

	private HashMap<Point, Noeud> liste_fermee = new HashMap<Point, Noeud>();
	private HashMap<Point, Noeud> liste_ouverte = new HashMap<Point, Noeud>();
	private HashSet<Point> points = new HashSet<Point>();

	private HashSet<Bloc> obstacles;

	private Noeud depart = new Noeud();
	private Point arrivee;
	
	private int maille ;

	public AStar(HashSet<Bloc> obstacles, int width, int height, int maille) {
		this.maille = maille ;
		this.obstacles = obstacles;
		this.points.add(new Point(15, 20));

		for (int i = 0; i <= width; i += maille) {
			for (int j = 0; j <= height; j += maille) {
				points.add(new Point(i, j));
			}
		}
	}

	private int distance(int x1, int y1, int x2, int y2) {
		return (x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2);
	}

	public ArrayList<Point> process(Point depart, Point arrivee) throws Exception {
		this.depart.parent = depart.clone();
		this.arrivee = arrivee.clone();

		// déroulement de l'algo A*

		// initialisation du noeud courant
		Point courant = depart.clone();

		// ajout de courant dans la liste ouverte
		liste_ouverte.put(courant, this.depart);
		ajouter_liste_fermee(courant);
		adjacentes(courant);

		/*
		 * tant que la destination n'a pas été atteinte et qu'il reste des noeuds à explorer dans la
		 * liste ouverte
		 */
		while (!((courant.x == arrivee.x) && (courant.y == arrivee.y)) && (!liste_ouverte.isEmpty())) {

			/*
			 * on cherche le meilleur noeud de la liste ouverte, on sait qu'elle n'est pas vide donc
			 * il existe
			 */
			courant = meilleur_noeud(liste_ouverte);

			// on le passe dans la liste fermee, il ne peut pas déjà y être
			ajouter_liste_fermee(courant);

			// on recommence la recherche des noeuds adjacents
			adjacentes(courant);
		}

		// si la destination est atteinte, on remonte le chemin
		if ((courant.x == arrivee.x) && (courant.y == arrivee.y)) {
			return retrouver_chemin();
		}
		else {
			System.out.println(courant);
			throw new Exception("Erreur dans l'algorithme");
		}
	}

	private ArrayList<Point> retrouver_chemin() {

		// l'arrivée est le dernier élément de la liste fermée
		Noeud tmp = liste_fermee.get(arrivee);
		ArrayList<Point> chemin = new ArrayList<Point>();

		Point n = new Point(arrivee.x, arrivee.y);
		Point prec = new Point(tmp.parent.x, tmp.parent.y);

		chemin.add(n);

		while (!prec.equals(new Point(depart.parent.x, depart.parent.y))) {
			n = new Point(prec.x, prec.y);
			chemin.add(n);

			tmp = liste_fermee.get(tmp.parent);
			prec.x = tmp.parent.x;
			prec.y = tmp.parent.y;
		}
		chemin.add(depart.parent);

		return chemin;
	}

	void ajouter_liste_fermee(Point p) {
		Noeud n = liste_ouverte.get(p);
		liste_fermee.put(p, n);

		// il faut le supprimer de la liste ouverte, ce n'est plus une solution explorable
		if (liste_ouverte.remove(p) == null)
			System.err.println("Erreur, le noeud n'apparait pas dans la liste ouverte, impossible à supprimer");
	}

	private Point meilleur_noeud(HashMap<Point, Noeud> list) {
		Iterator<Entry<Point, Noeud>> it = list.entrySet().iterator();
		Entry<Point, Noeud> entry = it.next();

		int m_coutf = entry.getValue().cout_f;
		Point m_noeud = entry.getKey();

		while (it.hasNext()) {
			entry = it.next();
			
			if (entry.getValue().cout_f < m_coutf) {
				m_coutf = entry.getValue().cout_f;
				m_noeud = entry.getKey();
			}
		}

		return m_noeud;
	}

	private void adjacentes(Point n) {
		Noeud tmp = new Noeud();

		// on met tous les noeud adjacents dans la liste ouverte (+vérif)
		for (int i = n.x - 1; i <= n.x + 1; i++) {
			for (int j = n.y - 1; j <= n.y + 1; j++) {
				if ((i == n.x) && (j == n.y)) // case actuelle n, on oublie
					continue;
				if (crossObstacle(i, j)) // obstace, terrain non franchissable, on oublie
					continue;

				Point it = new Point(i, j);
				if (!liste_fermee.containsKey(it)) {
					// le noeud n'est pas déjà présent dans la liste fermée

					/*
					 * calcul du cout G du noeud en cours d'étude : cout du parent + distance
					 * jusqu'au parent
					 */
					tmp.cout_g = liste_fermee.get(n).cout_g + distance(i, j, n.x, n.y);

					// calcul du cout H du noeud à la destination
					tmp.cout_h = distance(i, j, arrivee.x, arrivee.y);
					tmp.cout_f = tmp.cout_g + tmp.cout_h;
					tmp.parent = n;

					if (liste_ouverte.containsKey(it)) {
						/*
						 * le noeud est déjà présent dans la liste ouverte, il faut comparer les
						 * couts
						 */
						if (tmp.cout_f < liste_ouverte.get(it).cout_f) {
							/* si le nouveau chemin est meilleur, on met à jour */
							liste_ouverte.put(it, tmp);
						}

						// le noeud courant a un moins bon chemin, on ne change rien
					}
					else {
						// le noeud n'est pas présent dans la liste ouverte, on l'y ajoute
						liste_ouverte.put(new Point(i, j), tmp);
					}
				}
			}
		}
	}

	private boolean crossObstacle(int x, int y) {

		x *= maille ;
		y *= maille ;
		
		for (Bloc b : obstacles) {
			if (x >= b.getX() && x <= b.getX() + b.getWidth() && y >= b.getY() && y <= b.getY() + b.getHeight()) {
				return true;
			}
		}

		return false;
	}

	/**
	 * @return the points
	 */
	public HashSet<Point> getPoints() {
		return points;
	}

}
