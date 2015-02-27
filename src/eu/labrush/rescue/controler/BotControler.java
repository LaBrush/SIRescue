package eu.labrush.rescue.controler;

import java.util.HashSet;

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

	HashSet<Bot> bots = new HashSet<Bot>();
	AbstractBotBehaviour generalBehaviour ;
	
	Personnage hero ;
	PersonnageControler personnageControler ;
	
	public BotControler(Level level, PersonnageControler personnageControler) {
		super(level);
	
		this.hero = level.getHeroControler().getPersonnage();
		this.personnageControler = personnageControler;
		
		generalBehaviour = new AbstractBotBehaviour();
		
		this.physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				for (Bot b : bots) {
					generalBehaviour.update(b);
				}
			}
		});
	}

	public void add(Bot ennemi) {
		bots.add(ennemi);
		personnageControler.add(ennemi);
	}
}
