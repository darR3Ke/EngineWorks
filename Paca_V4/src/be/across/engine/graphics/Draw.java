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

		float[] vertices = { // ondersste driehoek
								x, y + height, z, 1f, 				// ID: 0
								x, y, z, 1f,  						// ID: 1
								x + width, y, z, 1f,				// ID: 2
								x + width, y + height, z, 1f };		// ID: 4
		
		byte[] indices = { // driehoek onder
							0, 1, 2,
							//bovenste dreikhoek
							2, 3, 0	};
		
		float[] colors = { color.getR(), color.getG(), color.getB(), color.getA(), 
							color.getR(), color.getG(), color.getB(), color.getA(), 
							color.getR(), color.getG(), color.getB(), color.getA(), 
							color.getR(), color.getG(), color.getB(), color.getA(), };
		
		
		Screen screen = Screen.getInstance();
		screen.fillVertexBuffer(vertices, vertices.length);
		screen.fillIndicesBuffer(indices, indices.length);
		screen.fillColorBuffer(colors, colors.length);
		screen.drawBuffers();
			
	}

}
