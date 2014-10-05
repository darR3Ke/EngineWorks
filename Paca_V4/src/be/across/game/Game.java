package be.across.game;

import java.util.ArrayList;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import be.across.engine.GameFramework;
import be.across.engine.GameObject;
import be.across.engine.graphics.Color4f;
import be.across.game.objects.Punt;

public class Game implements GameFramework {

	private ArrayList<GameObject> objecten = new ArrayList<GameObject>();
	private Punt punt;

	@Override
	public void init() {

		// objecten.add(new testTexture());
		punt = new Punt(-0.5f, -0.5f, 1f, 1f, new Color4f(1f, 0f, 0f, 0.5f));
		objecten.add(punt);

	}

	@Override
	public void getInput() {
		if (Keyboard.isKeyDown(Keyboard.KEY_UP))
			punt.moveY(0.005f);
		if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
			punt.moveY(-0.005f);
		if (Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
			punt.moveX(0.005f);
		if (Keyboard.isKeyDown(Keyboard.KEY_LEFT))
			punt.moveX(-0.005f);

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
