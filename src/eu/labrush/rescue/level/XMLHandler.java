package eu.labrush.rescue.level;

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {
	// Nous nous servirons de cette variable plus tard
	private String node = null;

	// début du parsing
	public void startDocument() throws SAXException {
		System.out.println("Début du parsing");
	}

	// fin du parsing
	public void endDocument() throws SAXException {
		System.out.println("Fin du parsing");
	}
}
