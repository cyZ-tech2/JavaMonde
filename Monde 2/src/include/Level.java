package include;

public class Level {
    private char[][] grid;
    private int height;
    private int width;
    public Player player;
    public int playerX;
    public int playerY;

    public enum Direction{
        HAUT,
        BAS,
        GAUCHE,
        DROITE;
    }

    public Level(int height, int width) {
        this.height = height;
        this.width = width;
        this.grid = new char[height][width];
        createLevel();
    }

    private void createLevel() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = ' ';
            }
        }
    }

    public void addWall(int x, int y) {
        if (x >= 0 && x < width && y >= 0 && y < height) {
            grid[y][x] = '#';
        } else {
            System.out.println("Coordonnées invalides : " + x + "," + y);
        }
    }

    public void addWallOutSide() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == 0 || i == height - 1 || j == 0 || j == width - 1) {
                    grid[i][j] = '#';
                }
            }
        }
    }

    public void placePlayer(Player j, int x, int y) {
        if (j == null) {
            throw new IllegalArgumentException("Le joueur n'a pas de valeur initialisé correct ");
        }
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Erreur : Position (" + x + "," + y + ") hors des limites du niveau.");
        }
        if (grid[y][x] == '#') {
            throw new IllegalArgumentException("Erreur : Impossible de placer le joueur sur un mur en (" + x + "," + y + ").");
        }
        this.player = j;
        this.playerX = x;
        this.playerY = y;
        grid[x][y] = '1';
    }


    public void displayMap() {
        System.out.println("=== Niveau (" + width + "x" + height + ") ===");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(grid[i][j]); // utilisation de print pour rester sur la ligne
            }
            System.out.println();
        }

        if (player != null) {
            System.out.println("INFO : " + player.getName() + " (Score: " + player.getScore() + ") est en position (" + playerX + ", " + playerY + ")");
        } else {
            System.out.println("INFO : Aucun player dans ce niveau.");
        }
    }

}