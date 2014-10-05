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
			
	}
	
	public void moveY(float f){
		this.y += f;
	}
	public void moveX(float f){
		this.x += f;
	}

}
