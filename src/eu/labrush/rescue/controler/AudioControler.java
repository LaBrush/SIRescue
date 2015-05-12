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
	
	@SuppressWarnings("resource")
	public void play(Object o, String use) {
		try {
			
			if(resources.get(o.getClass()) == null || resources.get(o.getClass()).get(use) == null){
				return ;
			}
			
			Clip clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(resources.get(o.getClass()).get(use)));
			clip.open(inputStream);
			clip.start();
						
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@SuppressWarnings("rawtypes")
	public void add(Class o, String use, String file){
		if(!resources.containsKey(o)){
			resources.put(o, new HashMap<String, String>());
		}
		
		resources.get(o).put(use, file);
	}
}
