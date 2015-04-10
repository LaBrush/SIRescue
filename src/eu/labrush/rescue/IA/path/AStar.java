package eu.labrush.rescue.IA.path;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import eu.labrush.rescue.model.Bloc;

/**
 * Algorithm taken from the Wikipedia's dedicated article
 * http://en.wikipedia.org/wiki/A*_search_algorithm
 * 
 * @author adrienbocquet
 */
public class AStar {

	int maille;
	int maxJump = 150;
	HashSet<Bloc> obstacles;

	public AStar(int maille, HashSet<Bloc> obstacles) {
		this.maille = maille;
		this.obstacles = obstacles;
	}

	public ArrayList<Point> findPath(Point start, Point goal) throws Exception {

		if (cross(start)) {
			throw new Exception("The starting point is inside a block");
		}
		if (cross(goal)) {
			throw new Exception("The goal is inside a block");
		}

		HashSet<Point> closeset = new HashSet<Point>();
		HashSet<Point> openset = new HashSet<Point>();
		HashMap<Point, Point> came_from = new HashMap<Point, Point>();

		HashMap<Point, Double> g_score = new HashMap<Point, Double>();
		HashMap<Point, Double> f_score = new HashMap<Point, Double>();

		openset.add(start);
		g_score.put(start, .0);
		f_score.put(start, g_score.get(start) + heuristic_cost_estimate(start, goal));

		while (!openset.isEmpty()) {
			Point current = min_f_score(f_score, openset);

			if (current.equals(goal)) {
				return reconstruct_path(came_from, goal);
			}

			openset.remove(current);
			closeset.add(current);

			for (Point neighbor : neighbor_nodes(current)) {
				if (closeset.contains(neighbor)) {
					continue;
				}

				double tentative_g_score = g_score.get(current) + dist_between(current, neighbor);

				if (!openset.contains(neighbor) || tentative_g_score < g_score.get(neighbor)) {
					came_from.put(neighbor, current);
					g_score.put(neighbor, tentative_g_score);
					f_score.put(neighbor, g_score.get(neighbor) + heuristic_cost_estimate(neighbor, goal));

					if (!openset.contains(neighbor)) {
						openset.add(neighbor);
					}
				}

			}
		}

		throw new Exception("No solution found");
	}

	private ArrayList<Point> neighbor_nodes(Point current) {
		ArrayList<Point> voisins = new ArrayList<Point>();

		int x = current.x, y = current.y;

		for (int i = x - 1; i <= x + 1; i++) {
			for (int j = y - 1; j <= y + 1; j++) {

				// si le point considéré est hors de la grille ou qu'il s'agit du point lui-même, on
				// saute
				if (i < 0 || j < 0 || (i == x && j == y))
					continue;

				Point p = new Point(i, j);
				if (!cross(p)) {
					voisins.add(new Point(i, j));
				}
			}
		}

		return voisins;
	}

	private Point min_f_score(HashMap<Point, Double> f_score, HashSet<Point> openset) {
		double min_score = Double.MAX_VALUE;
		Point best = null;

		for (Point p : f_score.keySet()) {
			if (f_score.get(p) < min_score && openset.contains(p)) {
				best = p;
				min_score = f_score.get(p);
			}
		}

		return best;
	}

	private double heuristic_cost_estimate(Point point, Point goal) {

		Bloc vertical = null;
		int minHeightDelta = Integer.MAX_VALUE;

		for (Bloc b : obstacles) {
			if (b.isHurting()) {
				continue;
			}

			if ((point.x * maille >= b.getX()) && (point.x * maille <= b.getX() + b.getWidth()) && (point.y * maille >= b.getY())
					&& (point.y * maille - b.getY() < minHeightDelta)) {
				vertical = b;
				minHeightDelta = (int) (point.y * maille - b.getY());
			}
		}
		return dist_between(point, goal) + 10 * minHeightDelta + (minHeightDelta > maxJump ? -100 : 250);
	}

	private ArrayList<Point> reconstruct_path(HashMap<Point, Point> came_from, Point current) {
		ArrayList<Point> total_path = new ArrayList<Point>();
		total_path.add(current);

		while (came_from.containsKey(current)) {
			current = came_from.get(current);
			total_path.add(current);
		}

		return total_path;
	}

	private boolean cross(Point p) {
		int x = p.x * maille, y = p.y * maille;

		for (Bloc b : obstacles) {
			if (x >= b.getX() && x <= b.getX() + b.getWidth() && y >= b.getY() && y <= b.getY() + b.getHeight()) {
				return true;
			}
		}

		return false;
	}

	private double dist_between(Point p1, Point p2) {
		return Math.sqrt(Math.pow(p1.x - p2.x, 2) + Math.pow(p1.y - p2.y, 2));
	}
}
