package net.voidinteractive.safariTD.entity.particle;

import net.voidinteractive.safariTD.entity.Entity;
import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.graphics.Sprite;

public class Particle extends Entity {
	private Sprite sprite;
	
	private int life;
	private int time = 0;
	
	protected double xx, yy, zz;
	protected double xAbs, yAbs, zAbs;
	
	public Particle(int x, int y, int life) {
		this.x = x;
		this.y = y;
		this.xx = x;
		this.yy = y;
		this.life = life + random.nextInt(20) - 10;
		sprite = Sprite.normPart;
		
		this.xAbs = random.nextGaussian();
		this.yAbs = random.nextGaussian();
		this.zz = random.nextFloat() + 2.0;
		//this.zAbs = random.nextGaussian();
	}
	
	public void update() {
		time++;
		if (time >= 7400)
			time = 0;
		if (time > life)
			remove();
		
		zAbs -= 0.09;
		
		if (zz < 0) {
			zz = 0;
			zAbs *= -0.75;
			xAbs *= 0.5;
			yAbs *= 0.5;
		}
		
		move(xx + xAbs, (yy + yAbs) + (zz + zAbs));
	}
	
	private void move(double x, double y) {
		if (collision(x, y)) {
			xAbs *= -0.5;
			yAbs *= -0.5;
			zAbs *= -0.5;
		}
		
			xx += xAbs;
			yy += yAbs;
			zz += zAbs;
	}
	
	public boolean collision(double x, double y) {
		boolean solid = false;
		
		for (int i = 0; i < 4; i++) {
			double xT = (x - i % 2 * 16) / 16;
			double yT = (y - i / 2 * 16) / 16;
			
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

	public void render(Screen screen) {
		screen.renderSprite((int)xx - 1, (int)yy - (int)zz, sprite, false);
	}
}