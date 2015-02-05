/**
 * 
 */
package eu.labrush.rescue.core;

import eu.labrush.rescue.core.graphic.GraphicCore;

/**
 * @author adrienbocquet
 *
 */
public class SIRescue {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GraphicCore graphics = GraphicCore.getInstance();
		graphics.start();
	}

}
