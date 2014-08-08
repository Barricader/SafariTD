package net.voidinteractive.safariTD.graphics;

public class Sprite {
	public final int SIZE;
	private int x, y;
	private int width, height;
	public int[] pixels;
	protected SpriteSheet sheet;
	
	// Default Sprites
	//public static Sprite grass = new Sprite(0, 2, 16, SpriteSheet.tiles);
	//public static Sprite flowers = new Sprite(1, 2, 16, SpriteSheet.tiles);
	//public static Sprite rocks = new Sprite(2, 0, 16, SpriteSheet.tiles);
	public static Sprite voidSprite = new Sprite(16, 0xffffff);
	
	// Spawn Level Sprites
	public static Sprite spawnGrass = new Sprite(0, 0, 16, SpriteSheet.spawnLevel);
	public static Sprite spawnDirt = new Sprite(1, 0, 16, SpriteSheet.spawnLevel);
	public static Sprite spawnBrick = new Sprite(0, 1, 16, SpriteSheet.spawnLevel);
	public static Sprite spawnWater = new Sprite(1, 1, 16, SpriteSheet.spawnLevel);
	
	// Player Sprites
	public static Sprite playerF = new Sprite(0, 5, 32, SpriteSheet.tiles);
	public static Sprite playerB = new Sprite(2, 5, 32, SpriteSheet.tiles);
	public static Sprite playerS = new Sprite(1, 5, 32, SpriteSheet.tiles);
	
	public static Sprite playerF1 = new Sprite(0, 6, 32, SpriteSheet.tiles);
	public static Sprite playerF2 = new Sprite(0, 7, 32, SpriteSheet.tiles);
	
	public static Sprite playerB1 = new Sprite(2, 6, 32, SpriteSheet.tiles);
	public static Sprite playerB2 = new Sprite(2, 7, 32, SpriteSheet.tiles);
	
	public static Sprite playerS1 = new Sprite(1, 6, 32, SpriteSheet.tiles);
	public static Sprite playerS2 = new Sprite(1, 7, 32, SpriteSheet.tiles);
	
	// Projectile Sprites
	public static Sprite wizProj = new Sprite(0, 0, 16, SpriteSheet.wizProj);
	
	// Particles
	public static Sprite normPart = new Sprite(2, 0xD0D0D0);
	
	public static Sprite dummy = new Sprite(0, 0, 32, SpriteSheet.dummyD);
	
	protected Sprite(int w, int h, SpriteSheet sheet) {
		SIZE = (width == height) ? width : -1;
		this.width = w;
		this.height = h;
		this.sheet = sheet;
	}
	
	public Sprite(int x, int y, int size, SpriteSheet sheet) {
		this.x = x * size;
		this.y = y * size;
		SIZE = size;
		width = size;
		height = size;
		this.sheet = sheet;
		pixels = new int[SIZE * SIZE];
		
		load();
	}
	
	public Sprite(int width, int height, int color) {
		SIZE = -1;
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
		setColor(width, height, color);
	}
	
	public Sprite(int size, int color) {
		SIZE = size;
		width = size;
		height = size;
		pixels = new int[SIZE * SIZE];
		setColor(color);
	}
	
	public Sprite(int[] pixels, int w, int h) {
		SIZE = (w == h) ? w : -1;
		this.width = w;
		this.height = h;
		this.pixels = new int[pixels.length];
		for (int i = 0; i < pixels.length; i++)
			this.pixels[i] = pixels[i];
	}
	
	public static Sprite[] split(SpriteSheet sheet) {
		int amount = (sheet.getWidth() * sheet.getHeight()) /(sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT);
		Sprite[] sprites = new Sprite[amount];
		int[] pixels = new int[sheet.SPRITE_WIDTH * sheet.SPRITE_HEIGHT];
		int i = 0;
		
		for (int yPos = 0; yPos < sheet.getHeight() / sheet.SPRITE_HEIGHT; yPos++) {
			for (int xPos = 0; xPos < sheet.getWidth() / sheet.SPRITE_WIDTH; xPos++) {
				for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
					for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
						int xOffset = x + xPos * sheet.SPRITE_WIDTH;
						int yOffset = y + yPos * sheet.SPRITE_HEIGHT;
						pixels[x + y * sheet.SPRITE_WIDTH] = sheet.getPixels()[xOffset + yOffset * sheet.getWidth()];
					}
				}
				
				sprites[i++] = new Sprite(pixels, sheet.SPRITE_WIDTH, sheet.SPRITE_HEIGHT);
			}
		}
		
		return sprites;
	}
	
	private void setColor(int color) {
		for (int i = 0; i < SIZE * SIZE; i++) {
			pixels[i] = color;
		}
	}
	
	private void setColor(int w, int h, int color) {
		for (int i = 0; i < w * h; i++) {
			pixels[i] = color;
		}
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	private void load() {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				pixels[x + y * width] = sheet.getPixels()[(this.x + x) + (this.y + y) * sheet.SPRITE_WIDTH];
			}
		}
	}
}