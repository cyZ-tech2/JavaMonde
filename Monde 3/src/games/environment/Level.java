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

    public Level() {
    }


    public Level(Map map, Player player) {
        this.map = map;
        this.player = player;
        if (player != null) {
            this.playerX = 1;
            this.playerY = 1;
            this.firstX = 1;
            this.firstY = 1;
        }
    }

    public int getPosX(){
        return this.playerX;
    }

    public int getPosY(){
        return this.playerY;
    }

    public void createLevel(Scanner scanner, Player[] p) {
        if(p == null || p.length < 1){
            System.out.println("Erreur : Impossible de créer le niveau car il y a zéro joueur");
            return;
        }
        if (p.length > 1) {
            PlayerExtension.displayPlayers(p);
            System.out.println("Quel joueur voulez vous sélectionner (donner son numéro) :");
            int n = scanner.nextInt();
            scanner.nextLine();
            this.player = p[n - 1];
        } else {
            this.player = p[0];
        }

        int w = 0;
        int h = 0;
        do {
            System.out.print("Donner la taille de la hauteur du niveau : ");
            w = scanner.nextInt();
            scanner.nextLine();
            System.out.print("Donner la taille de la largeur du niveau : ");
            h = scanner.nextInt();
            scanner.nextLine();
            if(w <= 0 || h <= 0){
                System.out.println("ERREUR : Valeurs incorrectes");
            }
        }
        while (w <= 0 || h <= 0);

        this.map = new Map(w, h);
        this.map.initEmpty();
        this.map.addWallOutSide();
        System.out.println(getNbCredit());
        Random r = new Random();
        while (true) {
            try {
                int rx = 1 + r.nextInt(w - 1);
                int ry = 1 + r.nextInt(h - 1);
                if (map.getCell(rx, ry) != '#') {
                    placePlayer(this.player, rx, ry);
                    this.firstX = rx;
                    this.firstY = ry;
                    break;
                }
            } catch (Exception e) {

            }
        }
    }

    private void spawnPlayerRandomly() {
        Random r = new Random();
        int x, y;

        do {
            x = r.nextInt(map.getWidth());
            y = r.nextInt(map.getHeight());
        } while (map.getCell(x, y) == '#');

        this.playerX = x;
        this.playerY = y;
        this.firstX = x;
        this.firstY = y;
    }

    public int nbCredit(){
        Random r = new Random();
        return r.nextInt(10) + 1;
    }

    public int getNbCredit(){
        return this.credit;
    }

    public void placeCredit(){
        this.credit = nbCredit();
        int x1, y1;
        for(int i = 0; i < this.credit; i++) {

            do {
                Random a = new Random();
                x1 = a.nextInt(map.getWidth());
                y1 = a.nextInt(map.getHeight());
            } while (x1 <= 0 || x1 >= map.getWidth() || y1 <= 0 || y1 >= map.getHeight() || map.getCell(x1, y1) != ' ');

            map.setCell(x1, y1, '.');
        }
    }

    public void placeTrap(){
        int x2, y2;
        for(int i = 0; i < 5; i++) {

            do {
                Random t = new Random();
                x2 = t.nextInt(map.getWidth());
                y2 = t.nextInt(map.getHeight());
            } while (x2 <= 0 || x2 >= map.getWidth() || y2 <= 0 || y2 >= map.getHeight() || map.getCell(x2, y2) != ' ');

            map.setCell(x2, y2, '*');
        }
    }

    public void placePlayer(Player j, int x, int y) {
        if (j == null) {
            throw new IllegalArgumentException("Le joueur n'a pas de valeur initialisé correct ");
        }
        if (x <= 0 || x >= map.getWidth() || y <= 0 || y >= map.getHeight()) {
            throw new IllegalArgumentException("Erreur : Position (" + x + "," + y + ") hors des limites");
        }
        if (map.getCell(x, y) == '#') {
            throw new IllegalArgumentException("Erreur : Impossible de placer le joueur sur un mur");
        }
        if (this.player != null) {
            map.setCell(this.playerX, this.playerY, ' ');
        }

        this.player = j;
        this.playerX = x;
        this.playerY = y;
        map.setCell(x, y, '1');
    }

    public void movePlayer(String directionInput) {

        int newX = playerX;
        int newY = playerY;


        switch (directionInput.toLowerCase()) {
            case "z":
                newY--;
                break;
            case "s":
                newY++;
                break;
            case "d":
                newX++;
                break;
            case "q":
                newX--;
                break;
            default:
                System.out.println("Direction inconnue (utilisez z,q,s,d)");
                return;
        }

        if (newX >= 0 && newX < map.getWidth() && newY >= 0 && newY < map.getHeight()) {
            char nextCell = map.getCell(newX, newY);

            if (nextCell == ' ') {
                placePlayer(this.player, newX, newY);
            } else if (nextCell == '.' || nextCell == '*') {
                if (nextCell == '.') {
                    placePlayer(this.player, newX, newY);
                    player.addPoint(1);
                    credit--;
                    if (credit == 0) {
                        System.out.println("NIVEAU TERMINE");
                        System.out.println("INFO : " + player.getName() + " ( " + player.getHp() + " )" + " : Score (" + player.getScore() + ") est en position (" + playerX + ", " + playerY + ")");
                        System.exit(0);
                    }
                }
                if (nextCell == '*') {
                    map.setCell(newX, newY, ' ');
                    player.removeHp(2);
                    placePlayer(this.player, firstX, firstY);
                    if (player.getHp() <= 0) {
                        System.out.println("GAME OVER");
                        System.out.println("INFO : " + player.getName() + " ( " + player.getHp() + " )" + " : Score (" + player.getScore() + ") est en position (" + playerX + ", " + playerY + ")");
                    }
                }
            } else {
                System.out.println("Impossible : Il y a un mur !");
            }
        }
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
            System.out.println("INFO : " + player.getName() + " ( " + player.getHp() +" PV " +") " + " : Score (" + player.getScore() +") est en position (" + playerX + ", " + playerY + ")");
        }
    }

    public static Level loadFromFile(String pathString, Player p) throws IOException {
        Path path = Paths.get(pathString);

        List<String> lines = Files.readAllLines(path);

        if (lines.isEmpty()) {
            throw new IOException("Le fichier est vide");
        }

        int h = lines.size();
        int w = lines.get(0).length();

        Map loadedMap = new Map(w, h);

        for (int y = 0; y < h; y++) {
            String line = lines.get(y);
            for (int x = 0; x < w; x++) {
                if (x < line.length()) {
                    loadedMap.setCell(x, y, line.charAt(x));
                } else {
                    loadedMap.setCell(x, y, ' ');
                }
            }
        }

        return new Level(loadedMap, p);
    }

}