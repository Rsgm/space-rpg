package spacegame.game;

import com.badlogic.gdx.*;
import spacegame.gui.Assets;
import spacegame.gui.screens.SpaceScreen;

public class SpaceGame extends Game {
    private Preferences prefs;
    private Preferences save;

    private GamePlay gamePlay;
    public static Screen screen;

    @Override
    public void create() {
        Assets.load();

        prefs = Gdx.app.getPreferences("hakd-prefs");

        if (!prefs.getBoolean("played-before")) {
            newPrefs();
        }

        int width = prefs.getInteger("width");
        int height = prefs.getInteger("height");
        boolean fullscreen = prefs.getBoolean("fullscreen");
        boolean vsync = prefs.getBoolean("vsync");

        Gdx.graphics.setDisplayMode(width, height, fullscreen);
        Gdx.graphics.setVSync(vsync); // because no one needs to render 4000 frames per second, but then again it keeps the room warm

        Gdx.app.setLogLevel(prefs.getInteger("log-level")); // TODO save a copy of the console to a log file

        setScreen(new SpaceScreen(this));
    }

    private void newPrefs() {
    /*
     * the directory for these can't be changes, on linux it is /home/[user
	 * name]/.prefs/, on windows it is users/[user name]/.prefs/, I have not
	 * tested mac yet
	 */

        prefs.putBoolean("played-before", false);
        prefs.putInteger("width", 800);
        prefs.putInteger("height", 600);
        prefs.putBoolean("fullscreen", false);
        prefs.putBoolean("vsync", true);
        prefs.putBoolean("sound", true);
        prefs.putInteger("log-level", Application.LOG_DEBUG);

        prefs.flush();
        // unfortunately this makes this game non-portable, kind of

        // TODO ask user if they want the game to save anything, including settings and game save. Maybe ask in the settings and default to no saving
    }

    @Override
    public void dispose() {
        super.dispose();

        if (gamePlay != null) {
//            gamePlay.dispose();
        }
        Assets.dispose();
    }

    public GamePlay getGamePlay() {
        return gamePlay;
    }

    public void setGamePlay(GamePlay gamePlay) {
        this.gamePlay = gamePlay;
    }
}
