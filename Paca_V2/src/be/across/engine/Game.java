package be.across.engine;

import be.across.engine.screen.Screen;

public class Game {
	
	private Thread looper;

	public static void main(String[] arghs){
		init();
		
		if (looper == null || !running) {
			looper = new Thread() {
				
			}
		}

	}

	
	
	
	
	/*
	 * Initialize the game
	 */
	public static void init() {
		Screen scherm = new Screen();
		scherm.start();
	}
}





private void startGame(){
	if (looper == null || !running) {
		looper = new Thread(this);
		looper.start();
	}
	
}