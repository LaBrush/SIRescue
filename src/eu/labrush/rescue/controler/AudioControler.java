package eu.labrush.rescue.controler;

import java.io.File;
import java.util.HashMap;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * @author adrienbocquet
 *
 */
public class AudioControler {

	@SuppressWarnings("rawtypes")
	private HashMap<Class, HashMap<String, String>> resources = new HashMap<Class, HashMap<String, String>>();

	public void play(Object o, String use) {
		if (resources.get(o.getClass()) == null || resources.get(o.getClass()).get(use) == null) {
			return;
		}

		play(resources.get(o.getClass()).get(use));
	}

	public Clip play(String src) {
		
		try {
			// on demande les ressources du systeme
			Clip clip = AudioSystem.getClip();
			
			// on ouvre le fichier qui correspond à la classe et au cas d'utiliation
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(src));
			clip.open(inputStream);
			clip.start(); // on commence la lecture
			
			//Si on veut faire taire
			//clip.stop();
			
			// On attend que le clip soit lu et on le referme
			new Thread(new Runnable() {
				public void run() {
					try {
						Thread.sleep(clip.getMicrosecondLength() * 1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					clip.close();
				}
			});

			inputStream.close();

			return clip ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null ;
	}

	@SuppressWarnings("rawtypes")
	public void add(Class o, String use, String file) {
		if (!resources.containsKey(o)) {
			resources.put(o, new HashMap<String, String>());
		}

		resources.get(o).put(use, file);
	}

	@SuppressWarnings("rawtypes")
	public void setResources(HashMap<Class, HashMap<String, String>> resources) {
		this.resources = resources;
	}
	

}
