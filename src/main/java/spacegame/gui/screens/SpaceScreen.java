package spacegame.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import spacegame.game.GamePlay;
import spacegame.game.Planet;
import spacegame.game.Ship;
import spacegame.game.Station;
import spacegame.gui.SpaceInput;

import java.util.HashSet;
import java.util.Set;

public class SpaceScreen extends DefaultScreen {
    private final ModelBatch modelBatch;
    private final Environment environment;
    private final Set<ModelInstance> planets = new HashSet<ModelInstance>();
    private final Set<ModelInstance> stations = new HashSet<ModelInstance>();
    private final Set<ModelInstance> ships = new HashSet<ModelInstance>();

    private final GamePlay gamePlay;
    private final SpaceInput input;

    public SpaceScreen(Game game) {
        super(game);

        gamePlay = new GamePlay(this);

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.near = 0.1f;
        cam.far = 1000000f;
        cam.update();

        modelBatch = new ModelBatch();

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1f));
        environment.add(new DirectionalLight().set(.7f, .7f, 1, gamePlay.getPlayer().getShip().getPosition()));
        input = new SpaceInput(cam, gamePlay);
    }

    @Override
    public void show() {
        super.show();
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
//        System.out.println(1f / delta);

        input.update(); // this causes the camera to update twice, maybe remove the camera update from the super class method

        gamePlay.getPlayer().update((int) (delta * 1000));

        modelBatch.begin(cam);
        modelBatch.render(planets, environment);
        modelBatch.render(stations, environment);
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

    public void loadObject(Object o) {
        if (o instanceof Planet) {
            Planet p = (Planet) o;
            if (p.isLoaded) {
                return;
            }
            p.load();
            planets.add(p.getModelInstance());
        } else if (o instanceof Ship) {
            Ship s = (Ship) o;
            if (s.isLoaded) {
                return;
            }
            s.load();
            ships.add(s.getModelInstance());
        } else if (o instanceof Station) {
            Station s = (Station) o;
            if (s.isLoaded) {
                return;
            }
            s.load();
            stations.add(s.getModelInstance());
        }
    }

    public void disposeObject(Object o) {
        if (o instanceof Planet) {
            Planet p = (Planet) o;
            if (!p.isLoaded) {
                return;
            }
            p.dispose();
            planets.remove(p.getModelInstance());
        } else if (o instanceof Ship) {
            Ship s = (Ship) o;
            if (!s.isLoaded) {
                return;
            }
            s.dispose();
            ships.remove(s.getModelInstance());
        } else if (o instanceof Station) {
            Station s = (Station) o;
            if (!s.isLoaded) {
                return;
            }
            s.dispose();
            stations.remove(s.getModelInstance());
        }
    }

    public ModelBatch getModelBatch() {
        return modelBatch;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public Set<ModelInstance> getPlanets() {
        return planets;
    }

    public Set<ModelInstance> getShips() {
        return ships;
    }

    public GamePlay getGamePlay() {
        return gamePlay;
    }

    public Set<ModelInstance> getStations() {
        return stations;
    }
}
