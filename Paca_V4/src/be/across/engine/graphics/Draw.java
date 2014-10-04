package be.across.engine.graphics;

public class Draw {

	public static void rect(float x, float y, float z, float width, float height) {
		rect(x, y, 0, width, height, new Color4f(1, 1, 1, 1));
	}

	public static void rect(float x, float y, float z, float width, float height, Color4f kleur) {
		rect(x, y, 0, width, height, kleur, 0);
	}

	public static void rect(float x, float y, float z, float width, float height, float rot) {
		rect(x, y, 0, width, height, new Color4f(1, 1, 1, 1), rot);
	}

	public static void rect(float x, float y, float z, float width, float height, Color4f kleur, float rot) {
		final int AMOUNT_OF_VERTICES = 4;
		final int AMOUNT_OF_INDICES= 6;

		float[] vertices = { // ondersste driehoek
								x, y + height, z, 			// ID: 0
								x, y, z,  					// ID: 1
								x + width, y, z,			// ID: 2
								x + width, y + height, z };	//ID: 4
		
		byte[] indices = { // driehoek onder
							0, 1, 2,
							//bovenste dreikhoek
							2, 3, 0	};
		
		
		
		Screen screen = Screen.getInstance();
		screen.fillVertexBuffer(vertices, AMOUNT_OF_VERTICES);
		screen.fillIndicesBuffer(indices, AMOUNT_OF_INDICES);
		screen.drawBuffers();
			
	}

}
