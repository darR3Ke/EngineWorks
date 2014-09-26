package be.across.game.objects;

import be.across.engine.physics.Physics;

public class Player1 extends GameObject{
	private static final float SIZE_X = 16;
	private static final float SIZE_Y = SIZE_X * 7;
	private static final float SPEED = 4f;
	
	private Ball ball;
	
	
	
	public Player1(float x, float y, Ball ball){
		this.x = x;
		this.y = y;
		this.sx = SIZE_X;
		this.sy = SIZE_Y;
		this.ball = ball;
		
	}

	@Override
	public void update() {
		if(Physics.checkCollisions(this, ball)){
			ball.reverseX(getCenterY());
		}
		
	}

	public static float getSizeY() {
		return SIZE_Y;
	}
	
	public void move(float mag){
		y += SPEED * mag;
	}

}
