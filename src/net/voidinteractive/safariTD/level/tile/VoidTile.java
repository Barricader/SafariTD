package net.voidinteractive.safariTD.level.tile;

import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.graphics.Sprite;

public class VoidTile extends Tile {
	public VoidTile(Sprite sprite) {
		super(sprite);
	}
	
	public void render(int x, int y, Screen screen) {
		screen.renderTile(x << 4, y << 4, this);
	}
}