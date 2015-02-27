package eu.labrush.rescue.controler;

import java.util.HashSet;

import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.utils.Listener;

/**
 * @author adrienbocquet
 *
 */
public class BotControler extends AbstractControler {

	HashSet<Bot> ennemis = new HashSet<Bot>();
	
	Personnage hero ;
	
	BotControler(Level level) {
		super(level);
	
		this.hero = level.getHeroControler().getPersonnage();
		
		this.physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				for (Bot e : ennemis) {
					
				}
			}
		});
	}

}
