package eliascregard.game;

import eliascregard.graphics.ObjectRenderer;
import eliascregard.graphics.Texture;
import eliascregard.util.Vector2;

import static eliascregard.main.Settings.*;
import static eliascregard.util.Global.*;
import static org.lwjgl.opengl.GL11.*;

public class Game {

    private final Map map;
    private final Player player;
    private final RayCaster rayCaster;
    private final ObjectRenderer objectRenderer;
    private final Texture skyTexture;

    public Game(Map map) {
        this.map = map;
        this.player = new Player(this);
        this.objectRenderer = new ObjectRenderer(this);
        this.rayCaster = new RayCaster(this);
        this.skyTexture = new Texture(RES_FOLDER + "textures/sky.png");
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
        skyTexture.bind();
        double directionOffset = player.getAngle() / Math.PI;
        double x2 = 1;
        if (directionOffset > 1) {
            x2 = -3;
        }
        renderTexturedQuad(1 - 2 * directionOffset, 1, 2, 1);
        renderTexturedQuad(-(x2 + 2 * directionOffset), 1, 2, 1);

        objectRenderer.draw();
    }
}
