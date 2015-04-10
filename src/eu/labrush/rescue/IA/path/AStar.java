package eu.labrush.rescue.IA.path;

import java.util.ArrayList;
import java.util.HashSet;

import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Vecteur;

/**
 * @author adrienbocquet
 */
@SuppressWarnings("unused")
public class AStar {

	private Vecteur speed ;
	private double gravity ;
	private int maxJump ;
	
	private Point[][] points;
	private HashSet<Bloc> obstacles;

	ArrayList<ArrayList<Point>> closed_list;

	private Point depart;
	private Point arrivee;

	private int maille;

	public AStar(HashSet<Bloc> obstacles, int width, int height, int maille) {
		this.speed = new Vecteur(300, 900);
		this.gravity = PhysicCore.GRAVITY;

		//TODO: Pourquoi devoir diviser par 1/4 au lieu 1/2 ???
		int maxJump = (int) (-.25* Math.pow(speed.getY(),2) / gravity / maille);
		
		this.maille = maille;
		this.obstacles = obstacles;

		points = new Point[(width - (width % maille) + maille)][(height - (height % maille) + maille)];

		for (int i = 0; i <= width; i += maille) {
			for (int j = 0; j <= height; j += maille) {
				points[i][j] = new Point(i, j);
				for(Bloc b: this.obstacles){
					if(onBloc(b, points[i][j]) == true){
						points[i][j].cineticReserve = maxJump ;
					}
				}
			}
		}
	}

	public ArrayList<Point> findPath(Point depart, Point arrivee) {

		depart.cineticReserve = 5 ;
		
		this.depart = depart;
		this.arrivee = arrivee;

		this.closed_list = new ArrayList<ArrayList<Point>>();

		ArrayList<Point> current_col = new ArrayList<Point>();
		current_col.add(depart);
		closed_list.add(current_col);

		ArrayList<Point> next_col = new ArrayList<Point>();

		boolean impossible = false ;
		
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
					if (distance(p, node) <= lim) {
						best = p;
						break;
					}
				}
				lim++;
			}

			trajet.add(best);
			node = best;
			i--;
		}

		return trajet;
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

	//Teste si un point est à moins d'une unité au dessus d'un bloc
	private boolean onBloc(Bloc b, Point p){
		int xb = (int)(b.getX()/maille), yb = (int)(b.getY()/maille);
		int wb = (int)b.getWidth(), hb = (int)b.getHeight();
		int xp = p.x, yp = p.y;
		
		return (inRange(yp, yb+hb, yb+hb+1) && inRange(xp, xb, xb+wb)) ;
	}

	private boolean inRange(int val, int sub, int sup) {
		return val <= sup && val >= sub;
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
