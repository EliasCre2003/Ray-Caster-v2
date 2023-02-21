package eliascregard.util;

import static eliascregard.main.Settings.*;
import static org.lwjgl.opengl.GL11.*;

public class Global {

    public static Vector2 pixelCoordsToGLCoords(double x, double y) {
        return new Vector2(x / HALF_WIDTH - 1, 1 - y / HALF_HEIGHT);
    }

    public static Vector2 pixelCoordsToGLCoords(Vector2 pixelCoords) {
        return pixelCoordsToGLCoords(pixelCoords.x, pixelCoords.y);
    }

    public static void renderTexturedQuad(double x, double y, double width, double height) {
        glBegin(GL_QUADS);
        glTexCoord2d(0, 0);
        glVertex2d(x, y);
        glTexCoord2d(1, 0);
        glVertex2d(x + width, y);
        glTexCoord2d(1, 1);
        glVertex2d(x + width, y - height);
        glTexCoord2d(0, 1);
        glVertex2d(x, y - height);
        glEnd();
    }

}
