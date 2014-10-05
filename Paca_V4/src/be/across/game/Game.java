package be.across.game;

import java.util.ArrayList;
import java.util.Random;

import be.across.engine.GameFramework;
import be.across.engine.GameObject;
import be.across.engine.graphics.Color4f;
import be.across.game.objects.Punt;

public class Game implements GameFramework {

	private ArrayList<GameObject> objecten = new ArrayList<GameObject>();

	@Override
	public void init() {

		// objecten.add(new testTexture());
		objecten.add(new Punt(-0.5f, -0.5f, 1f, 1f, new Color4f(1f, 0f, 0f, 1.0f)));

	}

	@Override
	public void getInput() {

	}

	@Override
	public void update() {
		for (GameObject go : objecten) {
			go.update();
		}

	}

	@Override
	public void render() {
		for (GameObject go : objecten) {
			go.render();
		}

	}

}
