package spacegame.gui.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import spacegame.game.SpaceGame;
import spacegame.gui.Assets;

public abstract class DefaultScreen implements Screen {
    int width = Gdx.graphics.getWidth();
    int height = Gdx.graphics.getHeight();

    SpaceGame game;

    Camera cam;
    SpriteBatch batch;
    ShaderProgram gdxShader;

    public DefaultScreen(Game game) {
        this.game = (SpaceGame) game;

        batch = new SpriteBatch();
        gdxShader = Assets.shaders.get(Assets.Shader.GDX_DEFAULT);
        batch.setShader(gdxShader);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT | GL30.GL_DEPTH_BUFFER_BIT | (Gdx.graphics.getBufferFormat().coverageSampling ? GL30.GL_COVERAGE_BUFFER_BIT_NV : 0));
        cam.update();
        gdxShader.setUniformMatrix("u_projTrans", cam.combined);
    }

    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        batch.dispose();
        gdxShader.dispose();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = (SpaceGame) game;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public Camera getCam() {
        return cam;
    }

    public void setCam(OrthographicCamera cam) {
        this.cam = cam;
    }
}
