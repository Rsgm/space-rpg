package spacegame.random;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;
import java.util.List;

public class StationGen {
    public final ModelInstance model;
    private List<HullPiece> pieces = new ArrayList<HullPiece>();

    public StationGen(NoiseGen noiseGen) {
        pieces.add(new HullPiece(null));

        Texture t;
        ModelBuilder builder = new ModelBuilder();
        builder.begin();

        for (HullPiece piece : pieces) {
            Pixmap pixmap = noiseGen.getTexture(NoiseGen.TextureNoise.STATION, 7);
            t = new Texture(pixmap);
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            pixmap.dispose();

            Mesh hullMesh = genStationHullMesh(piece.size);
            hullMesh.transform(piece.transform);

            builder.part(null, hullMesh, GL30.GL_TRIANGLES, new Material(TextureAttribute.createDiffuse(t)));
        }

        model = new ModelInstance(builder.end());
    }

    private Mesh genStationHullMesh(Vector3 size) { // TODO read https://github.com/mattdesl/lwjgl-basics/wiki/LibGDX-Meshes
        MeshBuilder meshBuilder = new MeshBuilder();
        meshBuilder.begin(VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates,
                GL30.GL_TRIANGLES);

        meshBuilder.cylinder(size.x, size.y, size.z, 10, 0, 360, true);

//        Matrix4 mat = new Matrix4();
//        mat.rotate(Vector3.Y, 18);
//        mat.rotate(Vector3.X, -90);
//        mat.trn(0, size.y / 2, 0);
//        meshBuilder.sphere(mat, size.x, size.x, size.x, 5, 5, 0, 180, 0, 180);
//        mat.rotate(Vector3.X, 180);
//        mat.trn(0, -size.y, 0);
//        meshBuilder.sphere(mat, size.x, size.x, size.x, 5, 5, 0, 180, 0, 180);
        return meshBuilder.end();
    }

    private class HullPiece {
        int depth;
        PieceType type;
        Vector3 size = new Vector3();
        Vector3 pos = new Vector3();
        Vector3 rot = new Vector3(Vector3.Y);
        Matrix4 transform = new Matrix4();

        private HullPiece(HullPiece parentPiece) {
            if (parentPiece == null) {
                depth = 1;
                type = PieceType.HULL;
                rot = Vector3.Y;

                size.x = (int) (Math.random() * 5 + 10);
                size.y = (int) (Math.random() * 20 + 80);
                size.z = size.x;

                transform.trn(Vector3.Y);

                int children = (int) (Math.random() * 5 + 5);

                for (int i = 0; i < children; i++) {
                    pieces.add(new HullPiece(this));
                }
                return;
            }

            Vector3 parentRotation;
            type = PieceType.values()[(int) (Math.random() * PieceType.values().length)];
            depth = parentPiece.depth + 1;

            switch (type) {
                case HULL:
                    size.x = parentPiece.size.x; //(float) (Math.random() * (parentPiece.size.x / 2) + parentPiece.size.x / 2);
                    size.y = (int) (Math.random() * (parentPiece.size.y / 2) + parentPiece.size.y / 2);
                    size.z = size.x;

                    parentRotation = parentPiece.rot.cpy();
                    rot = new Vector3(parentRotation);
                    do {
                        rot.rotate(Vector3.X, ((int) (Math.random() * 4)) * 90);
                        rot.rotate(Vector3.Z, ((int) (Math.random() * 4)) * 90);
                        rot.rotate(Vector3.Y, ((int) (Math.random() * 4)) * 90); // do Y axis last
                        rot.nor();
                    }
                    while (!parentRotation.isPerpendicular(rot, .1f));

                    pos.add(parentPiece.pos);
                    pos.add(rot.cpy().scl(size.y / 2));
                    pos.add(parentRotation.cpy().scl((int) (Math.random() * parentPiece.size.y * .8 - parentPiece.size.y * .8 / 2)));

                    transform.rotate(Vector3.Y, rot);
                    transform.trn(pos);

                    int children = depth == 2 ? 2 : 0;// (int) (Math.random() * (size.len() / 8));
//                    children /= depth; // stops giant stations, although, that may be cool

                    for (int i = 0; i < children; i++) {
                        pieces.add(new HullPiece(this));
                    }
                    break;
                case BULGE:
                    size.x = (int) (Math.random() * parentPiece.size.x + parentPiece.size.x); //(float) (Math.random() * (parentPiece.size.x / 2) + parentPiece.size.x / 2);
                    size.y = (int) (Math.random() * size.x + size.x / 4);
                    size.z = size.x;

                    rot = new Vector3(parentPiece.rot);

                    pos.add(parentPiece.pos);
                    pos.add(rot.cpy().scl((int) (Math.random() * parentPiece.size.y * .8 - parentPiece.size.y * .8 / 2)));

                    transform.rotate(Vector3.Y, rot);
                    transform.trn(pos);

                    break;
//                case SOLAR_PANELS:
//
//                    break;
            }
        }

    }

    enum PieceType {
        HULL, BULGE//, SOLAR_PANELS
    }

    public ModelInstance getModel() {
        return model;
    }
}
