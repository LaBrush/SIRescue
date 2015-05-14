package eu.labrush.rescue.level;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

/**
 * @author adrienbocquet
 *
 */
public class SoundXMLHandler extends DefaultHandler {

	@SuppressWarnings("rawtypes")
	HashMap<Class, HashMap<String, String>> resources = new HashMap<Class, HashMap<String, String>>();
	ArrayList<String> levels = new ArrayList<String>();
	
	public SoundXMLHandler() {
	}

	public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {
		switch (qName) {
			case "sound":
				@SuppressWarnings("rawtypes")
				Class c = null;

				try {
					c = Class.forName(attrs.getValue("name"));
					if (!resources.containsKey(c)) {
						resources.put(c, new HashMap<String, String>());
					}

					resources.get(c).put(attrs.getValue("case"), attrs.getValue("src"));
					
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				break;
			
			case "level":
				levels.add(attrs.getValue("src"));
				break; 

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
