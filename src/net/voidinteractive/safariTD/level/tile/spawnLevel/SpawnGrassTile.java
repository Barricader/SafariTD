package net.voidinteractive.safariTD.level.tile.spawnLevel;

import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.graphics.Sprite;
import net.voidinteractive.safariTD.level.tile.Tile;

public class SpawnGrassTile extends Tile {
	public SpawnGrassTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}