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
import spacegame.random.BattleshipGen;

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
        cam.position.set(0f, 0f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 0.1f;
        cam.far = 300f;
        cam.update();

        modelBatch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));
        environment.add(new PointLight().set(1, 0, .3f, 10, 10, 10, 10));

        ModelInstance ship = new BattleshipGen().getShip();
        ships.add(ship);
    }

    @Override
    public void show() {
        CameraInputController input = new CameraInputController(cam);
        input.zoom(-15f);
        Gdx.input.setInputProcessor(input);

    }

    @Override
    public void render(float delta) {
        super.render(delta);

        modelBatch.begin(cam);
        modelBatch.render(bodies, environment);

        modelBatch.render(ships, environment);
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
