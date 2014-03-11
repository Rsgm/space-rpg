package spacegame.random;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class PlanetGen {
    public final ModelInstance model;

    public PlanetGen(NoiseGen noiseGen, int radius) {
        Texture t = noiseGen.getTexture(NoiseGen.TextureNoise.PLANET, 7);
        ModelBuilder builder = new ModelBuilder();

        model = new ModelInstance(builder.createSphere(radius * 2, radius * 2, radius * 2, 32, 16, new Material(TextureAttribute.createDiffuse(t)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates));
    }

    public ModelInstance getModel() {
        return model;
    }
}
