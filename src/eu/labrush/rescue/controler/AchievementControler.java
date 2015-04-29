package eu.labrush.rescue.controler;

import java.util.Observable;
import java.util.Observer;

import eu.labrush.rescue.level.Level;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Personnage;

/**
 * @author adrienbocquet
 *
 */
public class AchievementControler extends AbstractControler {

	BlocControler blocControler;
	HeroControler heroControler;
	
	Personnage hero;

	Observer heroListener = new Observer() {
		@Override
		public void update(Observable o, Object arg) {
			Personnage h = (Personnage) o;

			for (Bloc b : blocControler.getBlocs()) {
				
				if (b.isEnder() && h.getX() >= b.getX() && h.getX() < b.getX() + b.getWidth() && h.getY() == b.getY() + b.getHeight()) {
					setChanged();
					notifyObservers("done");
					deleteObservers();
				}
			}
		}
	};

	public AchievementControler(Level level, HeroControler heroControler, BlocControler blocControler) {
		super(level);
		this.blocControler = blocControler;

		heroControler.addObserver(new Observer() {
			@Override
			public void update(Observable o, Object arg) {
				if (hero != null)
					hero.deleteObserver(heroListener);

				if (((HeroControler) o).getPersonnage() != null)
					((HeroControler) o).getPersonnage().addObserver(heroListener);
			}
		});

	}

}
