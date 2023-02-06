package eliascregard.game;

import eliascregard.game.Game;
import eliascregard.game.GameObject;
import eliascregard.rendering.Texture;
import org.w3c.dom.Text;

import static eliascregard.main.Settings.*;
import static org.lwjgl.opengl.GL11.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ObjectRenderer {

    private final Game game;
    private final HashMap<String, Texture> wallTextures;


    public ObjectRenderer(Game game) {
        this.game = game;
        wallTextures = loadWallTextures();
    }

    public void draw(long window) {
        renderGameObjects(window);
    }

    private void renderGameObjects(long window) {
//        for (GameObject gameObject : game.getRayCasting().getObjectsToRender()) {
//            BufferedImage wallColumn = gameObject.wallColumn();
//            if (wallColumn != null) {
//                g2.drawImage(wallColumn, (int) gameObject.position().x, (int) gameObject.position().y, null);
//            }
//        }
        Ray[] gameObjects = game.getRayCasting().getRayCastingResults();
        glBegin(GL_QUADS);
        for (int i = 0; i < gameObjects.length; i++) {
            Ray gameObject = gameObjects[i];
            double x = (double) i * SCALE / HALF_WIDTH - 1;
            double y = 1 - ((HALF_HEIGHT - gameObject.projectionHeight() / 2) / HALF_HEIGHT);
            double width = SCALE / HALF_WIDTH;
            double height = gameObject.projectionHeight() / HALF_HEIGHT - 1;

            glColor3f(1f,0,0);
            glVertex2d(x, y);
            glColor3f(0,1f,0);

            glVertex2d(x + width, y);
            glColor3f(0,0,1f);

            glVertex2d(x + width, y - height);
            glColor3f(1f,0,0);

            glVertex2d(x, y - height);
        }
        glEnd();
    }

    public static BufferedImage getTexture(String path, Dimension resolution) {
        BufferedImage image;
        Dimension size;
        try {
            image = ImageIO.read(new File(path));
            size = new Dimension(image.getWidth(), image.getHeight());
        } catch (IOException e) {
            throw new IllegalArgumentException("could not read image: " + path, e);
        }
        if (resolution.width == size.width && resolution.height == size.height) {
            return image;
        }
        BufferedImage resizedImage = new BufferedImage(resolution.width, resolution.height, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale((double) resolution.width / size.width, (double) resolution.height / size.height);
        AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        return scaleOp.filter(image, resizedImage);

    }
    public static BufferedImage getTexture(String path) {
        return getTexture(path, new Dimension(TEXTURE_SIZE, TEXTURE_SIZE));
    }

    public HashMap<String, Texture> getWallTextures() {
        return wallTextures;
    }

    private static HashMap<String, Texture> loadWallTextures() {
        HashMap<String, Texture> textures = new HashMap<>();
        textures.put("1", new Texture("src/eliascregard/res/textures/1.png"));
        textures.put("2", new Texture("src/eliascregard/res/textures/2.png"));
        textures.put("3", new Texture("src/eliascregard/res/textures/3.png"));
        textures.put("4", new Texture("src/eliascregard/res/textures/4.png"));
        textures.put("5", new Texture("src/eliascregard/res/textures/5.png"));
        return textures;
    }

}
