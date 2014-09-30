package be.across.engine;

import be.across.engine.draw.Draw;

public abstract class GameObject {
	
	protected float x, y, sx, sy;
	
	public abstract void update();
	
	public void render(){
		Draw.rect(x, y, sx, sy);
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

}
