package be.across.engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

public class Screen {
	private static final Screen instance = new Screen(); // Screen object aanmaken
	private FloatBuffer vertexBuffer;
	private ByteBuffer indicesBuffer;
	private int vaoId;
	private int vboId;
	private int vboEId;


	private static final int VERTEX_SIZE = 3;
	private static int totalVertexAmount;
	private static int totalIndicesAmount;

	private Screen() { // private constructor voor singleton
	}

	public static Screen getInstance() { // Screen object terug geven
		return instance;
	}

	public synchronized void init(int width, int height, String title) {
		try {
			Display.setDisplayMode(new DisplayMode(width, height)); // resolutie zetten van scherm obj
			Display.setTitle(title); // titel zetten van scherm obj
			ContextAttribs context = new ContextAttribs(3, 3); // context van GL libraries zetten naar openGL API 3.3
			Display.create(new PixelFormat(), context.withProfileCore(true)); // aanmaken van scherm obj
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public void initGL() {
		glClearColor(0, 0, 0, 0); // de clear waarde RGB + alpha
	}

	public void render() {

	//	glClear(GL_COLOR_BUFFER_BIT); // scherm schoonmaken

		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboEId);
		glDrawElements(GL_TRIANGLES, totalIndicesAmount, GL_UNSIGNED_BYTE, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);

	}

	public void update() {
		Display.update(); // het scherm updaten
	}

	public void stop() {
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboId);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboEId);
		
		glBindVertexArray(0);
		glDeleteBuffers(vaoId);
		
		Display.destroy(); // scherm obj destroyen
	}

	
	public void fillVertexBuffer(float[] buffer, int amountOfVertices){
		vertexBuffer = BufferUtils.createFloatBuffer(amountOfVertices * VERTEX_SIZE);
		vertexBuffer.put(buffer);
		vertexBuffer.flip();
		totalVertexAmount = amountOfVertices;
	}
	
	public void fillIndicesBuffer(byte[] buffer, int amountOfIndices){
		indicesBuffer = BufferUtils.createByteBuffer(amountOfIndices);
		indicesBuffer.put(buffer);
		indicesBuffer.flip();
		totalIndicesAmount = amountOfIndices;
	}
	
	public void drawBuffers() {
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);

		vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, vertexBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
		vboEId = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboEId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
	}

}
