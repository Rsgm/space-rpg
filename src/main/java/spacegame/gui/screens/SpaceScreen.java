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
        cam.position.set(10f, 0f, 0f);
        cam.lookAt(0, 0, 0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();


        ModelBatch batch = new ModelBatch();

        float[] verts = new float[3 * 3 * 3];
        int i = 0;

        verts[i++] = -4; // x
        verts[i++] = 0; // y
        verts[i++] = 0; // z

        verts[i++] = 4; // x
        verts[i++] = 0; // y
        verts[i++] = 0; // z

        verts[i++] = 0f; // x
        verts[i++] = 0f; // y
        verts[i++] = 4f; // z

        verts[i++] = 0; // x
        verts[i++] = 4; // y
        verts[i++] = 0; // z

        verts[i++] = 4; // x
        verts[i++] = 0; // y
        verts[i++] = 0; // z

        verts[i++] = 0f; // x
        verts[i++] = 0f; // y
        verts[i++] = 4f; // z

        verts[i++] = 0; // x
        verts[i++] = 4; // y
        verts[i++] = 0; // z

        verts[i++] = -4; // x
        verts[i++] = 0; // y
        verts[i++] = 0; // z

        verts[i++] = 0f; // x
        verts[i++] = 0f; // y
        verts[i++] = 4f; // z

//        ModelMesh mesh = new ModelMesh();
//        mesh.vertices = verts;
//     ModelData modelData = new ModelData();
//        modelData.addMesh(mesh);
//        m = new Model(modelData);
//
//        new ModelInstance(m);

        mesh = new Mesh(true, 9, 0,  // static mesh with 4 vertices and no indices
                VertexAttribute.Position());

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
        mesh.render(Assets.shaders.get(Assets.Shader.GDX_DEFAULT), GL30.GL_TRIANGLES);
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
