package eliascregard.graphics;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;

public class Texture {

    private int id;
    private int width;
    private int height;

    public Texture(String filename) {
        BufferedImage image;
        try {
            image = ImageIO.read(new File(filename));
            width = image.getWidth();
            height = image.getHeight();
            int[] pixelsRaw = image.getRGB(0, 0, width, height, null, 0, width);
            ByteBuffer pixels = BufferUtils.createByteBuffer(width * height * 4);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    int pixel = pixelsRaw[i * width + j];
                    pixels.put((byte) ((pixel >> 16) & 0xFF)); // RED
                    pixels.put((byte) ((pixel >> 8) & 0xFF));  // GREEN
                    pixels.put((byte) (pixel & 0xFF));         // BLUE
                    pixels.put((byte) ((pixel >> 24) & 0xFF)); // ALPHA
                }
            }
            pixels.flip();
            id = glGenTextures();
            System.out.println(id);
            glBindTexture(GL_TEXTURE_2D, id);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bind(int sampler) {
        if (sampler > 31 || sampler < 0)
            throw new IllegalArgumentException("Sampler must be between 0 and 31");
        glActiveTexture(GL_TEXTURE0 + sampler);
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    public int getId() {
        return id;
    }
}
