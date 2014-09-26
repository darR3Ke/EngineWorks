package be.across.game;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import be.across.game.objects.Ball;
import be.across.game.objects.GameObject;
import be.across.game.objects.Player1;
import be.across.game.objects.Player2;
import be.across.game.objects.Wall;

public class Game {
	private ArrayList<GameObject> objects;
	private Ball ball;
	private Player1 player;
	private Player2 enemy;
	
	private int player1Score = 0;
	private int player2Score = 0;
	
	
	public Game(){
		objects = new ArrayList<GameObject>();						// Lijst van objecten maken
		
		
		
		ball = new Ball(Display.getWidth() / 2 - Ball.getSizeBall() /2, Display.getHeight() /2 - Ball.getSizeBall() / 2);
		
		player = new Player1(16, Display.getHeight() /2 - Player1.getSizeY() / 2, ball);
		enemy = new Player2(Display.getWidth()-Player2.getSizeX()-16, Display.getHeight() /2 - Player1.getSizeY() / 2, ball);
		
		Wall wallBot = new Wall(0, 0, Display.getWidth(), Wall.getStdSize(), ball);
		Wall wallTop = new Wall(0, Display.getHeight()-Wall.getStdSize(), Display.getWidth(), Wall.getStdSize(), ball);
		
		
		objects.add(ball);
		objects.add(player);
		objects.add(enemy);
		objects.add(wallBot);
		objects.add(wallTop);
		

	}
	
	public void getInput(){
		if(Keyboard.isKeyDown(Keyboard.KEY_UP)){
			player.move(1);
			
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_DOWN)){
			player.move(-1);
		}
		
	}
	public void update(){
		for(GameObject go : objects)
			go.update();											// voor ieder object in de lijst, doe een update
		getInput();
		
		if(ball.getX() > Display.getWidth()){
			player1Score++;
			ball.resetPos();
		}
		if(ball.getX() < 0){
			player2Score++;
			ball.resetPos();
		}
		
	}
	
	public void render(){
		for(GameObject go : objects)								// voor ieder object in de lijst, doe een render
			go.render();
		
		Display.setTitle("PacaPong - Player 1 : " + player1Score + " Player 2 : " + player2Score);
	}

}
