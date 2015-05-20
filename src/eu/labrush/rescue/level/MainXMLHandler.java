package eu.labrush.rescue.level;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import eu.labrush.rescue.IA.behaviour.BotBehaviour;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.arme.Arme;
import eu.labrush.rescue.utils.Tuple;

public class MainXMLHandler extends DefaultHandler {

	HashMap<String, Arme> armes = new HashMap<String, Arme>();
	HashMap<String, Tuple<Bot, BotBehaviour>> botTypes = new HashMap<String, Tuple<Bot, BotBehaviour>>();
	ArrayList<String> levels = new ArrayList<String>();

	/**
	 * Redéfinition de la méthode pour intercepter les événements
	 */
	public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {

		switch (qName) {
			case "arme":
				addArme(attrs);
				break;
			case "botType":
				addBot(attrs);
				break;
			case "level":
				addLevel(attrs);
				break;
		}
	}

	private void addLevel(Attributes attrs) {
		levels.add("resources/config/" + attrs.getValue("src"));
	}

	private void addArme(Attributes attrs) {
		Arme arme = new Arme(attrs.getValue("tir"), Integer.parseInt(attrs.getValue("damage")), Integer.parseInt(attrs.getValue("reload")),
				Integer.parseInt(attrs.getValue("recul")));
		
		armes.put(attrs.getValue("name"), arme);

	}

	private void addBot(Attributes attrs) throws SAXException {
		Bot bot = new Bot(0, 0);

		bot.addArme(armes.get(attrs.getValue("arme")).clone());

		bot.setLife(Integer.parseInt(attrs.getValue("life")));
		bot.getDimension().setSize(Integer.parseInt(attrs.getValue("width")), Integer.parseInt(attrs.getValue("height")));

		bot.setImage(attrs.getValue("image") != null ? attrs.getValue("image") : "");

		BotBehaviour behaviour = null;

		try {
			behaviour = (BotBehaviour) Class.forName("eu.labrush.rescue.IA.behaviour." + attrs.getValue("comportement")).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
			throw new SAXException("Erreur durant la creation d'un comoprtement ennemi");
		}

		botTypes.put(attrs.getValue("name"), new Tuple<Bot, BotBehaviour>(bot, behaviour));
	}

	public void characters(char[] data, int start, int end) {
	}

	public void notationDecl(String name, String publicId, String systemId) {
		System.out.println(name + " - " + publicId + " - " + systemId);
	}

	public void error(SAXParseException e) throws SAXException {
		System.out.println("ERROR : " + e.getMessage());
		throw e;
	}

	public void fatalError(SAXParseException e) throws SAXException {
		System.out.println("FATAL ERROR : " + e.getMessage());
		throw e;
	}

	public void warning(SAXParseException e) throws SAXException {
	}
}
