package net.voidinteractive.safariTD.entity.mob;

import java.util.List;

import net.voidinteractive.safariTD.graphics.AnimSprite;
import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.graphics.SpriteSheet;
import net.voidinteractive.safariTD.level.Node;
import net.voidinteractive.safariTD.util.Vector2i;

public class Star extends Mob {
	private AnimSprite up = new AnimSprite(32, 32, 3, SpriteSheet.dummyU);
	private AnimSprite down = new AnimSprite(32, 32, 3, SpriteSheet.dummyD);
	private AnimSprite left = new AnimSprite(32, 32, 3, SpriteSheet.dummyL);
	private AnimSprite right = new AnimSprite(32, 32, 3, SpriteSheet.dummyR);
	
	private AnimSprite animSprite = down;
	
	private int time = 0;
	private double xAbs = 0;
	private double yAbs = 0;
	private double speed = 1;
	
	private List<Node> path = null;
	
	public Star(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		
		sprite = animSprite.getSprite();
	}
	
	private void move() {
		xAbs = 0;
		yAbs = 0;
		
		int playerX = (int) level.getPlayerAt(0).getX();
		int playerY = (int) level.getPlayerAt(0).getY();
		Vector2i start = new Vector2i((int)getX() >> 4, (int)getY() >> 4);
		Vector2i dest = new Vector2i(playerX >> 4, playerY >> 4);
		if (time % 4 == 0)
			path = level.findPath(start, dest);
		
		if (path != null) {
			if (path.size() > 0) {
				Vector2i vec = path.get(path.size() - 1).tile;
				if (x < vec.getX() << 4)
					xAbs += speed;
				if (x > vec.getX() << 4)
					xAbs -= speed;
				if (y < vec.getY() << 4)
					yAbs += speed;
				if (y > vec.getY() << 4)
					yAbs -= speed;
			}
		}
		
		if (xAbs != 0 || yAbs != 0) {
			move(xAbs, yAbs);
			moving = true;
		}
		else
			moving = false;
	}
	
	public void update() {
		time++;
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