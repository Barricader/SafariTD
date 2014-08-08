package net.voidinteractive.safariTD.entity.projectile;

import java.util.Random;

import net.voidinteractive.safariTD.entity.Entity;
import net.voidinteractive.safariTD.graphics.Sprite;


//TODO: Restructure it to allow it to get who was damaged and who sent the projectile
public abstract class Projectile extends Entity {
	protected final double xOrigin, yOrigin;
	protected double angle;
	protected double x, y;
	protected double nX, nY;
	protected double distance;
	protected double speed, range, damage;
	Sprite sprite;
	
	protected final Random random = new Random();
	
	public Projectile(double x, double y, double dir) {
		xOrigin = x;
		yOrigin = y;
		this.x = x;
		this.y = y;
		
		angle = dir;
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public int getSpriteSize() {
		return sprite.SIZE;
	}
	
	protected void move() {
		
	}
}