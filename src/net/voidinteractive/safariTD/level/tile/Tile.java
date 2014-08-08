package net.voidinteractive.safariTD.level.tile;

import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.graphics.Sprite;
import net.voidinteractive.safariTD.level.tile.spawnLevel.*;

public class Tile {
	public Sprite sprite;
	
	public static Tile voidTile = new VoidTile(Sprite.voidSprite);
	
	public static Tile spawnGrass = new SpawnGrassTile(Sprite.spawnGrass);
	public static Tile spawnDirt = new SpawnDirtTile(Sprite.spawnDirt);
	public static Tile spawnBrick = new SpawnBrickTile(Sprite.spawnBrick);
	public static Tile spawnWater = new SpawnWaterTile(Sprite.spawnWater);
	
	public final static int colorSpawnGrass = 0xFF00FF00;
	public final static int colorSpawnDirt = 0xFF804000;
	public final static int colorSpawnBrick = 0xFF808080;
	public final static int colorSpawnWater = 0xFF0000FF;
	
	public Tile(Sprite sprite) {
		this.sprite = sprite;
	}
	
	public void render(int x, int y, Screen screen) {
		
	}
	
	public boolean solid() {
		return false;
	}
}