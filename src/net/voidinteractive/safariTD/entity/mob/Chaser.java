package net.voidinteractive.safariTD.entity.mob;

import java.util.List;

import net.voidinteractive.safariTD.graphics.AnimSprite;
import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.graphics.SpriteSheet;

public class Chaser extends Mob {
	private AnimSprite up = new AnimSprite(32, 32, 3, SpriteSheet.dummyU);
	private AnimSprite down = new AnimSprite(32, 32, 3, SpriteSheet.dummyD);
	private AnimSprite left = new AnimSprite(32, 32, 3, SpriteSheet.dummyL);
	private AnimSprite right = new AnimSprite(32, 32, 3, SpriteSheet.dummyR);
	
	private AnimSprite animSprite = down;
	
	//private int time = 0;
	private double xAbs = 0;
	private double yAbs = 0;
	private double speed = 0.8;
	
	public Chaser(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		
		sprite = animSprite.getSprite();
	}
	
	private void move() {
		xAbs = 0;
		yAbs = 0;
		
		List<Player> players = level.getPlayers(this, 50);
		if (players.size() > 0) {
			Player player = players.get(0);
			
			if (x < player.getX())
				xAbs += speed;
			else if (x > player.getX())
				xAbs -= speed;
			if (y < player.getY())
				yAbs += speed;
			else if (y > player.getY())
				yAbs -= speed;
		}
		if (xAbs != 0 || yAbs != 0) {
			move(xAbs, yAbs);
			moving = true;
		}
		else
			moving = false;
	}
	
	public void update() {
		move();
				
		if (moving)
			animSprite.update();
		else
			animSprite.setFrame(0);
		
		if (xAbs < 0) {
			dir = Direction.LEFT;
			animSprite = left;
		}
		else if (xAbs > 0) {
			dir = Direction.RIGHT;
			animSprite = right;
		}
		if (yAbs < 0) {
			dir = Direction.UP;
			animSprite = up;
		}
		else if (yAbs > 0) {
			dir = Direction.DOWN;
			animSprite = down;
		}
	}

	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x - 16), (int)(y - 16), this);
	}

}