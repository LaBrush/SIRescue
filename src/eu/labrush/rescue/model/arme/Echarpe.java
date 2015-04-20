package eu.labrush.rescue.model.arme;

import java.awt.Dimension;
import java.util.Observable;

import eu.labrush.rescue.core.physics.ClassicPhysicBehaviour;
import eu.labrush.rescue.model.AbstractObject;
import eu.labrush.rescue.model.Personnage;
import eu.labrush.rescue.model.Trajectoire;
import eu.labrush.rescue.model.Vecteur;

/**
 * @author adrienbocquet
 *
 */
public class Echarpe extends Tir {

	// ces quatres vecteurs représentent les points en haut, gauche et droite, et en bas gauche et
	// droite du rectangle
	public Vecteur hg, hd, bg, bd;

	public Echarpe(Vecteur position, int angle, int damage, int recul, Personnage owner) {
		super(position, angle, damage, recul, owner);
		owner.getTrajectoire().addObserver(this);

		this.dim = new Dimension(30, 10);
		this.update(owner.getTrajectoire(), null);

		this.behaviour = new ClassicPhysicBehaviour(getTrajectoire(), dim);
		this.behaviour.setGravity(0);
		
		//On utilise l'écharpe comme fouet, le tir est donc limité dans le temps
		Thread t = new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				use();
			}
		});
		t.start();
	}

	public void update(Observable obs, Object obj) {
		if (obs == owner.getTrajectoire()) {
			Vecteur v = (Vecteur) ((Trajectoire) obs).getPosition();
			double x, y;

			x = v.getX() + owner.getWidth() / 2;
			y = v.getY() + owner.getHeight() / 2 - this.getHeight() / 2;

			x += owner.getWidth() / 2 * Math.cos(Math.toRadians(angle/2));
			y += getHeight() * Math.sin(Math.toRadians(angle/2));
			
			this.getTrajectoire().setPosition(new Vecteur(x, y));

			double angle = Math.toRadians(this.angle);
			
			bg = this.getTrajectoire().getPosition();
			bd = new Vecteur(this.getWidth() * Math.cos(angle), this.getWidth() * Math.sin(angle));
			hg = new Vecteur(this.getHeight() * Math.cos(angle + Math.PI / 2), this.getHeight() * Math.sin(angle + Math.PI / 2));
			hd = new Vecteur().add(bd).add(hg);

			bd.add(bg);
			hg.add(bg);
			hd.add(bg);

			setChanged();
			notifyObservers();
		}
	}

	@Override
	public boolean cross(AbstractObject o) {
		return pointIn(o, bg) || pointIn(o, bd) || pointIn(o, hg) || pointIn(o, hd);
	}

	private boolean pointIn(AbstractObject o, Vecteur p) {
		return o.getX() <= p.getX() && o.getX() + o.getWidth() >= p.getX() && o.getY() <= p.getY() && o.getY() + o.getHeight() >= p.getY();
	}

	@Override
	public void use() {
		setChanged();
		notifyObservers("done");
	}

}
