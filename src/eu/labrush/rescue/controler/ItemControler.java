package eu.labrush.rescue.controler;

import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.Item;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.utils.Listener;
import eu.labrush.rescue.view.ItemView;

/**
 * @author adrienbocquet
 *
 */
public class ItemControler extends AbstractControler {

	HashMap<Item, ItemView> items = new HashMap<Item, ItemView>();
	Personnage hero ;
	
	public ItemControler(Level level, HeroControler heroControler) {
		super(level);
		hero = heroControler.getPersonnage();
		
		heroControler.addObserver(new Observer(){
			@Override
			public void update(Observable o, Object arg) {
				hero = ((HeroControler) o).getPersonnage();
			}
		});

		this.graphics.addObserver(new Listener<DrawRequest>() {
			@Override
			public void update(DrawRequest req) {
				for(ItemView v: items.values()){
					v.draw(req);
				}
			}
		});	
		
		this.physics.addObserver(new Listener<PhysicCore>(){
			@Override
			public void update(PhysicCore req) {
				for(Item item: items.keySet()){
					if(item.intersect(hero)){
						item.deliver(hero);
						removeItem(item);
					};
				}
			}
		});
	}
	
	public void add(Item item){
		this.items.put(item, new ItemView(item));
	}
	
	public void removeItem(Item item){
		this.items.remove(item);
	}

	public HashMap<Item, ItemView> getItems() {
		return items;
	}

}
