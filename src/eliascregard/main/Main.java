package eliascregard.main;
import static eliascregard.main.Settings.*;

import eliascregard.game.Game;
import eliascregard.game.Map;
import eliascregard.graphics.Model;
import eliascregard.graphics.Shader;

import eliascregard.graphics.Texture;
import org.joml.Matrix4f;
import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {
    private long window;

    public void run() {
        System.out.println("LWJGL version: " + Version.getVersion());

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();

    }

    private void init() {
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_TRUE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
        window = glfwCreateWindow(SCREEN_SIZE.width, SCREEN_SIZE.height, "Ray Caster", NULL, NULL);
        if (window == NULL) {
            throw new RuntimeException("Failed to create the GLFW window");
        }
        System.out.println(window);

        // Set up a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
            }
        });

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                window,
                (vidmode.width() - pWidth.get(0)) / 2,
                (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);

        Game game = new Game(new Map(
                new int[][]{
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                        {1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 1},
                        {1, 0, 0, 4, 0, 0, 0, 1, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 2, 0, 2, 0, 0, 0, 0},
                        {1, 0, 2, 0, 0, 0, 0, 2, 0, 0, 0, 1},
                        {1, 2, 2, 2, 0, 2, 2, 2, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                        {1, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1},
                        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
                }
        ));

        glClearColor(0.2f, 0.2f, 0.2f, 0.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        double deltaTime = 0;
        int totalFrames = 0;
        long averageFps = 0;
        while (!glfwWindowShouldClose(window)) {
            long startTime = System.nanoTime();

            game.update(window, deltaTime);

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            game.draw();

            glfwSwapBuffers(window); // swap the color buffers

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();


            deltaTime = (System.nanoTime() - startTime) / 1_000_000_000.0;
            int fps = (int) (1 / deltaTime);
//            System.out.println(fps + " fps");
            totalFrames++;
            averageFps += fps;
            System.out.println(game.getPlayer().getAngle());

        }
        System.out.println("Average fps: " + averageFps / totalFrames);
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
