package be.across.game;

import java.util.ArrayList;
import java.util.Random;

import be.across.engine.GameFramework;
import be.across.engine.GameObject;
import be.across.game.objects.Punt;
import be.across.game.objects.testTexture;

public class Game implements GameFramework {
	
	private ArrayList<GameObject> objecten = new ArrayList<GameObject>();

	@Override
	public void init() {
		
	//	objecten.add(new testTexture());
		Random random = new Random();
		objecten.add(new Punt(random.nextInt(1024), random.nextInt(768)));

	}

	@Override
	public void getInput() {

	}

	@Override
	public void update() {
		for(GameObject go: objecten){
			go.update();
		}

	}

	@Override
	public void render() {
		for(GameObject go:objecten){
			go.render();
		}

	}

}
