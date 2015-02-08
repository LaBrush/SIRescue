/**
 * 
 */
package eu.labrush.rescue.core.graphic;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author adrienbocquet
 *
 *         Les méthodes de dessin de DrawRequest sont basées en partie sur les fonctions de
 *         référence du logiciel Processing https://processing.org/reference/
 * 
 *         S'il vous manque une fonction n'hésitez pas à l'imlémenter, en suivant la nomenclature
 *         processing si possible
 * 
 *         Javadoc Graphic2D http://docs.oracle.com/javase/7/docs/api/java/awt/Graphics2D.html
 * 
 *         Chaque fonction se charge de placer le repère en bas à gauche au lieu de haut à gauche
 *
 */
public class DrawRequest {

	private Graphics2D g;
	private int height = 0;
	private int width = 0;

	/**
	 * @param g
	 * @param width
	 * @param height
	 */
	public DrawRequest(Graphics2D g, int width, int height) {
		this.g = g;
		this.width = width;
		this.height = height;
	}

	public void rect(int x, int y, int width, int height) {
		this.g.drawRect(x, this.height - y - height, width, height);
	}

	public void rect(int x, int y, int width, int height, Color c) {
		this.g.setColor(c);
		rect(x, y, width, height);
		this.g.setColor(Color.BLACK);
	}

	public void image(BufferedImage img, int x, int y, int angle) {

		// on ajoute un décalage sur x et y en fonction de l'angle de rotation
		// afin d'obtenir une rotation par raport au coins inférieur gauche
		y = (int) (this.height - y - img.getHeight() * Math.cos(Math.toRadians(angle)));
		x = (int) (x - img.getHeight() * Math.sin(Math.toRadians(angle)));

		AffineTransform trans = new AffineTransform();
		trans.translate(x, y);
		trans.rotate(Math.toRadians(-angle));
		this.g.drawImage(img, trans, null);
	}

	public void image(BufferedImage img, int x, int y) {
		image(img, x, y, 0);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setWidth(int width) {
		this.width = width;
	}

}
