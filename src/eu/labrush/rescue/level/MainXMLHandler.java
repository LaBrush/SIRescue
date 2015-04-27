package eu.labrush.rescue.level;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import eu.labrush.rescue.model.arme.Arme;

public class MainXMLHandler extends DefaultHandler {

	HashMap<String, Arme> armes = new HashMap<String, Arme>();
	HashMap<String, String[]> botTypes = new HashMap<String, String[]>();
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
				break ;
		}
	}

	private void addLevel(Attributes attrs) {
		levels.add("resources/" + attrs.getValue("src"));
	}

	private void addArme(Attributes attrs) {
		// si on ajoute une arme (noeud armes)
		if (attrs.getValue("name") != null && attrs.getValue("damage") != null && attrs.getValue("reload") != null) {
			Arme arme = new Arme(attrs.getValue("tir"), Integer.parseInt(attrs.getValue("damage")), Integer.parseInt(attrs.getValue("reload")),
					Integer.parseInt(attrs.getValue("recul")));
			armes.put(attrs.getValue("name"), arme);
		}
	}

	private void addBot(Attributes attrs) {
		String[] attributes = new String[2];

		attributes[0] = attrs.getValue("arme");
		attributes[1] = attrs.getValue("comportement");

		botTypes.put(attrs.getValue("name"), attributes);
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
