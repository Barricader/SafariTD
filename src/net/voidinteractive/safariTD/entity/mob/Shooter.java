package net.voidinteractive.safariTD.entity.mob;

import java.util.List;

import net.voidinteractive.safariTD.entity.Entity;
import net.voidinteractive.safariTD.entity.projectile.WizardProjectile;
import net.voidinteractive.safariTD.graphics.AnimSprite;
import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.graphics.Sprite;
import net.voidinteractive.safariTD.graphics.SpriteSheet;
import net.voidinteractive.safariTD.util.Vector2i;

public class Shooter extends Mob {
	private AnimSprite up = new AnimSprite(32, 32, 3, SpriteSheet.dummyU);
	private AnimSprite down = new AnimSprite(32, 32, 3, SpriteSheet.dummyD);
	private AnimSprite left = new AnimSprite(32, 32, 3, SpriteSheet.dummyL);
	private AnimSprite right = new AnimSprite(32, 32, 3, SpriteSheet.dummyR);
	private AnimSprite animSprite = down;
	
	private int fireRate = 0;
	private int time = 0;
	private int xAbs = 0, yAbs = 0;
	
	private Entity rand = null;
	
	public Shooter(int x, int y) {
		this.x = x << 4;
		this.y = y << 4;
		
		sprite = Sprite.dummy;
		fireRate = WizardProjectile.FIRE_RATE / 2;
	}
	
	public void update() {
		time++;
		
		if (fireRate > 0)
			fireRate--;
		
		if (time % (random.nextInt(60) + 30) == 0) {
			xAbs = random.nextInt(3) - 1;
			yAbs = random.nextInt(3) - 1;
			
			if (random.nextInt(4) == 0) {
				xAbs = 0;
				yAbs = 0;
			}
		}
		
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
		
		if (xAbs != 0 || yAbs != 0) {
			//move(xAbs, yAbs);
			moving = true;
		}
		else
			moving = false;
		
		//shootRandom();
		shootClosest();
	}
	
	@SuppressWarnings("unused")
	private void shootRandom() {
		List<Entity> entities = level.getEntities(this, 50);

		Player p = level.getClientPlayer();
		Vector2i pVec = new Vector2i((int)p.getX(), (int)p.getY());
		Vector2i sVec = new Vector2i((int)x, (int)y);
		double vecDist = Vector2i.getDist(pVec, sVec);
		
		if (vecDist <= 50)
			entities.add(p);
		
		if (entities.size() > 0) {
			if (time % (60 + random.nextInt(61)) == 0) {
				int index = random.nextInt(entities.size());
				rand = entities.get(index);
			}
		}
		
		if (entities.size() > 0) {
			if (rand != null) {
				double distX = rand.getX() - x;
				double distY = rand.getY() - y;
				double angle = Math.atan2(distY, distX);
				shoot(x, y, angle);
			}
		}
	}
	
	private void shootClosest() {
		List<Entity> entities = level.getEntities(this, 100);
		
		Player p = level.getClientPlayer();
		Vector2i pVec = new Vector2i((int)p.getX(), (int)p.getY());
		Vector2i sVec = new Vector2i((int)x, (int)y);
		double vecDist = Vector2i.getDist(pVec, sVec);
		
		if (vecDist <= 50)
			entities.add(p);
		
		double min = 0;
		Entity minEntity = null;
		
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			double dist = Vector2i.getDist(new Vector2i((int)x, (int)y), new Vector2i((int)e.getX(), (int)e.getY()));
			
			if (i == 0 || dist < min) {
				min = dist;
				minEntity = e;
			}
		}
		
		if (minEntity != null) {
			if (fireRate <= 0) {
				double distX = minEntity.getX() - x;
				double distY = minEntity.getY() - y;
				double angle = Math.atan2(distY, distX);
				shoot(x, y, angle);
				fireRate = WizardProjectile.FIRE_RATE / 2;
			}
		}
	}
	
	public void render(Screen screen) {
		sprite = animSprite.getSprite();
		screen.renderMob((int)x - 16, (int)y - 16, this);
	}
}
