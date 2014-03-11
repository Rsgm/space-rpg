package spacegame.gui;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import spacegame.game.GamePlay;
import spacegame.game.Ship;

public class SpaceInput extends CameraInputController {
    private final GamePlay gamePlay;
    private final float turnSpeed = 0.1f;

    private boolean wPressed = false;
    private boolean sPressed = false;
    private boolean aPressed = false;
    private boolean dPressed = false;
    private boolean qPressed = false;
    private boolean ePressed = false;

    public SpaceInput(Camera camera, GamePlay gamePlay) {
        super(camera);
        this.gamePlay = gamePlay;

        translateUnits = 0;
        pinchZoomFactor = 0;
        translateTarget = false;
        forwardTarget = false;

        scrollFactor = -100;

        target = gamePlay.getPlayer().getShip().getPosition();
        camera.position.set(target.cpy().add(10, 0, 10));
        camera.lookAt(target);
    }

    @Override
    public boolean scrolled(int amount) {
        return zoom(amount * scrollFactor);
    }

    @Override
    public void update() {
        if (wPressed || sPressed || aPressed || dPressed || qPressed || ePressed) {
            Ship ship = gamePlay.getPlayer().getShip();
            if (wPressed) {
                ship.pitch(-turnSpeed);
            }
            if (sPressed) {
                ship.yaw(turnSpeed);
            }
            if (aPressed) {
                ship.yaw(-turnSpeed);
            }
            if (dPressed) {
                ship.yaw(turnSpeed);
            }
            if (qPressed) {
                ship.roll(-turnSpeed);
            }
            if (ePressed) {
                ship.roll(turnSpeed);
            }
            if (autoUpdate) {
                camera.update();
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                wPressed = true;
                break;
            case Input.Keys.S:
                sPressed = true;
                break;

            case Input.Keys.A:
                aPressed = true;
                break;
            case Input.Keys.D:
                dPressed = true;
                break;

            case Input.Keys.Q:
                qPressed = true;
                break;
            case Input.Keys.E:
                ePressed = true;
                break;
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.W:
                wPressed = false;
                break;
            case Input.Keys.S:
                sPressed = false;
                break;

            case Input.Keys.A:
                aPressed = false;
                break;
            case Input.Keys.D:
                dPressed = false;
                break;

            case Input.Keys.Q:
                qPressed = false;
                break;
            case Input.Keys.E:
                ePressed = false;
                break;
        }
        return true;
    }
}
