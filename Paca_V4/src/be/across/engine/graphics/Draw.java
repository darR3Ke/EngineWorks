package be.across.engine.graphics;

public class Draw {

	public static void rect(float x, float y, float z, float width, float height) {
		rect(x, y, 0, width, height, new Color4f(1, 1, 1, 1));
	}

	public static void rect(float x, float y, float z, float width, float height, Color4f color) {
		rect(x, y, 0, width, height, color, 0);
	}

	public static void rect(float x, float y, float z, float width, float height, float rot) {
		rect(x, y, 0, width, height, new Color4f(1, 1, 1, 1), rot);
	}

	public static void rect(float x, float y, float z, float width, float height, Color4f color, float rot) {

		byte[] indices = { // driehoek onder
							0, 1, 2,
							//bovenste dreikhoek
							2, 3, 0	};
		
								// teken punten							// kleur per punt										// aanhangpunten van textures
		float[] vertexColor = {x, y + height, z, 1f, 			color.getR(), color.getG(), color.getB(), color.getA(), 		0, 0,
								x, y, z, 1f,					color.getR(), color.getG(), color.getB(), color.getA(), 		0, 1,
								x + width, y, z, 1f,			color.getR(), color.getG(), color.getB(), color.getA(), 		1, 1, 
								x + width, y + height, z, 1f,	 color.getR(), color.getG(), color.getB(), color.getA(),		1, 0, 
		};
		
		Texture texture = new Texture("res/test.png");
		
		
		Screen screen = Screen.getInstance();
		screen.fillBuffer(vertexColor, vertexColor.length);
		screen.fillIndicesBuffer(indices, indices.length);
		screen.setTexture(texture.getTexture());
		screen.drawBuffers();
			
	}

}
