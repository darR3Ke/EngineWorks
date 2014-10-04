package be.across.game.objects;

import java.util.Random;

import be.across.engine.GameObject;
import be.across.engine.graphics.Color4f;

public class Punt extends GameObject{
	private Random random = new Random();
	
	public Punt(float x, float y){
		this.x = x;
		this.y = y;
		this.sx = 0.05f;
		this.sy = 0.05f;
		this.texLoaded = false;
		color4f = new Color4f(random.nextFloat(), random.nextFloat(), random.nextFloat(), random.nextFloat());
	}

	@Override
	public void update() {
		this.x = (random.nextFloat()-0.5f)*2;
		this.y = (random.nextFloat()-0.5f)*2;
		this.color4f.setA(random.nextFloat());
		this.color4f.setR(random.nextFloat());
		this.color4f.setG(random.nextFloat());
		this.color4f.setB(random.nextFloat());
		
	}

}
