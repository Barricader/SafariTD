package net.voidinteractive.safariTD;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import javax.swing.JFrame;

import net.voidinteractive.safariTD.entity.mob.Player;
import net.voidinteractive.safariTD.graphics.Font;
import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.input.Keyboard;
import net.voidinteractive.safariTD.input.Mouse;
import net.voidinteractive.safariTD.level.Level;
import net.voidinteractive.safariTD.level.TileCoord;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	private static int width = 300;
	private static int height = width / 16 * 9;
	private static int scale = 3;
	public static String title = "SafariTD";
	
	private Thread thread;
	private JFrame frame;
	private Keyboard key;
	private Level level;
	private Player player;
	private boolean running = false;
	
	private Screen screen;
	
	@SuppressWarnings("unused")
	private Font font;
	
	private BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	
	public Game() {
		Dimension size = new Dimension(width * scale, height * scale);
		setPreferredSize(size);
		
		screen = new Screen(width, height);
		frame = new JFrame();
		key = new Keyboard();
		//level = new RandomLevel(64, 64);
		level = Level.spawn;
		font = new Font();
		TileCoord playerSpawn = new TileCoord(20, 60);
		player = new Player(playerSpawn.x(), playerSpawn.y(), key);
		level.add(player);
		Mouse mouse = new Mouse();
		
		addMouseListener(mouse);
		addMouseMotionListener(mouse);
		addKeyListener(key);
	}
	
	public static int getWindowWidth() {
		return width * scale;
	}
	
	public static int getWindowHeight() {
		return height * scale;
	}
	
	public synchronized void start() {
		running = true;
		thread = new Thread(this, "Display");
		thread.start();
	}
	
	public synchronized void stop() {
		running = false;
		try {
			thread.join();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / 60.0;
		double delta = 0;
		int frames = 0;
		//Sound.theme.play();
		
		requestFocus();
		
		while (running) {
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			
			while (delta >= 1) {
				update();
				delta--;
			}
			
			render();
			frames++;
			
			if (System.currentTimeMillis() - timer > 1000) {
				timer += 1000;
				frame.setTitle(title + " | " + frames + " fps");
				frames = 0;
			}
		}
		
		stop();
	}
	
	public void update() {
		key.update();
		level.update();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		screen.clear();
		double xScroll = player.getX() - screen.width / 2;
		double yScroll = player.getY() - screen.height / 2;
		level.render((int)xScroll, (int)yScroll, screen);
		
		//font.render(50, 50, -4, 0x202060, "Hello, my name\nis Jose.", screen);;
		
		// Realm UI Bar
		//Sprite test = new Sprite(80, height, 0x202020);
		//screen.renderSprite(width - 80, 0, test, true);
		
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = screen.pixels[i];
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.frame.setResizable(false);
		game.frame.setTitle(title + " | " + "0 fps");
		game.frame.add(game);
		game.frame.pack();
		game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.frame.setLocationRelativeTo(null);
		game.frame.setVisible(true);
		game.start();
	}
}