package net.voidinteractive.safariTD.graphics;

public class AnimSprite extends Sprite {
	private int frame = 0;
	private int rate = 5;
	private int time = 0;
	private int length = -1;
	private Sprite sprite;
	
	public AnimSprite(int w, int h, int length, SpriteSheet sheet) {
		super(w, h, sheet);
		this.length = length;
		sprite = sheet.getSprites()[0];
		if (length > sheet.getSprites().length)
			System.err.println("Error: Length of anim is too long...");
	}
	
	public void update() {
		time++;
		//if (time >= 7500)
		//	time = 0;
		if (time % rate == 0) {
			if (frame >= length - 1)
				frame = 0;
			else
				frame++;
			sprite = sheet.getSprites()[frame];
		}
	}
	
	public Sprite getSprite() {
		return sprite;
	}
	
	public void setFrameRate(int rate) {
		this.rate = rate;
	}

	public void setFrame(int frame) {
		if (frame > sheet.getSprites().length - 1)
			return;
		sprite = sheet.getSprites()[frame];
	}
}