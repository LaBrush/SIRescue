package eu.labrush.rescue.level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.sound.sampled.Clip;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import eu.labrush.rescue.IA.behaviour.BotBehaviour;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.arme.Arme;
import eu.labrush.rescue.utils.Tuple;

/**
 * @author adrienbocquet
 *
 */
public class LevelManager {

	private SAXParserFactory factory = SAXParserFactory.newInstance();

	private GraphicCore graphics;
	private PhysicCore physics;

	private ArrayList<String> levels;
	private Level currentLevel;

	HashMap<String, Arme> armes;
	HashMap<String, Tuple<Bot, BotBehaviour>> botTypes;
	
	SoundXMLHandler soundsHandler ;
	Clip level_theme ;

	public LevelManager(GraphicCore graphics, PhysicCore physics) {
		this.graphics = graphics;
		this.physics = physics;
		
		loadSounds();
	}

	public void load(String source) {
		try {
			MainXMLHandler loader = new MainXMLHandler();

			SAXParser parser = factory.newSAXParser();
			parser.parse("./resources/config/" + source, loader);

			this.levels = loader.levels;
			this.armes = loader.armes;
			this.botTypes = loader.botTypes;

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void loadLevel() {
		if(currentLevel != null){
			currentLevel.heroControler.stop();
			level_theme.stop();
		}
		
		currentLevel = null ;
		
		graphics.stop();
		physics.stop();

		graphics.clearObservers();
		physics.clearObservers();
		
		GraphicCore.getKeyboard().clearObservers();
		
		System.gc();
		
		if (levels.size() == 0) {
		
			currentLevel = new EndLevel(graphics, physics);
			level_theme = currentLevel.audioControler.play(soundsHandler.levels.get(0));
			
			graphics.start();
			physics.start();
		}
		else {
			try {
				currentLevel = new Level(graphics, physics);

				LevelXMLHandler loader = new LevelXMLHandler(currentLevel, armes, botTypes);

				SAXParser parser = factory.newSAXParser();
				parser.parse(levels.get(0), loader);

				currentLevel.audioControler.setResources(soundsHandler.resources) ;
				currentLevel.getAchievementControler().addObserver(new Observer() {
					@Override
					public void update(Observable o, Object arg) {

						if ("done".equals(arg)) {
							levels.remove(0);
							soundsHandler.levels.remove(0);
						}

						if ("done".equals(arg) || "restart".equals(arg)) {
							o.deleteObserver(this);
							loadLevel();
						}
					}
				});

				level_theme = currentLevel.audioControler.play(soundsHandler.levels.get(0));
				graphics.start();
				physics.start();
				
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public synchronized void loadSounds() {
		soundsHandler = new SoundXMLHandler();

		SAXParser parser;
		try {
			parser = factory.newSAXParser();
			parser.parse("resources/config/sounds.xml", soundsHandler);
			
		} catch (IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
		}
	}
}
