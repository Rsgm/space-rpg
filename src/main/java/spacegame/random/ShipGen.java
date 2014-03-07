package spacegame.random;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class ShipGen {
	public final ModelInstance ship;

	public ShipGen() {
		Texture t = NoiseGen.getTexture(NoiseGen.TextureNoise.BATTLESHIP);
		ModelBuilder builder = new ModelBuilder();
		builder.begin();

		// hull
		builder.part(null, genShipHullVerts(), GL30.GL_TRIANGLE_STRIP, new Material(TextureAttribute.createDiffuse(t)));

		ship = new ModelInstance(builder.end());
	}

	public Mesh genShipHullVerts() { // TODO read https://github.com/mattdesl/lwjgl-basics/wiki/LibGDX-Meshes
		float[] verts = new float[]{-4, 0, 0, 0, 0, // these comments are just to keep the line breaks
				4, 0, 0, 0, 0,   //
				0, 0, 4, 1, 0, //
				0, 4, 0, 0, 1};

		Mesh mesh = new Mesh(true, 12, 12, VertexAttribute.Position(), //
				new VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + "0"));
		mesh.setVertices(verts);
		mesh.setIndices(new short[]{0, 1, 2, 0, 1, 3, 0, 2, 3, 1, 2, 3});
		return mesh;
	}

	public ModelInstance getShip() {
		return ship;
	}
}
