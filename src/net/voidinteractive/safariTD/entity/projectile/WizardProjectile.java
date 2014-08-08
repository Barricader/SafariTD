package net.voidinteractive.safariTD.entity.projectile;

import net.voidinteractive.safariTD.entity.spawner.ParticleSpawner;
import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.graphics.Sprite;

public class WizardProjectile extends Projectile {
	// Lower = faster
	public static final int FIRE_RATE = 15;
	
	public WizardProjectile(double x, double y, double dir) {
		super(x, y, dir);
		range = 120;
		speed = 3;
		damage = 20;
		
		sprite = Sprite.wizProj;
		
		nX = speed * Math.cos(angle);
		nY = speed * Math.sin(angle);
	}
	
	public void update() {
		if (level.tileCollision((int)(x + nX), (int)(y + nY), 8, 4, 4)) {
			level.add(new ParticleSpawner((int)x, (int)y, 15, 40, level));
			remove();
		}
		move();
	}
	
	
	
	protected void move() {
		x += nX * 1.25;
		y += nY * 1.25;
		
		if (dist() > range)
			remove();
	}
	
	
	protected double dist() {
		double dist = 0;
		dist = Math.sqrt(Math.abs((xOrigin - x) * (xOrigin - x) + (yOrigin - y) * (yOrigin - y)));
		return dist;
	}
	
	public void render(Screen screen) {
		screen.renderProjectile((int)x - 7, (int)y - 2, this);
	}
}
