package be.across.game.objects;

import be.across.engine.physics.Physics;

public class Wall extends GameObject{
	
	private Ball ball;
	private static final float STD_SIZE = 16;
	
	
	public Wall (float x, float y, float sx, float sy, Ball ball){
		this.x = x;
		this.y = y;
		this.sx = sx;
		this.sy = sy;
		
		this.ball = ball;
		
	}

	@Override
	public void update() {
		if(Physics.checkCollisions(this, ball)){
			ball.reverseY();
		}
		
	}

	public static float getStdSize() {
		return STD_SIZE;
	}

}
