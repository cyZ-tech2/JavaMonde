package games.environment;

import games.character.Player;
import games.character.PlayerExtension;

import java.util.Scanner;
import java.util.Random;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;

public class Level {
    private Map map;
    public Player player;
    public int playerX;
    public int playerY;
    private int firstX;
    private int firstY;
    private int credit;
    private String levelPath;

    public Level() {
    }

    public void start(Scanner scanner, Player[] p, String path) {
        if (p == null || p.length < 1) {
            System.out.println("Erreur : Pas de joueurs");
            return;
        }
        this.levelPath = path;

        choosePlayer(scanner, p);

        boolean retry = true;
        while (retry) {
            try {
                loadLevelFromFile();
                resetPlayerState();
                retry = play(scanner);
            } catch (IOException e) {
                System.out.println("Erreur lecture fichier : " + e.getMessage());
                retry = false;
            }
        }
    }

    private void choosePlayer(Scanner scanner, Player[] p) {
        if (p.length > 1) {
            PlayerExtension.displayPlayers(p);
            System.out.println("Quel joueur voulez vous sélectionner (donner son numéro) :");
            int n = scanner.nextInt();
            scanner.nextLine();
            this.player = p[n - 1];
        } else {
            this.player = p[0];
        }
    }

    private void loadLevelFromFile() throws IOException {
        Path path = Paths.get(this.levelPath);
        List<String> lines = Files.readAllLines(path);

        if (lines.isEmpty()) throw new IOException("Fichier vide");

        int h = lines.size();
        int w = 0;
        for (String line : lines) {
            if (line.length() > w) w = line.length();
        }

        this.map = new Map(w, h);
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
                    this.firstX = x;
                    this.firstY = y;
                    playerFound = true;
                } else {
                    this.map.setCell(x, y, c);
                    if (c == '.') {
                        this.credit++;
                    }
                }
            }
        }

        if (!playerFound) {
            spawnPlayerRandomly();
        }

        if (this.credit == 0) {
            System.out.println("ATTENTION : Aucun crédit (.) trouvé dans le fichier !");
        }
    }

    private void resetPlayerState() {
        if (this.player != null) {
            this.player.setScore(0);
            this.player.setHp(5);
        }
    }

    private void spawnPlayerRandomly() {
        Random r = new Random();
        while (true) {
            int rx = r.nextInt(map.getWidth());
            int ry = r.nextInt(map.getHeight());
            char c = map.getCell(rx, ry);
            if (c != '#' && c != '*' && c != '.') {
                placePlayer(this.player, rx, ry);
                this.firstX = rx;
                this.firstY = ry;
                break;
            }
        }
    }

    private boolean play(Scanner scanner) {
        boolean running = true;
        displayMap();

        while (running) {
            System.out.println("Déplacement (Z: Haut, S: Bas, Q: Gauche, D: Droite) ou 'exit' pour quitter :");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Retour au menu...");
                return false;
            }

            int status = movePlayer(input);

            if (status == 1) {
                displayMap();
                System.out.println("NIVEAU TERMINE !");
                return false;
            } else if (status == 2) {
                displayMap();
                System.out.println("GAME OVER");
                System.out.println("Voulez-vous recommencer le niveau ? (1: Oui / 2: Non)");
                int choix = scanner.nextInt();
                scanner.nextLine();

                if (choix == 1) {
                    System.out.println("Rechargement du niveau...");
                    return true;
                } else {
                    return false;
                }
            }
            if (status == 0) {
                displayMap();
            }
        }
        return false;
    }

    public int movePlayer(String directionInput) {
        int newX = playerX;
        int newY = playerY;

        switch (directionInput.toLowerCase()) {
            case "z": newY--; break;
            case "s": newY++; break;
            case "d": newX++; break;
            case "q": newX--; break;
            default:
                System.out.println("Direction inconnue");
                return 0;
        }

        if (newX >= 0 && newX < map.getWidth() && newY >= 0 && newY < map.getHeight()) {
            char nextCell = map.getCell(newX, newY);

            if (nextCell != '#') {
                if (nextCell == '.') {
                    placePlayer(this.player, newX, newY);
                    player.addPoint(1);
                    credit--;
                    if (credit == 0) return 1;
                } else if (nextCell == '*') {
                    map.setCell(newX, newY, ' ');
                    player.removeHp(2);
                    placePlayer(this.player, firstX, firstY);
                    if (player.getHp() <= 0) return 2;
                } else {
                    placePlayer(this.player, newX, newY);
                }
            } else {
                System.out.println("Impossible : Mur !");
            }
        }
        return 0;
    }

    public void placePlayer(Player j, int x, int y) {
        if (this.player != null) map.setCell(this.playerX, this.playerY, ' ');
        this.player = j;
        this.playerX = x;
        this.playerY = y;
        map.setCell(x, y, '1');
    }

    public void displayMap() {
        System.out.println("=== Niveau (" + map.getWidth() + "x" + map.getHeight() + ") ===");
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                System.out.print(map.getCell(j, i));
            }
            System.out.println();
        }
        if (player != null) {
            System.out.println("INFO : " + player.getName() + " ( " + player.getHp() + " PV ) : Score (" + player.getScore() + ")");
        }
    }
}