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
@SuppressWarnings("rawtypes")
public class AudioControler {

	private HashMap<Class, String> resources = new HashMap<Class, String>();
	private HashMap<Class, Clip> playing = new HashMap<Class, Clip>();
	
	public AudioControler() {

	}

	public void play(Object o) {
		try {
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(resources.get(o.getClass())));
			clip.open(inputStream);
			clip.start();
			
			playing.put(o.getClass(), clip);
			
			inputStream.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}

	}
	
	public void add(Object o, String file){
		resources.put(o.getClass(), file);
	}
}
