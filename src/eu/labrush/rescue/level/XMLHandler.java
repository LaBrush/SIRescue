package eu.labrush.rescue.level;

import java.awt.Dimension;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;

public class XMLHandler extends DefaultHandler {

	AbstractObject current ;
	Level level ;
	
	XMLHandler(Level level){
		this.level = level ;
	}
	
	/**
	 * Redéfinition de la méthode pour intercepter les événements
	 */
	public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {

		switch (qName){
			case "bloc":
				current = new Bloc();				
				break ;
			case "hero":
				current = new Personnage(0, 0);
				break ;
			case "bot":
				current = new Bot(0, 0);				
				break ;

				
			case "position":
				setPosition(attrs);
				break ;
			case "dimensions":
				setDimension(attrs);
				break ;
		}
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
		
		switch (qName){
			case "bloc":
				level.blocControler.add((Bloc)current);
				break ;
			case "hero":
				level.heroControler.setPersonnage((Personnage)current);
				break ;
			case "bot":
				level.botControler.add((Bot)current);
				break ;
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
