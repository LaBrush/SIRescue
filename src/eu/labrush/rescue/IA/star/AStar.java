package eu.labrush.rescue.IA.star;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Bloc;

/**
 * @author adrienbocquet
 */
@SuppressWarnings("unused")
public class AStar {

	private Point[][] points;
	private HashSet<Bloc> obstacles;

	ArrayList<ArrayList<Point>> closed_list;

	private Point depart;
	private Point arrivee;

	private int maille;

	public AStar(HashSet<Bloc> obstacles, int width, int height, int maille) {
		this.maille = maille;
		this.obstacles = obstacles;

		points = new Point[(width - (width % maille) + maille)][(height - (height % maille) + maille)];

		for (int i = 0; i <= width; i += maille) {
			for (int j = 0; j <= height; j += maille) {
				points[i][j] = new Point(i, j);
			}
		}
	}

	public ArrayList<Point> findPath(Point depart, Point arrivee) {

		this.depart = depart;
		this.arrivee = arrivee;

		this.closed_list = new ArrayList<ArrayList<Point>>();

		ArrayList<Point> current_col = new ArrayList<Point>();
		current_col.add(depart);
		closed_list.add(current_col);

		ArrayList<Point> next_col = new ArrayList<Point>();

		do {
			next_col = new ArrayList<Point>();
			closed_list.add(next_col);

			for (Point node : current_col) {
				next_col.addAll(voisins(node));
			}

			current_col = next_col;
		} while (!next_col.contains(arrivee));

		// On reconstitue ensuite le trajet
		ArrayList<Point> trajet = new ArrayList<Point>();
		trajet.add(arrivee);
		Point node = arrivee;
		
		int i = closed_list.size() - 2;

		while (i >= 0) {
			
			current_col = closed_list.get(i);
			Point best = null;
			int lim = 1;

			while (best == null) {
				for (Point p : current_col) {
					// Si le point est voisin du noeud considéré
					if (distance(p, node)<= lim) {
						best = p;
						break;
					}
				}
				lim++;
			}
			
			trajet.add(best);
			node = best ;
			i--;
		}

		ArrayList<Point> tmp = new ArrayList<Point>();
		for (ArrayList<Point> a : closed_list) {
			tmp.addAll(a);
		}

		return trajet;
		//return tmp ;

	}

	private ArrayList<Point> voisins(Point parent) {
		Point[] voisins = parent.getVoisins();
		ArrayList<Point> open_list = new ArrayList<Point>();

		for (Point p : voisins) {
			if (p != null && !cross(p) && !inClosedList(p)) {
				open_list.add(p);
			}
		}

		return open_list;
	}

	private boolean inClosedList(Point p) {
		for (ArrayList<Point> col : closed_list) {
			if (col.contains(p)) {
				return true;
			}
		}
		return false;
	}

	private int distance(Point p1, Point p2) {
		return (int) (Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
	}

	public boolean cross(Point p) {
		int x = p.x * maille, y = p.y * maille;

		for (Bloc b : obstacles) {
			if (x >= b.getX() && x <= b.getX() + b.getWidth() && y >= b.getY() && y <= b.getY() + b.getHeight()) {
				return true;
			}
		}

		return false;
	}
}
