package net.voidinteractive.safariTD.graphics;

import java.util.Random;

import net.voidinteractive.safariTD.entity.mob.Chaser;
import net.voidinteractive.safariTD.entity.mob.Mob;
import net.voidinteractive.safariTD.entity.mob.Shooter;
import net.voidinteractive.safariTD.entity.mob.Star;
import net.voidinteractive.safariTD.entity.projectile.Projectile;
import net.voidinteractive.safariTD.level.tile.Tile;

public class Screen {
	public final int MAP_SIZE = 64;
	public final int MAP_SIZE_MASK = MAP_SIZE - 1;
	private final int ALPHA = 0xFFFF00FF;
	
	public int width, height;
	public int xOffset, yOffset;
	
	public int[] pixels;
	public int[] tiles = new int[MAP_SIZE * MAP_SIZE];
	
	private Random random = new Random();
	
	public Screen(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		
		for (int i = 0; i < tiles.length; i++) {
			tiles[i] = random.nextInt(0xffffff);
		}
	}
	
	public void clear() {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = 0;
		}
	}
	
	public void renderSheet(int xPos, int yPos, SpriteSheet sheet, boolean fixed) {
		if (!fixed) {
			xPos -= xOffset;
			yPos -= yOffset;
		}
		
		for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
			int yAbs = y + yPos;
			for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
				int xAbs = x + xPos;
				if (xAbs < 0 || xAbs > width || yAbs < 0 || yAbs > height - 2)
					continue;
				pixels[xAbs + yAbs * width] = sheet.getPixels()[x + y * sheet.SPRITE_WIDTH];
			}
		}
	}
	
	public void renderSprite(int xPos, int yPos, Sprite sprite, boolean fixed) {
		if (!fixed) {
			xPos -= xOffset;
			yPos -= yOffset;
		}
		
		for (int y = 0; y < sprite.getHeight(); y++) {
			int yAbs = y + yPos;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xAbs = x + xPos;
				if (xAbs < 0 || xAbs > width || yAbs < 0 || yAbs > height - 2)
					continue;
				int color = sprite.pixels[x + y * sprite.getWidth()];
				if (color != ALPHA)
					pixels[xAbs + yAbs * width] = sprite.pixels[x + y * sprite.getWidth()];
			}
		}
	}
	
	public void renderText(int xPos, int yPos, int fontColor, Sprite sprite, boolean fixed) {
		if (!fixed) {
			xPos -= xOffset;
			yPos -= yOffset;
		}
		
		for (int y = 0; y < sprite.getHeight(); y++) {
			int yAbs = y + yPos;
			for (int x = 0; x < sprite.getWidth(); x++) {
				int xAbs = x + xPos;
				if (xAbs < 0 || xAbs > width || yAbs < 0 || yAbs > height - 2)
					continue;
				int color = sprite.pixels[x + y * sprite.getWidth()];
				if (color != ALPHA)
					pixels[xAbs + yAbs * width] = fontColor;
			}
		}
	}
	
	public void render(int xOffset, int yOffset) {
		for (int y = 0; y < height; y++) {
			int yPos = y + yOffset;
			if (yPos < 0 || yPos >= height)
				continue;
			for (int x = 0; x < width; x++) {
				int xPos = x + xOffset;
				if (xPos < 0 || xPos >= width)
					continue;
				//pixels[xPos + yPos * width] = Sprite.grass.pixels[(x & 15) + (y & 15) * Sprite.grass.SIZE];
			}
		}
	}
	
	public void renderTile(int xPos, int yPos, Tile tile) {
		xPos -= xOffset;
		yPos -= yOffset;
		for (int y = 0; y < tile.sprite.SIZE; y++) {
			int yAbs = y + yPos;
			for (int x = 0; x < tile.sprite.SIZE; x++) {
				int xAbs = x + xPos;
				if (xAbs < -tile.sprite.SIZE || xAbs >= width || yAbs < 0 || yAbs >= height)
					break;
				if (xAbs < 0)
					xAbs = 0;
				pixels[xAbs + yAbs * width] = tile.sprite.pixels[x + y * tile.sprite.SIZE];
			}
		}
	}
	
	public void renderMob(int xPos, int yPos, Mob mob) {
		xPos -= xOffset;
		yPos -= yOffset;
		for (int y = 0; y < mob.getSprite().SIZE; y++) {
			int yAbs = y + yPos;
			int yS = y;
			for (int x = 0; x < mob.getSprite().SIZE; x++) {
				int xAbs = x + xPos;
				int xS = x;
				if (xAbs < -mob.getSprite().SIZE || xAbs >= width || yAbs < 0 || yAbs >= height)
					break;
				if (xAbs < 0)
					xAbs = 0;
				int color = mob.getSprite().pixels[xS + yS * mob.getSprite().SIZE];
				if (mob instanceof Chaser && color == 0xFF472BBF)
					color = 0x802B49;
				if (mob instanceof Star && color == 0xFF472BBF)
					color = 0x2B6631;
				if (mob instanceof Shooter && color == 0xFF472BBF)
					color = 0xB98730;
				if (color != ALPHA)
					pixels[xAbs + yAbs * width] = color;
			}
		}
	}
	
	public void renderMob(int xPos, int yPos, Sprite sprite, int flip) {
		xPos -= xOffset;
		yPos -= yOffset;
		for (int y = 0; y < sprite.SIZE; y++) {
			int yAbs = y + yPos;
			int yS = y;
			if (flip == 2 || flip == 3) {
				yS = 31 - y;
			}
			for (int x = 0; x < sprite.SIZE; x++) {
				int xAbs = x + xPos;
				int xS = x;
				if (flip == 1)
					xS = 31 - x;
				if (xAbs < -sprite.SIZE || xAbs >= width || yAbs < 0 || yAbs >= height)
					break;
				if (xAbs < 0)
					xAbs = 0;
				int color = sprite.pixels[xS + yS * sprite.SIZE];
				if (color != ALPHA)
					pixels[xAbs + yAbs * width] = color;
			}
		}
	}
	
	public void renderProjectile(int xPos, int yPos, Projectile p) {
		xPos -= xOffset;
		yPos -= yOffset;
		for (int y = 0; y < p.getSpriteSize(); y++) {
			int yAbs = y + yPos;
			for (int x = 0; x < p.getSpriteSize(); x++) {
				int xAbs = x + xPos;
				if (xAbs < -p.getSpriteSize() || xAbs >= width || yAbs < 0 || yAbs >= height)
					break;
				if (xAbs < 0)
					xAbs = 0;
				int color = p.getSprite().pixels[x + y *  p.getSprite().SIZE];
				if (color != ALPHA)
					pixels[xAbs + yAbs * width] = color;
			}
		}
	}
	
	public void drawRect(int xPos, int yPos, int w, int h, int color, boolean fixed) {
		if (!fixed) {
			xPos -= xOffset;
			yPos -= yOffset;
		}
		
		for (int x = xPos; x < xPos + w; x++) {
			if (x < 0 || x >= this.width || yPos >= this.height)
				continue;
			if (yPos > 0)
				pixels[x + yPos * this.width] = color;
			if (yPos + h >= this.height)
				continue;
			if (yPos + h > 0)
				pixels[x + (yPos + h) * this.width] = color;
		}
		
		for (int y = yPos; y <= yPos + h; y++) {
			if (xPos >= this.width || y < 0 || y >= this.height)
				continue;
			if (xPos > 0)
				pixels[xPos + y * this.width] = color;
			if (xPos + w >= this.width)
				continue;
			if (xPos + w > 0)
				pixels[(xPos + w) + y * this.width] = color;
		}
	}
	
	public void setOffset(int xOffset, int yOffset) {
		this.xOffset = xOffset;
		this.yOffset = yOffset;
	}
}