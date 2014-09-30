package be.across.game.objects;


public class Ball extends GameObject{
	private static final float SIZE_BALL = 16;
	private static final float MAX_SPEEDX = 4f;
	private static final float MAX_SPEEDY = 8f;
	private static final float DAMPING = 0.05f;
	
	private float velX;
	private float velY;
	
	private final float startX;
	private final float startY;
	
	
	public Ball(float x, float y) {
		
		startX = x;
		startY = y;
		
		this.x = x;
		this.y = y;
		this.sx = SIZE_BALL;
		this.sy = SIZE_BALL;
		
		velX = -MAX_SPEEDX;
		velY = 0;
		
	}

	@Override
	public void update() {
		x += velX;
		y += velY;
		
	}

	public static float getSizeBall() {
		return SIZE_BALL;
	}
	
	public void reverseX(float center){
		velY += (getCenterY() - center) * DAMPING;
		velX *= -1;
		
		if (velY > MAX_SPEEDY) {
			velY = MAX_SPEEDY;
		}
		if (velY < -MAX_SPEEDY){
			velY = -MAX_SPEEDY;
		}
	}
	
	public void reverseY(){
		velY *= -1;
	}
	
	public void resetPos(){
		x = startX;
		y = startY;
		
		velY = 0;
		velX *= -1;
		
	}
	
}
