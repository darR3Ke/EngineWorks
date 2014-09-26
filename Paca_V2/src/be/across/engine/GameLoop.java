package be.across.engine;

import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;
import be.across.engine.draw.Draw;
import be.across.engine.screen.Screen;


public class GameLoop {
	
	private volatile boolean running = false;
	private volatile boolean gameOver = false;
	private volatile boolean isPaused = false;
	
	private static Screen scherm;
	
	/*
	 * waardes die later in een config file zullen komen
	 */
	
	/* gebruikt voor threading.
	private static final int NO_DELAYS_PER_YIELD = 16;						// minimum frames dat altijd gerendered wordt als de timeDiff 0 is
	*/
	
	private static final int MAX_FRAME_SKIP = 5;							// maximum aantal frames dat geskipt mag worden als timeDiff 0 is
	private long period = 16 * 1000000L; 									// timeframe waarin alles moet gebeuren om de juist fps te behouden. wordt berekend. 60FPS --> 1000(miliseconden)/60 = 16 
																			// * 1000000L omzetten milliseconds naar nanoseconds
	
	
	public GameLoop(){
		init();																// initialize game paramaters
		initGL();															// initialize openGL
		runGame();															// starting the gameLoop Thread           
		cleanUp();															// opruimen van objecten en afsluiten van schermen
	}
	
	
	private void cleanUp() {
		scherm.stop();
		System.exit(0);
	}


	/*
	 * Initialize the game
	 */
	public static void init() {
		scherm = new Screen();
		scherm.init();
	}	
	
	
	public static void initGL(){
		scherm.initGL();
		
		
	}
	
	/*
	 * de daadwerkelijke GameLoop
	 */
	public void runGame() {
		
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
	 * einde van het spel door de gebruiker
	 */
	public void stopGame(){
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
	private void gameUpdate() {
		if (!isPaused && !gameOver){

			// hier komt stuff voor het spel
			if(Display.isCloseRequested()) {
				stopGame();
			}
		}
	}

	/*
	 * buffer opvullen met crap
	 */
	private void gameRender() {
		// graphics engine da dingen in de buffer zet
		Draw.rect(0, 0, 16, 16);
		
	}
	
	/*
	 * buffer naar het scherm painten
	 */
	private void paintScreen() {
		// buffer naar het scherm sturen
		scherm.update();
		
	}
	
	
	




}
