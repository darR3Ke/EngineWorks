package be.across.engine;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

import be.across.engine.screen.Screen;
import be.across.game.Game;


public class GameLoop {
	
	private static boolean running = false;
	private static boolean gameOver = false;
	private static boolean isPaused = false;
	
	private static Screen scherm;											// scherm object
	private static Game game;
	
	/*
	 * waardes die later in een config file zullen komen
	 */
	
	/* gebruikt voor threading.
	private static final int NO_DELAYS_PER_YIELD = 16;						// minimum frames dat altijd gerendered wordt als de timeDiff 0 is
	*/
	
	private static final int MAX_FRAME_SKIP = 5;							// maximum aantal frames dat geskipt mag worden als timeDiff 0 is
	private static long period = 16 * 1000000L; 									// timeframe waarin alles moet gebeuren om de juist fps te behouden. wordt berekend. 60FPS --> 1000(miliseconden)/60 = 16 
																			// * 1000000L omzetten milliseconds naar nanoseconds
	
	
	public static void main(String[] arghs){
		initGL();															// initialize openGL
		initGame();															// initialize game paramaters
		runGame();															// starting the gameLoop Thread           
		cleanUp();															// opruimen van objecten en afsluiten van schermen
	}
	
	/*
	 * Initialize the game
	 */
	public static void initGame() {
		game = new Game();
	}	
	
	
	public static void initGL(){
		scherm = new Screen();
		scherm.init();
		scherm.initGL();
		
		
	}
	
	/*
	 * de daadwerkelijke GameLoop
	 */
	public static void runGame() {
		
		long beforeTime, afterTime, timeDiff, sleepTime;					// worden gebruikt voor variable sleepTimers
		long overSleepTime = 0L;
		long excess = 0L;
		
		/* gebruikt voor threading, niet nodig op het moment.
		 * 		int noDelays = 0;													// counter om te zien hoeveel frames zijn gerendered zonder delays
		 */
		
		
		
		beforeTime = System.nanoTime();										// baseline time
		
		running = true;
		
		while(running){
			gameUpdate();													// game update
			gameRender();													// render een frame
			paintScreen();													// stuur het frame naar het scherm
			
			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime;								// kijken hoe lang alles gedraait heeft
			
			sleepTime = (period - timeDiff) - overSleepTime;				// kijken hoeveel sleeptime we moeten gebruiken
			
			if (sleepTime > 0) {											// er is tijd over nadat alles is gedaan
				try {
					Thread.sleep(sleepTime/1000000L);						// omzetten van nanoseconden naar miliseconden
				} catch (InterruptedException e) { }
				overSleepTime = (System.nanoTime() - afterTime) - sleepTime;
			}
			else {															// er is geen tijd over na alles te doen
				excess -= sleepTime;										// opslaan van "extra tijd"
				overSleepTime = 0L;											

				/*  No lwjgl threading wegens problemen, misschien later een gameupdate thread. Dus moet ik geen yield doen op het moment.		
				if (++noDelays >= NO_DELAYS_PER_YIELD) {
					Thread.yield();											// prioriteit afstaan zodat andere dingen kunne draaien
					noDelays = 0;
				}
				*/
				
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
		if (!isPaused && !gameOver){
			
			game.update();

			// hier komt stuff voor het spel
			if(Display.isCloseRequested()) {
				stopGame();
			}
			if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
				stopGame();
			}
		}
	}

	/*
	 * buffer opvullen met crap
	 */
	private static void gameRender() {
		// graphics engine da dingen in de buffer zet
		scherm.render();
		game.render();
		
	}
	
	/*
	 * buffer naar het scherm painten
	 */
	private static void paintScreen() {
		// buffer naar het scherm sturen
		scherm.update();
		
	}
	
	
	




}
