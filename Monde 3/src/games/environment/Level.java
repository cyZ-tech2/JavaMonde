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
    private Player player;

    public Level() {
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void start(Scanner scanner, Player[] p, String path) {
        if (p == null || p.length < 1) {
            System.out.println("Erreur : Pas de joueurs");
            return;
        }
        this.map.setLevelPath(path);

        choosePlayer(scanner, p);

        boolean retry = true;
        while (retry) {
            try {
                map.loadLevelFromFile();
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
            this.setPlayer(p[n - 1]);
        } else {
            this.setPlayer(p[0]);
        }
    }

    private void resetPlayerState() {
        if (this.player != null) {
            this.player.setScore(0);
            this.player.setHp(5);
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
        int newX = player.playerX;
        int newY = player.playerY;

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
                    this.map.credit--;
                    if (this.map.credit == 0) return 1;
                } else if (nextCell == '*') {
                    map.setCell(newX, newY, ' ');
                    player.removeHp(2);
                    placePlayer(this.player, player.firstX, player.firstY);
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
        if (this.player != null) map.setCell(this.player.playerX, this.player.playerY, ' ');
        this.player = j;
        this.player.playerX = x;
        this.player.playerY = y;
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