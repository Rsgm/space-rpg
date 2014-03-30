package spacegame;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import spacegame.game.SpaceGame;

import java.io.IOException;

public class Main {
    public static final SpaceGame game = new SpaceGame();

    public static void main(String[] args) throws IOException {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.resizable = false;
        config.useGL30 = true;
        config.samples = 8; // can go to 16 or 32 I think
        config.title = "space game";
        new LwjglApplication(game, config);
    }
}
