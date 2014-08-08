package net.voidinteractive.safariTD.entity.mob;

import net.voidinteractive.safariTD.Game;
import net.voidinteractive.safariTD.entity.projectile.Projectile;
import net.voidinteractive.safariTD.entity.projectile.WizardProjectile;
import net.voidinteractive.safariTD.graphics.AnimSprite;
import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.graphics.Sprite;
import net.voidinteractive.safariTD.graphics.SpriteSheet;
import net.voidinteractive.safariTD.input.Keyboard;
import net.voidinteractive.safariTD.input.Mouse;

public class Player extends Mob {
	private Keyboard input;
	private Sprite sprite;
	private int anim = 0;
	
	private AnimSprite up = new AnimSprite(32, 32, 3, SpriteSheet.playerU);
	private AnimSprite down = new AnimSprite(32, 32, 3, SpriteSheet.playerD);
	private AnimSprite left = new AnimSprite(32, 32, 3, SpriteSheet.playerL);
	private AnimSprite right = new AnimSprite(32, 32, 3, SpriteSheet.playerR);
	
	private AnimSprite animSprite = down;
	
	private int fireRate = 0;
	
	public Player(Keyboard input) {
		this.input = input;
	}
	
	public Player(int x, int y, Keyboard input) {
		this.x = x;
		this.y = y;
		this.input = input;
		
		fireRate = WizardProjectile.FIRE_RATE;
	}
	
	public void update() {
		if (moving)
			animSprite.update();
		else
			animSprite.setFrame(0);
		if (fireRate > 0)
			fireRate--;
		double xAbs = 0, yAbs = 0;
		double speed = 1.5;
		
		if (anim <= 8000)
			anim++;
		else
			anim = 0;
		
		if (input.left) {
			xAbs -= speed;
			animSprite = left;
		}
		else if (input.right) {
			xAbs += speed;
			animSprite = right;
		}
		if (input.up) {
			yAbs -= speed;
			animSprite = up;
		}
		else if (input.down) {
			yAbs += speed;
			animSprite = down;
		}
		
		if (xAbs != 0 || yAbs != 0) {
			move(xAbs, yAbs);
			moving = true;
		}
		else
			moving = false;
		
		clear();
		updateShooting();
	}
	
	private void clear() {
		for (int i = 0; i < level.getProjectiles().size(); i++) {
			Projectile p = level.getProjectiles().get(i);
			if (p.isRemoved()) {
				level.getProjectiles().remove(i);
			}
		}
	}

	private void updateShooting() {
		if (Mouse.getB() == 1 && fireRate <= 0) {
			double dX = Mouse.getX() - Game.getWindowWidth() / 2;
			double dY = Mouse.getY() - Game.getWindowHeight() / 2;
			double dir = Math.atan2(dY, dX);
			shoot(x, y, dir);
			fireRate = WizardProjectile.FIRE_RATE;
			//Sound.shoot.play();
		}
	}

	public void render(Screen screen) {
		int flip = 0;
		sprite = animSprite.getSprite();
		screen.renderMob((int)(x - 16),(int) (y - 16), sprite, flip);
	}
}