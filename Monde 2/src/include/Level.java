package include;

public class Level {
    private char[][] grid;
    private int height;
    private int width;
    public Player player; // Note: En POO stricte, cela devrait être private avec un getter, mais je garde votre style public
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
        this.grid = new char[width][height];
        createLevel();
    }

    private void createLevel() {
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

    public void placePlayer(Player j, int x, int y) {
        if (j == null) {
            throw new IllegalArgumentException("Le joueur n'a pas de valeur initialisé correct ");
        }
        if (x < 0 || x >= width || y < 0 || y >= height) {
            throw new IllegalArgumentException("Erreur : Position (" + x + "," + y + ") hors des limites du niveau.");
        }
        if (grid[x][y] == '#') {
            throw new IllegalArgumentException("Erreur : Impossible de placer le joueur sur un mur en (" + x + "," + y + ").");
        }

        // Si le joueur était déjà placé ailleurs, on efface sa trace
        if (this.player != null) {
            grid[this.playerX][this.playerY] = ' ';
        }

        this.player = j;
        this.playerX = x;
        this.playerY = y;
        grid[x][y] = '1';
    }

    /**
     * Tente de déplacer le joueur dans une direction donnée.
     * Gère les collisions (Murs).
     */
    public void movePlayer(String directionInput) {
        int newX = playerX;
        int newY = playerY;

        switch (directionInput.toLowerCase()) { // Gestion minuscule/majuscule
            case "z":  // Haut
                newY--;
                break;
            case "s":  // Bas
                newY++;
                break;
            case "d":  // Droite
                newX++;
                break;
            case "q":  // Gauche
                newX--;
                break;
            default:
                System.out.println("Direction inconnue (utilisez z,q,s,d)");
                return;
        }

        // Vérification des limites et des murs avant de bouger
        if (newX >= 0 && newX < width && newY >= 0 && newY < height) {
            // MODIFICATION : Accès grid[newX][newY]
            if (grid[newX][newY] != '#') {
                // On utilise placePlayer pour faire le déplacement proprement
                placePlayer(this.player, newX, newY);
            } else {
                System.out.println("Impossible : Il y a un mur !");
            }
        } else {
            System.out.println("Impossible : Hors limites !");
        }
    }

    public void displayMap() {
        System.out.println("=== Niveau (" + width + "x" + height + ") ===");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(grid[j][i]);
            }
            System.out.println();
        }

        if (player != null) {
            System.out.println("INFO : " + player.getName() + " (Score: " + player.getScore() + ") est en position (" + playerX + ", " + playerY + ")");
        } else {
            System.out.println("INFO : Aucun joueur dans ce niveau.");
        }
    }
}