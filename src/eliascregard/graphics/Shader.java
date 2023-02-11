package eliascregard.graphics;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL20.*;

public class Shader {

    private final int programID;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    public Shader(String vertexShader, String fragmentShader) {
        programID = glCreateProgram();
        vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShaderID, readFile(vertexShader));
        glCompileShader(vertexShaderID);
        if (glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Could not compile vertex shader!");
            System.err.println(glGetShaderInfoLog(vertexShaderID, 500));
            System.exit(-1);
        }

        fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragmentShaderID, readFile(fragmentShader));
        glCompileShader(fragmentShaderID);
        if (glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Could not compile fragment shader!");
            System.err.println(glGetShaderInfoLog(fragmentShaderID, 500));
            System.exit(-1);
        }

        glAttachShader(programID, vertexShaderID);
        glAttachShader(programID, fragmentShaderID);

        glBindAttribLocation(programID, 0, "vertices");
        glBindAttribLocation(programID, 1, "textures");

        glLinkProgram(programID);
        if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE) {
            System.err.println("Could not link program!");
            System.err.println(glGetProgramInfoLog(programID, 500));
            System.exit(-1);
        }
        glValidateProgram(programID);
        if (glGetProgrami(programID, GL_VALIDATE_STATUS) == GL_FALSE) {
            System.err.println("Could not validate program!");
            System.err.println(glGetProgramInfoLog(programID, 500));
            System.exit(-1);
        }

    }

    public void setUniform(String name, int value) {
        int location = glGetUniformLocation(programID, name);
        if (location != -1) {
            glUniform1i(location, value);
        }
    }

    public void setUniform(String name, float value) {
        int location = glGetUniformLocation(programID, name);
        if (location != -1) {
            glUniform1f(location, value);
        }
    }

    public void setUniform(String name, Matrix4f value) {
        int location = glGetUniformLocation(programID, name);
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        if (location != -1) {
            glUniformMatrix4fv(location,false, buffer);
        }
    }

    public void bind() {
        glUseProgram(programID);
    }

    private String readFile(String file) {
        StringBuilder string = new StringBuilder();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                string.append(line).append("\n");
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return string.toString();
    }

}
