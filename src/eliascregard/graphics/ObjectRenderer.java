package eliascregard.graphics;

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
    private final Texture[] wallTextures;
    private final Shader shader;


    public ObjectRenderer(Game game) {
        this.game = game;
        wallTextures = loadWallTextures();
        shader = new Shader(
                RES_FOLDER + "shaders/game_shader_vs.glsl",
                RES_FOLDER + "shaders/game_shader_fs.glsl"
        );
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
                    ray.offset() * (HALF_WIDTH - SCALE), 0
            );
            double textureWidth = SCALE / SCREEN_SIZE.width;
            double textureHeight = 1;
//            if (ray.projectionHeight() >= SCREEN_SIZE.height) {
//                position.y = 1;
//                width = SCALE / HALF_WIDTH;
//                height = 2;
//
//                textureHeight = (2 / (ray.projectionHeight() / HALF_HEIGHT));
//                textureHeight = Math.min(Math.max(textureHeight, 0), 1);
//                texturePosition.set(-texturePosition.x, -textureHeight / 2);
//                textureWidth = -SCALE / HALF_WIDTH;
//            }
            wallTextures[ray.texture()-1].bind();

            glBegin(GL_QUADS);
                glTexCoord2d(texturePosition.x, texturePosition.y);
                glVertex2d(position.x, position.y);

                glTexCoord2d(texturePosition.x + textureWidth, texturePosition.y);
                glVertex2d(position.x + width, position.y);

                glTexCoord2d(texturePosition.x + textureWidth, texturePosition.y + textureHeight);
                glVertex2d(position.x + width, position.y - height);

                glTexCoord2d(texturePosition.x, texturePosition.y + textureHeight);
                glVertex2d(position.x, position.y - height);
            glEnd();
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

    public Texture[] getWallTextures() {
        return wallTextures;
    }

    private static Texture[] loadWallTextures() {
        Texture[] textures = new Texture[]{
                new Texture(RES_FOLDER + "textures/1.png"),
                new Texture(RES_FOLDER + "textures/2.png"),
                new Texture(RES_FOLDER + "textures/3.png"),
                new Texture(RES_FOLDER + "textures/4.png"),
                new Texture(RES_FOLDER + "textures/5.png")
        };
        return textures;
    }

}
