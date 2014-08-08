package net.voidinteractive.safariTD.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
	private String path;
	public final int SIZE;
	public final int SPRITE_WIDTH, SPRITE_HEIGHT;
	private int width, height;
	private int[] pixels;
	
	private Sprite[] sprites;
	
	public static SpriteSheet tiles = new SpriteSheet("/tex/sheets/spritesheet.png", 256);
	public static SpriteSheet spawnLevel = new SpriteSheet("/tex/sheets/spawnLevel.png", 48);
	public static SpriteSheet wizProj = new SpriteSheet("/tex/sheets/projectiles/wizard.png", 48);
	
	public static SpriteSheet player = new SpriteSheet("/tex/sheets/player.png", 128, 96);
	public static SpriteSheet playerU = new SpriteSheet(player, 1, 0, 1, 3, 32);
	public static SpriteSheet playerD = new SpriteSheet(player, 0, 0, 1, 3, 32);
	public static SpriteSheet playerL = new SpriteSheet(player, 2, 0, 1, 3, 32);
	public static SpriteSheet playerR = new SpriteSheet(player, 3, 0, 1, 3, 32);
	
	public static SpriteSheet dummy = new SpriteSheet("/tex/sheets/king.png", 128, 96);
	public static SpriteSheet dummyU = new SpriteSheet(dummy, 1, 0, 1, 3, 32);
	public static SpriteSheet dummyD = new SpriteSheet(dummy, 0, 0, 1, 3, 32);
	public static SpriteSheet dummyL = new SpriteSheet(dummy, 2, 0, 1, 3, 32);
	public static SpriteSheet dummyR = new SpriteSheet(dummy, 3, 0, 1, 3, 32);
	
	public SpriteSheet(SpriteSheet sheet, int x, int y, int w, int h, int spriteSize) {
		int xx = x * spriteSize;
		int yy = y * spriteSize;
		int width = w * spriteSize;
		int height = h * spriteSize;
		pixels = new int[width * height];
		
		if (w == h)
			SIZE = w;
		else
			SIZE = -1;
		SPRITE_WIDTH = width;
		SPRITE_HEIGHT = height;
		
		for (int y0 = 0; y0 < height; y0++) {
			int yPos = yy + y0;
			for (int x0 = 0; x0 < width; x0++) {
				int xPos = xx + x0;
				pixels[x0 + y0 * width] = sheet.pixels[xPos + yPos * sheet.SPRITE_WIDTH];
			}
		}
		
		int frame = 0;
		sprites = new Sprite[w * h];
		for (int yAbs = 0; yAbs < h; yAbs++) {
			for (int xAbs = 0; xAbs < w; xAbs++) {
				int[] spritePixels = new int[spriteSize * spriteSize];
				for (int y0 = 0; y0 < spriteSize; y0++) {
					for (int x0 = 0; x0 < spriteSize; x0++) {
						spritePixels[x0 + y0 * spriteSize] = pixels[(x0 + xAbs * spriteSize) + (y0 + yAbs * spriteSize) * SPRITE_WIDTH];
					}
				}
				Sprite sprite = new Sprite(spritePixels, spriteSize, spriteSize);
				sprites[frame++] = sprite;
			}
		}
	}
	
	public SpriteSheet(String path, int size) {
		this.path = path;
		this.SIZE = size;
		SPRITE_WIDTH = size;
		SPRITE_HEIGHT = size;
		pixels = new int[SIZE * SIZE];
		load();
	}
	
	public SpriteSheet(String path, int w, int h) {
		this.path = path;
		this.SIZE = -1;
		SPRITE_WIDTH = w;
		SPRITE_HEIGHT = h;
		pixels = new int[w * h];
		load();
	}
	
	public int[] getPixels() {
		return pixels;
	}
	
	public Sprite[] getSprites() {
		return sprites;
	}
	
	private void load() {
		try {
			System.out.print("Loading: " + path);
			BufferedImage image = ImageIO.read(SpriteSheet.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			pixels = new int[width * height];
			image.getRGB(0, 0, width, height, pixels, 0, width);
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		catch (Exception e) {
			//e.printStackTrace();
			System.err.println("...failed!");
		}
		finally {
			System.out.println("...succeeded!");
		}
		
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
}