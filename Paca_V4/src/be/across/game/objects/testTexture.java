package be.across.game.objects;


import be.across.engine.GameObject;

public class testTexture extends GameObject{
	
	public testTexture(){
		this.x = 100;
		this.y = 100;
		this.sx = 200;
		this.sy = 300;
		this.texName = "stocking.png";
		this.texLoaded = true;
	}

	@Override
	public void update() {
		x += 1;
		
	}

}
