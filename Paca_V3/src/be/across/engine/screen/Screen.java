package be.across.engine.screen;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Screen {
	
	
	public Screen (){
		this(1024, 768);													// wrapper voor een default scherm resolutie
		
	}
	public Screen(int width, int height){
		try {
			Display.setDisplayMode(new DisplayMode(width, height));			// instellen van de resolutie van het schermobject dat we gaan aanmaken
			Display.create();												// aanmaken van een scherm object 
		} catch (LWJGLException e) { e.printStackTrace(); }
	}
	
	public void initGL(){
		glMatrixMode(GL_PROJECTION); 										// Switch matrix mode van Modelview (waarin we tekenen) naar Projection (de viewpoints)
		glLoadIdentity(); 													// de matrix clearen met de identity matrix
		glOrtho(0, 1024, 0, 768, 1, -1); 									// initializen van ons viewpoint ( 1, -1) is de Z-axis voor 2d 
		glMatrixMode(GL_MODELVIEW);											// terug switchen naar onze teken matrix
		
		glClearColor(0, 0, 0, 0); 											// de clear waarde RGB +alpha
		
		glDisable(GL_DEPTH_TEST);											// test voor 3d, niet nodig op het moment 		
		glEnable(GL_TEXTURE_2D);
		
	}
	
	public void render(){
		glClear(GL_COLOR_BUFFER_BIT);										// scherm schoonmaken
	}
	
	public void update(){
		Display.update();													// het scherm updaten
	}
	
	public void stop(){
		Display.destroy();													// scherm obj destroyen
	}

}
