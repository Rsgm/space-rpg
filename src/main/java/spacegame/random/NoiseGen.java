package spacegame.random;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.*;
import libnoiseforjava.util.*;

import java.util.Random;

public class NoiseGen {
    private static final int[] NOISE_GEN_SIZE = new int[]{2000, 1500, 1000, 750, 500, 250, 100, 50};
    private static final Random RANDOM = new Random();
    private final int seed = RANDOM.nextInt();

    public Texture getTexture(TextureNoise type, int resolution) {
        try {
            ModuleBase noise = null;
            NoiseMapBuilder builder = null;
            RendererImage renderer = new RendererImage();
            int width = 0;
            int height = 0;

            int size = 100;//NOISE_GEN_SIZE[resolution];
            switch (type) {
                case FRIGATE:
                    noise = frigate();

                    builder = new NoiseMapBuilderPlane();
                    ((NoiseMapBuilderPlane) builder).setBounds(-1, 1, -1, 1);
                    ((NoiseMapBuilderPlane) builder).enableSeamless(true);
                    width = size * 2;
                    height = size;

                    renderer.clearGradient();
                    renderer.addGradientPoint(-1, new ColorCafe(40, 40, 40, 255));
                    renderer.addGradientPoint(0, new ColorCafe(120, 120, 120, 255));
                    renderer.addGradientPoint(1, new ColorCafe(200, 200, 200, 255));
                    break;
                case SCOUT:
                    break;
                case DESTROYER:
                    break;
                case CRUISER:
                    break;
                case BATTLESHIP:
                    break;
                case PLANET:
                    noise = terrain();

                    builder = new NoiseMapBuilderSphere();
                    ((NoiseMapBuilderSphere) builder).setBounds(-90, 90, -180, 180);
                    width = size * 2;
                    height = size;

                    renderer.clearGradient();
                    renderer.addGradientPoint(0, new ColorCafe(32, 128, 255, 255));
                    renderer.addGradientPoint(.25, new ColorCafe(240, 240, 88, 255));
                    renderer.addGradientPoint(.5, new ColorCafe(0, 140, 0, 255));
                    renderer.addGradientPoint(.75, new ColorCafe(120, 120, 120, 255));
                    renderer.addGradientPoint(1, new ColorCafe(234, 234, 234, 255));
                    break;
                case STATION:
                    noise = frigate();

                    builder = new NoiseMapBuilderPlane();
                    ((NoiseMapBuilderPlane) builder).setBounds(-1, 1, -1, 1);
                    ((NoiseMapBuilderPlane) builder).enableSeamless(true);
                    width = size * 2;
                    height = size;

                    renderer.clearGradient();
                    renderer.addGradientPoint(0, new ColorCafe(40, 40, 40, 255));
                    renderer.addGradientPoint(1, new ColorCafe(120, 120, 120, 255));
                    break;
            }

            if (noise == null || builder == null) {
                return null;
            }

            builder.setSourceModule(noise);
            builder.setDestSize(width, height);
            builder.setDestNoiseMap(new NoiseMap(width, height));
            builder.build();

            Pixmap pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
            NoiseMap noiseMap = builder.getDestNoiseMap();
            ImageCafe imageCafe = new ImageCafe(width, height);
            renderer.setSourceNoiseMap(noiseMap);
            renderer.setDestImage(imageCafe);
            renderer.render();

            for (int y = 0; y < size; y++) {
                for (int x = 0; x < width; x++) { // maybe use separate threads to get the values and set them to arrays. e.g. float[250][5000] ten times
                    ColorCafe color = imageCafe.getValue(x, y);
                    int c = Color.rgba8888((float) color.getRed() / 255f, (float) color.getGreen() / 255f, (float) color.getBlue() / 255f, (float) color.getAlpha() / 255f);
                    pixmap.drawPixel(x, y, c);
                }
            }

            Texture texture = new Texture(pixmap);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            pixmap.dispose();
            return texture;
        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }
        return null;
    }

    private ModuleBase frigate() {
        try {
            Perlin perlin;

            perlin = new Perlin();
            perlin.setNoiseQuality(libnoiseforjava.NoiseGen.NoiseQuality.QUALITY_BEST);
            perlin.setSeed(seed);
            perlin.setFrequency(1);
            perlin.setPersistence(.45);
            perlin.setLacunarity(2.5);
            perlin.setOctaveCount(6);


            Select select = new Select(new Const(-1), new Const(1), new Abs(perlin));
            select.setBounds(.5, 1);

            Clamp clamp = new Clamp(select);
            clamp.setBounds(-1, 1);
            return clamp;
        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }
        return null;
    }

    private ModuleBase terrain() {// modified version of http://libnoise.sourceforge.net/tutorials/tutorial5.html
        try {
            Perlin land1;
            Billow land2;
            RidgedMulti mountainTerrain;
            Billow baseFlatTerrain;
            ScaleBias flatTerrain;
            Perlin terrainType;
            ScaleBias terrainTypeScaleBias;
            Select finalTerrain;
            Select landSelector;

            land1 = new Perlin();
            land1.setNoiseQuality(libnoiseforjava.NoiseGen.NoiseQuality.QUALITY_BEST);
            land1.setSeed(seed + 1);
            land1.setFrequency(2);
            land1.setPersistence(.45);
            land1.setLacunarity(2.5);
            land1.setOctaveCount(6);

            land2 = new Billow();
            land2.setNoiseQuality(libnoiseforjava.NoiseGen.NoiseQuality.QUALITY_BEST);
            land2.setSeed(seed + 2);
            land2.setFrequency(3);
            land2.setPersistence(.45);
            land2.setLacunarity(2.5);
            land2.setOctaveCount(6);

            ScaleBias scale = new ScaleBias(new Add(land1, land2));
            scale.setScale(.5);
            scale.setBias(0);

            Clamp land = new Clamp(scale);
            land.setBounds(-1, 1);


            mountainTerrain = new RidgedMulti();
            mountainTerrain.setNoiseQuality(libnoiseforjava.NoiseGen.NoiseQuality.QUALITY_BEST);
            mountainTerrain.setSeed(seed + 3);
            mountainTerrain.setFrequency(25);

            ScaleBias mountainScale = new ScaleBias(mountainTerrain);
            mountainScale.setScale(.25);
            mountainScale.setBias(.65);

            baseFlatTerrain = new Billow();
            baseFlatTerrain.setNoiseQuality(libnoiseforjava.NoiseGen.NoiseQuality.QUALITY_BEST);
            baseFlatTerrain.setSeed(seed + 4);
            baseFlatTerrain.setFrequency(15);

            flatTerrain = new ScaleBias(baseFlatTerrain);
            flatTerrain.setScale(0.125);
            flatTerrain.setBias(.5);

            terrainType = new Perlin();
            terrainType.setNoiseQuality(libnoiseforjava.NoiseGen.NoiseQuality.QUALITY_BEST);
            terrainType.setSeed(seed + 5);
            terrainType.setFrequency(4);
            terrainType.setPersistence(0.25);

            terrainTypeScaleBias = new ScaleBias(terrainType);
            terrainTypeScaleBias.setScale(.3);
            terrainTypeScaleBias.setBias(.6);

            finalTerrain = new Select(flatTerrain, mountainScale, terrainTypeScaleBias);
            finalTerrain.setBounds(.7, 10);
            finalTerrain.setEdgeFalloff(.05);

            landSelector = new Select(new Const(0), finalTerrain, land);
            landSelector.setBounds(0, 10); // -0.3, 1
            landSelector.setEdgeFalloff(.2);

            Clamp clamp = new Clamp(landSelector);
            clamp.setBounds(0, 1);
            return clamp;
        } catch (ExceptionInvalidParam exceptionInvalidParam) {
            exceptionInvalidParam.printStackTrace();
        }
        return null;
    }

    public enum TextureNoise {
        SCOUT, DESTROYER, CRUISER, BATTLESHIP, PLANET, STATION, FRIGATE,
    }
}
