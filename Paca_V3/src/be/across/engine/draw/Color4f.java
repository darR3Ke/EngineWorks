package be.across.engine.draw;

public class Color4f {
	private float r, g, b, a;
	
	public Color4f(){
		this(1,1,1,0);
	}
	
	public Color4f(float r, float g, float b, float a){
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public float getR() {
		return r;
	}

	public float getG() {
		return g;
	}

	public float getB() {
		return b;
	}

	public float getA() {
		return a;
	}

}
