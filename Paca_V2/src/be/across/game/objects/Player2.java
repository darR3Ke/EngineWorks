package be.across.game.objects;

import be.across.engine.physics.Physics;

public class Player2 extends GameObject{
	private static final float SIZE_X = 16;
	private static final float SIZE_Y = SIZE_X * 7;
	private static final float MAX_SPEEDY = 4f;
	private static final float DAMPING = 0.05f;		
	
	private Ball ball;
	
	
	
	public Player2(float x, float y, Ball ball){
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
		
		float speed = (ball.getCenterY() - getCenterY()) * DAMPING;
		
		if (speed > MAX_SPEEDY){
			speed = MAX_SPEEDY;
		}
		if (speed < -MAX_SPEEDY){
			speed = -MAX_SPEEDY;
		}
		
		y += speed;
		
	}

	public static float getSizeY() {
		return SIZE_Y;
	}
	
	public static float getSizeX() {
		return SIZE_X;
	}

	public void move(float mag){
		y += mag;
	}

}
