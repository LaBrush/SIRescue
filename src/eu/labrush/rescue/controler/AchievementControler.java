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
	PersonnageControler personnageControler;
	
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
	
	Observer endListener = new Observer(){

		@Override
		public void update(Observable o, Object arg) {
			if(itemControler.getItems().size() == 0 && personnageControler.getPersonnages().size() == 1){//plus d'item et seulement le héro
				setChanged();
				notifyObservers("done");
				deleteObservers();
			}
		}
		
	};

	public AchievementControler(Level level, HeroControler heroControler, ItemControler itemControler, PersonnageControler personnageControler) {
		super(level);
		this.itemControler = itemControler;
		this.personnageControler = personnageControler;

		heroControler.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				if (hero != null)
					hero.deleteObserver(heroListener);

				if (((HeroControler) o).getPersonnage() != null)
					((HeroControler) o).getPersonnage().addObserver(heroListener);
			}
		});
		
		itemControler.addObserver(endListener);
		personnageControler.addObserver(endListener);

	}

}
