package eu.labrush.rescue.level;

import java.util.ArrayList;
import java.util.HashMap;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import eu.labrush.rescue.IA.behaviour.BossBehaviour;
import eu.labrush.rescue.IA.behaviour.BotBehaviour;
import eu.labrush.rescue.IA.behaviour.ToucherBotBehaviour;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.ArmeItem;
import eu.labrush.rescue.model.Bloc;
import eu.labrush.rescue.model.Bot;
import eu.labrush.rescue.model.Item;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Vecteur;
import eu.labrush.rescue.model.arme.Arme;
import eu.labrush.rescue.utils.Tuple;

/**
 * @author adrienbocquet
 *
 */
public class LevelXMLHandler extends DefaultHandler {

	Level level;

	private AbstractObject current;
	private int id;

	private HashMap<Integer, Bloc> blocs = new HashMap<Integer, Bloc>();
	private HashMap<String, Arme> armes;
	private HashMap<String, Tuple<Bot, BotBehaviour>> botTypes;

	private ArrayList<Integer> usedBlocId = new ArrayList<Integer>();

	private String current_data;
	private BotBehaviour behaviour;

	public LevelXMLHandler(Level level, HashMap<String, Arme> armes, HashMap<String, Tuple<Bot, BotBehaviour>> botTypes) {
		this.level = level;
		this.armes = armes;
		this.botTypes = botTypes;
	}

	/**
	 * Redéfinition de la méthode pour intercepter les événements
	 */
	public void startElement(String namespaceURI, String lName, String qName, Attributes attrs) throws SAXException {

		switch (qName) {
			case "bloc":
				current = new Bloc();

				String attr = attrs.getValue("id");
				id = (attr == null ? 0 : Integer.parseInt(attr));

				((Bloc) current).setHurting(Boolean.parseBoolean(attrs.getValue("hurting")));
				break;
			case "hero":
				current = new Personnage(0, 0);
				break;
			case "bot":
				// see @setType
				break;
			case "item":
				String val = attrs.getValue("content");

				if (val == null) {
					val = "";
				}

				switch (val) {
					case "arme":
						Arme arme = armes.get(attrs.getValue("type")).clone();
						arme.setCartouchesLeft(Integer.parseInt(attrs.getValue("cartouches")));
						current = new ArmeItem(0, 0, arme);
						break;
					default:
						current = new Item(0, 0, 0, 0);
						break;
				}

				break;

			case "position":
				setPosition(attrs);
				break;
			case "dimensions":
				setDimension(attrs);
				break;
			case "arme":
				addArme(attrs);
				break;
		}
	}

	private void addArme(Attributes attrs) {
		Arme arme = armes.get(attrs.getValue("type")).clone();

		if (arme != null) {
			((Personnage) current).setCurrentArme(arme);

			String cartouches = attrs.getValue("cartouches");
			// Current_data correspond alors au nombre de cartouches dans l'arme
			if (cartouches != null) {
				arme.setCartouchesLeft(Integer.parseInt(cartouches));
			}
		}

	}

	private void setDimension(Attributes attrs) {
		current.getDimension().setSize(Integer.parseInt(attrs.getValue(0)), Integer.parseInt(attrs.getValue(1)));
	}

	private void setPosition(Attributes attrs) {
		Vecteur pos = new Vecteur();

		pos.setX(Integer.parseInt(attrs.getValue(0)));
		pos.setY(Integer.parseInt(attrs.getValue(1)));

		current.getTrajectoire().setPosition(pos);
	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		switch (qName) {
		// definition des éléments
			case "background":
				level.blocControler.setBackground("./resources/images/" + current_data);
				break ;
		
			case "bloc":
				if (id > 0) {
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
				level.botControler.add((Bot) current, behaviour);
				current = null;
				break;
			case "item":
				level.itemControler.add((Item) current);
				current = null;
				break;

			// Definition de leurs attributs
			case "type":
				current = botTypes.get(current_data).first.clone();
				
				try {
					behaviour = botTypes.get(current_data).second.getClass().newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
								
				if (behaviour instanceof BossBehaviour) {
					((BossBehaviour) behaviour).setBlocs(blocs.values());
				}


				if (behaviour == null) {
					throw new SAXException("Try to instanciate a non existant bot behaviour");
				}

				if (behaviour instanceof ToucherBotBehaviour
						&& !((Bot) current).getCurrentArme().getTirClass().equals("eu.labrush.rescue.model.arme.Bouclier")) {
					throw new SAXException("A ToucherBotBehaviour must have a Bouclier");
				}

				break;

			case "life":
				((Personnage) current).setMaxLife(Integer.parseInt(current_data));
				((Personnage) current).setLife(Integer.parseInt(current_data));
				break;

			case "bloc_id":
				if(!(behaviour instanceof ToucherBotBehaviour))
				{
					return ;
				}
					
				int id = Integer.parseInt(current_data);
				Bloc bloc = blocs.get(id);
				if (bloc == null) {
					throw new SAXException("a bloc is poiting on a non existant bloc (bloc id:" + current_data + ")");
				}

				if (usedBlocId.contains(id)) {
					throw new SAXException("Cannot set two bots on the same bloc (bloc " + id + ")");
				}
				else {
					usedBlocId.add(id);
				}

				current.getTrajectoire().setPosition(new Vecteur(bloc.getX(), bloc.getY() + bloc.getHeight()));

				((ToucherBotBehaviour) behaviour).setBloc(bloc);
				break;
		}
	}

	public void characters(char[] data, int start, int end) {
		current_data = new String(data, start, end);
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
