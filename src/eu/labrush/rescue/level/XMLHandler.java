package eu.labrush.rescue.level;

import java.awt.Color;
import java.awt.Dimension;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;
import eu.labrush.rescue.model.arme.Arme;

public class XMLHandler extends DefaultHandler {

	private AbstractObject current;
	private int id;
	
	private HashMap<Integer, Bloc> blocs = new HashMap<Integer, Bloc>();

	Level level;

	XMLHandler(Level level) {
		this.level = level;
	}

	/**
	 * Redéfinition de la méthode pour intercepter les événements
	 */
	public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {

		switch (qName) {
			case "bloc":
				current = new Bloc();
				String attr =  attrs.getValue("id");
				id = (attr == null ? 0 : Integer.parseInt(attr));
				break;
			case "hero":
				current = new Personnage(0, 0);
				break;
			case "bot":
				current = new Bot(0, 0);
				break;

			case "position":
				setPosition(attrs);
				break;
			case "dimensions":
				setDimension(attrs);
				break;
			case "arme":
				setArme(attrs);
				break ;
		}
	}

	private void setArme(Attributes attrs) {
		 ((Personnage)current).addArme(new Arme("Resistance", 20, 300, Color.BLUE));		
	}

	private void setDimension(Attributes attrs) {
		Dimension dim = new Dimension();
		dim.setSize(Integer.parseInt(attrs.getValue(0)), Integer.parseInt(attrs.getValue(1)));
		current.setDimension(dim);
	}

	private void setPosition(Attributes attrs) {
		Vecteur pos = new Vecteur();

		pos.setX(Integer.parseInt(attrs.getValue(0)));
		pos.setY(Integer.parseInt(attrs.getValue(1)));

		current.getTrajectoire().setPosition(pos);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		switch (qName) {
			case "bloc":
				if(id > 0){
					blocs.put(id, (Bloc) current);
				}
				level.blocControler.add((Bloc) current);
				current = null; // on place l'élément courant à null afin d'éviter des modifications
								// après que la balise soit fermée
				break;
			case "hero":
				Personnage p = (Personnage) current;
				level.heroControler.setPersonnage(p);
				current = null;
				break;
			case "bot":
				Bloc bloc = blocs.get(id);
				Bot bot = (Bot) current ;
				
				if(bloc == null){ throw new SAXException("a bloc is poiting on a non existant bloc") ; }
				
				bot.getTrajectoire().setPosition(new Vecteur(bloc.getX(), bloc.getY()+bloc.getHeight()));
				
				level.botControler.add(bot, bloc);
				current = null;
				break;
		}
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
