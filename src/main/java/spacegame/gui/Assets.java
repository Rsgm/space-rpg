package spacegame.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Disposable;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;

public class Assets {
    public static Skin skin;
    public static HashMap<Shader, ShaderProgram> shaders;

    public static void load() {
        skin = new Skin(Gdx.files.internal("skins/uiskin.json"));

        skin.getAtlas().findRegion("default").getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        generateShaders();
    }

    private static void generateShaders() {
        shaders = new HashMap<Shader, ShaderProgram>();

        for (Shader s : Shader.values()) {
            if (s != Shader.GDX_DEFAULT) {
                FileHandle vert = Gdx.files.internal("shaders/" + s.name().toLowerCase() + ".vert");
                FileHandle frag = Gdx.files.internal("shaders/" + s.name().toLowerCase() + ".frag");
                ShaderProgram shader = new ShaderProgram(vert, frag);
                shaders.put(s, shader);

                if (shader.getLog().length() != 0) {
                    Gdx.app.log("Shader Log", shader.getLog());
                }
            }
        }

        shaders.put(Shader.GDX_DEFAULT, SpriteBatch.createDefaultShader());
    }

    public static void dispose() {
        Field[] Fields = Assets.class.getFields();
        for (Field f : Fields) {
            if (Arrays.asList(f.getType().getGenericInterfaces()).contains(Disposable.class)) {
                try {
                    ((Disposable) f.get(Disposable.class)).dispose();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        for (ShaderProgram s : shaders.values()) {
            s.dispose();
        }
    }

    public enum Shader {
        GDX_DEFAULT, DEFAULT
    }
}
