package eu.labrush.rescue.controler;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

import eu.labrush.rescue.IA.path.AStar;
import eu.labrush.rescue.IA.path.Point;
import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.Item;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.utils.Listener;
import eu.labrush.rescue.utils.Tuple;
import eu.labrush.rescue.view.ItemView;

/**
 * @author adrienbocquet
 *
 */
public class ItemControler extends AbstractControler {

	ArrayList<Point> trajet = new ArrayList<Point>();
	AStar star;

	List<Tuple<Item, ItemView>> items = new CopyOnWriteArrayList<Tuple<Item, ItemView>>();
	Personnage hero;
	BlocControler blocControler;

	Item currentItem;
	
	//Oui c'est moche, je sais...
	static Thread AThread = new Thread(); // Le thread dans lequel AStar fonctionne

	public ItemControler(Level level, HeroControler heroControler, BlocControler blocControler) {
		super(level);
		hero = heroControler.getPersonnage();
		this.blocControler = blocControler;

		heroControler.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				hero = ((HeroControler) o).getPersonnage();
				setAStar();
			}
		});

		this.graphics.addObserver(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				for (Tuple<Item, ItemView> v : items) {
					v.second.draw(req);
				}
			}
		});

		this.physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				if (items.size() == 0) {
					req.delObserver(this);
					return;
				}

				Item item = items.get(0).first;

				if (item.intersect(hero)) {
					item.deliver(hero);
					removeItem(item);
				}
			}
		});

	}

	public void add(Item item) {
		this.items.add(new Tuple<Item, ItemView>(item, new ItemView(item)));
		currentItem = this.items.get(0).first;
		setAStar();
		throwUpdate();
	}

	public void removeItem(Item item) {

		for (Tuple<Item, ItemView> t : this.items) {
			if (t.first == item) {
				this.items.remove(t);
			}
		}

		if (this.items.size() > 0) {
			currentItem = this.items.get(0).first;
		}
		else {
			currentItem = null;
		}

		throwUpdate();
	}

	public List<Tuple<Item, ItemView>> getItems() {
		return items;
	}

	private void setAStar() {
		// TEST ASTAR
		star = new AStar(10, this.blocControler.getBlocs());
		
		graphics.addObserver(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				for (Point p : trajet) {
					req.rect(p.x * 10, p.y * 10, 1, 1);
				}
			}
		});

		AThread.interrupt();
		
		AThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (currentItem != null) {

					try {
						trajet = star.findPath(new Point(hero), new Point(currentItem));
					} catch (Exception e1) {
						System.out.println("error");
						e1.printStackTrace();
					}

					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
						break;
					}
				}

			}
		});
		
		AThread.start();

	}

}
