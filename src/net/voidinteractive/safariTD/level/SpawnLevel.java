package net.voidinteractive.safariTD.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

import net.voidinteractive.safariTD.entity.mob.Shooter;
import net.voidinteractive.safariTD.entity.mob.Star;

public class SpawnLevel extends Level {
	public SpawnLevel(String path) {
		super(path);
	}

	protected void loadLevel(String path) {
		try {
			BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
			width = image.getWidth();
			height = image.getHeight();
			tiles = new int[width * height];
			image.getRGB(0, 0, width, height, tiles, 0, width);
		}
		catch (IOException e) {
			e.printStackTrace();
			System.out.println("Could not load level file!");
		}
		
		Random random = new Random();
		for (int i = 0; i < 2; i++) {
			//add(new Chaser(random.nextInt(10) + 15, random.nextInt(20) + 45));
			add(new Star(random.nextInt(10) + 15, random.nextInt(20) + 45));
			add(new Shooter(random.nextInt(10) + 15, random.nextInt(20) + 45));
		}
	}

	protected void generateLevel() {
		
	}
}