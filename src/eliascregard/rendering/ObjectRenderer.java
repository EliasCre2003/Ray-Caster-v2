package eliascregard.rendering;

import eliascregard.game.Game;
import eliascregard.game.Ray;
import eliascregard.util.Vector2;
import static eliascregard.util.Global.*;

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

    public void draw() {
        renderGameObjects();
    }

    private void renderGameObjects() {
        Ray[] rays = game.getRayCasting().getRayCastingResults();
        for (int i = 0; i < NUM_RAYS; i++) {
            Ray ray = rays[i];
            Vector2 position = pixelCoordsToGLCoords(
                    (double) i * SCALE,
                    HALF_HEIGHT - ray.projectionHeight() / 2);
            double width = SCALE / HALF_WIDTH;
            double height = ray.projectionHeight() / HALF_HEIGHT;

            Vector2 texturePosition = pixelCoordsToGLCoords(
                    ray.offset() * (HALF_HEIGHT - SCALE), 0
            );
            double textureWidth = SCALE / HALF_WIDTH;
            double textureHeight = 1;
            if (ray.projectionHeight() >= SCREEN_SIZE.height) {
                position.y = 1;
                width = SCALE / HALF_WIDTH;
                height = 2;

                textureHeight = (2 / (ray.projectionHeight() / HALF_HEIGHT));
                textureHeight = Math.min(Math.max(textureHeight, 0), 1);
                texturePosition.y = textureHeight / 2;
                System.out.println(texturePosition.y);
                textureWidth = SCALE / HALF_WIDTH;
            }

            Model model = new Model(
                    new double[]{
                            position.x, position.y,
                            position.x + width, position.y,
                            position.x + width, position.y - height,
                            position.x, position.y - height
                    },
                    new double[]{
                            texturePosition.x + textureWidth, texturePosition.y - textureHeight,
                            texturePosition.x, texturePosition.y - textureHeight,
                            texturePosition.x, texturePosition.y,
                            texturePosition.x + textureWidth, texturePosition.y
                    },
                    new int[]{
                            0, 1, 2,
                            2, 3, 0
                    }

            );
            wallTextures.get(String.valueOf(ray.texture())).bind();
            model.render();
        }
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
