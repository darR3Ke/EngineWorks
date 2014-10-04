package be.across.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import be.across.engine.graphics.Screen;
import be.across.game.Game;

public class GameLoop {

	private static final int DISPLAY_WIDTH = 1024;
	private static final int DISPLAY_HEIGHT = 768;
	private static final String DISPLAY_TITLE = "Kader naam";

	private static boolean running = false;
	private static boolean gameOver = false;
	private static boolean isPaused = false;

	private static Screen screen; // scherm object
	private static Game game; // game object

	private static final int MAX_FRAME_SKIP = 5; // maximum aantal frames dat geskipt mag worden als timeDiff 0 is
	private static long PERIOD = (1L * 1000000000L / 60L); // 60 updates per seconden omzetten naar nano seconden

	private static int fps, ups, second = 0;

	public static void main(String[] arghs) {
		initGL(); // initialize openGL
		initGame(); // initialize game paramaters
		runGame(); // starting the gameLoop
		cleanUp(); // opruimen van objecten en afsluiten van schermen
	}

	/*
	 * Initialize the display screen
	 */
	public static void initGL() {
		screen = Screen.getInstance();
		screen.init(DISPLAY_WIDTH, DISPLAY_HEIGHT, DISPLAY_TITLE); // Screen object maken
		screen.initGL(); // OpenGL starten voor het Screen Object
	}

	/*
	 * Initialize the game
	 */
	public static void initGame() {
		try {
			Keyboard.create(); // aanmaken van het keyboard object dat alle keyboard handeling doet
			Mouse.create(); // aanmaken van het mouse object dat alle mouse handeling doet
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		game = new Game(); // spel object met alle code
		game.init();
		running = true; // het spel start met lopen
	}

	/*
	 * de daadwerkelijke GameLoop
	 */
	public static void runGame() {

		long beforeTime, afterTime, timeDiff, sleepTime; // worden gebruikt voor variable sleepTimers
		long excess = 0L;

		beforeTime = System.nanoTime(); // baseline time

		while (running) {
			gameUpdate(); // game update
			gameRender(); // render een frame
			paintScreen(); // stuur het frame naar het scherm

			afterTime = System.nanoTime();
			timeDiff = afterTime - beforeTime; // kijken hoe lang alles gedraait heeft

			// debug fps en ups code
			second += timeDiff;
			if (second > 1000000000L) {
				System.out.println("FPS : " + fps + " UPS : " + ups);
				fps = 0;
				ups = 0;
				second = 0;
			}

			sleepTime = (PERIOD - timeDiff); // kijken hoeveel sleeptime we moeten gebruiken

			if (sleepTime > 0) { // er is tijd over nadat alles is gedaan
				try {
					Thread.sleep(sleepTime / 1000000L); // omzetten van nanoseconden naar miliseconden
				} catch (InterruptedException e) {
				}
			} else { // er is geen tijd over na alles te doen
				excess -= sleepTime; // opslaan van tijd die tekort was om alles te doen
			}

			beforeTime = System.nanoTime();

			int skips = 0;
			while ((excess > PERIOD) && (skips < MAX_FRAME_SKIP)) {
				gameRender(); // extra game update
				afterTime = System.nanoTime();
				timeDiff = afterTime - beforeTime;
				second += timeDiff;
				excess -= timeDiff;
				skips++;
				beforeTime = System.nanoTime();
			}
			second += sleepTime;
		}
	}

	/*
	 * Opruimen van omgeving
	 */
	private static void cleanUp() {
		screen.stop();
		System.exit(0);
	}

	/*
	 * einde van het spel door de gebruiker
	 */
	public static void stopGame() {
		running = false;
	}

	public void pauseGame() {
		isPaused = true;
	}

	public void resumeGame() {
		isPaused = false;
	}

	/*
	 * game bewerkingen en opvangen van gebruiker acties
	 */
	private static void gameUpdate() {
		ups++;
		game.getInput();
		if (!isPaused && !gameOver) {
			if (Display.isCloseRequested()) {
				stopGame();
			}
			if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
				stopGame();
			}
			game.update(); // scoring en game related code uitvoeren
		}
	}

	/*
	 * buffer opvullen met crap
	 */
	private static void gameRender() {
		fps++;
		screen.render(); // scherm en matrix schoonmaken voor de volgende tekenstap
		game.render(); // gameobjecten in de matrix zetten

	}

	/*
	 * buffer naar het scherm painten
	 */
	private static void paintScreen() {
		screen.update(); // matrix op het scherm tekenen
	}
}
