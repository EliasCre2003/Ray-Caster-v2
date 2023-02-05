package eliascregard.game;

import java.awt.*;

public class Map {

    private final Dimension size;
    private final int[][] map;
    public Map(int[][] map) {
        this.size = new Dimension(map.length, map[0].length);
        this.map = map;
    }

    public Map(int width, int height) {
        this.size = new Dimension(width, height);
        this.map = new int[width][height];
    }

    public int[][] getMap() {
        return this.map;
    }

    public int get(int x, int y) {
        return this.map[x][y];
    }

    public int get(Point p) {
        return get(p.x, p.y);
    }

    public boolean isWall(int x, int y) {
        if (x < 0 || x >= size.width || y < 0 || y >= size.height) {
            return false;
        }
        return map[x][y] >= 1;
    }
    public boolean isWall(Point p) {
        return isWall(p.x, p.y);
    }

    public void set(int x, int y, int value) {
        this.map[x][y] = value;
    }

    public int getWidth() {
        return this.size.width;
    }

    public int getHeight() {
        return this.size.height;
    }

}
