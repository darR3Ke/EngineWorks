package be.across.engine;

import be.across.engine.graphics.Color4f;
import be.across.engine.graphics.Draw;

public abstract class GameObject {
	
	protected float x, y, sx, sy;					// positie X, positie Y, size X, size Y
	protected Color4f color4f;
	protected String texName;							// texture naam
	protected boolean texLoaded;					// true als er een texture is
	
	
	public abstract void update();
	
	public void render(){
		if (!texLoaded) {
			Draw.rect(x, y, 0, sx, sy, color4f);
		} else {
//			Draw.tex(x, y, sx, sy, texName);
		}
		
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public float getSx() {
		return sx;
	}

	public float getSy() {
		return sy;
	}
	
	public float getCenterY(){
		return y + sy / 2;
	}
	
	 public float getCenterX(){
		 return x + sx / 2;
	 }

}
