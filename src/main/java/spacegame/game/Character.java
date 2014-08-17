package spacegame.game;

import com.badlogic.gdx.math.Vector3;

public class Character {
    private final Ship ship;


    public Character(Vector3 pos) {
        this.ship = new Ship(pos);
    }

    public void update(int deltaTime) {
        ship.update(deltaTime);
    }

    public Ship getShip() {
        return ship;
    }
}
