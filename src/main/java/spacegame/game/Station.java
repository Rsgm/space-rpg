package spacegame.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import spacegame.random.NoiseGen;
import spacegame.random.StationGen;

public class Station implements Disposable {
    private final NoiseGen noiseGen = new NoiseGen();
    private final Matrix4 transform;
    private ModelInstance modelInstance;

    final Vector3 position; // in meters
    final Vector3 rotation;

    public boolean isLoaded = false;

    public Station(Vector3 pos) {
        position = pos;

        rotation = new Vector3(Vector3.Y);
        rotation.rotate(Vector3.X, (int) (Math.random() * 360));
        rotation.rotate(Vector3.Y, (int) (Math.random() * 360));
        rotation.rotate(Vector3.Z, (int) (Math.random() * 360));

        transform = new Matrix4().rotate(Vector3.Y, rotation).trn(pos);
    }

    /**
     * Loads all resources of this object.
     */
    public void load() {
        if (isLoaded) {
            return;
        }
        modelInstance = new StationGen(noiseGen).getModel();
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

    public ModelInstance getModelInstance() {
        return modelInstance;
    }

    public Vector3 getPosition() {
        return position.cpy();
    }

    public Vector3 getRotation() {
        return rotation.cpy();
    }
}
