package net.voidinteractive.safariTD.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.voidinteractive.safariTD.entity.Entity;
import net.voidinteractive.safariTD.entity.mob.Player;
import net.voidinteractive.safariTD.entity.particle.Particle;
import net.voidinteractive.safariTD.entity.projectile.Projectile;
import net.voidinteractive.safariTD.graphics.Screen;
import net.voidinteractive.safariTD.level.tile.Tile;
import net.voidinteractive.safariTD.util.Vector2i;

public class Level {
	protected int width, height;
	protected int[] tilesInt;
	protected int[] tiles;
	
	private List<Entity> entities = new ArrayList<Entity>();
	private List<Projectile> projectiles = new ArrayList<Projectile>();
	private List<Particle> particles = new ArrayList<Particle>();
	private List<Player> players = new ArrayList<Player>();
	
	private Comparator<Node> nodeSorter = new Comparator<Node>() {
		public int compare(Node n0, Node n1) {
			if (n1.fCost < n0.fCost)
				return 1;
			if (n1.fCost > n0.fCost)
				return -1;
			
			return 0;
		}
	};
	
	public int compare(Node arg0, Node arg1) {
		return 0;
	}
	
	public static Level spawn = new SpawnLevel("/tex/levels/spawn.png");
	
	public Level(int width, int height) {
		this.width = width;
		this.height = height;
		tilesInt = new int[width * height];
		generateLevel();
	}
	
	public Level(String path) {
		loadLevel(path);
		generateLevel();
	}
	
	protected void generateLevel() {
		
	}
	
	protected void loadLevel(String path) {
		
	}
	
	public void update() {
		for (int i = 0; i < entities.size(); i++)
			entities.get(i).update();
		for (int i = 0; i < projectiles.size(); i++)
			projectiles.get(i).update();
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).update();
		for (int i = 0; i < players.size(); i++)
			players.get(i).update();
		remove();
	}
	
	private void remove() {
		for (int i = 0; i < entities.size(); i++)
			if (entities.get(i).isRemoved())
				entities.remove(i);
		for (int i = 0; i < projectiles.size(); i++)
			if (projectiles.get(i).isRemoved())
				projectiles.remove(i);
		for (int i = 0; i < particles.size(); i++)
			if (particles.get(i).isRemoved())
				particles.remove(i);
		for (int i = 0; i < players.size(); i++)
			if (players.get(i).isRemoved())
				players.remove(i);
	}
	
	public List<Projectile> getProjectiles() {
		return projectiles;
	}
	
	//private void time() {
	//	
	//}
	
	public boolean tileCollision(int x, int y, int size, int xOffset, int yOffset) {
		boolean solid = false;
		
		for (int i = 0; i < 4; i++) {
			int xT = (x - i % 2 * size + xOffset) >> 4;
			int yT = (y - i / 2 * size + yOffset) >> 4;

			if (getTile(xT, yT).solid())
				solid = true;
		}
		
		return solid;
	}
	
	public void render(int xScroll, int yScroll, Screen screen) {
		screen.setOffset(xScroll, yScroll);
		int x0 = xScroll >> 4;
		int x1 = (xScroll + screen.width + 16) >> 4;
		int y0 = yScroll >> 4;
		int y1 = (yScroll + screen.height + 16) >> 4;
		
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				getTile(x, y).render(x, y, screen);
			}
		}
		
		for (int i = 0; i < entities.size(); i++) 
			entities.get(i).render(screen);
		for (int i = 0; i < projectiles.size(); i++)
			projectiles.get(i).render(screen);
		for (int i = 0; i < particles.size(); i++)
			particles.get(i).render(screen);
		for (int i = 0; i < players.size(); i++)
			players.get(i).render(screen);
	}
	
	public void add(Entity e) {
		e.init(this);
		if (e instanceof Particle)
			particles.add((Particle) e);
		else if (e instanceof Projectile)
			projectiles.add((Projectile) e);
		else if (e instanceof Player) {
			players.add((Player)e);
		}
		else
			entities.add(e);
	}
	
	public List<Player> getPlayer() {
		return players;
	}
	
	public Player getPlayerAt(int index) {
		return players.get(index);
	}
	
	public Player getClientPlayer() {
		return players.get(0);
	}
	
	private double getDist(Vector2i tile, Vector2i goal) {
		double distX = tile.getX() - goal.getX();
		double distY = tile.getY() - goal.getY();
		
		return Math.sqrt(distX * distX + distY * distY);
	}
	
	private boolean vectorInList(List<Node> list, Vector2i vec) {
		for (Node n : list) {
			if (n.tile.equals(vec))
				return true;
		}
		return false;
	}
	
	public List<Node> findPath(Vector2i start, Vector2i goal) {
		List<Node> openList = new ArrayList<Node>();
		List<Node> closedList = new ArrayList<Node>();
		Node cur = new Node(start, null, 0, getDist(start, goal));
		
		openList.add(cur);
		while (openList.size() > 0) {
			Collections.sort(openList, nodeSorter);
			cur = openList.get(0);
			
			if (cur.tile.equals(goal)) {
				List<Node> path = new ArrayList<Node>();
				while (cur.parent != null) {
					path.add(cur);
					cur = cur.parent;
				}
				
				openList.clear();
				closedList.clear();
				
				return path;
			}
			
			openList.remove(cur);
			closedList.add(cur);
			
			for (int i = 0 ; i < 9; i++) {
				if (i == 4)
					continue;
				int x = cur.tile.getX();
				int y = cur.tile.getY();
				int xDir = (i % 3) - 1;
				int yDir = (i / 3) - 1;
				
				Tile curTile = getTile(x + xDir, y + yDir);
				if (curTile == null)
					continue;
				if (curTile.solid())
					continue;
				
				Vector2i a = new Vector2i(x + xDir, y + yDir);
				double gCost = cur.gCost + (getDist(cur.tile, a) == 1 ? 1: 0.95);
				double hCost = getDist(a, goal);
				
				Node node = new Node(a, cur, gCost, hCost);
				if (vectorInList(closedList, a) && gCost >= node.gCost)
					continue;
				if (!vectorInList(openList, a) || gCost < node.gCost)
					openList.add(node);
			}
		}
		
		closedList.clear();
		return null;
	}
	
	public List<Entity> getEntities(Entity e, int radius) {
		List<Entity> result = new ArrayList<Entity>();
		int eX = (int)e.getX();
		int eY = (int)e.getY();
		
		for (int i = 0; i < entities.size(); i++) {
			Entity entity = entities.get(i);
			
			if (entity.equals(e))
				continue;
			
			int x = (int)entity.getX();
			int y = (int)entity.getY();
			
			int distX = Math.abs(x - eX);
			int distY = Math.abs(y - eY);
			double dist = Math.sqrt(distX * distX + distY * distY);
			
			if (dist <= radius)
				result.add(entity);
		}
		
		return result;
	}
	
	public List<Player> getPlayers(Entity e, int radius) {
		List<Player> result = new ArrayList<Player>();
		int eX = (int)e.getX();
		int eY = (int)e.getY();
		
		for (int i = 0; i < players.size(); i++) {
			Player player = players.get(i);
			int x = (int)player.getX();
			int y = (int)player.getY();
			
			int distX = Math.abs(x - eX);
			int distY = Math.abs(y - eY);
			double dist = Math.sqrt((distX * distX) + (distY * distY));
			
			if (dist <= radius)
				result.add(player);
		}
		return result;
	}
	
	// Switch levels
	/*private HashMap<String, Level> levelMap = new HashMap<String, Level>();
	private Level currentLevel;

	public void addLevel(Level level) {
	     levelMap.put(level.getName(), level);
	}

	public void getLevel(String name) {
	     return levelMap.get(name);
	}

	public Level getCurrentLevel() { return currentLevel; }

	public void gotoLevel(Player player, String levelName) {
	     Level level = getLevel(levelName);
	     currentLevel = level;
	     player.setLevel(level);

	     // do other initialization stuff to the level here
	}*/
	
	// Grass = 0xFF00FF00
	// Flower = 0xFFFFFF00
	// Rock = 0xFF808080
	public Tile getTile(int x, int y) {
		if (x < 0 || y < 0 || x >= width || y >= height)
			return Tile.voidTile;
		if (tiles[x + y * width] == Tile.colorSpawnGrass)
			return Tile.spawnGrass;
		else if (tiles[x + y * width] == Tile.colorSpawnDirt)
			return Tile.spawnDirt;
		else if (tiles[x + y * width] == Tile.colorSpawnBrick)
			return Tile.spawnBrick;
		else if (tiles[x + y * width] == Tile.colorSpawnWater)
			return Tile.spawnWater;
		return Tile.voidTile;
	}
}