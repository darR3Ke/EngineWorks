package be.across.engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import java.io.IOException;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
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
//		glPushMatrix(); 													// alles in de matrix op het moment ff op de stack gooien
		{
			glColor4f(kleur.getR(), kleur.getG(), kleur.getB(), kleur.getA());
			
			FloatBuffer vertexBuffer = BufferUtils.createFloatBuffer(AMOUNT_OF_VERTICES * VERTEX_SIZE);
			vertexBuffer.put(new float[]{x, y, x, y+height, x+width, y+height, x+width, y});
			vertexBuffer.flip();
			
			FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(AMOUNT_OF_VERTICES * COLOR_SIZE);
			colorBuffer.put(new float[]{kleur.getR(), kleur.getG(), kleur.getB(), 		kleur.getR(), kleur.getG(), kleur.getB(),
										kleur.getR(), kleur.getG(), kleur.getB(), 		kleur.getR(), kleur.getG(), kleur.getB()    });
			colorBuffer.flip();
			
			int vboVertexId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vboVertexId);
			glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			
			int vboColorId = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vboColorId);
			glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			

		}
//		glPopMatrix(); 														// de matrix terug van de stack halen
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
