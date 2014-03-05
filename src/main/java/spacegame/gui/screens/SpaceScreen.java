package spacegame.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.VertexAttribute;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import spacegame.gui.Assets;

public class SpaceScreen extends DefaultScreen {
    //        private final Model m;
    private final Mesh mesh;

    public SpaceScreen(Game game) {
        super(game);

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();

        ModelBatch batch = new ModelBatch();

        float[] verts = new float[24];
        int i = 0;

        verts[i++] = -1; // x1
        verts[i++] = -1; // y1
        verts[i++] = -2; // z1
        verts[i++] = 1; // color
        verts[i++] = 0; // color
        verts[i++] = 0; // color
        verts[i++] = 1; // color

        verts[i++] = 1f; // x2
        verts[i++] = -1; // y2
        verts[i++] = -2; // z2
        verts[i++] = .7f; // color
        verts[i++] = 0; // color
        verts[i++] = 0; // color
        verts[i++] = 1; // color

        verts[i++] = 1f; // x3
        verts[i++] = 1f; // y2
        verts[i++] = 2f; // z2
        verts[i++] = 1; // color
        verts[i++] = 1; // color
        verts[i++] = 1; // color
        verts[i++] = 1; // color

        verts[i++] = -1; // x4
        verts[i++] = 1f; // y4
        verts[i++] = 2f; // z4
        verts[i++] = 0; // color
        verts[i++] = .6f; // color
        verts[i++] = 1; // color
        verts[i++] = 1; // color

//        ModelMesh mesh = new ModelMesh();
//        mesh.vertices = verts;
//     ModelData modelData = new ModelData();
//        modelData.addMesh(mesh);
//        m = new Model(modelData);
//
//        new ModelInstance(m);

        mesh = new Mesh(true, 8, 0,  // static mesh with 4 vertices and no indices
                VertexAttribute.Position(), VertexAttribute.Color());

        mesh.setVertices(verts);

        // TODO read https://github.com/mattdesl/lwjgl-basics/wiki/LibGDX-Meshes
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new CameraInputController(cam));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        batch.setProjectionMatrix(cam.combined);
        batch.begin();
        mesh.render(Assets.shaders.get(Assets.Shader.GDX_DEFAULT), GL30.GL_TRIANGLE_FAN);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void hide() {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }
}
