package net.voidinteractive.safariTD.graphics;

public class Font {
	private static SpriteSheet font = new SpriteSheet("/tex/fonts/font.png", 16);
	private static Sprite[] chars = Sprite.split(font);

	private static String charIndex = //
			"ABCDEFGHIJKLM"  + //
			"NOPQRSTUVWXYZ"  + //
			"0123456789*-+"  + //
			"|/\\[]~=<>_.,:" + //
			";'\"()!?#$%";

	public Font() {

	}
	
	public void render(int x, int y, String text, Screen screen) {
		render(x, y, 0, 0, text, screen);
	}

	public void render(int x, int y, int spacing, int color, String text, Screen screen) {
		int xOffset = 0;
		int line = 0;
		for (int i = 0; i < text.length(); i++) {
			xOffset += 16 + spacing;
			char curChar = Character.toUpperCase(text.charAt(i));
			if (curChar == '\n') {
				line++;
				xOffset = 0;
			}
			int index = charIndex.indexOf(curChar);
			if (index == -1)
				continue;
			screen.renderText(x + xOffset, y + line * 18, color, chars[index], true);
		}
	}
}