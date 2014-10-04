package be.across.engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

public class Screen {
	private static Screen instance = null;
	private static int width;
	private static int height;
	private static String title;
	

	
	
	private Screen() {}
	
	public static void init(){
		init(width, height, title);
	}
	
	public synchronized Screen init(int width, int height, String title){
		this.width = width;
		this.height = height;
		this.title = title;
		
		if (instance == null) instance = new Screen();
		try {
			Display.setDisplayMode(new DisplayMode(width, height));			// instellen van de resolutie van het schermobject dat we gaan aanmaken
			Display.setTitle(title);
			Display.create();												// aanmaken van een scherm object 
		} catch (LWJGLException e) { e.printStackTrace(); }
		return instance;
	}
	
	public static Screen getInstance(){										// getting the same instances of the singleton
		if (instance == null) init();
		return instance;
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
		
		glEnableClientState(GL_VERTEX_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexId);
		glVertexPointer(VERTEX_SIZE, GL_FLOAT, 0, 0L);

		glEnableClientState(GL_COLOR_ARRAY);
		glBindBuffer(GL_ARRAY_BUFFER, vboColorId);
		glColorPointer(COLOR_SIZE, GL_FLOAT, 0, 0L);
		
		glDrawArrays(GL_QUADS, 0, AMOUNT_OF_VERTICES);
		
		glDisableClientState(GL_COLOR_ARRAY);
		glDisableClientState(GL_VERTEX_ARRAY);
		
	}
	
	public void update(){
		Display.update();													// het scherm updaten
	}
	
	public void stop(){
		Display.destroy();													// scherm obj destroyen
	}

}
