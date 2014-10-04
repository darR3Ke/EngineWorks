package be.across.engine.graphics;

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

	public void setR(float r) {
		this.r = r;
	}

	public void setG(float g) {
		this.g = g;
	}

	public void setB(float b) {
		this.b = b;
	}

	public void setA(float a) {
		this.a = a;
	}

}
