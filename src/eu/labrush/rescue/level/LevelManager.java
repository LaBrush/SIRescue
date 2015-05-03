package eu.labrush.rescue.level;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import eu.labrush.rescue.core.audio.AudioCore;
import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.model.arme.Arme;
import eu.labrush.rescue.utils.Listener;

/**
 * @author adrienbocquet
 *
 */
public class LevelManager {

	private SAXParserFactory factory = SAXParserFactory.newInstance();

	private GraphicCore graphics;
	private PhysicCore physics;
	private AudioCore audio;

	private ArrayList<String> levels;
	private Level currentLevel;

	HashMap<String, Arme> armes;
	HashMap<String, String[]> botTypes;

	public LevelManager(GraphicCore graphics, PhysicCore physics, AudioCore audio) {
		this.graphics = graphics;
		this.physics = physics;
		this.audio = audio;
	}

	public void load(String source) {
		try {
			MainXMLHandler loader = new MainXMLHandler();

			SAXParser parser = factory.newSAXParser();
			parser.parse("resources/config/" + source, loader);

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
		currentLevel = null ;
		
		graphics.stop();
		physics.stop();

		graphics.clearObservers();
		physics.clearObservers();
		
		GraphicCore.getKeyboard().clearObservers();
		
		if (levels.size() == 0) {

			graphics.addObserver(new Listener<DrawRequest>() {
				@Override
				public void update(DrawRequest req) {
					req.fillRect(0, req.getHeight(), req.getWidth(), req.getHeight(), Color.BLACK);
					req.drawString("Vous avez fini SI Rescue !", 20, 250, Color.WHITE, 30, true);
					req.drawString("C'est négligeable", 20, 200, Color.WHITE, 20, true);
				}
			});

			graphics.start();
		}
		else {
			try {
				currentLevel = new Level(graphics, physics, audio);

				LevelXMLHandler loader = new LevelXMLHandler(currentLevel, armes, botTypes);

				SAXParser parser = factory.newSAXParser();
				parser.parse(levels.get(0), loader);

				currentLevel.getAchievementControler().addObserver(new Observer() {
					@Override
					public void update(Observable o, Object arg) {
						if ("done".equals(arg)) {
							levels.remove(0);
						}

						if ("done".equals(arg) || "restart".equals(arg)) {
							o.deleteObserver(this);
							loadLevel();
						}
					}
				});

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
}
