package be.across.game.objects;

import be.across.engine.GameObject;
import be.across.engine.graphics.Color4f;

public class Punt extends GameObject{
	
	public Punt(float x, float y, float sx, float sy, Color4f color4f){
		this.x = x;
		this.y = y;
		this.sx = sx;
		this.sy = sy; 
		this.texLoaded = false;
		this.color4f =  color4f;
	}

	@Override
	public void update() {
	/*	this.x = (random.nextFloat()-0.5f)*2;
		this.y = (random.nextFloat()-0.5f)*2;
		this.color4f.setA(random.nextFloat());
		this.color4f.setR(random.nextFloat());
		this.color4f.setG(random.nextFloat());
		this.color4f.setB(random.nextFloat());*/
		
	}

}
