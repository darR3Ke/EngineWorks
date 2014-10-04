package be.across.game.objects;

import java.util.Random;

import be.across.engine.GameObject;
import be.across.engine.graphics.Color4f;

public class Punt extends GameObject{
	private Random random = new Random();
	
	public Punt(int x, int y){
		this.x = x;
		this.y = y;
		this.sx = 16;
		this.sy = 16;
		this.texLoaded = false;
		color4f = new Color4f(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
	}

	@Override
	public void update() {
		this.x = random.nextInt(1024);
		this.y = random.nextInt(768);
		this.color4f.setA(random.nextFloat());
		this.color4f.setR(random.nextFloat());
		this.color4f.setG(random.nextFloat());
		this.color4f.setB(random.nextFloat());
		
	}

}
