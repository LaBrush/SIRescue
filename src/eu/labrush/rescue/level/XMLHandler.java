package eu.labrush.rescue.level;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {

	/**
	 * Redéfinition de la méthode pour intercepter les événements
	 */
	public void startElement(String namespaceURI, String lname, String qname, Attributes attrs) throws SAXException {

		System.out.println("---------------------------------------------");
		// cette variable contient le nom du nœud qui a créé l'événement
		System.out.println("qname = " + qname);

		// Cette dernière contient la liste des attributs du nœud
		if (attrs != null) {
			for (int i = 0; i < attrs.getLength(); i++) {
				// nous récupérons le nom de l'attribut
				String aname = attrs.getLocalName(i);
				// Et nous affichons sa valeur
				System.out.println("Attribut " + aname + " valeur : " + attrs.getValue(i));
			}
		}
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("Fin de l'élément " + qName);
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
