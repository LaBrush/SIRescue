package eu.labrush.rescue.IA.behaviour;

import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;

/**
 * @author adrienbocquet
 *
 */
public interface BotBehaviour {

	public void update(Personnage hero);
	public void setBot(Bot bot);
	
}
