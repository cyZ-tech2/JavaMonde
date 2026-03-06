package games.environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

public class Map {
    private char[][] grid;
    private int height;
    private int width;
    public int credit;
    public String levelPath;

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

    public void loadLevelFromFile() throws IOException {
        Path path = Paths.get(this.levelPath);
        List<String> lines = Files.readAllLines(path);

        if (lines.isEmpty()) throw new IOException("Fichier vide");

        int h = lines.size();
        int w = 0;
        for (String line : lines) {
            if (line.length() > w) w = line.length();
        }

        Map map = new Map(w, h);
        this.credit = 0;
        boolean playerFound = false;

        for (int y = 0; y < h; y++) {
            String line = lines.get(y);
            for (int x = 0; x < w; x++) {
                char c = ' ';
                if (x < line.length()) {
                    c = line.charAt(x);
                }

                if (c == '1') {
                    this.map.setCell(x, y, ' ');
                    placePlayer(this.player, x, y);
                    this.map.firstX = x;
                    this.map.firstY = y;
                    playerFound = true;
                } else {
                    this.map.setCell(x, y, c);
                    if (c == '.') {
                        this.map.credit++;
                    }
                }
            }
        }

        if (!playerFound) {
            spawnPlayerRandomly();
        }

        if (this.map.credit == 0) {
            System.out.println("ATTENTION : Aucun crédit (.) trouvé dans le fichier !");
        }
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
    }

    private void spawnPlayerRandomly() {
        Random r = new Random();
        while (true) {
            int rx = r.nextInt(map.getWidth());
            int ry = r.nextInt(map.getHeight());
            char c = map.getCell(rx, ry);
            if (c != '#' && c != '*' && c != '.') {
                placePlayer(this.player, rx, ry);
                this.map.firstX = rx;
                this.map.firstY = ry;
                break;
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