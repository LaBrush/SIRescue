/**
 * 
 */
package eu.labrush.rescue.core.graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
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

	public DrawRequest(Graphics2D g, int width, int height) {
		this.g = g;
		this.width = width;
		this.height = height;

		this.g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public void rect(int x, int y, int width, int height) {
		this.g.drawRect(x, this.height - y - height, width, height);
	}

	public void rect(int x, int y, int width, int height, Color c) {
		this.rect(x, y, width, height, 1, c);
	}

	public void rect(int x, int y, int width, int height, int border, Color c) {
		this.g.setColor(c);
		this.g.setStroke(new BasicStroke(border));
		rect(x, y, width, height);
		this.g.setColor(Color.BLACK);
	}

	public void rect(int x, int y, int width, int height, int angle) {

		// on ajoute un décalage sur x et y en fonction de l'angle de rotation
		// afin d'obtenir une rotation par raport au coins inférieur gauche
		y = (int) (this.height - y - height * Math.cos(Math.toRadians(angle)));
		x = (int) (x - height * Math.sin(Math.toRadians(angle)));

		this.g.translate(x, y);
		this.g.rotate(Math.toRadians(-angle));
		this.g.drawRect(0, 0, width, height);
		this.g.rotate(Math.toRadians(angle));
		this.g.translate(-x, -y);

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

	public void fillRect(int x, int y, int width, int height, Color c) {
		this.g.setColor(c);
		this.g.fillRect(x, this.getHeight() - y, width, height);
		this.g.setColor(Color.BLACK);
	}

	public void drawString(String s, int x, int y) {
		this.g.drawString(s, x, this.height - y);
	}

	public void drawString(String s, int x, int y, Color c) {
		drawString(s, x, y, c, 10, false);
	}

	public void drawString(String s, int x, int y, Color c, int fontSize, boolean centered) {
		
		this.g.setFont(new Font("Arial", Font.PLAIN, fontSize));
		
		if (centered) {
			FontMetrics fm = this.g.getFontMetrics();
			Rectangle2D r = fm.getStringBounds(s, this.g);
			x = (this.getWidth() - (int) r.getWidth()) / 2;
		}

		this.g.setColor(c);
		this.g.drawString(s, x, this.height - y);
		this.g.setColor(Color.BLACK);
	}

	public void line(double x1, double y1, double x2, double y2) {
		this.g.drawLine((int) x1, this.height - (int) y1, (int) x2, this.height - (int) y2);
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
