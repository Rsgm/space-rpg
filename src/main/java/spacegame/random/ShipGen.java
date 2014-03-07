package spacegame.random;

import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

public class ShipGen {
    public final ModelInstance ship;

    public ShipGen() {
        Texture t = NoiseGen.getTexture(NoiseGen.TextureNoise.BATTLESHIP);
        ModelBuilder builder = new ModelBuilder();
        builder.begin();

        // hull
        final Mesh mesh = genShipHullMesh();
        builder.part(null, mesh, GL30.GL_TRIANGLES, new Material(TextureAttribute.createDiffuse(t)));

        ship = new ModelInstance(builder.end());
    }

    private Mesh genShipHullMesh() { // TODO read https://github.com/mattdesl/lwjgl-basics/wiki/LibGDX-Meshes


        MeshBuilder meshBuilder = new MeshBuilder();
        meshBuilder.begin(VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates,
                GL30.GL_TRIANGLES);

        meshBuilder.box(5, 5, 5);


        return meshBuilder.end();
    }

    /**
     * This returns a (hopefully)unit vector with a random direction snapped to 30 degree intervals.
     */
    private Vector3 randomDirection() {
        // spherical coords, r = 1
        int theta = ((int) (Math.random() * 12)) * 30;
        int phi = ((int) (Math.random() * 12)) * 30;

        Vector3 v = new Vector3();
        // convert to rectangular coords
        v.x = (float) (Math.cos(theta) * Math.sin(phi));
        v.y = (float) (Math.sin(theta) * Math.sin(phi));
        v.z = (float) (Math.cos(phi));
        return v;
    }

    public ModelInstance getShip() {
        return ship;
    }
}
