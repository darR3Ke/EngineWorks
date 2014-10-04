package be.across.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import be.across.engine.screen.Screen;
import be.across.game.Game;


public class GameLoop {
	
	private static final int DISPLAY_WIDTH = 1024;
	private static final int DISPLAY_HEIGHT = 768;
	
	private static boolean running = false;
	private static boolean gameOver = false;
	private static boolean isPaused = false;
	
	private static Screen scherm;											// scherm object
	private static Game game;												// game object
	
	private static final int MAX_FRAME_SKIP = 5;							// maximum aantal frames dat geskipt mag worden als timeDiff 0 is
	private static long period = 16 * 1000000L; 							// timeframe waarin alles moet gebeuren om de juist fps te behouden. wordt berekend. 60FPS --> 1000(miliseconden)/60 = 16 
																			// * 1000000L omzetten milliseconds naar nanoseconds
	
	
	public static void main(String[] arghs){
		initGL();															// initialize openGL
		initGame();															// initialize game paramaters
		runGame();															// starting the gameLoop            
		cleanUp();															// opruimen van objecten en afsluiten van schermen
	}
	
	
	/*
	 * Initialize the display screen
	 */
	public static void initGL(){
		scherm = new Screen(DISPLAY_WIDTH, DISPLAY_HEIGHT);					// Screen object maken
		scherm.initGL();													// OpenGL starten voor het Screen Object
	}
	
	/*
	 * Initialize the game
	 */
	public static void initGame() {
		try {
			Keyboard.create();												// aanmaken van het keyboard object dat alle keyboard handeling doet
			Mouse.create();													// aanmaken van het mouse object dat alle mouse handeling doet
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		game = new Game();													// spel object met alle code
		game.init();
		running = true;														// het spel start met lopen
	}	
	
	
	/*
	 * de daadwerkelijke GameLoop
	 */
	public static void runGame() {
		
		long beforeTime, afterTime, timeDiff, sleepTime;					// worden gebruikt voor variable sleepTimers
		long excess = 0L;													
		
		
		beforeTime = System.nanoTime();										// baseline time
		
		while(running){
			gameUpdate();													// game update
			gameRender();													// render een frame
			paintScreen();													// stuur het frame naar het scherm
			
			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;								// kijken hoe lang alles gedraait heeft
			
			sleepTime = (period - timeDiff);								// kijken hoeveel sleeptime we moeten gebruiken
			
			if (sleepTime > 0) {											// er is tijd over nadat alles is gedaan
				try {
					Thread.sleep(sleepTime/1000000L);						// omzetten van nanoseconden naar miliseconden
				} catch (InterruptedException e) { }
			}
			else {															// er is geen tijd over na alles te doen
				excess -= sleepTime;										// opslaan van tijd die tekort was om alles te doen
			}
			beforeTime = System.nanoTime();
			
			
			if (running) {													// check of het spel nog aant draaien is
				/*
				 * Als de frame animaties te lang duren om binne de period klaar te geraken gaan we extra game updates zonder frames te renderen en naar het scherm te sturen
				 */
				int skips = 0;
				while ((excess > period) && (skips < MAX_FRAME_SKIP)) {
					excess -= period;
					gameUpdate();												// extra game update
					skips++;
				}
			}
		}
	}
	
	
	/*
	 * Opruimen van omgeving 
	 */
	private static void cleanUp() {
		scherm.stop();
		System.exit(0);
	}
	
	/*
	 * einde van het spel door de gebruiker
	 */
	public static void stopGame(){
		running = false;
	}
	
	public void pauseGame(){
		isPaused = true;
	}
	
	public void resumeGame(){
		isPaused = false;
	}
	
	/*
	 * game bewerkingen en opvangen van gebruiker acties
	 */
	private static void gameUpdate() {
		game.getInput();
		if (!isPaused && !gameOver){
			if(Display.isCloseRequested()) {
				stopGame();
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				stopGame();
			}
			game.update();														// scoring en game related code uitvoeren
		}
	}

	/*
	 * buffer opvullen met crap
	 */
	private static void gameRender() {
		scherm.render();														// scherm en matrix schoonmaken voor de volgende tekenstap
		game.render();															// gameobjecten in de matrix zetten
		
	}
	
	/*
	 * buffer naar het scherm painten
	 */
	private static void paintScreen() {
		scherm.update();														// matrix op het scherm tekenen
	}
}
