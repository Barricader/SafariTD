package net.voidinteractive.safariTD.entity;

import java.util.Random;

import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.graphics.Sprite;
import net.voidinteractive.safariTD.level.Level;

public class Entity {
	protected double x, y;
	private boolean removed = false;
	protected Level level;
	protected final Random random = new Random();
	protected Sprite sprite;
	
	public Entity() {
		
	}
	
	public Entity(int x, int y, Sprite sprite) {
		this.x = x;
		this.y = y;
		this.sprite = sprite;
	}
	
	public void update() {
		
	}
	
	public void render(Screen screen) {
		if (sprite != null)
			screen.renderSprite((int)x, (int)y, sprite, true);
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void remove() {
		// Remove from level
		removed = true;
	}
	
	public boolean isRemoved() {
		return removed;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public void init(Level level) {
		this.level = level;
	}
}