package net.voidinteractive.safariTD.entity.spawner;

import net.voidinteractive.safariTD.entity.Entity;
import net.voidinteractive.safariTD.level.Level;

public abstract class Spawner extends Entity {
	
	public enum Type {
		MOB, PARTICLE;
	}
	
	@SuppressWarnings("unused")
	private Type type;
	
	public Spawner(int x, int y, Type type, int amount, Level level) {
		init(level);
		this.x = x;
		this.y = y;
		this.type = type;
	}
}