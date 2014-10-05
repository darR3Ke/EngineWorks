package be.across.engine.graphics;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.PixelFormat;

public class Screen {
	private static final Screen instance = new Screen(); // Screen object aanmaken
	private FloatBuffer vertexBuffer;
	private FloatBuffer vBuffer;
	private ByteBuffer indicesBuffer;
	private FloatBuffer colorBuffer;
	private int vaoId = 0;
	private int vboId = 0;
	private int vboCId = 0;
	private int vboIId = 0;
	
	private int textureId;
	
	private int vsId = 0;
	private int fsId = 0;
	private int pId = 0;

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
			Display.create(new PixelFormat(), context.withProfileCore(true).withForwardCompatible(true)); // aanmaken van scherm obj
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		glViewport(0, 0, width, height);
	}

	public void initGL() {
		glClearColor(0f, 0f, 0f, 0f); // de clear waarde RGB + alpha
	}

	public void render() {

		glClear(GL_COLOR_BUFFER_BIT); // scherm schoonmaken
		
		glUseProgram(pId);
		
		glActiveTexture(GL_TEXTURE0);
		glBindTexture(GL_TEXTURE_2D, textureId);

		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIId);
		glDrawElements(GL_TRIANGLES, totalIndicesAmount, GL_UNSIGNED_BYTE, 0);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);

		glUseProgram(0);
	}

	public void update() {
		Display.update(false); // het scherm updaten
	}

	public void stop() {
		glUseProgram(0);
		glDetachShader(pId, vsId);
		glDetachShader(pId, fsId);
		
		glDeleteShader(vsId);
		glDeleteShader(fsId);
		glDeleteProgram(pId);
		
		glDisableVertexAttribArray(0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboId);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		glDeleteBuffers(vboIId);
		
		glBindVertexArray(0);
		glDeleteBuffers(vaoId);
		
		Display.destroy(); // scherm obj destroyen
	}

	
	public void fillVertexBuffer(float[] buffer, int amountOfVertices){
		vertexBuffer = BufferUtils.createFloatBuffer(amountOfVertices);
		vertexBuffer.put(buffer);
		vertexBuffer.flip();
	}
	
	public void fillColorBuffer(float[] buffer, int amountOfColors){
		colorBuffer = BufferUtils.createFloatBuffer(amountOfColors);
		colorBuffer.put(buffer);
		colorBuffer.flip();
	}
	
	public void fillBuffer(float[] buffer, int bufferSize){
		vBuffer = BufferUtils.createFloatBuffer(bufferSize);
		vBuffer.put(buffer);
		vBuffer.flip();
	}
	
	public void fillIndicesBuffer(byte[] buffer, int amountOfIndices){
		indicesBuffer = BufferUtils.createByteBuffer(amountOfIndices);
		indicesBuffer.put(buffer);
		indicesBuffer.flip();
		totalIndicesAmount = amountOfIndices;
	}
	
	public void setTexture(int textureId){
		this.textureId = textureId;
	}
	
	public void drawBuffers() {
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);

		vboId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboId);
		glBufferData(GL_ARRAY_BUFFER, vBuffer, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, (4*4)+(4*4)+(4*2), 0);		// teken punten 
		glVertexAttribPointer(1, 4, GL_FLOAT, false, (4*4)+(4*4)+(4*2), 0+(4*4));		// kleuren 
		glVertexAttribPointer(2, 2, GL_FLOAT, false, (4*4)+(4*4)+(4*2) ,0+(4*4)+(4*4));		// textures
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		vboIId = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vboIId);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indicesBuffer, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		//shader stuff
		vsId = this.loadShader("shaders/shader.vert", GL20.GL_VERTEX_SHADER);
		fsId = this.loadShader("shaders/shader.frag", GL20.GL_FRAGMENT_SHADER);
		
		pId = glCreateProgram();
		glAttachShader(pId, vsId);
		glAttachShader(pId, fsId);
		
		glBindAttribLocation(pId, 0, "in_Position");
		glBindAttribLocation(pId, 1, "in_Color");
		glBindAttribLocation(pId, 2, "in_TextureCoord");
		
		glLinkProgram(pId);
		glValidateProgram(pId);
	}
	
	private int loadShader(String filename, int type) {
		StringBuilder shaderSource = new StringBuilder();
		int shaderID = 0;
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String line;
			while ((line = reader.readLine()) != null) {
				shaderSource.append(line).append("\n");
			}
			reader.close();
		} catch (IOException e) {
			System.err.println("Could not read file.");
			e.printStackTrace();
			System.exit(-1);
		}
		
		shaderID = glCreateShader(type);
		glShaderSource(shaderID, shaderSource);
		glCompileShader(shaderID);
		
		return shaderID;
	}

}
