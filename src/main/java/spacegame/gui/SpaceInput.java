package spacegame.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import spacegame.game.GamePlay;
import spacegame.game.Ship;

public class SpaceInput extends CameraInputController {
    private final GamePlay gamePlay;
    private final float turnSpeed = 40f;
    private final float accelleration = 100f;

    private boolean wPressed;
    private boolean sPressed;
    private boolean aPressed;
    private boolean dPressed;
    private boolean qPressed;
    private boolean ePressed;
    private boolean shiftPressed;
    private boolean controlPressed;
    private boolean spacePressed;

    public SpaceInput(Camera camera, GamePlay gamePlay) {
        super(camera);
        this.gamePlay = gamePlay;

        translateUnits = 0;
        pinchZoomFactor = 0;
        translateTarget = false;
        forwardTarget = false;

        scrollFactor = -10;

        target = gamePlay.getPlayer().getShip().getPosition();
        camera.position.set(target);
        camera.lookAt(target);

//        zoom(-10);
    }

    @Override
    public boolean scrolled(int amount) {
        return zoom(amount * scrollFactor);
    }

    @Override
    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        target.add(gamePlay.getPlayer().getShip().getDeltaPosition());
        camera.position.add(gamePlay.getPlayer().getShip().getDeltaPosition());

        if (wPressed || sPressed || aPressed || dPressed || qPressed || ePressed || shiftPressed || controlPressed) {
            Ship ship = gamePlay.getPlayer().getShip();
            if (wPressed) {
                ship.pitch += turnSpeed * deltaTime;
            }
            if (sPressed) {
                ship.pitch += -turnSpeed * deltaTime;
            }
            if (aPressed) {
                ship.yaw += turnSpeed * deltaTime;
            }
            if (dPressed) {
                ship.yaw += -turnSpeed * deltaTime;
            }
            if (qPressed) {
                ship.roll += -turnSpeed * deltaTime;
            }
            if (ePressed) {
                ship.roll += turnSpeed * deltaTime;
            }
            if (shiftPressed) {
                ship.velocity += accelleration * deltaTime;
            }
            if (controlPressed) {
                ship.velocity -= accelleration * deltaTime;
            }
            if (spacePressed) {
                ship.velocity = 0;
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

            case Input.Keys.SPACE:
                spacePressed = true;
                break;
            case Input.Keys.SHIFT_LEFT:
                shiftPressed = true;
                break;
            case Input.Keys.CONTROL_LEFT:
                controlPressed = true;
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

            case Input.Keys.SPACE:
                spacePressed = false;
                break;
            case Input.Keys.SHIFT_LEFT:
                shiftPressed = false;
                break;
            case Input.Keys.CONTROL_LEFT:
                controlPressed = false;
                break;
        }
        return true;
    }
}
