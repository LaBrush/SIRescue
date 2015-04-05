package eu.labrush.rescue.view;

import java.awt.Color;
import java.util.Observable;

import eu.labrush.rescue.core.graphic.DrawRequest;
import eu.labrush.rescue.model.Item;

public class ItemView extends AbstractView {

	public ItemView(Observable model) {
		super(model);
		
		Item item = (Item) model ;
		
		this.x = (int) item.getX();
		this.y = (int) item.getY();
		
		this.width = (int) item.getWidth();
		this.height = (int) item.getHeight();
	}

	@Override
	protected void bindModel(Observable model) {
	}

	@Override
	public void draw(DrawRequest req) {
		req.rect(x, y, width, height, Color.green);
	}

}
