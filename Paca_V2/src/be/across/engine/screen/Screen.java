package be.across.engine.screen;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Screen {
	
	
	private static final int DISPLAY_WIDTH = 768;
	private static final int DISPLAY_HEIGHT = 1024;
	
	
	public void start(){
		try {
			Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));
			Display.create();
		} catch (LWJGLException e) { e.printStackTrace(); }


	}
	
	public void stop(){
		Display.destroy();
	}

}
