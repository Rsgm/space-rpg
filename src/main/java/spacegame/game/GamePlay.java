package spacegame.game;

import com.badlogic.gdx.math.Vector3;
import spacegame.gui.screens.SpaceScreen;

import java.util.ArrayList;
import java.util.List;

public class GamePlay {
    public static final int CORES = Runtime.getRuntime().availableProcessors();
    private final List<Thread> updateThreadSet = new ArrayList<Thread>();
    private final SpaceScreen screen;

    public boolean threadsActive = true;

    private final List<Character> characters = new ArrayList<Character>();
    private final List<Planet> planets = new ArrayList<Planet>();
    private final List<Station> stations = new ArrayList<Station>();
    private Character player;

    public GamePlay(SpaceScreen screen) {
        this.screen = screen;
        createUniverse();

        for (int i = 0; i < CORES; i++) {
            Thread t = new Thread(new UpdateThread());
            t.start();
            t.setName("Update Thread " + i);
            updateThreadSet.add(t);
        }
    }

    private void createUniverse() {
        int planetAmount = 1;//(int) (Math.random() * 1000 + 500);
//        int stationAmount = (int) (Math.random() * planetAmount / 2 + planetAmount / 2);
//        int characterAmount = (int) (Math.random() * 50 + 50);

        for (int i = 0; i < planetAmount; i++) {
            Vector3 pos = new Vector3();
            pos.x = (int) (Math.random() * 10000);
            pos.y = (int) (Math.random() * 10000);
            pos.z = (int) (Math.random() * 10000);
            Planet p = new Planet(pos);
            planets.add(p);
            screen.loadObject(p);

            Station s = new Station(pos.cpy().add(0, 0, p.radius * 1.25f));
            stations.add(s);
            screen.loadObject(s);
        }

        Station playerSpawn = stations.get((int) (Math.random() * stations.size()));
        player = new Character(playerSpawn.getPosition().add(50, 0, 50));
        screen.loadObject(player.getShip());
    }

    private class UpdateThread implements Runnable {
        long lastTime = System.currentTimeMillis();

        @Override
        public void run() {
            while (threadsActive) {
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                int deltaTime = (int) (System.currentTimeMillis() - lastTime); // this game will not be running for 60 years, so I wil cast it to an int
                lastTime = System.currentTimeMillis();

                for (int i = updateThreadSet.indexOf(Thread.currentThread()); i < characters.size(); i += updateThreadSet.size()) {
                    Character c = characters.get(i);
                    c.update(deltaTime);
                }
            }
        }
    }

    public Character getPlayer() {
        return player;
    }
}
