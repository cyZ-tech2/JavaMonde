package games.environment;

import java.util.Random;

public class Map {
    private char[][] grid;
    private int height;
    private int width;

    public Map(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new char[width][height];
    }

    public void initEmpty() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[j][i] = ' ';
            }
        }
    }

    public void addWall(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[x][y] = '#';
        } else {
            System.out.println("Coordonnées invalides : " + x + "," + y);
        }
    }

    public void addWallOutSide() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    grid[j][i] = '#';
                }
            }
        }
    }

    public char getCell(int x, int y) {
        return grid[x][y];
    }

    public void setCell(int x, int y, char c) {
        grid[x][y] = c;
    }

    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
}