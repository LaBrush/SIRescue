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
	TirControler tirControler ;
	
	public BotControler(Level level, PersonnageControler personnageControler, TirControler tirControler, HeroControler heroControler) {
		super(level);
	
		this.hero = heroControler.getPersonnage();
		this.personnageControler = personnageControler;
		this.tirControler = tirControler ;
		
		generalBehaviour = new AbstractBotBehaviour();
		
		this.physics.addObserver(new Listener<PhysicCore>() {
			@Override
			public void update(PhysicCore req) {
				for (Bot b : bots) {
					generalBehaviour.update(b, hero);
				}
			}
		});
	}
	
	public void add(Bot ennemi) {
		bots.add(ennemi);
		ennemi.setTirManager(tirControler.getTirInterface());
		personnageControler.add(ennemi);
	}
}
