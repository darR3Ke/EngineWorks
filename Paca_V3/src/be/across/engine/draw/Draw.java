package be.across.engine.draw;

import static org.lwjgl.opengl.GL11.*;

import java.io.IOException;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;


public class Draw {
	
	private static Texture texture;
	
	public static void rect(float x, float y, float width, float height) {
		rect(x, y, width, height, new Color4f(1, 1, 1, 0));
	}
	
	public static void rect(float x, float y, float width, float height, Color4f kleur) {
		rect(x, y, width, height, kleur, 0);
	}
	
	public static void rect(float x, float y, float width, float height, float rot) {
		rect(x, y, width, height, new Color4f(1, 1, 1, 0) , rot);
	}
	
	public static void rect(float x, float y, float width, float height, Color4f kleur, float rot){
		glPushMatrix(); 													// alles in de matrix op het moment ff op de stack gooien
		{
			glTranslatef(x, y, 0); 												// de matrix positioneren
			glRotatef(rot, 0, 0, 1); 											// de matrix roteren
			glColor4f(kleur.getR(), kleur.getG(), kleur.getB(), kleur.getA());
			
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

	public static void tex(float x, float y, float width, float height, String texName ) {
		tex(x, y, width, height, texName, new Color4f(1, 1, 1, 0), 0);
	}
	
	public static void tex(float x, float y, float width, float height, String texName , Color4f kleur, float rot) {
		
		try {
			texture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/" + texName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		texture.bind();
		
		glPushMatrix();
		{
			glTranslatef(x, y, 0);
			glRotatef(rot, 0, 0, 1);
			glColor4f(kleur.getR(), kleur.getG(), kleur.getB(), kleur.getA());
			
			glBegin(GL_QUADS);
			{
				
				glTexCoord2f(1, 1);
				glVertex2f(0, 0);	

				glTexCoord2f(1, 0);
				glVertex2f(0,height);

				
				glTexCoord2f(0, 0);
				glVertex2f(width, height);
				
				glTexCoord2f(0, 1);
				glVertex2f(width,0);
				
			}
			glEnd();
		}
		glPopMatrix();
	}

}
