package spacegame.gui.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import spacegame.game.SpaceGame;
import spacegame.gui.Assets;

public class MenuScreen extends DefaultScreen {
    private final Stage stage;
    private final Table table;


    public MenuScreen(SpaceGame spaceGame) {
        super(spaceGame);

        cam = new OrthographicCamera();
        ((OrthographicCamera) cam).setToOrtho(false, width, height);
        cam.update();

        stage = new Stage();
        stage.getSpriteBatch().setShader(Assets.shaders.get(Assets.Shader.DEFAULT));
        table = new Table();
        stage.addActor(table);
        table.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        TextButton start = new TextButton("Start", Assets.skin);
        table.add(start);
        table.row();
        table.add(new TextButton("Settings", Assets.skin));
        table.row();
        table.add(new TextButton("Quit", Assets.skin));

        start.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

                game.setScreen(new SpaceScreen(game));
            }
        });
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        stage.setViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
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
}
