package be.across.engine.draw;

import static org.lwjgl.opengl.GL11.*;

public class Draw {
	
	public static void rect(float x, float y, float width, float height)
	{
		rect(x, y, width, height, 0);
	}
	
	public static void rect(float x, float y, float width, float height, float rot){
		glPushMatrix(); 													// alles in de matrix op het moment ff op de stack gooien
		{
			glTranslatef(x, y, 0); 												// de matrix positioneren
			glRotatef(rot, 0, 0, 1); 											// de matrix roteren 
			
			glBegin(GL_QUADS); 													// beginnen met tekenen in QUADS (4 punten)
			{
				glVertex2f(0,0);												//punt tekenen op 0,0
				glVertex2f(0,height);
				glVertex2f(width, height);
				glVertex2f(width,0);
			}
			glEnd();
		}
		glPopMatrix(); 														// de matrix terug van de stack halen
	}

}
