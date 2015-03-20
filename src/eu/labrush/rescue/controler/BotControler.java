package eu.labrush.rescue.controler;

import java.util.HashMap;

import eu.labrush.rescue.IA.behaviour.BotBehaviour;
import eu.labrush.rescue.IA.behaviour.ToucherBotBehaviour;
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

	HashMap<Bot, BotBehaviour> bots = new HashMap<Bot, BotBehaviour>();
	ToucherBotBehaviour generalBehaviour ;
	
	Personnage hero ;
	PersonnageControler personnageControler ;
	TirControler tirControler ;
	
	public BotControler(Level level, PersonnageControler personnageControler, TirControler tirControler, HeroControler heroControler) {
		super(level);
	
		this.hero = heroControler.getPersonnage();
		this.personnageControler = personnageControler;
		this.tirControler = tirControler ;
				
		this.physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				for (Bot b : bots.keySet()) {
					bots.get(b).update(hero);
				}
			}
		});
	}
	
	public void add(Bot ennemi) {
		bots.put(ennemi, new ToucherBotBehaviour(ennemi));
		
		ennemi.setTirManager(tirControler.getTirInterface());
		personnageControler.add(ennemi);
	}
	
	public void add(Bot ennemi, BotBehaviour behaviour) {
		behaviour.setBot(ennemi);
		bots.put(ennemi, behaviour);
		
		ennemi.setTirManager(tirControler.getTirInterface());
		personnageControler.add(ennemi);
	}
}
