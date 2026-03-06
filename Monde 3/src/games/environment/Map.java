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

    public void loadLevelFromFile(Level level) throws IOException {
        Path path = Paths.get(this.levelPath);
        List<String> lines = Files.readAllLines(path);

        if (lines.isEmpty()) throw new IOException("Fichier vide");

        int h = lines.size();
        int w = 0;
        for (String line : lines) {
            if (line.length() > w) w = line.length();
        }

        this.width = w;
        this.height = h;
        this.grid = new char[w][h];
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
                    this.setCell(x, y, ' ');
                    level.placePlayer(level.getPlayer(), x, y);
                    level.getPlayer().firstX = x;
                    level.getPlayer().firstY = y;
                    playerFound = true;
                } else {
                    this.setCell(x, y, c);
                    if (c == '.') {
                        this.credit++;
                    }
                }
            }
        }

        if (!playerFound) {
            spawnPlayerRandomly(level);
        }

        if (this.credit == 0) {
            System.out.println("ATTENTION : Aucun crédit (.) trouvé dans le fichier !");
        }
    }

    private void spawnPlayerRandomly(Level level) {
        Random r = new Random();
        while (true) {
            int rx = r.nextInt(this.width);
            int ry = r.nextInt(this.height);
            char c = getCell(rx, ry);

            if (c != '#' && c != '*' && c != '.') {
                level.placePlayer(level.getPlayer(), rx, ry);
                level.getPlayer().firstX = rx;
                level.getPlayer().firstY = ry;
                break;
            }
        }
    }

    public void setLevelPath(String levelPath) {
        this.levelPath = levelPath;
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