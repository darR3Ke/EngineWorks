package be.across.engine.screen;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_TEST;
import static org.lwjgl.opengl.GL11.GL_MODELVIEW;
import static org.lwjgl.opengl.GL11.GL_PROJECTION;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glClearColor;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glLoadIdentity;
import static org.lwjgl.opengl.GL11.glMatrixMode;
import static org.lwjgl.opengl.GL11.glOrtho;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Screen {
	
	
	private static final int DISPLAY_WIDTH = 1024;
	private static final int DISPLAY_HEIGHT = 768;
	
	
	public void init(){
		try {
			Display.setDisplayMode(new DisplayMode(DISPLAY_WIDTH, DISPLAY_HEIGHT));	// instellen van de resolutie van het schermobject dat we gaan aanmaken
			Display.create();												// aanmaken van een scherm object 
			Keyboard.create();												// aanmaken Keyboard object om keyboard te ontvangen
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
	
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT);										// scherm schoonmaken
	}
	
	public void update(){
		Display.update();													// het scherm updaten
	}
	
	public void stop(){
		Display.destroy();													// scherm obj destroyen
		Keyboard.destroy();													// keyboard obj destroyen
	}

}
