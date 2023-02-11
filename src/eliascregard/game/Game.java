package eliascregard.game;

import eliascregard.graphics.ObjectRenderer;

public class Game {

    private final Map map;
    private final Player player;
    private final RayCaster rayCaster;
    private final ObjectRenderer objectRenderer;

    public Game(Map map) {
        this.map = map;
        this.player = new Player(this);
        this.objectRenderer = new ObjectRenderer(this);
        this.rayCaster = new RayCaster(this);
    }

    public void update(long window, double deltaTime) {
        player.update(window, deltaTime);
        rayCaster.update();
    }

    public Map getMap() {
        return map;
    }
    public Player getPlayer() {
        return player;
    }
    public Ray[] getRayCastingResults() {
        return rayCaster.getRayCastingResults();
    }
    public ObjectRenderer getObjectRenderer() {
        return objectRenderer;
    }
    public RayCaster getRayCasting() {
        return rayCaster;
    }

    public void draw() {

        objectRenderer.draw();
    }
}
