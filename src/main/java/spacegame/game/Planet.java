package spacegame.game;

import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;
import spacegame.random.NoiseGen;
import spacegame.random.PlanetGen;

public class Planet implements Disposable { // TODO more planet types
    private final NoiseGen noiseGen = new NoiseGen();
    private final Matrix4 transform;
    private ModelInstance modelInstance;

    final int radius;

    public boolean isLoaded = false;

    public Planet(Vector3 pos) {
        Vector3 rot = new Vector3(Vector3.Y);
        rot.rotate(Math.random() >= 0.5 ? Vector3.X : Vector3.Z, (int) (Math.random() * 90 - 45));
        transform = new Matrix4().rotate(Vector3.Y, rot).trn(pos);

        radius = (int) (Math.random() * 200 + 1000);
    }

    /**
     * Loads all resources of this object.
     */
    public void load() {
        if (isLoaded) {
            return;
        }
        modelInstance = new PlanetGen(noiseGen, radius).getModel();
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
}
