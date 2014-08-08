package net.voidinteractive.safariTD.entity.mob;

import net.voidinteractive.safariTD.entity.Entity;
import net.voidinteractive.safariTD.entity.projectile.Projectile;
import net.voidinteractive.safariTD.entity.projectile.WizardProjectile;
import net.voidinteractive.safariTD.graphics.Screen;

public abstract class Mob extends Entity {
	protected boolean moving = false;
	protected boolean walking = false;
	
	protected enum Direction {
		UP, DOWN, LEFT, RIGHT;
	}
	
	protected Direction dir;
	
	public void move(double xAbs, double yAbs) {
		if (xAbs != 0 && yAbs != 0) {
			move(xAbs, 0);
			move(0, yAbs);
			return;
		}
		
		
		if (xAbs > 0)
			dir = Direction.RIGHT;
		if (xAbs < 0)
			dir = Direction.LEFT;
		if (yAbs > 0)
			dir = Direction.DOWN;
		if (yAbs < 0)
			dir = Direction.UP;
		
		while (xAbs != 0) {
			if (Math.abs(xAbs) > 1) {
				if (!collision(abs(xAbs), yAbs))
					this.x += abs(xAbs);
				xAbs -= abs(xAbs);
			}
			else {
				if (!collision(abs(xAbs), yAbs))
					this.x += xAbs;
				xAbs = 0;
			}
		}
		
		while (yAbs != 0) {
			if (Math.abs(yAbs) > 1) {
				if (!collision(xAbs, abs(yAbs)))
					this.y += abs(yAbs);
				yAbs -= abs(yAbs);
			}
			else {
				if (!collision(xAbs, abs(yAbs)))
					this.y += yAbs;
				yAbs = 0;
			}
		}
	}
	
	private int abs(double value) {
		if (value < 0)
			return -1;
		return 1;
	}
	
	public abstract void update();
	
	public abstract void render(Screen screen);
	
	protected void shoot(double x, double y, double dir) {
		Projectile p = new WizardProjectile(x, y, dir);
		level.add(p);
	}
	
	private boolean collision(double xAbs, double yAbs) {
		boolean solid = false;
		double xT, yT;
		
		for (int i = 0; i < 4; i++) {
			xT = ((x + xAbs) - i % 2 * 15) / 16;
			yT = ((y + yAbs) - i / 2 * 15) / 16;
			
			int iX = (int)Math.ceil(xT);
			int iY = (int)Math.ceil(yT);
			
			if (i % 2 == 0)
				iX = (int)Math.floor(xT);
			if (i / 2 == 0)
				iY = (int)Math.floor(yT);

			if (level.getTile(iX, iY).solid())
				solid = true;
		}
		
		return solid;
	}
}