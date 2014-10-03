package be.across.game.objects;

import java.util.Random;

import be.across.engine.GameObject;

public class Punt extends GameObject{
	
	public Punt(int x, int y){
		this.x = x;
		this.y = y;
		this.sx = 1;
		this.sy = 1;
		this.texLoaded = false;
		
		Random random = new Random();
		this.r = random.nextFloat();
		this.g = random.nextFloat();
		this.b = random.nextFloat();
		this.a = 0;
		
	}

	@Override
	public void update() {
		
	}

}
