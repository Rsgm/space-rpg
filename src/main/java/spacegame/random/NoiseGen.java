package spacegame.random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import libnoiseforjava.exception.ExceptionInvalidParam;
import libnoiseforjava.module.*;
import libnoiseforjava.util.ColorCafe;
import libnoiseforjava.util.ImageCafe;
import libnoiseforjava.util.NoiseMap;
import libnoiseforjava.util.RendererImage;

public class NoiseGen {
	private static final double NOISE_GENERATION_SIZE = 64;

	public static Texture getTexture(TextureNoise type) {
		try {
			ModuleBase noise = null;
			RendererImage renderer = new RendererImage();

			switch(type) {
				case SCOUT:
					break;
				case DESTROYER:
					break;
				case CRUISER:
					break;
				case BATTLESHIP:
					noise = battleShip();

					renderer.clearGradient();
					renderer.addGradientPoint(0, new ColorCafe(50, 50, 50, 255));
					renderer.addGradientPoint(1, new ColorCafe(150, 150, 150, 255));
					break;
				case PLANET:
					noise = terrain();

					renderer.clearGradient();
					renderer.addGradientPoint(-1, new ColorCafe(20, 20, 50, 255));
					renderer.addGradientPoint(1, new ColorCafe(180, 180, 180, 255));
					break;
			}

			if(noise == null) {
				return null;
			}

			double[][] noiseArray = new double[(int) NOISE_GENERATION_SIZE][(int) NOISE_GENERATION_SIZE];
			for(int y = 0; y < NOISE_GENERATION_SIZE; y++) {
				for(int x = 0; x < NOISE_GENERATION_SIZE; x++) { // maybe use separate threads to get the values and set them to arrays. e.g. float[250][5000] ten times
					float f = (float) noise.getValue((x - NOISE_GENERATION_SIZE / 2) / NOISE_GENERATION_SIZE, 0, (y - NOISE_GENERATION_SIZE / 2) / NOISE_GENERATION_SIZE);
					noiseArray[x][((int) (NOISE_GENERATION_SIZE - y - 1))] = f;
				}
				Gdx.app.debug("Filling Noise Array", (float) y / NOISE_GENERATION_SIZE * 100 + "% done");
			}

			Pixmap pixmap = new Pixmap((int) NOISE_GENERATION_SIZE, (int) NOISE_GENERATION_SIZE, Pixmap.Format.RGBA8888);
			NoiseMap noiseMap = new NoiseMap((int) NOISE_GENERATION_SIZE, (int) NOISE_GENERATION_SIZE);
			noiseMap.setNoiseMap(noiseArray);
			ImageCafe imageCafe = new ImageCafe((int) NOISE_GENERATION_SIZE, (int) NOISE_GENERATION_SIZE);
			renderer.setSourceNoiseMap(noiseMap);
			renderer.setDestImage(imageCafe);
			renderer.render();

			for(int y = 0; y < NOISE_GENERATION_SIZE; y++) {
				for(int x = 0; x < NOISE_GENERATION_SIZE; x++) { // maybe use separate threads to get the values and set them to arrays. e.g. float[250][5000] ten times
					ColorCafe color = imageCafe.getValue(x, y);
					int c = Color.rgba8888((float) color.getRed() / 255f, (float) color.getGreen() / 255f, (float) color.getBlue() / 255f, (float) color.getAlpha() / 255f);
					pixmap.drawPixel(x, y, c);
				}
				Gdx.app.debug("Filling Pixmap", (float) y / NOISE_GENERATION_SIZE * 100 + "% done");
			}

			Texture texture = new Texture(pixmap);
			texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
			pixmap.dispose();
			return texture;
		} catch(ExceptionInvalidParam exceptionInvalidParam) {
			exceptionInvalidParam.printStackTrace();
		}
		return null;
	}

	private static ModuleBase battleShip() {
		try {
			Perlin perlin;

			perlin = new Perlin();
			perlin.setNoiseQuality(libnoiseforjava.NoiseGen.NoiseQuality.QUALITY_BEST);
			perlin.setSeed((int) (Math.random() * Integer.MAX_VALUE));
			perlin.setFrequency(1);
			perlin.setPersistence(.45);
			perlin.setLacunarity(2.5);
			perlin.setOctaveCount(6);

			Clamp clamp = new Clamp(perlin);
			clamp.setBounds(-1, 1);
			return clamp;
		} catch(ExceptionInvalidParam exceptionInvalidParam) {
			exceptionInvalidParam.printStackTrace();
		}
		return null;
	}

	private static ModuleBase terrain() {// modified version of http://libnoise.sourceforge.net/tutorials/tutorial5.html
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
			land1.setSeed((int) (Math.random() * Integer.MAX_VALUE));
			land1.setFrequency(5);
			land1.setPersistence(.45);
			land1.setLacunarity(2.5);
			land1.setOctaveCount(6);

			land2 = new Billow();
			land2.setNoiseQuality(libnoiseforjava.NoiseGen.NoiseQuality.QUALITY_BEST);
			land2.setSeed((int) (Math.random() * Integer.MAX_VALUE));
			land2.setFrequency(5);
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
			mountainTerrain.setSeed((int) (Math.random() * Integer.MAX_VALUE));
			mountainTerrain.setFrequency(25);

			ScaleBias mountainScale = new ScaleBias(mountainTerrain);
			mountainScale.setScale(.25);
			mountainScale.setBias(.65);


			baseFlatTerrain = new Billow();
			baseFlatTerrain.setNoiseQuality(libnoiseforjava.NoiseGen.NoiseQuality.QUALITY_BEST);
			baseFlatTerrain.setSeed((int) (Math.random() * Integer.MAX_VALUE));
			baseFlatTerrain.setFrequency(15);

			flatTerrain = new ScaleBias(baseFlatTerrain);
			flatTerrain.setScale(0.125);
			flatTerrain.setBias(.5);

			terrainType = new Perlin();
			terrainType.setNoiseQuality(libnoiseforjava.NoiseGen.NoiseQuality.QUALITY_BEST);
			terrainType.setSeed((int) (Math.random() * Integer.MAX_VALUE));
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
		} catch(ExceptionInvalidParam exceptionInvalidParam) {
			exceptionInvalidParam.printStackTrace();
		}
		return null;
	}

	public enum TextureNoise {
		SCOUT, DESTROYER, CRUISER, BATTLESHIP, PLANET,
	}
}
