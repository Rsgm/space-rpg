package spacegame.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import spacegame.random.ShipGen;

import java.util.ArrayList;
import java.util.List;

public class SpaceScreen extends DefaultScreen {
    private final ModelBatch modelBatch;
    private final Environment environment;
    private final List<ModelInstance> bodies = new ArrayList<ModelInstance>();
    private final List<ModelInstance> ships = new ArrayList<ModelInstance>();

    public SpaceScreen(Game game) {
        super(game);

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 0f, 0f);
        cam.lookAt(0, 0, 0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();

        modelBatch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        PointLight light = new PointLight();
        //		light.set(.5f, 0, 1, 10, 10, 10, 1);
        //		environment.add(light);
        environment.add(new DirectionalLight().set(0f, 0f, 0.8f, -1f, -0.8f, -0.2f));

        ModelInstance ship = new ShipGen().getShip();
        ColorAttribute attr = ColorAttribute.createAmbient(.4f, .3f, .9f, 1);
        ships.add(ship);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new CameraInputController(cam));
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        modelBatch.begin(cam);
        modelBatch.render(bodies);

        modelBatch.render(ships);
        modelBatch.end();
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
