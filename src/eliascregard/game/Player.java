package eliascregard.game;

import eliascregard.util.Vector2;
import static eliascregard.main.Settings.*;
import static org.lwjgl.glfw.GLFW.*;

import java.awt.*;

public class Player {
    private final Vector2 position = PLAYER_START_POSITION;
    private final Point mapPosition = new Point((int) position.x, (int) position.y);
    private double angle = PLAYER_START_ANGLE;
    private final double speed = PLAYER_SPEED;
    private final double rotationSpeed = PLAYER_ROTATION_SPEED;
    private final Game game;

    public Player(Game game) {
        this.game = game;
    }

    public Vector2 getPosition() {
        return position;
    }
    public Point getMapPosition() {
        return mapPosition;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle % Math.TAU;
    }
    public void rotate(double deltaAngle) {
        angle = (angle + deltaAngle) % Math.TAU;
    }

    public void update(long window, double deltaTime) {
        Vector2 deltaPosition = new Vector2();
        double deltaAngle = 0;
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            deltaPosition.x += 1;
        }
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            deltaPosition.x -= 1;
        }
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            deltaPosition.y -= 1;
        }
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            deltaPosition.y += 1;
        }
        deltaPosition.normalize();
        if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS) {
            deltaAngle -= rotationSpeed * deltaTime;
        }
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS) {
            deltaAngle += rotationSpeed * deltaTime;
        }
        rotate(deltaAngle);
        move(deltaPosition, deltaTime);
    }

    public void move(Vector2 direction, double deltaTime) {
        direction.rotate(angle);
        Vector2 deltaPosition = direction.scaled(speed * deltaTime);
        position.add(deltaPosition);
        checkWallCollision(deltaPosition.x, deltaPosition.y);
        mapPosition.setLocation((int) position.x, (int) position.y);
    }

    private boolean checkWall(int x, int y) {
        return game.getMap().isWall(x, y);
    }

    private void checkWallCollision(double dx, double dy) {
        if (checkWall((int) (position.x + dx), (int) (position.y))) {
            position.x -= dx;
        }
        if (checkWall((int) (position.x), (int) (position.y + dy))) {
            position.y -= dy;
        }
    }



}
