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
    public final ModelInstance model;

    private Vector3 hullOrigin;
    private Vector3 hullDiagonal;
    private int numberOfExtrusions;

    public ShipGen(NoiseGen noiseGen) {
        design();

        Texture t;
        ModelBuilder builder = new ModelBuilder();
        builder.begin();

        // hull
        t = noiseGen.getTexture(NoiseGen.TextureNoise.FRIGATE, 7);
        Mesh hullMesh = genShipHullMesh();
        builder.part(null, hullMesh, GL30.GL_TRIANGLES, new Material(TextureAttribute.createDiffuse(t)));

//        for (int i = 0; i < numberOfExtrusions; i++) {
//            t = noiseGen.getTexture(NoiseGen.TextureNoise.FRIGATE, 7);
//            Mesh extrusionMesh = genShipExtrusionMesh(hullOrigin);
//            builder.part(null, extrusionMesh, GL30.GL_TRIANGLES, new Material(TextureAttribute.createDiffuse(t)));
//        }

        model = new ModelInstance(builder.end());
    }

    private void design() {
        int hullWidthRange = 5;
        int hullWidthMin = 5;
        int hullHeightRange = 3;
        int hullHeightMin = 4;
        int hullDepthRange = 5;
        int hullDepthMin = 5;

        hullDiagonal = new Vector3();
        hullDiagonal.x = (float) (Math.random() * hullWidthRange + hullWidthMin);
        hullDiagonal.y = (float) (Math.random() * hullHeightRange + hullHeightMin);
        hullDiagonal.z = (float) (Math.random() * hullDepthRange + hullDepthMin);

        hullOrigin = new Vector3(hullDiagonal.x / 2, hullDiagonal.y / 2, hullDiagonal.z / 2);

        numberOfExtrusions = (int) (Math.random() * 10 + 30);
    }

    private Mesh genShipHullMesh() { // TODO read https://github.com/mattdesl/lwjgl-basics/wiki/LibGDX-Meshes
        MeshBuilder meshBuilder = new MeshBuilder();
        meshBuilder.begin(VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates,
                GL30.GL_TRIANGLES);

        meshBuilder.box(hullDiagonal.x, hullDiagonal.y, hullDiagonal.z);
        return meshBuilder.end();
    }

    private Mesh genShipExtrusionMesh(Vector3 origin) { // TODO read https://github.com/mattdesl/lwjgl-basics/wiki/LibGDX-Meshes
        MeshBuilder meshBuilder = new MeshBuilder();
        meshBuilder.begin(VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates,
                GL30.GL_TRIANGLES);

        Vector3 mountPoint = new Vector3();
        Vector3 diagonal = new Vector3();

        int side = (int) (Math.random() * 5);
        switch (side) {
            case 0: // back
                diagonal.x = (float) (Math.random() * hullDiagonal.x / 2);
                diagonal.y = (float) (Math.random() * hullDiagonal.y);
                diagonal.z = (float) (Math.random() * hullDiagonal.z);
                diagonal.scl(4 / diagonal.len()); // makes sure that there are no tiny or huge pieces

                mountPoint.x = (float) (Math.random() * hullDiagonal.x / 2);
                mountPoint.y = (float) (Math.random() * hullDiagonal.y);
                mountPoint.z = 0;

                mountPoint.add(0, 0, -diagonal.z / 2);
                mountPoint.add(0, -origin.y, -origin.z);
                break;
            case 1: // left (from front
                diagonal.x = (float) (Math.random() * hullDiagonal.x / 2);
                diagonal.y = (float) (Math.random() * hullDiagonal.y);
                diagonal.z = (float) (Math.random() * hullDiagonal.z);
                diagonal.scl(7 / diagonal.len());

                mountPoint.x = 0;
                mountPoint.y = (float) (Math.random() * hullDiagonal.y);
                mountPoint.z = (float) (Math.random() * hullDiagonal.z);

                mountPoint.add(-diagonal.x / 2, 0, 0);
                mountPoint.add(-origin.x, -origin.y, -origin.z);
                break;
            case 2: // top
                diagonal.x = (float) (Math.random() * hullDiagonal.x / 2);
                diagonal.y = (float) (Math.random() * hullDiagonal.y);
                diagonal.z = (float) (Math.random() * hullDiagonal.z);
                diagonal.scl(7 / diagonal.len());

                mountPoint.x = (float) (Math.random() * hullDiagonal.x / 2);
                mountPoint.y = 0;
                mountPoint.z = (float) (Math.random() * hullDiagonal.z);

                mountPoint.add(0, diagonal.y / 2, 0);
                mountPoint.add(0, origin.y, -origin.z);
                break;
            case 3: // bottom
                diagonal.x = (float) (Math.random() * hullDiagonal.x / 2);
                diagonal.y = (float) (Math.random() * hullDiagonal.y);
                diagonal.z = (float) (Math.random() * hullDiagonal.z);
                diagonal.scl(7 / diagonal.len());

                mountPoint.x = (float) (Math.random() * hullDiagonal.x / 2);
                mountPoint.y = 0;
                mountPoint.z = (float) (Math.random() * hullDiagonal.z);

                mountPoint.add(0, -diagonal.y / 2, 0);
                mountPoint.add(0, -origin.y, -origin.z);
                break;
            case 4: // front
                diagonal.x = (float) (Math.random() * hullDiagonal.x / 2);
                diagonal.y = (float) (Math.random() * hullDiagonal.y);
                diagonal.z = (float) (Math.random() * hullDiagonal.z);
                diagonal.scl(4 / diagonal.len()); // makes sure that there are no tiny or huge pieces

                mountPoint.x = (float) (Math.random() * hullDiagonal.x / 2);
                mountPoint.y = (float) (Math.random() * hullDiagonal.y);
                mountPoint.z = 0;

                mountPoint.add(0, 0, diagonal.z / 2);
                mountPoint.add(0, -origin.y, origin.z);
                break;
        }

        meshBuilder.box(mountPoint.x, mountPoint.y, mountPoint.z, diagonal.x, diagonal.y, diagonal.z);
        meshBuilder.box(-mountPoint.x, mountPoint.y, mountPoint.z, diagonal.x, diagonal.y, diagonal.z);


        return meshBuilder.end();
    }

    public ModelInstance getModel() {
        return model;
    }
}
