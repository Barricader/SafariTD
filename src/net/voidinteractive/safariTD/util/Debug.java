package net.voidinteractive.safariTD.util;

import net.voidinteractive.safariTD.graphics.Screen;

public class Debug {
	private Debug() {
		
	}
	
	public static void drawRect(int x, int y, int w, int h, Screen screen, boolean fixed) {
		screen.drawRect(x, y, w, h, 0x202020, fixed);
	}
	
	public static void drawRect(int x, int y, int w, int h, int color, Screen screen, boolean fixed) {
		screen.drawRect(x, y, w, h, color, fixed);
	}
}