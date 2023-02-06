package eliascregard.rendering;

import org.lwjgl.BufferUtils;

import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;

public class Model {
    private final int drawCount;
    private final int verticesId;
    private final int textureId;
    private final int indexId;

    public Model(double[] vertices, double[] textureCoords, int[] indices) {
        drawCount = indices.length;

        verticesId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, verticesId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(vertices), GL_STATIC_DRAW);

        textureId = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, textureId);
        glBufferData(GL_ARRAY_BUFFER, createBuffer(textureCoords), GL_STATIC_DRAW);

        indexId = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
        IntBuffer buffer = BufferUtils.createIntBuffer(indices.length);
        buffer.put(indices);
        buffer.flip();
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void render() {

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);

        glBindBuffer(GL_ARRAY_BUFFER, verticesId);
        glVertexPointer(2, GL_DOUBLE, 0, 0);

        glBindBuffer(GL_ARRAY_BUFFER, textureId);
        glTexCoordPointer(2, GL_DOUBLE, 0, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indexId);
        glDrawElements(GL_TRIANGLES, drawCount, GL_UNSIGNED_INT, 0);

        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        glDisableClientState(GL_VERTEX_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
    }

    private DoubleBuffer createBuffer(double[] data) {
        DoubleBuffer buffer = BufferUtils.createDoubleBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

}
