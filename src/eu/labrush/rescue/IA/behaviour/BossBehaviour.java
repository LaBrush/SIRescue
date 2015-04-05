package eu.labrush.rescue.IA.behaviour;

import java.util.HashSet;

import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;


/**
 * @author ducousso
 *
 */
public class BossBehaviour implements BotBehaviour {

	HashSet<Bloc> blocs ;

	public BossBehaviour(HashSet<Bloc> blocs) {
		super();
		this.blocs = blocs;
	}

	@Override
	public void update(Personnage hero) {
		/*Bloc lePlusProche = (Bloc) this.blocs.toArray()[0];
		for(Bloc bloc: this.blocs){
			if(false){
				lePlusProche = bloc ;
			}
		}*/
		
	}
	
	/**
	 * @param blocs the blocs used by the fight algorithm
	 */
	public void setBlocs(HashSet<Bloc> blocs) {
		this.blocs = blocs;
	}

	@Override
	public void setBot(Bot bot) {
		// TODO Auto-generated method stub
		
	}

}
