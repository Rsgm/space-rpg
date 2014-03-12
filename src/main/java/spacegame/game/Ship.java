package spacegame.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import spacegame.random.NoiseGen;
import spacegame.random.ShipGen;

public class Ship implements Disposable {
    private final NoiseGen noiseGen = new NoiseGen();
    private Matrix4 transform;
    private ModelInstance modelInstance;

    private final Vector3 position;
    private final Vector3 deltaPosition;
    private final Vector3 direction;
    public float velocity; // meters per second

    public float yaw;
    public float pitch;

    public float roll;
    public boolean isLoaded = false;

    public Ship(Vector3 pos) {
        position = pos;
        deltaPosition = new Vector3();
        direction = new Vector3(Vector3.Z);
        velocity = 2f;

        yaw = 0;
        pitch = 0;
        roll = 0;

        transform = new Matrix4().rotate(new Quaternion()).setFromEulerAngles(yaw, pitch, roll).trn(position);
    }

    /**
     * @param deltaTime The change in time in milliseconds.
     */
    public void update(int deltaTime) {
        Quaternion rotation = transform.getRotation(new Quaternion());

        direction.set(Vector3.Z);
        direction.set(rotation.transform(direction));

        deltaPosition.set(direction.cpy().scl(velocity * (float) deltaTime / 1000f));
        position.add(deltaTime);

        transform.rotate(new Quaternion().setEulerAngles(yaw, pitch, roll)).trn(deltaPosition);
        modelInstance.transform = transform;

        yaw = 0;
        pitch = 0;
        roll = 0;
    }

    /**
     * Loads all resources of this object.
     */
    public void load() {
        if (isLoaded) {
            return;
        }
        modelInstance = new ShipGen(noiseGen).getModel();
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

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    public Vector3 getPosition() {
        return position.cpy();
    }

    public Vector3 getDeltaPosition() {
        return deltaPosition;
    }

    public Vector3 getDirection() {
        return direction;
    }
}
