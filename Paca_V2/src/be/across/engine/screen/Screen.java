package be.across.engine.screen;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Screen {
	
	
	private static final int DISPLAY_WIDTH = 1024;
	private static final int DISPLAY_HEIGHT = 768;
	
	
	public void init(){
		try {
			Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));	// instellen van de resolutie van het schermobject dat we gaan aanmaken
			Display.create();												// aanmaken van een scherm object 
		} catch (LWJGLException e) { e.printStackTrace(); }
	}
	
	public void initGL(){
		glMatrixMode(GL_PROJECTION); 										// Switch matrix mode van Modelview (waarin we tekenen) naar Projection (de viewpoints)
		glLoadIdentity(); 													// de matrix clearen met de identity matrix
		glOrtho(0, 1024, 0, 768, 1, -1); 									// initializen van ons viewpoint ( 1, -1) is de Z-axis voor 2d 
		glMatrixMode(GL_MODELVIEW);											// terug switchen naar onze teken matrix
		
		glClearColor(0, 0, 0, 1); 											// de clear waarde RGB +alpha
		
		glDisable(GL_DEPTH_TEST);											// test voor 3d, niet nodig op het moment 										
		
	}
	
	public void update(){
		Display.update();													// het scherm updaten
	}
	
	public void stop(){
		Display.destroy();													// het scherm destroyen
	}

}
