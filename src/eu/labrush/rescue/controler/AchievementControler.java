package eu.labrush.rescue.controler;

import java.util.Observable;
import java.util.Observer;

import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.Personnage;

/**
 * @author adrienbocquet
 *
 */
public class AchievementControler extends AbstractControler {

	ItemControler itemControler;
	HeroControler heroControler;

	Personnage hero;

	Observer heroListener = new Observer() {
		@Override
		public void update(Observable o, Object arg) {
			Personnage h = (Personnage) o;

			h.addObserver(new Observer() {
				@Override
				public void update(Observable o, Object arg) {
					if ("dead".equals(arg)) {
						setChanged();
						notifyObservers("restart");
						deleteObservers();
					}
				}
			});

		}
	};
	
	Observer itemListener = new Observer(){

		@Override
		public void update(Observable o, Object arg) {
			if(((ItemControler)o).getItems().size() == 0){
				setChanged();
				notifyObservers("done");
				deleteObservers();
			}
		}
		
	};

	public AchievementControler(Level level, HeroControler heroControler, ItemControler itemControler) {
		super(level);
		this.itemControler = itemControler;

		heroControler.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				if (hero != null)
					hero.deleteObserver(heroListener);

				if (((HeroControler) o).getPersonnage() != null)
					((HeroControler) o).getPersonnage().addObserver(heroListener);
			}
		});
		
		itemControler.addObserver(itemListener);

	}

}
