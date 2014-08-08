package net.voidinteractive.safariTD.entity.spawner;

import net.voidinteractive.safariTD.entity.particle.Particle;
import net.voidinteractive.safariTD.level.Level;

public class ParticleSpawner extends Spawner {
	@SuppressWarnings("unused")
	private int life;
	
	public ParticleSpawner(int x, int y, int life, int amount, Level level) {
		super(x, y, Type.PARTICLE, amount, level);
		this.life = life;
		
		for (int i = 0; i < amount; i++)
			level.add(new Particle(x, y, life));
	}
}