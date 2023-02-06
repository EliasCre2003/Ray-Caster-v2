package eliascregard.main;

import eliascregard.util.Vector2;

import java.awt.*;

public class Settings {
    public static final Dimension SCREEN_SIZE = new Dimension(1920, 1080);
    public static final int HALF_WIDTH = SCREEN_SIZE.width / 2;
    public static final int HALF_HEIGHT = SCREEN_SIZE.height / 2;
    public static final int FPS = 200;
    public static final int TICKSPEED = 0;
    public static final double FOV = Math.toRadians(60);
    public static final double HALF_FOV = FOV / 2;
    public static final int NUM_RAYS = 500;
    public static final int HALF_NUM_RAYS = NUM_RAYS / 2;
    public static final double SCALE = (double) SCREEN_SIZE.width / NUM_RAYS;
    public static final double DELTA_ANGLE = FOV / NUM_RAYS;
    public static final int MAX_DEPTH = 200;
    public static final Vector2 PLAYER_START_POSITION = new Vector2(1, 5);
    public static final double PLAYER_START_ANGLE = Math.toRadians(0);
    public static final double PLAYER_SPEED = 2;
    public static final double PLAYER_ROTATION_SPEED = Math.toRadians(180);
    public static final double SCREEN_DISTANCE = HALF_WIDTH / Math.tan(HALF_FOV);
    public static final int TEXTURE_SIZE = 256;
    public static final int HALF_TEXTURE_SIZE = TEXTURE_SIZE / 2;

}
