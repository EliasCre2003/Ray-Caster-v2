package eliascregard.util;

import static eliascregard.main.Settings.*;

public class Global {

    public static Vector2 pixelCoordsToGLCoords(double x, double y) {
        return new Vector2(x / HALF_WIDTH - 1, 1 - y / HALF_HEIGHT);
    }

    public static Vector2 pixelCoordsToGLCoords(Vector2 pixelCoords) {
        return pixelCoordsToGLCoords(pixelCoords.x, pixelCoords.y);
    }

}
