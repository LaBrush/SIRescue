package eu.labrush.rescue.controler;

import java.util.HashMap;

import eu.labrush.rescue.IA.behaviour.AbstractBotBehaviour;
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

	HashMap<Bot, AbstractBotBehaviour> bots = new HashMap<Bot, AbstractBotBehaviour>();
	AbstractBotBehaviour generalBehaviour ;
	
	Personnage hero ;
	PersonnageControler personnageControler ;
	TirControler tirControler ;
	
	public BotControler(Level level, PersonnageControler personnageControler, TirControler tirControler, HeroControler heroControler) {
		super(level);
	
		this.hero = heroControler.getPersonnage();
		System.out.println(hero);
		this.personnageControler = personnageControler;
		this.tirControler = tirControler ;
				
		this.physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				for (Bot b : bots.keySet()) {
					bots.get(b).update(b, hero);
				}
			}
		});
	}
	
	public void add(Bot ennemi) {
		bots.put(ennemi, new AbstractBotBehaviour());
		ennemi.setTirManager(tirControler.getTirInterface());
		personnageControler.add(ennemi);
	}
}
