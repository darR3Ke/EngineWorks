package be.across.engine;

import be.across.engine.draw.Draw;

public abstract class GameObject {
	
	protected float x, y, sx, sy;					// positie X, positie Y, size X, size Y
	protected float r, g, b, a;						// rood, groen, blauw, alpha
	protected String texName;							// texture naam
	protected boolean texLoaded;					// true als er een texture is
	
	
	public abstract void update();
	
	public void render(){
		if (!texLoaded) {
			Draw.rect(x, y, sx, sy);
		} else {
			Draw.tex(x, y, sx, sy, texName);
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
