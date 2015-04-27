package eu.labrush.rescue.level;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import eu.labrush.rescue.core.graphic.GraphicCore;
import eu.labrush.rescue.core.physics.PhysicCore;
import eu.labrush.rescue.model.arme.Arme;

/**
 * @author adrienbocquet
 *
 */
public class LevelManager {

	private SAXParserFactory factory = SAXParserFactory.newInstance();
	
	private GraphicCore graphics ;
	private PhysicCore physics;
	
	private ArrayList<String> levels ;
	private Level currentLevel ;
	
	HashMap<String, Arme> armes;
	HashMap<String, String[]> botTypes;
	
	public LevelManager(GraphicCore graphics, PhysicCore physics) {
		this.graphics = graphics ;
		this.physics = physics ;
	}

	public void load(String source) {
		try {
			MainXMLHandler loader = new MainXMLHandler() ;
			
			SAXParser parser = factory.newSAXParser();
			parser.parse("resources/"+source, loader);
			
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
	
	public void nextLevel(){
		if(levels.size() == 0){
			return ;
		}
		
		graphics.stop();
		physics.stop();
		
		graphics.clearObservers() ;
		physics.clearObservers() ;
		
		currentLevel = new Level(graphics, physics);
		
		try {
			LevelXMLHandler loader = new LevelXMLHandler(currentLevel, armes, botTypes) ;
			
			SAXParser parser = factory.newSAXParser();
			parser.parse(levels.get(0), loader);
			
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
