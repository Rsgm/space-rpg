package spacegame.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import spacegame.random.NoiseGen;
import spacegame.random.ShipGen;

public class Ship implements Disposable {
    private final NoiseGen noiseGen = new NoiseGen();
    private Matrix4 transform;
    private ModelInstance modelInstance;

    public Vector3 position; // in meters
    public float velocity; // meters per second
    Vector3 direction;
    Vector3 up;

    public boolean isLoaded = false;

    public Ship(Vector3 pos) {
        position = new Vector3(pos);
        velocity = 0;
        direction = new Vector3(Vector3.X);
        up = new Vector3(Vector3.Y);

        transform = new Matrix4().rotate(Vector3.Y, direction).trn(position);
    }

    /**
     * @param deltaTime The change in time in milliseconds.
     */
    public void update(int deltaTime) {
        position.add(direction.cpy().scl((float) deltaTime / 1000f));
        transform = new Matrix4().rotate(Vector3.X, direction).rotate(direction.cpy().crs(Vector3.Y), up).trn(position);
//        modelInstance.transform = transform; // this should not be needed
    }

    /**
     * Loads all resources of this object.
     */
    public void load() {
        if (isLoaded) {
            return;
        }
        modelInstance = new ShipGen(noiseGen).getModel();
        modelInstance.transform = transform;
    }

    /**
     * Releases all resources of this object.
     */
    @Override
    public void dispose() {
        if (!isLoaded) {
            return;
        }
        modelInstance.model.dispose();

    }

    public void pitch(float angle) {
        Vector3 cross = direction.cpy().crs(up);

        direction.rotate(cross, angle);
        up.rotate(cross, angle);
    }

    public void roll(float angle) {
        up.rotate(direction, angle);
    }

    public void yaw(float angle) {
        direction.rotate(up, angle);
    }

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    public Vector3 getPosition() {
        return position.cpy();
    }
}
