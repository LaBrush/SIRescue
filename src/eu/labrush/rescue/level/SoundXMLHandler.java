package eu.labrush.rescue.level;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import eu.labrush.rescue.controler.AudioControler;

/**
 * @author adrienbocquet
 *
 */
public class SoundXMLHandler extends DefaultHandler {

	AudioControler audioControler ;
	
	public SoundXMLHandler(Level level) {
		this.audioControler = level.audioControler ;
	}

	public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
		if(qName == "sound"){
			@SuppressWarnings("rawtypes")
			Class c = null ;
			
			try {
				c = Class.forName(attrs.getValue("name"));
				audioControler.add(c, attrs.getValue("case"), attrs.getValue("src"));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}
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
